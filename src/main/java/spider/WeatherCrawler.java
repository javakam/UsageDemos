package spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class WeatherCrawler {
    public static void main(String[] args) {
        String url = "http://www.weather.com.cn/textFC/hb.shtml"; // 全球天气预报页面
        try {
            Document doc = Jsoup.connect(url).get(); // 获取网页内容
            Elements items = doc.select(".conMidtab2"); // 获取所有天气预报信息
            for (Element item : items) {
                Elements trs = item.select("tr");
                for (int i = 2; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");
                    if (tds.size() > 0) {
                        String city = tds.get(0).text().trim(); // 城市
                        String weather = tds.get(5).text().trim(); // 天气
                        String temperature = tds.get(6).text().trim(); // 温度
                        System.out.println(city + " " + weather + " " + temperature);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}