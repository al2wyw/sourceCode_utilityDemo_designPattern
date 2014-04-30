package com.myws.serviceImpl;
import com.myws.service.ShowService;

import com.myws.model.ws;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;

import java.util.Locale;
import java.util.ResourceBundle;
public class ShowURLService implements ShowService{
	
	@Override
	public boolean action(List<?> l) {
		ResourceBundle rb = ResourceBundle.getBundle("ShowURLService", Locale.ENGLISH);
		log.info(rb.getString("start"));
		String path=ShowURLService.class.getClassLoader().getResource("/wsdl").getFile();
		log.info(System.getProperty("user.dir"));
		ArrayList<ws> list=(ArrayList<ws>)l;
		XMLInputFactory factory = XMLInputFactory.newInstance();
		File wsdl=new File(path);
		if(!wsdl.exists())
		{
			log.error(rb.getString("error.nowsdl"));
			return false;
		}else{
			if(wsdl.isDirectory()){
				File[] files=wsdl.listFiles();
				for(File f:files){
					String filename=f.getName();
					if(filename.endsWith("wsdl")){
						try{
							ws test=new ws();
							log.info("the current file is "+filename);
							String[] str=filename.split("\\.");
							log.info("the length of string array is "+str.length);
							String name=str[0];
							FileReader fileReader = new FileReader(f);
							XMLEventReader reader = factory.createXMLEventReader(fileReader);
							log.info(rb.getString("xmlstream"));
							while(reader.hasNext()){
				    			XMLEvent event = reader.nextEvent();
				    			if(event.isStartElement()){
				    				
				    				QName q=new QName("location");
				    				Attribute att=event.asStartElement().getAttributeByName(q);
				    				if(att!=null){
				    					String s=att.getValue();
				    					log.info("ws is "+name);
				    					log.info("xml from: "+s);
				    					test.setWsName(name);
				    					test.setUrl(s);
				    					list.add(test);
				    				}
				    			}
				    		}
							reader.close();
							fileReader.close();
						}catch(Exception e){
							log.error(e.getMessage(),e);
						}finally{
							
						}
					}
				}
				return true;
			}else{
				log.error(rb.getString("error.nodir"));
				return false;
			}
		}
	}
	
}
