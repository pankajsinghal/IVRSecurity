<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link rel="stylesheet" type="text/css" href="css/newuser.css"/>
<div id="stylized" class="myform">
	<form:form method="POST" commandName="user" id="form">
  		<h1>update User</h1>
     	<label>Name
 			<span class="small">Name</span>
   		</label>
 		<form:input path="userName" />
 
 		<label>User
 			<span class="small">Type Of User</span>
   		</label>
      	<form:select path = "role">
        	<form:option value = "select" label="--Select--"/>
        	<form:option value = "manager" label="Account Manager"/>
        	<form:option value = "operations" label="Operations"/>
        	<form:option value = "designer" label="Designer"/>
        	<form:option value = "reporter" label="Reporter"/>
        	<form:option value = "ROLE_ADMIN" label="ROLE_ADMIN"/>
        	<form:option value = "ROLE_USER" label="ROLE_USER"/>
    	</form:select> 
     	<button  type="submit">Update</button>
 
 		<div class="spacer"></div>
 	</form:form>
 </div>
 