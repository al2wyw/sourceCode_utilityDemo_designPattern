package com.expedia.e3.insurance.dateutil;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DateTime dt = DateTimeAdapter.parseDateTime("2013-05-12T10:42:11+07:00");
		String result = DateTimeAdapter.printDateTime(dt);
		System.out.println(result);
		System.out.println(dt.toString());
		System.out.println(dt.getYear()+" "+dt.getMonthOfYear()+" "+dt.getDayOfMonth());
		System.out.println(dt.getHourOfDay()+" "+dt.getMinuteOfHour()+" "+dt.getSecondOfMinute());
		System.out.println(dt.getZone().toString());
		System.out.println(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss ZZ").print(43245324532L));
	}

}
