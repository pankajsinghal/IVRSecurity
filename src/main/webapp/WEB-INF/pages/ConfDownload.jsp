<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Download CDR</title>
<link rel="stylesheet" type="text/css" href="css/newuser.css"/>
<script type="text/javascript">

function confirmComplete() {
	var answer=confirm("Are you sure you want to download this file");
	if (answer==true)
	  {
	    return true;
	  }
	else
	  {
		window.location.href = 'cdr.htm';
	   return false;
	  }
	}


</script>
</head>
<body>
 
<div id="stylized" class="myform">
<form action="confDownloadExcel" method="POST" >
 <h1>Download CDR File</h1>

<input type="hidden" name="filename" value="<%=request.getAttribute("filename")%>"> 
 <input type="hidden" name="despath" value="<%=request.getAttribute("despath")%>"> 
<input type="submit" name="submit" value="Download File" class="addjobbtn" onclick="{return confirmComplete();}">
</form>
</div>


</body>
</html>