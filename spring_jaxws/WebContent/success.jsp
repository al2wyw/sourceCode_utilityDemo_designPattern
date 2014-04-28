<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>show services</title>
</head>
<body>
<h1>Web Service List</h1>
<table style="border:solid 1px grey;border-collapse:collapse">
<s:iterator var="ent" value="urls">
<tr><td>${ent.wsName}</td><td><a href="${ent.url}">${ent.url}</a></td></tr>
</s:iterator>
</table>
</body>
</html>