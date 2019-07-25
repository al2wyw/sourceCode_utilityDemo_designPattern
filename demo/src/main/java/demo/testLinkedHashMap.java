package demo;

import java.util.LinkedHashMap;
import java.util.Map;

public class testLinkedHashMap {

	public static void main(String[] args) {
		//true means it will adjust the order
		Map<testLinkedHashMapObject, String> test = new LinkedHashMap<testLinkedHashMapObject, String>(10, .75F, true);
		test.put(new testLinkedHashMapObject(2, 5), "test1");
		test.put(new testLinkedHashMapObject(20, 50), "test2");
		test.put(new testLinkedHashMapObject(200, 500), "test3");
		System.out.println(test.get(new testLinkedHashMapObject(20, 50)));
		for(String s : test.values()){
			System.out.println(s);
		}
	}

}

class testLinkedHashMapObject {
	private int i;
	private int j;
	
	public testLinkedHashMapObject(int i, int j){
		this.i = i;
		this.j = j;
	}
	
	public int hashCode(){
		return i + j;
	}
	
	public boolean equals(Object obj) {
		if(this == obj )
			return true;
	    if(obj != null && obj instanceof testLinkedHashMapObject){
	    	testLinkedHashMapObject o = (testLinkedHashMapObject)obj;
	    	if(i == o.i && j == o.j)
	    		return true;
	    }
	    return false;
	}
}
