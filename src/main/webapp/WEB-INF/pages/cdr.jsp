<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
 
<script>
$(function() {
	
	$( "#datepicker").datepicker({ minDate: -200, maxDate: "+0M +0D",dateFormat: 'dd-mm-yy' });
	   
	});

</script>


<script type="text/javascript">
function formValidation()  
{  
	 
	
	var Date = document.getElementById("datepicker").value;   
					
	if(Date == "")  
	{  
		alert('Enter your date');  
		document.getElementById("datepicker").focus();
		return false;
	}
	

	var Time = document.getElementById("time").value;   
	
	 if(Time=="")
		 {
		 
			alert('Enter your Time !');  
			document.getElementById("time").focus();
			return false;
		 }
	 
	 var Interval = document.getElementById("interval").value; 
	 if(Interval=="Default")
	 {
	 	alert('Select your Interval Time !');  
		document.getElementById("interval").focus();
		return false;
	 }
	
		 	jobloader();
			return true;
		
}

function jobloader()
{
$('#submitbtn').hide();
$('#submitload').show();
}

</script>

<script>
$(document).ready(function() {
	$('#submitload').hide();
});

</script>


<div id="stylized" class="myform">

 <form:form method="GET" commandName="cdr" action="downloadcdr.htm" enctype="multipart/form-data" name="cdr" onSubmit="return formValidation();">
  <h1>CDR</h1>
  
   <label>Date
 <span class="small">DD-MM-YYYY</span>
 </label>
 
 <input type="text" name="date" id="datepicker"  />
 
 <div class= "time">
 <label>Time
 <span class="small">HH:MM:SS</span>
   </label>
  <div class='input-append' id='timepicker'> 
   <span class='add-on'>
 <input data-format='hh:mm:ss' name="time" type="text" id="time" placeholder="23:59:59" />

      <i data-date-icon='icon-calendar' data-time-icon='icon-time'></i>
    </span>
    </div>

 </div>
 
 <label>Interval
 <span class="small">1-10</span>
   </label>

<select name="interval" id="interval" >
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
</select>
  
  
  <div id="submitbtn">   <input  type="submit" name="submit" value="Submit" class=addjobbtn > </div>
 <div id="submitload" class="succesloading"><span>Please Wait.......</span><img src="css/image/ajax-loader.gif" alt="Ajax Indicator" /></div>
 
 <div class="spacer"></div>
  
  
 </form:form>

 </div>
 
 <script type='text/javascript'>
  $(function() {
    $('#timepicker').datetimepicker({
      pickDate: false
    });
  });
</script>

<script  src="js/bootstrap-datetimepicker.min.js"></script> 
 
