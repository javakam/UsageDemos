//import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Test {
    /*
    SimpleDateFormat 是线程不安全的类，一般不要定义为static变量，如果定义为static，必须加锁，或者使用DateUtils工具类。
    说明：如果是JDK8的应用，可以使用instant代替Date，LocalDateTime代替Calendar，DateTimeFormatter代替SimpleDateFormat，
    官方给出的解释：simple beautiful strong immutable thread-safe。
     */
    private static final SimpleDateFormat SDF_SGT = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

    public static void main(String[] args) {
//        String savePath = "D:\\caiji\\xxx.html";//模板文件地址
//        createHtmlFile("xxxxxaaaajjj", savePath);

//        caijiDb();

//        String ss="pdf0000651678779861585ccstock.cn.2012";
//        System.out.println(crypt(ss));

        //time1();
        //parseData("2023-03-13 08:06:28");
        //testNetFail();
        //createTodayFile();


//        try {
//            int i = 5;
//            while (i > 3) {
//                i -= 1;
//                System.out.println("hello");
//                Thread.sleep(30);
//            }
//            System.out.println("111");
//            Thread.sleep(5000);
//            System.out.println("222");
//            Thread.sleep(3);
//            System.out.println("333");
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }


//        String str = "[ok],600631,success";
//        String[] array = str.split("\\,");
//        //int num = Integer.parseInt(array[0]);
//        System.out.println(array[1]);

        //checkCaijiDb();
        //caijiDb();

//        String filedate = "20230517203000";//20230517203000
////        filedate=filedate.substring(0,8);
////        System.out.println(filedate);
//        filedate = filedate.substring(0, 4) + "-" + filedate.substring(4, 6) + "-" + filedate.substring(6, 8);
//        System.out.println(filedate);
//
//        final LocalDate datePublish = LocalDate.parse(filedate);
//        System.out.println("日期: " + datePublish + "  一个月前 : " + datePublish.minusMonths(1) + "  31天前: " + datePublish.minusDays(31));
//        LocalDate START_DATE_CAIJI = LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
//        System.out.println("上个月一号:" + START_DATE_CAIJI.getYear() + "=" + START_DATE_CAIJI.getMonthValue() + "=" + START_DATE_CAIJI.getDayOfMonth());
//        if (datePublish.isBefore(START_DATE_CAIJI)) {
//            System.out.println("111");
//        } else {
//            System.out.println("222");
//        }

        System.out.println(isNight());

    }

    private static boolean isNight() {
        final LocalTime localTime = LocalTime.now().withNano(0).withSecond(0).withMinute(0);
        return localTime.equals(LocalTime.parse("08:00")) || localTime.equals(LocalTime.parse("10:00"));
    }

    private static final SimpleDateFormat SDF_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");

    //每天更新文件，并删除旧文件
    private static void createTodayFile() {
        String lastFileName = "sgt_20230316.xls";
        String[] split = lastFileName.split("\\_");
        final String dateLastDay = split[1].split("\\.")[0];
        System.out.println(dateLastDay);

        final String today = SDF_YYYYMMDD.format(new Date());
        System.out.println(today);
        String newFileName = "sgt_" + today + ".xls";
        System.out.println(newFileName);
        /*
        20230316
        20230317
        sgt_20230317.xls
        true
         */
        System.out.println(comparedDate(dateLastDay, today));//true,下载新xls

//        String date1 = "20230316";
//        String date2 = "20230317";
//        System.out.println(compared(date1, date1));//false
//        System.out.println(compared(date2, date2));//false
//        System.out.println(compared(date1, date2));//true,下载新xls
    }

    /**
     * 两时间比较大小
     *
     * @param setDate 日期参数格式
     * @param nowDate 日期参数格式 yyyyMMdd
     * @return true setDate < nowDate and default
     */
    private static boolean comparedDate(String setDate, String nowDate) {
        try {
            Date date1 = SDF_YYYYMMDD.parse(setDate);
            Date date2 = SDF_YYYYMMDD.parse(nowDate);
            if (date1.getTime() < date2.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
        }
        return true;
    }

    //HttpClient 测试网络连接失败
//    private static void testNetFail() {
//        new Thread(() -> {
//            try {
//                //final URL fileUrl = new URL(fileRemoteUrl + filePath);
//                String urlYes = "http://pdf.xinpi.zqrb.cn/20230312/686cb6b1-986c-4c79-99b5-b300b3dc54e6.pdf";
//                String urlNo = "http://pdf.xinpi.zqrb.cn/20230313/P020230120548605634609.pdf";
//                HttpGet request = new HttpGet(urlNo);
//                request.addHeader("Accept", "*/*");
//                request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0");
//                request.addHeader("X-Requested-With", "XMLHttpRequest");
//                request.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
//                request.setConfig(RequestConfig.custom()
//                        .setConnectTimeout(10000)
//                        .setConnectionRequestTimeout(10000)
//                        .setSocketTimeout(10000)
//                        .buildxxx());
//
//                final HttpResponse response = HttpClientBuilder.create().buildxxx().execute(request);
//                int statusCode = response.getStatusLine().getStatusCode();
//                boolean isSuccess = String.valueOf(statusCode).startsWith("20");
//                System.out.println("状态码:" + statusCode);
//                System.out.println("isSuccess:" + isSuccess);
//                if (!isSuccess) {
//                    System.out.println(dumpHttpResponseString(response, "gbk"));
//                }
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }).start();
//    }

    //HttpClient 打印返回结果
