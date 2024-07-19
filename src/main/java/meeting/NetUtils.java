package meeting;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static meeting.SignatureUtil.sign;

public class NetUtils {
    public static final String secretId = "";
    public static final String secretKey = "";
    public static final String appId = "";
    public static final String sdkId = "";

    /**
     * 获取公共请求头
     *
     * @param httpMethod  请求方式：POST|GET
     * @param requestUri  请求uri
     * @param requestBody 请求体 GET方法请求体需传""
     * @return 拼接好的请求头
     */
    public static Map<String, String> getHeader(String httpMethod, String requestUri,
                                                String requestBody) {
        HashMap<String, String> header = new HashMap<>(8);
        //请求随机数
        String headerNonce = String.valueOf(new Random().nextInt(999999));
        //当前时间的UNIX时间戳
        String headerTimestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signature = null;
        try {
            signature = sign(secretId, secretKey, httpMethod, headerNonce, headerTimestamp, requestUri,
                    requestBody);
        } catch (Exception e) {
            //log.error("签名生成异常", e);
        }

        header.put("Content-Type", "application/json");
        header.put("X-TC-Key", secretId);
        header.put("X-TC-Timestamp", headerTimestamp);
        header.put("X-TC-Nonce", headerNonce);
        header.put("AppId", appId);
        header.put("X-TC-Version", "1.0");
        header.put("X-TC-Signature", signature);
        header.put("SdkId", sdkId);
        header.put("X-TC-Registered", "1");
        return header;
    }

    /**
     * 发起get请求
     * @param uri 	请求uri 生成签名使用
     * @param address 请求路径
     * @return 		请求结果的JsonStr
     */
    public static String sendGet(String address, String uri) {
        // 获取请求头
        Map<String, String> headers = getHeader("GET", uri, "");
        String result = "";
        // 创建 HttpClient 实例
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2) // 设置 HTTP 版本
                .connectTimeout(Duration.ofMillis(15000)) // 设置连接超时时间
                .build();
        // 创建 HttpRequest 对象
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address)) // 设置请求地址
                .header("User-Agent", "my-app") // 设置请求头（可选）
                .build();
        // 发送 HTTP GET 请求
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            if (statusCode == 200) {
                result = response.body();
            } else {
                //log.info("HTTP调用接口出错：" + address + " - " + statusCode);
            }
        } catch (Exception e) {
            //log.info("HTTP调用接口出错：" + e, e);
        }
        return result;
    }

    /**
     * 腾讯会议发送post请求，携带生产签名和公共请求头参数
     *
     * @param address     请求地址
     * @param uri         请求uri生产签名使用（这里主要用于生成签名，实际请求时可能不使用）
     * @param requestBody 请求参数
     * @return 请求响应结果
     */
    public static String sendPost(String address, String uri, String requestBody) {
        // 生成公共请求头参数和签名
        Map<String, String> headerMap = getHeaderTest("POST", uri, requestBody);
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8));
        // 添加请求头
        for (Map.Entry<String, String> header : headerMap.entrySet()) {
            requestBuilder.header(header.getKey(), header.getValue());
        }

        HttpRequest request = requestBuilder.build();
        String jsonStr = "";
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                jsonStr = response.body();
                //记录日志等操作
                //log.info("腾讯会议httpPost请求响应信息: {}", jsonStr);
            } else {
                //处理非200状态码
                //log.error("腾讯会议httpPost请求失败，状态码: {}", response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            //log.error("腾讯会议httpPost发送异常", e);
        }
        return jsonStr;
    }

    // 假设这是你的方法，用于生成请求头
    private static Map<String, String> getHeaderTest(String method, String uri, String requestBody) {
        // 实现你的逻辑来生成请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        // 在这里添加其他请求头，包括签名等
        return headers;
    }
}
