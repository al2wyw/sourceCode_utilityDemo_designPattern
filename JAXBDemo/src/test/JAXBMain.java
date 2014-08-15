package test;

import java.util.List;

import step2.JAXB2Tester;
import step3.*;

public class JAXBMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		ObjectFactory of = new ObjectFactory();
//		Guitars guitars = of.createGuitars();
//		List<Guitar> guitarList = guitars.getGuitar();
//		Guitar guitar1 = of.createGuitar();
//		guitar1.setBackSides("back sides 1");
//		guitar1.setId("test1");
//		guitar1.setModel("model 1");
//		guitar1.setNotes("this is the note 1");
//		Builder builder1 = of.createBuilder();
////		builder1.setLuthier("luthier 1");
//		builder1.setSmallShop("small shop 1");
//		builder1.setValue("1000");
//		guitar1.setBuilder(builder1);
//		Top top1 = of.createTop();
//		top1.setBearclaw("bear claw 1");
//		top1.setValue("10001");
//		guitar1.setTop(top1);
//		
//		Guitar guitar2 = of.createGuitar();
//		guitar2.setBackSides("back sides 2");
//		guitar2.setId("test2");
//		guitar2.setModel("model 2");
//		Builder builder2 = of.createBuilder();
//		builder2.setLuthier("luthier 2");
////		builder2.setSmallShop("small shop 2");
//		builder2.setValue("2000");
//		guitar2.setBuilder(builder2);
//		Top top2 = of.createTop();
//		top2.setBearclaw("bear claw 2");
//		top2.setValue("20001");
//		guitar2.setTop(top2);
//		
//		guitarList.add(guitar1);
//		guitarList.add(guitar2);
		

		Guitars guitars = new Guitars();
		List<Guitar> guitarList = guitars.getGuitar();
		Guitar guitar1 = new Guitar();
		guitar1.setBackSides("back sides 1");
		guitar1.setId("test1");
		guitar1.setModel("model 1");
		guitar1.setNotes("this is the note 1 <test>");
		Builder builder1 = new Builder();
//		builder1.setLuthier("luthier 1");
		builder1.setSmallShop("small shop 1");
		builder1.setValue("1000");
		guitar1.setBuilder(builder1);
		Top top1 = new Top();
		top1.setBearclaw("bear claw 1");
		top1.setValue("10001");
		guitar1.setTop(top1);
		
		Guitar guitar2 = new Guitar();
		guitar2.setBackSides("back sides 2");
		guitar2.setId("test2");
		guitar2.setModel("model 2");
		Builder builder2 = new Builder();
		builder2.setLuthier("luthier 2");
//		builder2.setSmallShop("small shop 2");
		builder2.setValue("2000");
		guitar2.setBuilder(builder2);
		Top top2 = new Top();
		top2.setBearclaw("bear claw 2");
		top2.setValue("20001");
		guitar2.setTop(top2);
		
		guitarList.add(guitar1);
		guitarList.add(guitar2);
		JAXB2Tester.bean2Xml(guitars);
		
		Guitars o = (Guitars)JAXB2Tester.xml2Bean(Guitars.class, "test.xml");
		for(Guitar g:o.getGuitar()){
			System.out.println(g.getModel());
			System.out.println(g.getNotes());
		}
	}

}
