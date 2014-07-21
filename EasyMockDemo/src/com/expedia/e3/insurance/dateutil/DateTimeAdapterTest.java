package com.expedia.e3.insurance.dateutil;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Assert;
import org.junit.Test;

public class DateTimeAdapterTest {
	
	@Test
	public void testParseDate(){
		DateTime dt = DateTimeAdapter.parseDate("2013-05-12");
		Assert.assertNotNull(dt);
		Assert.assertEquals(2013, dt.getYear());
		Assert.assertEquals(5, dt.getMonthOfYear());
		Assert.assertEquals(12, dt.getDayOfMonth());
		Assert.assertEquals(0, dt.getHourOfDay());
		Assert.assertEquals(0, dt.getMinuteOfHour());
		Assert.assertEquals(0, dt.getSecondOfMinute());
		Assert.assertEquals(0, dt.getMillisOfSecond());
	}
	
	@Test
	public void testParseDateTime(){ //no exception!!
		DateTime dt = DateTimeAdapter.parseDateTime("2013-05-12T23:11:20");
		Assert.assertNotNull(dt);
		Assert.assertEquals(2013, dt.getYear());
		Assert.assertEquals(5, dt.getMonthOfYear());
		Assert.assertEquals(12, dt.getDayOfMonth());
		Assert.assertEquals(23, dt.getHourOfDay());
		Assert.assertEquals(11, dt.getMinuteOfHour());
		Assert.assertEquals(20, dt.getSecondOfMinute());
		Assert.assertEquals(0, dt.getMillisOfSecond());
	}
	
	@Test
	public void testParseDBDateTimeOffset(){
		DateTime dt = DateTimeAdapter.parseDBDateTimeOffset("2013-05-12 10:42:11 +08:00");
		Assert.assertNotNull(dt);
		Assert.assertEquals(2013, dt.getYear());
		Assert.assertEquals(5, dt.getMonthOfYear());
		Assert.assertEquals(12, dt.getDayOfMonth());
		Assert.assertEquals(10, dt.getHourOfDay());
		Assert.assertEquals(42, dt.getMinuteOfHour());
		Assert.assertEquals(11, dt.getSecondOfMinute());
		Assert.assertEquals(0, dt.getMillisOfSecond());
		Assert.assertEquals("+08:00",dt.getZone().toString());
	}
	
	@Test
	public void testPrintDate(){
		DateTime dt = ISODateTimeFormat.dateOptionalTimeParser().parseDateTime("2013-05-12");
		String result = DateTimeAdapter.printDate(dt);
		Assert.assertNotNull(result);
		Assert.assertEquals("2013-05-12", result);
	}
	
	@Test
	public void testPrintDateTime(){
		DateTime dt = ISODateTimeFormat.dateTimeParser().withOffsetParsed().parseDateTime("2013-05-12T23:11:20+08:00");
		String result = DateTimeAdapter.printDateTime(dt);
		Assert.assertNotNull(result);
		Assert.assertEquals("2013-05-12T23:11:20.000+08:00", result);
	}
	
	@Test
	public void testDateToXMLGregorianCalendar(){
		
	}
	
	@Test
	public void testDateTimeToXMLGregorianCalendar(){
		
	}
}
