package spider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BilibiliHotListScraper {
    public static void main(String[] args) throws Exception {
        // 设置请求URL地址
        URL urlTarget = new URL("https://www.bilibili.com/v/popular/rank/all");
        HttpURLConnection connection = (HttpURLConnection) urlTarget.openConnection();
        connection.setRequestMethod("GET");

        // 获取请求数据
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        // 解析数据
        String html = content.toString();
        Pattern pattern = Pattern.compile("<li.*?rank-item.*?<div.*?title=\\\\\"(.*?)\\\\\".*?<a href=\\\\\"(.*?)\\\\\".*?<span.*?play\">(.*?)</span>.*?<span.*?dm\">(.*?)</span>.*?</li>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            String title = matcher.group(1);
            String url = matcher.group(2);
            String playCount = matcher.group(3);
            String danmakuCount = matcher.group(4);

            // 输出结果
            System.out.println(title);
            System.out.println(url);
            System.out.println(playCount);
            System.out.println(danmakuCount);
            System.out.println();
        }
    }
}
