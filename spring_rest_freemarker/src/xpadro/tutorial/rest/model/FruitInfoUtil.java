package xpadro.tutorial.rest.model;

import java.lang.reflect.Field;

import xpadro.tutorial.rest.model.FruitColor.Color;

public class FruitInfoUtil {
	public static Fruit getFruit(Class<? extends Fruit> clazz) throws InstantiationException, IllegalAccessException{
		Fruit fruit = clazz.newInstance();
		Field[] fields = clazz.getDeclaredFields();
		for(Field field:fields){
			if(field.getName().equals("appleName")){
				String name=field.getAnnotation(FruitName.class).value();
				fruit.setName(name);
			}else if(field.getName().equals("appleColor")){
				Color name=field.getAnnotation(FruitColor.class).color();
				String color = "";
				switch(name){
					case BLUE: color = "Blue";break;
					case RED: color = "Red";break;
					case GREEN: color = "Green";
				}
				fruit.setColor(color);
			}else{
				int id=field.getAnnotation(FruitProvider.class).id();
				String name=field.getAnnotation(FruitProvider.class).name();
				String address = field.getAnnotation(FruitProvider.class).address();
				String result = "The id of "+id+" is "+name+" from "+address;
				fruit.setProvider(result);
			}
		}
		return fruit;
	}
}
