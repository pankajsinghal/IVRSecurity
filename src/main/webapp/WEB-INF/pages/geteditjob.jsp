<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="css/jquery-ui.css" />
<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css" />
<link rel="stylesheet" type="text/css" href="css/newuser.css" />

<!--[if lt IE 9]>
      <script src="js/respond.min.js"></script>
      <script src="js/html5.js"></script>
    <![endif]-->

<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap-datetimepicker.min.css" />
<script type="text/javascript" src="js/bootstrap.min.js"></script>

<script>
	$(function() {
		$("#datepicker").datepicker({
			minDate : 0,
			maxDate : "+1M +10D"
		});
		$("#datepicker1").datepicker({
			minDate : 0,
			maxDate : "+1M +10D"
		});
		$("#check")
				.change(
						function() {
							if ($("#check").is(':checked')) {
								$("p")
										.append(
												'<div class= "dropdown"><label>DND Filter Options</label> <select path="dndOption"  id="DNDoption" class="other"><option value=0>Select All</option><option value=1>Banking/Insurance/Financial products/Credit cards</option><option value=2>Real Estate</option><option value=3>Education</option><option value=4>Health</option><option value=5>Consumer goods and Automobiles</option><option value=6>Communication/Broadcasting/Entertainment/IT</option><option value=7>Tourism and Leisure</option></select></div>');
							} else
								$(".dropdown").remove();
						});

	});
</script>
<script>
	function formValidation() {
		 
		var sdate = document.getElementById("datepicker").value;
		var edate = document.getElementById("datepicker1").value;

		var e = Date.parse(edate);
		var s = Date.parse(sdate);
		if (e < s) {
			alert('End Date should be greater than Start Date');
			document.getElementById("endtime").focus();
			return false;
		}

		if(e==s)
			{
				
				var starttime = document.getElementById("starttime").value;
				var endtime = document.getElementById("endtime").value;
				
				if (endtime < starttime)
				{
					alert("Enter end time less than start time for today date only");
					return false;
				}
				
				
				
			}
		
		var date = new Date();
		var DayToAdd = 7-1;
		var newdate = date.setDate(date.getDate() + DayToAdd); 
		if(s > newdate)
		{
			alert("Jobs can be scheduled for next 7 days only");
			document.getElementById("datepicker").focus();
			return false;
			
		}
		
		
		if(e > newdate )
		{
			alert("Jobs can be scheduled for next 7 days only");
			document.getElementById("datepicker1").focus();
			return false;
			
		}
		
		
		var stime = document.getElementById("starttime").value;
		test = (/^(?:(?:([01]?\d|2[0-3]):)?([0-5]?\d):)?([0-5]?\d)$/
				.test(stime));
		if (test == false) {
			alert("Start Time not in correct format!");
			return false;
		}

		var etime = document.getElementById("endtime").value;
		test = (/^(?:(?:([01]?\d|2[0-3]):)?([0-5]?\d):)?([0-5]?\d)$/
				.test(etime));
		if (test == false) {
			alert("End Time not in correct format!");
			return false;
		}
		
		
		
		var starttime = document.getElementById("starttime").value;
		  var endtime = document.getElementById("endtime").value;

		  var stimearray = starttime.split(":");
		  var newstarttime = stimearray[0];
		  
		  var etimearray = endtime.split(":");
		  var newetarttime = etimearray[0];
		  
		  if(newstarttime == newetarttime)
		   {
		   
		   alert("Start time and end time should not be same");
		   document.getElementById("endtime").focus();
		   return false;
		  
		   }
		

		var value = document.getElementById("snamecli").value;
		val = (/^(\d{10},)*\d{10}$/.test(value));
		if (val == false) {
			alert("Cli numbers should be of 10 digits and comma seperated!");
			return false;
		}
		var upriority = document.getElementById("priority").value;
		if (upriority == "Default") {
			alert('Select your priority from the list');
			document.getElementById("priority").focus();
			return false;
		}

		return true;

	}
</script>



	<div id="stylized" class="myform">

		<form:form method="POST" commandName="job" onSubmit="return formValidation();">
			<h1>Edit Job</h1>
			<label>Service Name <span class="small">Name</span>
			</label>

			<input type="text" readonly='readonly' disabled="disabled"
				value="${jobdetail.mxgraph.serviceName}" />

			<label>Job Name <span class="small">Name of Job</span>
			</label>
			<input disabled="disabled" type="text" readonly='readonly' value="${jobdetail.jobname}" />
			<form:input hidden="true" path="jobName" type="text" readonly='readonly' value="${jobdetail.jobname}" />

			<label>Start Date <span class="small">MM/DD/YYYY</span>
			</label>
			<form:input path="startDate" type="text" name="startdate"
				id="datepicker" value="${sdate}" />
			<label>End Date <span class="small">MM/DD/YYYY</span>
			</label>
			<form:input path="endDate" type="text" name="enddate"
				id="datepicker1" value="${edate}" />
				<label>Start Time <span class="small">HH:MM:SS</span>
				</label>
				<div class='input-append' id='datetimepicker1'>
					<span class='add-on'> <form:input data-format='hh:mm:ss'
							path="startTime" type="text" id="starttime"
							placeholder="23:59:59" value="${jobdetail.startTime}" /> <i
						data-date-icon='icon-calendar' data-time-icon='icon-time'></i>
					</span>
				</div>
				<label>End Time <span class="small">HH:MM:SS</span>
				</label>

				<div class='input-append' id='datetimepicker2'>
					<span class='add-on'> <form:input data-format='hh:mm:ss'
							path="endTime" type="text" id="endtime" placeholder="23:59:59"
							value="${jobdetail.endTime}" /> <i data-date-icon='icon-calendar'
						data-time-icon='icon-time'></i>
					</span>
				</div>
				<label>CLI <span class="small">Comma Separated</span>
				</label>
				
				<form:input path="cliNumber" type="text" name="clinum" id="snamecli" value="${cli}" />
				
				<label>Priority <span class="small">0-10</span>
				</label>
				
				<form:select path="priority" type="text" name="priority" id="priority">
				<c:forEach var="i" begin="1" end="10">
				<option value="${i}" ${i == jobdetail.priority ? 'selected' : ''}>${i}</option>
				</c:forEach>
				</form:select>
							
				<div id="submitbtn">
					<input type="submit" name="submit" value="Update Job"
						class=addjobbtn>
				</div>

				<div class="spacer"></div>
		</form:form>

	</div>


<script type='text/javascript'>
	$(function() {
		$('#datetimepicker1').datetimepicker({
			pickDate : false
		});
	});
</script>

<script type='text/javascript'>
	$(function() {
		$('#datetimepicker2').datetimepicker({
			pickDate : false
		});
	});
</script>

<script src="js/bootstrap-datetimepicker.min.js"></script>