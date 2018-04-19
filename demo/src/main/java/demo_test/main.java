package demo_test;

import java.util.*;

class test{
	
}

class testt extends test{
	
}

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test t1 = new test();
		testt t2 = new testt();
		List<test> list = new ArrayList<test>();
		list.add(t1);
		list.add(t2);
		//it will not raise an exception
		List ttt = list;
		ttt.add("string");
		List<test> l = Collections.checkedList(list, test.class);
		//it will raise an exception
		List tt = l;
		tt.add("string");

		System.out.println("good");
	}

}
