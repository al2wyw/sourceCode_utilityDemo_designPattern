package state;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class context {
	private state usersttate;
	private int count;
	private Properties stateMap;
	private final String STATEMAPFILENAME = "statemap";
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public state getUsersttate() {
		return usersttate;
	}

	public void setUsersttate(state usersttate) {
		this.usersttate = usersttate;
	}
	
	public void action(){//for decoration to add more state
		if(stateMap == null){
			//should change to resource implementation of spring
			InputStream is = this.getClass().getResourceAsStream(STATEMAPFILENAME);
			stateMap = new Properties();
			try {
				stateMap.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String classname = stateMap.getProperty(String.valueOf(count));
		if(classname!=null && !classname.equals(""))
			getStateFromMap();
		if(usersttate!=null){
			usersttate.action();
			count++;
		}
	}
	
	private void getStateFromMap(){
		String classname = stateMap.getProperty(String.valueOf(count));
		try {
			usersttate = (state)Class.forName(classname).newInstance();
		} catch (InstantiationException e){
			e.printStackTrace();
		} catch (IllegalAccessException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e) {

			e.printStackTrace();
		}
	}
}

class test{
	public static void main(String[] args){
		context test=new context();
		test.action();
		test.action();
		test.action();
		test.action();
		test.action();
		test.action();
		test.action();
		test.action();
		test.action();
	}
}