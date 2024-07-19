package meeting;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static meeting.NetUtils.sendGet;
import static meeting.NetUtils.sendPost;

public class TecentMeeting {
    private static final String MEETING_DOMAIN_URL = "";

    /**
     * 创建预约腾讯会议
     *
     * @param subject   主题
     * @param startTime 开始时间戳
     * @param duration  持续时间(单位分钟)
     * @param userId    企业注册时的用户名
     * @return 创建结果
     */
    public static Map<String, Object> creatMeeting(String subject, Long startTime,
                                                   Integer duration, String userId) {
        //Unix时间戳,单位为秒
        startTime = startTime / 1000;
        String endTime = String.valueOf(startTime + (duration * 60));
        HashMap<String, Object> resultMap = new HashMap<>(8);
        //请求体,get方法请求体需传""
        String resultBody = "{" +
                //会议结束时间
                "\"end_time\": \"" + endTime + "\"" + "," +
                //会议开始时间戳（单位秒）。
                "\"start_time\": \"" + startTime + "\"," +
                //用户的终端设备类型 1：PC
                "\"instanceid\": " + "1" + "," +
                // 会议类型：0：预约会议 1：快速会议
                "\"type\": " + "0" + "," +
                //腾讯会议用户唯一标识 不能为1--9内的数字
                "\"userid\": \"" + userId + "\"," +
                //会议主题
                "\"subject\": \"" + getUnicode(subject) + "\"" +
                "}";

        //创建会议uri
        String uri = "/v1/meetings";
        String address = MEETING_DOMAIN_URL + uri;
        try {
            String jsonStr = sendPost(address, uri, resultBody);
            JsonObject jsonObject = JsonParser.parseString(jsonStr).getAsJsonObject();
            if (null == jsonObject.get("error_info")) {
                JsonObject meetingInfo = jsonObject.get("meeting_info_list").getAsJsonArray().get(0).getAsJsonObject();
                resultMap.put("success", "true");
                resultMap.put("message", "创建预约会议成功");
                resultMap.put("meetingId", meetingInfo.get("meeting_id").getAsString());
                resultMap.put("meetingCode", meetingInfo.get("meeting_code").getAsString());
                resultMap.put("joinUrl", meetingInfo.get("join_url").getAsString());
            } else {
                resultMap.put("success", "false");
                resultMap.put("message", "创建预约会议失败");
                resultMap.put("errorInfo", jsonObject.get("error_info").getAsString());
            }
        } catch (Exception e) {
            resultMap.put("success", "false");
            resultMap.put("message", e.getMessage());
            //log.error("创建预约会议发生异常", e);
        }
        return resultMap;
    }

    /**
     * 防止api请求中传入中文导致的报错问题
     *
     * @param s 中文字符串
     * @return Unicode码
     */
    public static String getUnicode(String s) {
        try {
            StringBuilder out = new StringBuilder();
            byte[] bytes = s.getBytes("unicode");
            for (int i = 0; i < bytes.length - 1; i += 2) {
                out.append("\\u");
                String str = Integer.toHexString(bytes[i + 1] & 0xff);
                for (int j = str.length(); j < 2; j++) {
                    out.append("0");
                }
                String str1 = Integer.toHexString(bytes[i] & 0xff);
                out.append(str1);
                out.append(str);
            }
            return out.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    //====================================================================================

    /**
     * 根据 meetingId 查询腾讯会议
     *
     * @param meetingId 创建腾讯会议生成的meetingId
     * @param userId    创建人的id
     * @return 查询结果
     */
    public static Map<String, Object> queryMeetings(String meetingId, String userId) {
        HashMap<String, Object> resultMap = new HashMap<>(8);
        try {
            String uri = "/v1/meetings/" + meetingId + "?userid=" + userId + "&instanceid=1";
//      https://api.meeting.qq.com/v1/meetings/{meetingId}?userid={userid}&instanceid={instanceid}
//      String address = url + "/" + meetingId;
            String address = MEETING_DOMAIN_URL + uri;
            String jsonStr = sendGet(address, uri);
            JsonObject jsonObject = JsonParser.parseString(jsonStr).getAsJsonObject();
            resultMap.put("message", jsonObject.get("error_info").getAsString());
        } catch (Exception e) {
            //log.error("获取会议信息异常", e);
            resultMap.put("success", "false");
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }

    //====================================================================================

    /**
     * 取消预约的会议
     *
     * @param userId    预定人id
     * @param meetingId 会议id
     * @return 取消结果
     */
    public static Map<String, Object> cancelMeeting(String userId, String meetingId) {
        HashMap<String, Object> resultMap = new HashMap<>(8);
        //https://api.meeting.qq.com/v1/meetings/{meetingId}/cancel
        String address;
        //创建会议uri
        String uri = "/v1/meetings/" + meetingId + "/cancel";
        address = MEETING_DOMAIN_URL + uri;
        try {
            //请求体,get方法请求体需传""
            String resultBody = "{\n"
                    + "     \"meetingId\" : \"" + meetingId + "\",\n"
                    + "     \"userid\" : \"" + userId + "\",\n"
                    + "     \"instanceid\" : 1,\n"
                    + "     \"reason_code\" : 1\n"
                    + "}";

            String jsonStr = sendPost(address, uri, resultBody);
            JsonObject jsonObject = JsonParser.parseString(jsonStr).getAsJsonObject();
            if (null == jsonObject.get("error_info")) {
                resultMap.put("message", "取消会议成功");
                resultMap.put("success", "true");
            } else {
                resultMap.put("message", "取消会议失败");
                resultMap.put("errorInfo", jsonObject.get("error_info").getAsString());
                resultMap.put("success", "false");

            }
        } catch (Exception e) {
            resultMap.put("success", "false");
            resultMap.put("message", e.getMessage());
            //log.error("取消会议异常:", e);
        }
        return resultMap;
    }

}