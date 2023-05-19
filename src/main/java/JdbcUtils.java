import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库常用操作工具类
 */
public final class JdbcUtils {
    // mysql8驱动
    public static final String MYSQL8_DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * 结果集处理器
     */
    public interface ResultSetHandler<T> {
        /**
         * 将一个结果集转换成T类型
         *
         * @param rs
         * @return
         */
        T handle(ResultSet rs);
    }

    /**
     * 列处理器
     */
    public interface ColumnHandler<T> {
        /**
         * @param method     根据列名自动匹配的方法名
         * @param columnName 列名
         * @param t          对象
         * @param value      值
         * @return 返回true，表示用户已处理完成，无需再处理，返回false，则代表用户不处理
         */
        boolean handleColumn(Method method, String columnName, T t, Object value);
    }

    /**
     * 内部类的目的，就是为了将结果集中的数据自动封装成对象
     */
    public static class BeanListHandler<T> implements ResultSetHandler<List<T>> {
        private final Class<T> clazz;
        private ColumnHandler<T> columnHandler;

        public BeanListHandler(Class<T> clazz) {
            this.clazz = clazz;
        }

        public BeanListHandler(Class<T> clazz, ColumnHandler<T> columnHandler) {
            this.clazz = clazz;
            this.columnHandler = columnHandler;
        }

        @Override
        public List<T> handle(ResultSet rs) {
            // 返回值
            List<T> list = new ArrayList<>();
            // 存储所有列名（别名）
            List<String> columnNames = new ArrayList<>();
            // 存储所有方法，键名是列名（别名），值即其对应的setter方法
            Map<String, Method> methodMap = new HashMap<>();

            // 获取所有列名
            try {
                // 结果集元数据
                ResultSetMetaData rsmd = rs.getMetaData();
                // 返回查询结果集的列数
                int count = rsmd.getColumnCount();
                // 返回此类型的所有方法
                Method[] methods = clazz.getDeclaredMethods();

                for (int i = 0; i < count; i++) {
                    // 获取列名，如果起别名，则获取别名
                    String columnName = rsmd.getColumnLabel(i + 1);
                    columnNames.add(columnName);// 返回查询结果集的列名

                    // 组装出对象的方法名
                    String methodName = columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                    methodName = "set" + methodName;

                    for (Method me : methods) {
                        if (me.getName().equals(methodName)) {
                            methodMap.put(columnName, me);// 设置到map中
                            break;
                        }
                    }
                }

                // 准备工作已完成，将结果集中的数据转换成T类型的实例
                if (rs != null) {
                    // 获取无参的构造方法
                    Constructor<T> con = clazz.getDeclaredConstructor();
                    while (rs.next()) {
                        T t = con.newInstance();// T类型的实例
                        for (int i = 0; i < count; i++) {
                            String columnName = columnNames.get(i);

                            // 从结果集中取出对应列的数据
                            Object value = rs.getObject(columnName);

                            // 取出方法
                            Method method = methodMap.get(columnName);
                            if (method != null) {
                                if (columnHandler != null) {
                                    boolean done = columnHandler.handleColumn(method, columnName, t, value);

                                    if (!done) {
                                        // 通过反射给T类型的实例赋值
                                        method.invoke(t, value);
                                    }
                                }
                            }
                        }
                        list.add(t);
                    }
                }
                return list;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 获取数据库连接
     *
     * @param url
     * @param user
     * @param password
     * @return
     */
    public static Connection getConnection(String driver, String url, String user, String password) {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("无法创建数据库连接");
    }

    /**
     * 获取数据库连接
     *
     * @param url
     * @param user
     * @param password
     * @return
     */
    public static Connection getConnection(String url, String user, String password) {
        return getConnection(MYSQL8_DRIVER, url, user, password);
    }

    /**
     * 执行查询操作，返回结果集
     *
     * @param conn
     * @param sql
     * @param args
     * @return
     */
    private static ResultSet query(Connection conn, String sql, Object[] args) {
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            if (args != null) {
                // 给PreparedStatement实例设置参数
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }
            }
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("查询出现异常");
    }

    /**
     * 返回对象的集合
     *
     * @param <T>
     * @param conn
     * @param sql
     * @param args
     * @return
     */
    public static <T> T query(Connection conn, ResultSetHandler<T> handler, String sql, Object[] args) {
        ResultSet rs = query(conn, sql, args);
        return handler.handle(rs);
    }

    /**
     * 写操作
     *
     * @return 返回受影响的行数
     */
    public static int update(Connection conn, String sql, Object[] args) {
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            if (args != null) {
                // 给PreparedStatement实例设置参数
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            // e.printStackTrace();
        }
        return -1;
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}