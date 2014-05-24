<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<ol>
<li><s:property value="object.name"/></li>
<li><s:property value="object.id"/></li>
<li><s:property value="object.salary"/></li>
<li><s:property value="object.day"/></li>
<li><s:property value="object.level"/></li>
</ol>
</body>
</html>