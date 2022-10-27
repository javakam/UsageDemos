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
        //1.今天
        LocalDate now = LocalDate.now();
        int nowYear = now.getYear();
        int nowMonth = now.getMonthValue();
        int nowDay = now.getDayOfMonth();
        System.out.println(nowYear + " ; " + nowMonth + " ; " + nowDay);
        //结果为: 2022 ; 10 ; 27

        //2.指定日期
        //LocalDate date = LocalDate.of(2022, 8, 30);
        LocalDate date = LocalDate.now();
        LocalDate lastMonth = date.minusMonths(1); // 当前月份减1
        LocalDate lastMonthFirstDay = lastMonth.with(TemporalAdjusters.firstDayOfMonth()); // 获取上个月的第一天
        LocalDate lastMonthLastDay = lastMonth.with(TemporalAdjusters.lastDayOfMonth()); // 获取上个月的最后一天
        System.out.println(date);
        System.out.println(lastMonth);
        System.out.println(lastMonthFirstDay);
        System.out.println(lastMonthLastDay);
        /*
        结果为:
        2022 ; 10 ; 27
        2022-10-27
        2022-09-27
        2022-09-01
        2022-09-30
         */

//        LocalDate lastMonthFirstDay2 = LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
//        System.out.println();
//        System.out.println(lastMonthFirstDay2);
    }

    private static void line() {
        System.out.println("===================================");
    }
}