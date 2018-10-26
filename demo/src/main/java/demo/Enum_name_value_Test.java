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

        switch (test){
            case MAN:     System.out.println("a man");
                break;
            case WOMEN:   System.out.println("a woman");
                break;
            default: System.out.println("not found");
        }
    }

    public enum Gender{

        MAN("男人"), WOMEN("女人");

        private final String value;

        Gender(String value) {
            this.value = value;
        }
    }
}
