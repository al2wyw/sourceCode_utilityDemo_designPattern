package com.myws.serviceImpl;
import com.myws.service.ShowService;
import java.util.List;
import javax.xml.stream.*;
import org.apache.log4j.*;
import java.util.Locale;
import java.util.ResourceBundle;
public class ShowURLService implements ShowService{
	
	@Override
	public boolean action(List<?> l) {
		ResourceBundle rb = ResourceBundle.getBundle("showURLService", Locale.ENGLISH);
		log.info(rb.getString("start"));
		return true;
	}
	
}
