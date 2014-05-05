<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
 <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
</head>
<body style="width:800px;">
<div style="margin:auto;">
<s:form action="person" method="post" theme="simple">
<!-- 
<s:text name="person.name"/><s:textfield name="person.name" /> <br/>
<s:text name="person.id"/><s:textfield name="person.id"/> <br/>
<s:text name="person.salary"/><s:textfield name="person.salary"/><br/>
-->
<s:text name="person.name"/><input type="text" name="person.name" value="<s:property value='person.name'/>"/> <br/>
<s:text name="person.id"/><input type="text" name="person.id" value="<s:property value='person.id'/>"/> <br/>
<s:text name="person.salary"/><input type="text" name="person.salary" value="<s:property value='person.salary'/>"/><br/>
<s:submit key="submit" />
</s:form>
<s:fielderror/><br/>
</div>
</body>
</html>