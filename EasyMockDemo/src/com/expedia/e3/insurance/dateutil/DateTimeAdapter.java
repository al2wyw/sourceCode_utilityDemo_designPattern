package com.expedia.e3.insurance.dateutil;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateTimeAdapter
{
    private static final DateTimeFormatter	XML_DATE_TIME_FORMAT	= ISODateTimeFormat.dateTimeParser().withOffsetParsed();
    private static final DateTimeFormatter	XML_DATE_FORMAT			= ISODateTimeFormat.dateOptionalTimeParser();
    private static final DateTimeFormatter  DB_DATE_TIME_OFFSET_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss ZZ").withOffsetParsed();

    private DateTimeAdapter()
    {
    }

    public static DateTime parseDate(String dateStr)
    {
        return XML_DATE_FORMAT.parseDateTime(dateStr);
    }

    public static DateTime parseDBDateTimeOffset(String dbDateTimeOffset)
    {
        return DB_DATE_TIME_OFFSET_FORMAT.parseDateTime(dbDateTimeOffset);
    }

    public static String printDate(DateTime date)
    {
        return ISODateTimeFormat.date().print(date);
    }

    public static DateTime parseDateTime(String dateStr)
    {
        try
        {
            DateTime dateTime = XML_DATE_TIME_FORMAT.parseDateTime(dateStr);
            return dateTime;
        }
        catch(java.lang.IllegalArgumentException err)
        {
            // This is normally a format issue.  We'll try switching to a different format since some people decide to not send timezone.
            DateTime dateTime = ISODateTimeFormat.dateHourMinuteSecond().parseDateTime(dateStr);
            return dateTime;
        }
    }

    public static String printDateTime(DateTime date)
    {
        return ISODateTimeFormat.dateTime().print(date);
    }

    public static XMLGregorianCalendar dateTimeToXMLGregorianCalendar(DateTime dateTime)
    {
        try
        {
            DatatypeFactory dataTypeFactory = DatatypeFactory.newInstance();
            return dataTypeFactory.newXMLGregorianCalendar(dateTime.toGregorianCalendar());
        } catch (DatatypeConfigurationException ex)
        {

            return null;
        }
    }

    public static XMLGregorianCalendar dateToXMLGregorianCalendar(DateTime date)
    {
        try
        {
            XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            xmlGregorianCalendar.setDay(date.getDayOfMonth());
            xmlGregorianCalendar.setMonth(date.getMonthOfYear());
            xmlGregorianCalendar.setYear(date.getYear());
            return xmlGregorianCalendar;
        } catch (DatatypeConfigurationException ex)
        {

            return null;
        }
    }

    public static DateTime XMLGregorianCalendarToDateTime(XMLGregorianCalendar xmlGregorianCalendar)
    {
        DateTime dateTime = new DateTime(xmlGregorianCalendar.getYear());
        return dateTime;
    }

}
