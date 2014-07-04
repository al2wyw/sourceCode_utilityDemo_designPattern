<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
function get(){
	var form = document.forms[0];
	var stock=document.getElementById("stock").value;
	var id=document.getElementById("id").value;
	var action="spring/warehouses"+"/"+stock+"/"+"/products/"+id;
	form.action=action;
	form.submit();
}
function del(){
	var req = new XMLHttpRequest();
	if(!req){
		return;
	}
	var stock=document.getElementById("stock").value;
	var id=document.getElementById("id").value;
	var action="spring/warehouses"+"/"+stock+"/"+"/products/"+id;
	req.open("DELETE",action,true);
	req.onreadystatechange=function (){
		if(req.readyState==4&&req.status==204){
			alter("delete successfully");
		}
	};
	req.send();
}
function post(){
	var req = new XMLHttpRequest();
	if(!req){
		return;
	}
	req.open("POST","spring/warehouses/1/products",true);
	req.onreadystatechange=function (){
		if(req.readyState==4&&req.status==201){
			location.href=req.getResponseHeader("Location");
		}
	};
	var id=document.getElementById("id").value;
	var des=document.getElementById("des").value;
	var product='{"id":'+id+',';
	product+='"description":"'+des+'"}';
	req.setRequestHeader("Content-Type", "application/json");
	req.send(product);
}
function put(){
	var req = new XMLHttpRequest();
	if(!req){
		return;
	}
	req.open("PUT","spring/warehouses/1/products",true);
	req.onreadystatechange=function (){
		if(req.readyState==4&&req.status==202){
			location.href=req.getResponseHeader("Location");
		}
	};
	var id=document.getElementById("id").value;
	var des=document.getElementById("des").value;
	var product='{"id":'+id+',';
	product+='"description":"'+des+'"}';
	req.setRequestHeader("Content-Type", "application/json");
	req.send(product);
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>deploy successfully</h1>
<form method="GET" action="spring/warehouses/1" onsubmit="get()">
<fieldset>
<legend>Form1:</legend>
<table>
<tr><td>stockId:</td><td><input type="text" id="stock" name="stock" value=""/></td></tr>
<tr><td>productId: </td><td><input type="text" id="id" name="id" value=""/></td></tr>
<tr><td>description: </td><td><input type="text" id="des" name="description" value=""/></td></tr>
</table>
<input type="submit" value="get"/>
</fieldset>
</form>
<button onclick="del()">delete</button><br/>
<button onclick="put()">put</button><br/>
<button onclick="post()">post</button><br/>
<button onclick="location.href='spring/warehouses/1'">show</button><br/>
<form method="GET" action="spring/stocks/1/products/1" >
<fieldset>
<legend>Form2:</legend>
<select name="select" multiple>
<option value="test1">test1</option>
<option value="test2">test2</option>
<option value="test3">test3</option>
</select>
<br/>
<input type="checkbox" name="box" value="box1"/>box1<br/>
<input type="checkbox" name="box" value="box2"/>box2<br/>
<input type="checkbox" name="box" value="box3"/>box3<br/>
<br/>
<input type="submit" value="test"/>
</fieldset>
</form>
</body>
</html>