package annotation;

public class Main {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		
		Apple apple = (Apple) FruitInfoUtil.getFruit(Apple.class);
		apple.displayName();
		apple.displayColor();
	}

}
