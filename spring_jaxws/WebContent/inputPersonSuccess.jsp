<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>input person services</title>
</head>
<body>
<h1>Person save successfully</h1>
<p>
<s:property value="person.name"/>!<br/>
<s:property value="person.id"/>!<br/>
<s:property value="person.salary"/>!<br/>
THE END
</p>
<input type="text" value="test"/>
<textarea rows="10" cols="100">${requestScope.test}</textarea>
<p>${requestScope.test}</p>
</body>
</html>