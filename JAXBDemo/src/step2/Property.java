package step2;

/** 
*转换为xml的实体类 
*/ 
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlRootElement; 

//类注解，一个类最多可以有一个根元素 
@XmlRootElement(name="Property") 
public class Property { 

private String sequence; 
private Integer styleId; 
private String elementName; 
private String entityField; 

//普通属性 
@XmlElement 
public String getSequence() { 
return sequence; 
} 
public void setSequence(String sequence) { 
this.sequence = sequence; 
} 
@XmlElement 
public Integer getStyleId() { 
return styleId; 
} 
public void setStyleId(Integer styleId) { 
this.styleId = styleId; 
} 
@XmlElement 
public String getElementName() { 
return elementName; 
} 
public void setElementName(String elementName) { 
this.elementName = elementName; 
} 
@XmlElement 
public String getEntityField() { 
return entityField; 
} 
public void setEntityField(String entityField) { 
this.entityField = entityField; 
} 
} 
