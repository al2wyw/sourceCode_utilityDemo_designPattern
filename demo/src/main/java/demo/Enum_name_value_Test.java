package demo;

public class Enum_name_value_Test {

    public static void main(String[] args) {
        System.out.println(Gender.MAN.name() + " " + Gender.MAN.ordinal()+" "+ Gender.MAN.toString());
        Gender test = Gender.valueOf("MAN");
        if(test== Gender.MAN)
            System.out.println("a man");
        for(Gender gender : Gender.values()){
            System.out.println(gender.value);
        }
        System.out.println("Hello World!");

        Integer itest = null;
        int ii = itest;
        if (ii == 0)
            System.out.println("null is 0");
    }

    public enum Gender{

        MAN("男人"), WOMEN("女人");

        private final String value;

        Gender(String value) {
            this.value = value;
        }
    }
}
