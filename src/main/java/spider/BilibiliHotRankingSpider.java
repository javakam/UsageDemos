package spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BilibiliHotRankingSpider {

    public static void main(String[] args) {
        String url = "https://www.bilibili.com/v/popular/rank/all";
        String html = sendGet(url);
        parseHtml(html);
    }

    /**
     * 发送GET请求获取HTML页面内容
     *
     * @param url 请求的URL地址
     * @return HTML页面内容
     */
    private static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:60.0) Gecko/20100101 Firefox/60.0");
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常：" + e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 解析HTML页面内容，提取热榜信息
     *
     * @param html HTML页面内容
     */
    private static void parseHtml(String html) {
//        System.out.println(html);
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        Pattern pattern = Pattern.compile("<li class=\"rank-item.*?<div class=\"content\">.*?<a href=\"(.*?)\".*?>(.*?)</a>.*?" +
//                "<div class=\"detail\">(.*?)</div>.*?<i class=\"b-icon play\"></i>(.*?)</span>.*?<i class=\"b-icon view\"></i>(.*?)</span>.*?</li>");

        Pattern pattern = Pattern.compile("<li class=\"rank-item.*?<div class=\"content\">.*?<a href=\"(.*?)\".*?>(.*?)</a>.*?</li>");
        Matcher matcher = pattern.matcher(html);
        System.out.println(matcher.matches());

        while (matcher.find()) {
            System.out.println("------------");
            String title = matcher.group(2);
            String link = "https:" + matcher.group(1);
//            String playCount = matcher.group(4);
//            String viewCount = matcher.group(5);
            System.out.println("标题：" + title);
            System.out.println("链接：" + link);
//            System.out.println("播放量：" + playCount);
//            System.out.println("观看量：" + viewCount);
            System.out.println();
        }
    }
}