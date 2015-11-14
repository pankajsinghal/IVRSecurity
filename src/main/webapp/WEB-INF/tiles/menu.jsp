
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" type="text/css" href="css/sidemenu.css"/>
<div id = "mainmenu">
<div id='cssmenu'>

<ul>
  <sec:authorize access="hasRole('ADMIN')">
   <li class='has-sub'><a href='#'><span><img src="css/image/service1.png" alt=""/>User</span></a>
      <ul>
         <li><a href='newuser.htm'><img src="css/image/user-add-icon.png" alt=""/><span>ADD USER</span></a></li>
         <li><a href='listusers.htm'><img src="css/image/group-edit-icon.png" alt=""/><span>MODIFY USER</span></a></li>
         <li class='last'><a href='listuser.htm'><span><img src="css/image/user-business-close-icon.png" alt=""/>DELETE USER</span></a></li>
      </ul>
   </li>
  </sec:authorize>
	<sec:authorize access="hasAnyRole('ADMIN','ACCOUNTMANAGER','DESIGNER')">
   <li class='has-sub'><a href='mxgraph.html'><span><img src="css/image/3D-icon.png" alt=""/>Create Service</span></a>
   </li>
   </sec:authorize>
   <sec:authorize access="hasAnyRole('ADMIN','ACCOUNTMANAGER','DESIGNER')">
   <li class='last'><a href='viewservice.htm'><span><img src="css/image/Actions-view-pim-tasks-icon.png" alt=""/>View Service</span></a></li>
   </sec:authorize>
    <sec:authorize access="hasAnyRole('REPORTER','ADMIN','ACCOUNTMANAGER')">
   <li class='last'><a href='#'><span><img src="css/image/Printed-Matter-Books-icon.png" alt=""/>Report</span></a></li>
   </sec:authorize>
   <sec:authorize access="hasAnyRole('MAINTENANCE','ADMIN','ACCOUNTMANAGER')">
   <li class='last'><a href='#'><span>Maintenance</span></a></li>
</sec:authorize>
</ul>
</div>
</div>