package step3;

import java.util.regex.Pattern;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.xml.sax.SAXException;

@SuppressWarnings("deprecation")
public class CDATAContentHandler extends XMLSerializer{
	private static final Pattern XML_CHARS = Pattern.compile("[<>&]");
	
	public CDATAContentHandler(OutputFormat format){
		super(format);
	}
	
	public void characters(char[] ch,
            int start,
            int length)
     throws SAXException{
		boolean useCData = XML_CHARS.matcher(new String(ch,start,length)).find();
	    if (useCData) super.startCDATA();
	    	super.characters(ch, start, length);
	    if (useCData) super.endCDATA();
        
	}
	
}
