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
<s:form action="person" method="post">
<s:textfield key="person.name" /> 
<s:textfield key="person.id" /> 
<s:textfield key="person.salary" />
<s:submit key="submit" />
</s:form>
</div>
</body>
</html>