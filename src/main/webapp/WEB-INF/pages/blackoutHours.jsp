<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link rel="stylesheet" type="text/css" href="css/newuser.css" />
<script src="js/jquery-1.9.1.js"></script>
  
  <!--[if lt IE 9]>
      <script src="js/respond.min.js"></script>
      <script src="js/html5.js"></script>
    <![endif]-->
    
<link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="css/bootstrap-datetimepicker.min.css"/>
<script type="text/javascript" src="js/bootstrap.min.js"></script>


<script type="text/javascript">
function formValidation()  
{
	var stime = document.getElementById("starttime").value;
	test = (/^(?:(?:([01]?\d|2[0-3]):)?([0-5]?\d):)?([0-5]?\d)$/.test(stime));
	if(test==false)
		{
		alert("Start Time not in correct format!");
		return false;
		}
	
	var etime = document.getElementById("endtime").value;  
	test = (/^(?:(?:([01]?\d|2[0-3]):)?([0-5]?\d):)?([0-5]?\d)$/.test(etime));
	if(test==false)
	{
	alert("End Time not in correct format!");
	return false;
	}
	return true;
	}
</script>
<div id="stylized" class="myform">
<form:form method="POST" commandName="black" onSubmit="return formValidation();" autocomplete="off">
		<h1>Black Out Hours</h1>
<label>Start Time
 <span class="small">HH:MM:SS</span>
   </label>
 <div class='input-append' id='datetimepicker1'>
 <span class='add-on'>
 	<form:input data-format='hh:mm:ss' path="startBtime" type="text" id="starttime" placeholder="23:59:59" />
 	<i data-date-icon='icon-calendar' data-time-icon='icon-time'></i>
 	</span>
 </div>
 <label>End Time
 <span class="small">HH:MM:SS</span>
   </label>
 
 <div class='input-append' id='datetimepicker2'>
 <span class='add-on'>
 
 <form:input data-format='hh:mm:ss' path="endBtime" type="text" id="endtime" placeholder="23:59:59" />
 <i data-date-icon='icon-calendar' data-time-icon='icon-time'></i>
 </span>
 </div>
 <input  type="submit" name="submit" value="Submit" class=addjobbtn  >
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