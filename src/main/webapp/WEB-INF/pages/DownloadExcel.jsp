<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page import="java.io.*" %>
<html>
<head>
	<title></title>
</head>
<body>

<%
		String path= request.getAttribute("despath").toString();
		File f = new File(path); 
		if(f.exists())
		{
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment;filename="+request.getAttribute("filename"));
		ServletOutputStream output=response.getOutputStream();
		FileInputStream file=new FileInputStream(path);
		int c=0;
		while((c=file.read())!=-1)
		{
		output.write(c);
		}
		output.flush(); 
		output.close(); 
		file.close();
		f.delete();
		}
		else
		{
		    
		}

%>
</body>
</html>
