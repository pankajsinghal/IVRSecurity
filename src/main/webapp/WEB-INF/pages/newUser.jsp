<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
.errorblock {
	color: #000;
	background-color: #E2E2E2;
	border: 2px solid #949898;
	padding: 8px;
	margin: 16px;
}
</style>
<style type="text/css">
.select_cls {
	height: 74px;
	width: 10px;
}
</style>
<script src="js/jquery-1.9.1.js"></script>
<script src="js/listbox.js"></script>
<link rel="stylesheet" type="text/css" href="css/newuser.css" />
<div id="stylized" class="myform">
	<form:form method="POST" commandName="user">
		<h1>New User</h1>
		<form:errors path="*" cssClass="errorblock" element="div" />
		<label>Name <span class="small">Name</span>
		</label>
		<form:input path="userName" />
		<br>

		<label>User <span class="small">Type Of User</span>
		</label>

		<form:select path="role">
			<form:option value="select" label="--Select--" />
			<form:option value="ADMIN" label="Admin" />
			<form:option value="ACCOUNTMANAGER" label="Account Manager" />
			<form:option value="DESIGNER" label="Designer" />
			<form:option value="REPORTER" label="Reporter" />
			<form:option value="MAINTENANCE" label="Maintenance" />
		</form:select>

		<label>Password <span class="small">Enter Password</span>
		</label>

		<form:password path="password" />
		<label>Confirm Password <span class="small">Confirm
				Password</span>
		</label>
		<form:password path="cpassword" />

		<div class="listbox">
			<label>Country</label> 
			<form:select path="country" class="other">
				<c:forEach items="${servicelist}" var="countrylist">
					<option>
						<c:out value="${countrylist.countryName}" /></option>
				</c:forEach>
			</form:select>
			
		</div>
		<div class="listbox">
			<label>Operator</label> 
			<select id="list1" multiple="multiple" rows="2" class="other">
				<c:forEach items="${service}" var="operatorlist">
					<option>
						<c:out value="${operatorlist.operatorName}" /></option>
				</c:forEach>
			</select>
			<input id="button1" type="button" value="+" />
			<input id="button2" type="button" value="-" />
			
			<form:select path="operator" id="list2" multiple="multiple" rows="2">
			</form:select>
		</div><p></p>
		  <button id="btn1">+</button><br>
		  <button id="btn1">-</button>
		  <br><br>		
		<button type="submit">ADD</button>
		<div class="spacer"></div>
	</form:form>
</div>