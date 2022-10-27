import java.util.Scanner;

//一维数组插入数据
//https://blog.csdn.net/m0_57051895/article/details/126751853
public class 一维数组插入数据 {

    public static void main(String[] args) {
        //1.声明一个scores数组用来存储当前学员的成绩。
        int[] scores = {99, 85, 82, 63, 60};

        //2.声明一个比scores数组大1的newScores数组。
        int[] newScores = new int[scores.length + 1];

        //3.将scores数组中的数据元素通过赋值，存储到newScores数组当中。
        for (int i = 0; i < scores.length; i++) {
            newScores[i] = scores[i];
        }
        System.out.println("---------插入数据前的newScores数组---------");
        for (int i = 0; i < newScores.length; i++) {
            System.out.print(newScores[i] + "  ");
        }
        System.out.println();

        //4.使用Scanner类从控制台接收新学员成绩，存储到relt变量当中。
        Scanner in = new Scanner(System.in);
        System.out.println("请输入学员成绩：");
        int relt = in.nextInt();

        //5.声明一个变量index表示newScores数组的最后一个下标的值。
        int index = newScores.length - 1;

        //6.将relt变量当中的新学员成绩与newScores数组中学院成绩相比较，
        //  第一个比relt成绩小的数组元素下表赋值给index变量。
        for (int i = 0; i < newScores.length; i++) {
            if (relt > newScores[i]) {
                index = i;
                break;
            }
        }

        //7.使用for循环将比relt值小的数组元素向后移一位。
        for (int i = newScores.length - 1; i > index; i--) {
            newScores[i] = newScores[i - 1];
        }

        //8.relt赋值给newScores数组下标为index值的数据元素。
        newScores[index] = relt;

        //输出newScores数组中的数据元素。
        System.out.println("---------插入数据后的newScores数组---------");
        for (int i = 0; i < newScores.length; i++) {
            System.out.print(newScores[i] + "  ");
        }
    }

}
