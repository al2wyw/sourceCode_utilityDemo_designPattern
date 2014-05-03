package client.test;

import java.net.URL;
import client.com.myws.*;
import java.util.*;
public class peopleClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			URL url=new URL("http://localhost:8080/spring_jaxws/people?wsdl");
			ShowPeopleServiceService service=new ShowPeopleServiceService(url);
			ShowPeople port=service.getShowPeoplePort();
			List<Person> list=port.show();
			if(list!=null){
				for(Person p:list){
					System.out.println(p.getName()+" "+p.getId()+" "+p.getSalary());
				}
			}
			System.out.println("tset");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
