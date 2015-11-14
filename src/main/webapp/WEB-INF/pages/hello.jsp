<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<body>

	<h3>Username : ${username}</h3>	
	  <sec:authorize access="hasRole('ROLE_ADMIN')">
         This session will be visible to an admin only.<br/>
         You are an Administrator.<br/>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_USER')">
         This session will be visible to an Customer only.<br/>
         You are an Customer.<br/>
        </sec:authorize>
	<a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
	
</body>
</html>