//    private static String dumpHttpResponseString(final HttpResponse httpResponse, final String charset) throws IOException {
//        final BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), charset));
//        final StringBuilder sb = new StringBuilder();
//        String line = "";
//        while ((line = reader.readLine()) != null) {
//            sb.append(line);
//        }
//        reader.close();
//        return sb.toString();
//    }

    //dateTime 是 2023-03-13 08:06:28  或  2023-03-13  -》 转换成毫秒、秒
    private static void parseData(String dateTime) {
        try {
            if (dateTime == null || dateTime.isEmpty()) {
                return;
            }
            int len = dateTime.length();
            SimpleDateFormat formatter;
            if (len > 10) {
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            } else {
                formatter = new SimpleDateFormat("yyyy-MM-dd");
            }
            Date date = formatter.parse(dateTime);
            System.out.println("毫秒=" + date.getTime() + ";秒=" + date.getTime() / 1000L);

            //
            Date date2 = new Date(date.getTime());
            Calendar gc = Calendar.getInstance();
            gc.setTime(date2);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String dateFormat = sdf.format(gc.getTime());
            System.out.println("再转回时间=" + dateFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void time1() {
        Date date = new Date(1677204125L * 1000L);
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);
        String dateFormat = SDF_SGT.format(gc.getTime());
        System.out.println(dateFormat);
    }

//    private static String crypt(String str) {
//        if (str == null || str.trim().length() == 0) {
//            return "";
//        }
//        return DigestUtils.md5Hex(str);
//    }

    public static boolean createHtmlFile(String content, String savePath) {
        if (content == null || content.isEmpty()) {
            return false;
        }
        try {
            File f = new File(savePath);
            BufferedWriter o = new BufferedWriter(new FileWriter(f));
            o.write(content);
            o.flush();
            o.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /*
    2023年5月19日 14:08:51 结果
    连接成功！1
    closed
     */
    private static void checkCaijiDb() {
        Connection conn = JdbcUtils.getConnection("jdbc:mysql://192.168.10.50:3306/java_kcb_caiji", "root", "123456");
        String sql = "select 1";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            final ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int val = rs.getInt(1);
                System.out.println("连接成功！" + val);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(preparedStatement);
            JdbcUtils.closeConnection(conn);
            System.out.println("closed");
        }
    }

    private static void caijiDb() {
        Connection conn = JdbcUtils.getConnection("jdbc:mysql://192.168.10.50:3306/java_kcb_caiji", "root", "123456");
        String sql = "select * from caiji";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            //preparedStatement.setString(1,);
            final ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString("symbol") + "   " + rs.getString("fileurl")
                        + "   " + rs.getString("filedate") + "   " + rs.getString("filetype"));
                System.out.println("连接成功！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(preparedStatement);
            JdbcUtils.closeConnection(conn);
        }
    }

}
