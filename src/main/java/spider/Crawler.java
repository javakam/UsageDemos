package spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Crawler {
    public static void main(String[] args) {
        String url = "https://news.cnblogs.com/n/digg";
        try {
            Document doc = Jsoup.connect(url).get();
            Elements news = doc.select("div.content div.news_block");
            for (Element item : news) {
                String title = item.select("h2.news_entry a").text();
                String link = item.select("h2.news_entry a").attr("href");
                String summary = item.select("div.news_summary").text();
                System.out.println(title + " : " + link);
                System.out.println(summary);
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}