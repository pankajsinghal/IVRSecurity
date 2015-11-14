<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Login Page</title>
		<link rel="stylesheet" href="<c:url value="css/something.css"/>" />
		<link rel="stylesheet" type="text/css" href="css/header.css"/>
		<link rel="stylesheet" type="text/css" href="css/footer.css"/>
		
</head>
<body onload='document.f.j_username.focus();' style="margin:0;padding:0;" bgcolor="#dedede">


	
	

<div id='headermenu'>
 		
    <div id="oval_parent">
        <div id="oval">
		     <img src="css/image/bng.jpeg" alt="Smiley face" width="90" height="44">
	    </div>
    </div>
</div>

<div style="color: #FF0000;
    font-size: 17px;
    margin-bottom: -88px;
    margin-left: -22px;
    margin-top: 20px;
    text-align: center;">
<c:if test="${not empty error}">
		<div class="errorblock">
			Your login attempt was not successful, try again.<br /> Caused :
			${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
		</div>
	</c:if>
</div>



 <div class="login-form">
 
    <h1>Login Form</h1>
	
	 <form name='f' action="<c:url value='j_spring_security_check' />"
		method='POST'>
 
        <input type="text" name="j_username" placeholder="username">
 
        <input type="password" name="j_password" placeholder="password">
 
        <input type="submit" value="log in">
 
    </form>
 
	</div>
	<div style = "bottom:0;position: absolute;width: 100%;">
		
<footer><span>&copy;blackNgreen, 2013</span></footer>

</div>

	
</body>
</html>