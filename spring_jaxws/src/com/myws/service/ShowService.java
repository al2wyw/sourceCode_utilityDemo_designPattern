package com.myws.service;
import java.util.List;
import org.apache.log4j.*;
public interface ShowService {
	Logger log = Logger.getLogger(ShowService.class);
	boolean action(List<?> l);
}
