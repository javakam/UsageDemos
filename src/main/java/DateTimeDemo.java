import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class DateTimeDemo {

    public static void main(String[] args) {
        System.out.println(isNight());
        line();

        testLocalDate();
    }

    //判断当前是否处于 01:00 ~ 02:00 之间
    private static boolean isNight() {
        return LocalTime.now().withNano(0).withSecond(0).withMinute(0).equals(LocalTime.parse("01:00"));
    }

    //获取当前月份前一个月的一号
    private static void testLocalDate() {
        //今天
        LocalDate now = LocalDate.now();
        int nowYear = now.getYear();
        int nowMonth = now.getMonthValue();
        int nowDay = now.getDayOfMonth();
        System.out.println("今天: " + nowYear + " ; " + nowMonth + " ; " + nowDay);
        //结果为: 2023 ; 5 ; 18

        //昨天
        LocalDate zt = LocalDate.now().minusDays(1);
        System.out.println("昨天: " + zt.getYear() + " ; " + zt.getMonthValue() + " ; " + zt.getDayOfMonth());
        //结果为: 2023 ; 5 ; 17

        //明天
        LocalDate mt = LocalDate.now().plusDays(1);
        System.out.println("明天: " + mt.getYear() + " ; " + mt.getMonthValue() + " ; " + mt.getDayOfMonth());
        //结果为: 2023 ; 5 ; 19

        //十天前
        LocalDate mtBefore = LocalDate.now().minusDays(10);
        System.out.println("十天前: " + mtBefore.getYear() + " ; " + mtBefore.getMonthValue() + " ; " + mtBefore.getDayOfMonth());
        //结果为: 十天前: 2023 ; 5 ; 8

        //指定日期
        //LocalDate date = LocalDate.of(2022, 8, 30);
        LocalDate date = LocalDate.now();
        LocalDate lastMonth = date.minusMonths(1); // 当前月份减1
        LocalDate lastMonthFirstDay = lastMonth.with(TemporalAdjusters.firstDayOfMonth()); // 获取上个月的第一天
        LocalDate lastMonthLastDay = lastMonth.with(TemporalAdjusters.lastDayOfMonth()); // 获取上个月的最后一天
        System.out.println("今天: " + date);
        System.out.println("当前月份减1: " + lastMonth);
        System.out.println("上个月的第一天: " + lastMonthFirstDay);
        System.out.println("上个月的最后一天: " + lastMonthLastDay);
        /*
        结果为:
        今天: 2023 ; 5 ; 18
        昨天: 2023 ; 5 ; 17
        明天: 2023 ; 5 ; 19
        今天: 2023-05-18
        当前月份减1: 2023-04-18
        上个月的第一天: 2023-04-01
        上个月的最后一天: 2023-04-30
         */

//        LocalDate lastMonthFirstDay2 = LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
//        System.out.println();
//        System.out.println(lastMonthFirstDay2);
    }

    private static void line() {
        System.out.println("===================================");
    }
}