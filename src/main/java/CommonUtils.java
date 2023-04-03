import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    public static void main(String[] args) {
        //字符串是否包含中文 Use precompile
        String str1 = "Hello, 你好，世界！";
        String str2 = "Hello,world！";//中文叹号
        String str3 = "Hello,world!";
        System.out.println(containsChinese(str1));
        System.out.println(containsChinese(str2));
        System.out.println(containsChinese(str3));


    }

    //字符串是否包含中文 Use precompile
    /*
    在Java中，同样可以使用Unicode字符集来匹配中文字符。以下是一些常用的Unicode字符集：
    1.匹配中文字符集：[\u4e00-\u9fa5]
    2.匹配中文标点符号：[\u3001-\u301e,\u3030-\u303d]
    3.匹配中文及中文标点符号：[\u4e00-\u9fa5\u3001-\u301e,\u3030-\u303d]
     */
    private static final Pattern CHINESE_PATTERN = Pattern.compile("[\u4e00-\u9fa5]");
    private static final Pattern CHINESE_PATTERN2 = Pattern.compile("[\u3001-\u301e,\u3030-\u303d]");
    private static final Pattern CHINESE_PATTERN3 = Pattern.compile("[\u4e00-\u9fa5\u3001-\u301e,\u3030-\u303d]");

    private static boolean containsChinese(String str) {
        Matcher matcher = CHINESE_PATTERN.matcher(str);
        return matcher.find();
    }

}