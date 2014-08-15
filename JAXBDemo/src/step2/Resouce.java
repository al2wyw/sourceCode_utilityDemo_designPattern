package step2;

/** 
 *包含上面对象实体的类 
 */
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Resouce {
	private String picLarge;
	private List<Property> Properties;

	@XmlElement
	public String getPicLarge() {
		return picLarge;
	}

	public void setPicLarge(String picLarge) {
		this.picLarge = picLarge;
	}

	// 如果想在节点外面包一层xml元素节点，可以用
	@XmlElementWrapper(name = "properties")
	@XmlElement
	public List<Property> getProperties() {
		return Properties;
	}

	public void setProperties(List<Property> properties) {
		Properties = properties;
	}

}
