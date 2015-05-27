package regex;
import java.util.regex.*;
public class regtest {
    public static void main(String[] args){
    Pattern p=Pattern.compile("iamsmart(you)?(as)?");
    Matcher m=p.matcher("iamsmartffiamsmartyouasiamsmart");
    System.out.println(m.pattern());
    System.out.println(m.matches());
        while(m.find()){
    System.out.println(m.group());
    System.out.println(m.start());
    System.out.println(m.end());
    }
    Pattern p1=Pattern.compile("200\\d|19\\d{2}");
    Matcher m1=p1.matcher("1992");
     System.out.println("it is "+m1.matches());
   }
}
