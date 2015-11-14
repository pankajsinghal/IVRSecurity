<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <link href="css/table.css" rel="stylesheet" type="text/css">

<table cellspacing='0' id = "updatetable"> <!-- cellspacing='0' is important, must stay -->

	<!-- Table Header -->
	<thead>
		<tr>
			<th>NAME</th>
			<th></th>
		</tr>
	</thead>
	<!-- Table Header -->

	<!-- Table Body -->
	<tbody>

<c:forEach items="${userList}" var="userList"> 
<tr>
			<td><c:out value="${userList.username}" /></td>
			<td><button type="button" onclick="location.href='updateuser.htm?id=<c:url value="${userList.id}" />';">update</button></td>
		</tr>
</c:forEach>
</tbody>
	<!-- Table Body -->

</table>