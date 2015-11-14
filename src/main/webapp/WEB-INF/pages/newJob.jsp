<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<link rel="stylesheet" href="css/jquery-ui.css" />
<script src="js/jquery-1.9.1.js"></script>
 <script src="js/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css" />
<link rel="stylesheet" type="text/css" href="css/newuser.css"/>

  <!--[if lt IE 9]>
      <script src="js/respond.min.js"></script>
      <script src="js/html5.js"></script>
    <![endif]-->
    
<link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="css/bootstrap-datetimepicker.min.css"/>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/validation.js"></script>

 
 <script>
function isNumberKey(evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode;
    if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    } else {
        return true;
    }      
}
</script>

<style>

#loading-div-background 
    {
        display:none;
        position:fixed;
        top:0;
        left:0;
        background:black;
        width:100%;
        height:100%;
        
     }
 #loading-div
    {
         width: 300px;
         height: 200px;
         background-color: #dedede;
         text-align:center;
         position:absolute;
         left: 40%;
         top: 50%;
         margin-left:-40px;
         margin-top: -70px;
     }

 
</style>
    

 <div id="loading-div-background">
    <div id="loading-div" class="ui-corner-all" >
      <img style="height:50px;margin:50px;" src="css/image/wait.gif" alt="Loading.."/>
      <h2 style="color:gray;font-weight:bold;">Please wait....</h2>
     </div>
</div>



<div id="stylized" class="myform">

 <form:form method="POST" commandName="job" enctype="multipart/form-data" name="jobadd" onSubmit="return formValidation();" autocomplete="off">
  <h1>New Job</h1>
     <label>Service Name
 <span class="small">Name</span>
   </label>
   <form:select path="serviceName" id="servicename">
   				<option value="Default">--SELECT--</option>
				<c:forEach items="${mxGraphList}" var="service">
					<option>
						<c:out value="${service.serviceName}(${service.shortcode })" />
						</option>
				</c:forEach>
</form:select>
<label>Job Name
<span class="small">Name of Job</span>
</label>
<form:input path="jobName" type="text" id="jobname" />
<div id="usernameLoading" class="loadinging"><img src="css/image/loader.gif" alt="Ajax Indicator" /></div>
<div id="usernameduplicate" class="dupelicat"><img src="css/image/duplicate.png" alt="Ajax Indicator" width="21" height="21" /></div>
<div id="usernamedone" class="dupelicat"><img src="css/image/right.png" alt="Ajax Indicator" width="21" height="21" /></div>
<div id="usernameResult" class="errormsg_job">
 <input type="hidden" name="check_job" id="check_job" value='1'>
 </div> 
 
   <div class = "check" id="normarlgroup" >
<label>Normal
 <span class="small">Normal</span>
   </label>
 <form:radiobutton path="jobType" name="normal" id="normal" value='normal'  onclick="showStarcopy(this.id)" checked="checked" />
 </div> 
 

 <div class = "check" id='starcopygroup' >
<label>Star copy
 <span class="small">Star copy</span>
   </label>
 <form:radiobutton path="jobType" name="starcopy" id="statcopy" value='starcopy' onclick="showStarcopy(this.id)"  />
 </div>  
 
 
 <div class = "check" id="recorddedicationgroup" >
