package jaxb;

import org.apache.http.client.utils.DateUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by johnny.ly on 2016/7/22.
 */
public class XmlCalenderUtils {

    private static DatatypeFactory factory;

    static{
        try{
            factory = DatatypeFactory.newInstance();
        }catch (DatatypeConfigurationException e){

        }
    }
    /**
     *  把对方不带时区的XMLGregorianCalendar转换成我们的日期(带+08:00时区) 如'2016-10-10T00:00:00' -> '2016-10-10 00:00:00.000+08:00'
     * */
    public static Date convertToDate(XMLGregorianCalendar c){
        if(c == null){
            return null;
        }
        GregorianCalendar g = c.toGregorianCalendar();
        return g.getTime();
    }

    /**
     *  把我们的日期(带+08:00时区)转成不带时区和时间的XMLGregorianCalendar 如'2016-10-10 00:00:00.000+08:00' -> '2016-10-10'
     * */
    public static XMLGregorianCalendar convertToXMLDate(Date d){
        if(d == null){
            return null;
        }
        GregorianCalendar g = new GregorianCalendar();
        g.setTime(d);
        XMLGregorianCalendar xmlGregorianCalendar = factory.newXMLGregorianCalendar(g);
        xmlGregorianCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
        xmlGregorianCalendar.setHour(DatatypeConstants.FIELD_UNDEFINED);
        xmlGregorianCalendar.setMinute(DatatypeConstants.FIELD_UNDEFINED);
        xmlGregorianCalendar.setSecond(DatatypeConstants.FIELD_UNDEFINED);
        xmlGregorianCalendar.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
        return xmlGregorianCalendar;
    }

    /*test start*/
    public static void main(String[] ags) throws Exception{
        person p = new person();
        p.setName("peter");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d = df.parse("2016-10-10");
        p.setOpenDate(XmlCalenderUtils.convertToXMLDate(d));
        JAXBContext.newInstance(person.class).createMarshaller().marshal(p, System.out);
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><person><name>peter</name><openDate>2016-10-10T00:00:00</openDate></person>";//XMLGregorianCalendar 支持2016-10-10T00:00:00 或 2016-10-10，不能缺T，结尾带Z表示UTC标准时间，不带则表示不带时区的时间，解析出来完全不一样
        person rs = (person)JAXBContext.newInstance(person.class).createUnmarshaller().unmarshal(new ByteArrayInputStream(xml.getBytes()));
        System.out.println(rs.getOpenDate());
        System.out.println(XmlCalenderUtils.convertToDate(rs.getOpenDate()));//must the same
        System.out.println(d);//must the same
    }

    @XmlRootElement
    private static class person{
        private XMLGregorianCalendar openDate;
        private String name;

        public XMLGregorianCalendar getOpenDate() {
            return openDate;
        }

        public void setOpenDate(XMLGregorianCalendar openDate) {
            this.openDate = openDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    /*test end*/
}
