<%@page language="java" import="com.bng.core.bean.UploadConfig"%>
<link rel="stylesheet" type="text/css" href="css/header.css"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div id='headermenu'>
         
                 <li> 
				     <a href="<c:url value="/j_spring_security_logout" />"><span>Logout</span> </a> 
				 </li>
		
    <div id="oval_parent">
        <div id="oval">
		     <img src="css/image/bng.jpeg" alt="Smiley face" width="90" height="44">
	    </div>
    </div>

</div>
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
   <li class='has-sub'><a href='mxgraph.htm'><span><img src="css/image/3D-icon.png" alt=""/>Create Service</span></a>
   </li>
   </sec:authorize>
   <sec:authorize access="hasAnyRole('ADMIN','ACCOUNTMANAGER','DESIGNER')">
   <li class='has-sub'><a href='#'><span><img src="css/image/Actions-view-pim-tasks-icon.png" alt=""/>View Service</span></a>
   	<ul>
   		<li class='last'><a href='viewserviceOBD.htm'><span><img src="css/image/Actions-view-pim-tasks-icon.png" alt=""/>View OBD Service</span></a></li>
        <li class='last'><a href='viewserviceIVR.htm'><span><img src="css/image/Actions-view-pim-tasks-icon.png" alt=""/>View IVR Service</span></a></li>
   	</ul>
   	</li>
   </sec:authorize>
   <sec:authorize access="hasAnyRole('MAINTENANCE','ADMIN','ACCOUNTMANAGER')">
   <li class='has-sub'><a href='#'><span><img src="css/image/SchedulerIcon.png" alt=""/>Scheduler</span></a>
   		<ul>
         <li class='last'><a href='viewjob.htm'><span><img src="css/image/Actions-view-pim-tasks-icon - Copy.png" alt=""/>View Job</span></a></li>
         <li class='last'><a href='newjob.htm'><img src="css/image/user-add-icon.png" alt=""/><span>Add Job</span></a></li>
 		<li class='last'><a href='blackouthours.htm'><img src="css/image/black-list.png" alt=""/><span>&nbsp;&nbsp;&nbsp;Black Out Hour</span></a></li>         	
      </ul>
   </li>
</sec:authorize>
<sec:authorize access="hasAnyRole('REPORTER','ADMIN','ACCOUNTMANAGER')">
   <li class='has-sub'><a href='#'><span><img src="css/image/Documents-icon.png" alt=""/>File Store</span></a>
   	  <ul>
         <li class='last'><a href='fileviewer.htm'><span><img src="css/image/Documents-icon.png" alt=""/>View Filestore</span></a></li>
         <li class='last'><a href='playlistviewer.htm'><img src="css/image/black-list.png" alt=""/><span>Playlist</span></a></li>    	
      </ul>
   </li>
   </sec:authorize>
   
   <sec:authorize access="hasAnyRole('REPORTER','ADMIN','ACCOUNTMANAGER')">
   <li class='last'><a href='cdr.htm'><span><img src="css/image/Documents-icon.png" alt=""/>CDR</span></a></li>
   </sec:authorize>
   <sec:authorize access="hasAnyRole('MAINTENANCE','ADMIN','ACCOUNTMANAGER')">
   <li class='has-sub'><a href='#'><span><img src="css/image/SchedulerIcon.png" alt=""/>Upload Numbers</span></a>
   		<ul>
         <li class='last'><a href='blacklist.htm'><span><img src="css/image/Blacklist-icon.png" alt=""/>Blacklist Number</span></a></li>
         <li class='last'><a href='whitelist.htm'><img src="css/image/Actions-view-pim-tasks-icon - Copy.png" alt=""/><span>Whitelist Number</span></a></li>
 		<li class='last'><a href='redcarpet.htm'><img src="css/image/Moleskine-Red-Book-icon.png" alt=""/><span>Redcarpet Number</span></a></li>         	
      </ul>
   </li>
</sec:authorize>
<sec:authorize access="hasAnyRole('REPORTER','ADMIN','ACCOUNTMANAGER')">
   <li class='last'><a href='<%=UploadConfig.suburl%>'><span><img src="css/image/Documents-icon.png" alt=""/>Configure Subscription</span></a></li>
   </sec:authorize>
   
</ul>
</div>
