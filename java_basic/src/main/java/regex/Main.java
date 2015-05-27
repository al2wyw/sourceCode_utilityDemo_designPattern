package regex;
import java.io.*;
import java.util.regex.*;
public class Main {
    public static void checkDate(String sss){
        Pattern p=Pattern.compile("([\\d\\-\n])+",Pattern.CASE_INSENSITIVE);//\\w \\s \\d
        Matcher m=p.matcher(sss);
        if(!m.matches()){
            System.out.println("输入有误!请检查您的输入！");
            return;
        }
        String[] d=sss.split("-");
        if(d.length!=3||d[0].length()!=4||d[1].length()!=2||d[2].length()!=2){
            System.out.println("请按格式输入，并确保年月日正确输入！");
            return;
        }
        int year=Integer.parseInt(d[0]);
        int mon=Integer.parseInt(d[1]);
        int day=Integer.parseInt(d[2]);
        if(year<1990||year>2050){
            System.out.println("请输入大于1990同时小于2050的年份!");
            return;
        }
        if(mon<1||mon>12){
            System.out.println("一年只有12个月！");
            return;
        }
        if(mon==1||mon==3||mon==5||mon==7||mon==8||mon==10||mon==12){
            if(day<1||day>31){
                System.out.println("大月，只有31天!");
                return;
            }
        }else if(mon==2){
            if(year%4==0&&year%400==0){
                if(day>29||day<1){
                       System.out.println("闰年，2月只有29天！");
                       return;
                }
        }else{
            if(day<1||day>28){
                System.out.println("不是闰年，2月只有28天!");
                return;
            }
            }
        } else {
            if(day<1||day>30){
                System.out.println("小月，只有30天！");
                return;
            }
        }
    }
    public static void main(String[] args)throws Exception {
        String s="asdfg";
        String str=s.substring(1,4);//substring!!! bigenIndex starts from 0, endIndex-1
        CharSequence c=s.subSequence(0, 4);//writeObject();
        System.out.println(c);
        int test=Integer.parseInt("00034");
        System.out.println(test);
        boolean flag=true;
        while(flag){
        System.out.println("Enter the date(格式为yyyy-mm-dd): ");
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        String sss=br.readLine();
        checkDate(sss);
        System.out.println("是否重新输入(输入‘n’退出程序,输入‘y’则重新输入)？");
        String answer=br.readLine();
        if(answer.equals("n")){
            flag=false;
        }
        }
    }
}
