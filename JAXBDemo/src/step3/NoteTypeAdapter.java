package step3;

import java.util.regex.Pattern;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class NoteTypeAdapter extends XmlAdapter<String,String>{
	private final Pattern XML_CHARS = Pattern.compile("[<>&]");
	
	@Override
	public String marshal(String v) throws Exception {
		boolean useCData = XML_CHARS.matcher(v).find();
		if(useCData){
			return "<![CDATA["+v+"]]>";
		}else
			return v;
	}

	@Override
	public String unmarshal(String v) throws Exception {
		return v;
	}

}
