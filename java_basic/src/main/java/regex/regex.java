
package regex;
import java.util.regex.*;
public class regex {
public static void main(String[] args){
    Pattern p=Pattern.compile("iamsmart(you)?(as)?");//order is neccessary.
    String ss="sueiamsmartpeteriamsmartyouasanneiamsmartken";
    String[] s=p.split(ss);
    //String[] s=ss.split("(iamsmart(you)?(as)?)");//the same as above!
    for(int i=0;i<s.length;i++){
        System.out.println(s[i]);
    }
    Matcher mm=p.matcher(ss);
    System.out.println(mm.replaceAll("-"));
    Matcher m=p.matcher("iamsmartiamsmartyouasiamsmart");
    System.out.println(m.pattern());
    System.out.println(m.matches());
    System.out.println(m.lookingAt());
    System.out.println(m.group());
    m.reset();
    System.out.println(m.lookingAt());
    System.out.println(m.group());
    m.reset("iamsmartyouasiamsmartyou");
    m.find(7);
    System.out.println(m.group(2)+"  start from 7");//m.group(0) is the same as m.group(1)
    while(m.find()){
    System.out.println(m.group());
    System.out.println(m.start());
    System.out.println(m.end());
    }
    System.out.println(m.groupCount());//just return the number of parenthesis!
}
}
