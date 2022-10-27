import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainStringRegex {

    //https://www.runoob.com/regexp/regexp-example.html
    public static void main(final String[] args) {
//        final GsonBuilder gsBuilder = new GsonBuilder().disableHtmlEscaping();
//        final Gson gs = gsBuilder.create();
//
//        final String testJson = "{\"title\":\"移动金融\",\"content\":\"xx\"x\"}";
//        final String testJson2 = "{\"title\":\"移动金融\",\"content\":\"xx\"x\",\"time\":\"123456\"}";
//        final String testJson3 = "{\"code\":200,\"msg\":\"获取信息成功！\",\"status\":\"1\",\"data\":{\"id\":233,\"title\":\"移动金融客户端应用软件拟备案名单公示\",\"content\":\"xx\"x\",\"collectiontime\":\"1647434888\",\"author\":\"\",\"sourceid\":4,\"sourcename\":\"中国互联网金融协会\",\"classid\":1,\"classname\":\"政策\",\"newstime\":\"2022年3月15日\"}}";
//        System.out.println(testJson);
//
////        String js = gs.toJson(testJson);
////        System.out.println(js);
////        System.out.println("------\n");
////        Article article2 = gs.fromJson(js, Article.class);
////        System.out.println(article2.getContent());
//
//        //https://c.runoob.com/front-end/854/
//        // "content":"(.*?)",
//        //String regex = "\"content\":\"(.*?)\"}";
//        String regex = "\"content\":\"(.*?)\"([,}])"; //testJson1 和 2 没问题
//        String regex3 = "\"code\":(.*?),.*?\"status\":\"(.*?)\",.*?\"title\":\"(.*?)\",.*?\"content\":\"(.*?)\",.*?\"author\":\"(.*?)\",.*?\"newstime\":\"(.*?)\"([,}])";
//        System.out.println(regex3);
//        System.out.println();
//        final Matcher m = Pattern.compile(regex3).matcher(testJson3);
//        System.out.println("结果1: " + "   " + m.matches() + "  " + m.groupCount());
////        int index = 1;
////        while (m.find()) {
////            String ret = m.group(index++);
////            System.out.println(ret + "\n");
////        }
//
//        int max = m.groupCount() - 1;
//        if (m.find()) {
//            System.out.println(m.group());
//            System.out.println(m.group(1));
//            System.out.println(m.group(2));
//            System.out.println(m.group(3));
//            System.out.println(m.group(4));
//            System.out.println(m.group(5));
//            System.out.println(m.group(6));
//            System.out.println(m.group(7));
//        }
//
//        System.out.println("------\n");
//
//        test1();
//        System.out.println("------\n");
//

        //test2();
        test3();
    }

    //https://www.cnblogs.com/longronglang/p/6414087.html
    private static void test1() {
        final String regex = "\"FIX_ACC\":\"(.*?)\",OP_FLAG";//别忘了使用非贪婪模式！
        String text = "[{CSTM_NAME:\"广公司\",FIX_GNL:\"111810158\",\"FIX_ACC\":\"D2013060\"70003\",OP_FLAG:\"正常\",BUSS_KIND_NAMEx:3}]";
        Matcher matcher = Pattern.compile(regex).matcher(text);
        System.out.println("结果2: " + matcher.matches());

        while (matcher.find()) {
            String ret = matcher.group(1);
            System.out.println(ret);
        }
    }

    //检查 a 标签中的 href 是否正确
    public static void test2() {
        String text = "<p>附件：<a href=\"/goutongjiaoliu/113456/113469/4523657/2022040208522329336.pdf\">2021年第四季度支付体系运行总体情况.pdf</a></p>";

        final String regex = "<a.*?href=\"(.*?)\".*?>";
        Matcher matcher = Pattern.compile(regex).matcher(text);
        System.out.println("结果: " + matcher.matches());

        while (matcher.find()) {
            String ret = matcher.group(1);
            System.out.println(ret);
        }
        System.out.println("------\n");

        //临时解决方案 把 href 改为 herf 让 WebView 识别不了
        final String text_no_href = text.replace("href", "herf");
        System.out.println(text_no_href);

    }

    private static void test3() {
        //文本太长会报错 -> java: 常量字符串过长
        String text = "{\"code\":200,\"msg\":\"获取信息成功！\",\"status\":\"1\",\"data\":{\"id\":1509,\"title\":\"国务院关于进一步促进资本市场健康发展的若干意见\",\"content\":\"\\n<P align=center><STRONG><SPAN></SPAN></STRONG>&nbsp;</P>\\n<P align=center><STRONG><SPAN>国务院关于进一步促进资本市场健康发展的若干意见</SPAN>　</STRONG><SPAN></P>\\n<P align=center></SPAN><SPAN><STRONG>国发<SPAN>[2014]17</SPAN>号</STRONG></SPAN><SPAN></P>\\n<P align=center><SPAN><STRONG>2014</STRONG></SPAN></SPAN><SPAN><STRONG>年<SPAN>5</SPAN>月<SPAN>8</SPAN>日</STRONG></SPAN></P>\\n<P align=center><SPAN></SPAN><SPAN>&nbsp;</P>\\n<P>　　</SPAN><SPAN>　　各省、自治区、直辖市人民政府，国务综合运用法律、行政、行业自律等方式，完善资本市场信息传播管理制度。依法严肃查处造谣、传谣以及炒作不实信息误导投资者和影响社会稳定的机构、个人。</SPAN><SPAN></P>\\n<P>　　</SPAN><SPAN>　　（本文有删减）</SPAN><SPAN></P>\\n<P>　　</SPAN></P>\\n\\n\\t\\t\\t\\t\",\"collectiontime\":\"1650882560\",\"author\":\"\",\"sourceid\":30,\"sourcename\":\"中国证券投资者保护基金公司\",\"classid\":1,\"classname\":\"政策\",\"newstime\":\"2020-03-24 11:20\\n\"}}";

//        Gson gson = new Gson();
//        Article article = gson.fromJson(text, Article.class);
//        System.out.println(article.getData().getContent());

        String regex3 = "\"code\":(.*?),.*?\"status\":\"(.*?)\",.*?\"title\":\"(.*?)\",.*?\"content\":\"(.*?)\",.*?\"author\":\"(.*?)\",.*?\"newstime\":\"(.*?)\".*?[,}]";
        Matcher m = Pattern.compile(regex3).matcher(text);
        while (m.find()) {
            //System.out.println(m.group());
            System.out.println(m.group(1));
            System.out.println(m.group(2));
            System.out.println(m.group(3));
            System.out.println(m.group(4));
            System.out.println(m.group(5));
            System.out.println(m.group(6));
            //System.out.println(ret);
        }

        //2022年4月26日 17:02:26 https://www.runoob.com/regexp/regexp-metachar.html
        /*
        最后使用:
        "\"code\":(.*?),.*?\"status\":\"(.*?)\",.*?\"title\":\"(.*?)\",.*?\"content\":\"(.*?)\",.*?\"author\":\"(.*?)\",.*?\"newstime\":.*?\"(.*?\\s)\""

        "newstime": "2020-03-24 11:17\n"
        对应正则应该是: "newstime":.*?"(.*?[\n])" 或用最标准的写法 "newstime":.*?"(.*?\s)"
        而不是: "newstime":.*?"(.*?)"
         */

    }

    private static class Article implements Serializable {
        private String code;
        private String status;
        private Data data;

        private static class Data {
            private String title;
            private String content;
            private String author;
            private String newstime;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getNewstime() {
                return newstime;
            }

            public void setNewstime(String newstime) {
                this.newstime = newstime;
            }
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

}