<label>Record Dedication
 <span class="small">Record Dedication</span>
   </label>
 <form:radiobutton path="jobType" name="recordDedication" id="recordDedication" value="recordDedication"  onclick="showStarcopy(this.id)"  />
 </div> 


 

 
 
 <label>Start Date
 <span class="small">MM/DD/YYYY</span>
 </label>
 <% Date date = new Date();%>
 <form:input path="startDate" type="text" name="startdate" id="datepicker" value="<%=new java.text.SimpleDateFormat(\"MM/dd/yyyy\").format(date)%>"/>
 <label>End Date
 <span class="small">MM/DD/YYYY</span>
 </label>
 <form:input path="endDate" type="text" name="enddate" id="datepicker1" value="<%=new java.text.SimpleDateFormat(\"MM/dd/yyyy\").format(date)%>"/>
 <div class= "time">
 
 
 <label>Start Time
 <span class="small">HH:MM:SS</span>
   </label>
  <div class='input-append' id='datetimepicker1'> 
   <span class='add-on'>
 <form:input data-format='hh:mm:ss' path="startTime" type="text" id="starttime" placeholder="23:59:59" value="09:00:00"/>

      <i data-date-icon='icon-calendar' data-time-icon='icon-time'></i>
    </span>
    </div>
 
 
 <label>End Time
 <span class="small">HH:MM:SS</span>
   </label>
 
   <div class='input-append' id='datetimepicker2'> 
    <span class='add-on'>
 <form:input data-format='hh:mm:ss' path="endTime" type="text" id="endtime" placeholder="23:59:59" value="21:00:00"/>
 
      <i data-date-icon='icon-calendar' data-time-icon='icon-time'></i>
    </span>
    </div>
 
 
 
<%-- <div class = "check" id='dndfilters'>
<label>DND Filter
 <span class="small">Dnd Filter</span>
   </label>
 <form:checkbox path="dndcheck" name="dnd" id="check" onclick="showMe('div1', this)"  />
 </div> --%>
 </div>
 
<div id="div1" style="display:none">
<div class= "dropdown"><label>DND Filter Options</label> 
<form:select path="dndOption"  id="DNDoption" class="other">
<option value=0>Select All</option>
<option value=1>Banking/Insurance/Financial products/Credit cards</option>
<option value=2>Real Estate</option>
<option value=3>Education</option>
<option value=4>Health</option>
<option value=5>Consumer goods and Automobiles</option>
<option value=6>Communication/Broadcasting/Entertainment/IT</option>
<option value=7>Tourism and Leisure</option>
</form:select>
</div>
 </div>
 
 
 
  <label>CLI
 <span class="small">comma seperated</span>
	</label>
   <form:input path="cliNumber" type="text" name="clinum" id="snamecli" />

 <label>MSISDN
 <span class="small">Upload Numbers</span>
	</label>
 <form:input path="msisdn" type="file" name="msisdnfile" id="fname" accept=".csv" />
 <label>Priority
 <span class="small">0-10</span>
   </label>
 <form:select path="priority" type="text" name="priority" id="priority" >
 <option value="Default" selected>-- SELECT --</option>
<option value="1">1</option>
<option value="2">2</option>
<option value="3">3</option>
<option value="4">4</option>
<option value="5">5</option>
<option value="6">6</option>
<option value="7">7</option>
<option value="8">8</option>
<option value="9">9</option>
<option value="10">10</option>
</form:select>

  
 <div class = "check" id='retrygroup'>
<label>Retry
 <span class="small">Retry</span>
   </label>
 <form:checkbox path="retrycheck" name="retry" id="checkretry" onclick="getjsonfaliourresion()"  />
 </div>  

 <div id="failureresion">
 
 </div>

<form:input type="hidden" id="retryreason" path="retryreason"/>
 
 <div id="submitbtn">    <input  type="submit" name="submit" value="Add Job" class=addjobbtn > </div>
 <div id="submitload" class="succesloading"><span>Please Wait.......</span><img src="css/image/ajax-loader.gif" alt="Ajax Indicator" /></div>
 
 <div class="spacer"></div>
 
 	</form:form>
 </div>
 
 <script type='text/javascript'>
  $(function() {
    $('#datetimepicker1').datetimepicker({
      pickDate: false
    });
  });
</script>

 <script type='text/javascript'>
  $(function() {
    $('#datetimepicker2').datetimepicker({
      pickDate: false
    });
  });
</script>

<script  src="js/bootstrap-datetimepicker.min.js"></script> 
 