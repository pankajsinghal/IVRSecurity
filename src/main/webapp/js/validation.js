$(function() {
$( "#datepicker").datepicker({ minDate: 0, maxDate: "+1M +10D" });
$( "#datepicker1").datepicker({ minDate: 0, maxDate: "+1M +10D" });
   
});
	
function formValidation()  
	{  
		var sname = document.getElementById("servicename").value;  
		var sdate = document.getElementById("datepicker").value;  
		var edate = document.getElementById("datepicker1").value;  
		
						
		if(sname == "Default")  
		{  
			alert('Select your service from the list');  
			document.getElementById("servicename").focus();
			return false;
		}
	
		var JobName = document.getElementById("jobname").value;   
		
		 if(JobName=="")
			 {
			 
				alert('Enter your job name !');  
				document.getElementById("jobname").focus();
				return false;
			 }
		 
		 var job_len = jobname.value.length;
		 if(job_len > 20)
			 {
			 	alert("Job name should not grater than 20 characters !");
			 	document.getElementById("jobname").focus();
			 	return false;
			 }
		  
		 
		 if(/[^a-zA-Z0-9_-]/.test(JobName))
			 {
			 alert('You may only enter alpha (a-z), numeric (0-9) charcters and spicial charector(-, _)for the Job name');  
			 document.getElementById("jobname").focus();
			 return false;
			 }
		 
		 var CheckJob = document.getElementById("check_job").value; 
			 
		 if(CheckJob=="")
			 {
			 
				alert("Job name already Exist in the database try another !");
				document.getElementById('jobname').focus();
				return false;
				
			 }
		 
		 
		var  lenstarcopy = $('#statcopy').prop('checked');
		var  recorddedicationgroup = $('#recordDedication').prop('checked');
		if(lenstarcopy){}
		else if(recorddedicationgroup){}
		else { 	
		var e = Date.parse(edate);
		var s = Date.parse(sdate);
		if(e<s)
		{
			alert('End Date should be greater than Start Date');
			document.getElementById("datepicker1").focus();
			return false;
		}
		
		
		if(sdate=="")
			{
				alert("Please enter Start Date !");
				document.getElementById("datepicker").focus();
				return false;
			}
		
		

		if(edate=="")
			{
				alert("Please enter End Date !");
				document.getElementById("datepicker1").focus();
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
		
		 }

/*		
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
		
		
	*/	
		
		var value = document.getElementById("snamecli").value;   
		
		val = (/^[0-9]+(,[0-9]+)*$/.test(value));
		//val = (/^(\d{10},)*\d{10}$/.test(value));
		 if(val==false)
		 {
		   alert("Cli numbers should be in digits with comma separated!");
		   document.getElementById("snamecli").focus();
		   return false;
		 }
		 
		 
		 if(lenstarcopy){
			 
		 }else if(recorddedicationgroup){}
		 else{
		 
		 var fname = document.getElementById("fname").value;
		 
		 if(fname=="")
			 {
			 	
			 	alert("please upload csv file only..!");
				document.getElementById("fname").focus();
				return false;  
			 }
		 
		 var re = /(\.csv|\.CSV)$/i;
		 if(!re.exec(fname))
		 {
			alert("please upload csv file only..!");
			document.getElementById("fname").focus();
			return false;  
		 }
		 }
		 
		 
		 var upriority = document.getElementById("priority").value;
		 
			if(upriority == "Default")  
			{  
				alert('Select your priority from the list');  
				document.getElementById("priority").focus();
				return false;  
			} 
			
		 
				var returnresult = generatejson();
				
				if(returnresult=='maxretrynull'){alert("Please enter Max Retry value only numeric"); document.getElementById("max_retry").focus(); return false;}
				
				if(returnresult=='retryreasonnotnumeric'){ alert("Please Enter Retry Reason only numbers !"); return false;}
				
				if(returnresult=='retryvalueisbig'){alert("Retry Reason value should not greater than to Max Retry value !");return false;}
				
				jobloader();
				return true;
			
	}


function generatejson()
{
 var $textboxes = $('input[name="retry[]"]');
 var $textboxes_val = $('input[name="retryval[]"]');

var len = $textboxes.length;
var add_longcode='';
var maxRetry = '';
var max_retry = document.getElementById("max_retry").value;

var max_lavel='max-retry';

var maxRetry = maxRetry + '"' + max_lavel + '"' + ':' + '"'+ max_retry + '"' + ","; 

for(var i=0;i<len;i++)
   {
	 var value1 = $textboxes.eq(i).val();
	 
	 var retry_value = $textboxes_val.eq(i).val();
	 if(i==len-1){
		 var add_longcode = add_longcode + '"' + retry_value + '"' + ':' + '"'+value1 + '"';
	 }
	 else{
		 var add_longcode = add_longcode + '"' + retry_value + '"' + ':' + '"'+value1 + '"' + ","; 
	 }
	 
 } 

$("#retryreason").val("{"+ maxRetry + add_longcode+"}");


if(max_retry=="")
	{
		return "maxretrynull";
	}


var chks = document.getElementsByName('retry[]');                 
for (var i = 0; i < chks.length; i++)
{
	var num = chks[i].value;
	if (isNaN(num) || num=="")
	{
	chks[i].focus();
		return "retryreasonnotnumeric";
	}
	
}


for (var i = 0; i < chks.length; i++)
{
	var num = chks[i].value;
	if (parseInt(max_retry) < parseInt(num))
	{
		chks[i].focus();
		return "retryvalueisbig";
	}
	
}


}


function jobloader()
{
	$('#submitbtn').hide();
	$('#submitload').show();
}

$(document).ready(function() {
	
$('#submitload').hide();
 $('#usernameLoading').hide();
 $('#usernameduplicate').hide();
 $('#usernamedone').hide();

 $('#jobname').blur(function(){
	 $('#usernameduplicate').hide();
	 $('#usernamedone').hide();
 
	 var JobName = document.getElementById("jobname").value;  
 
	 if(JobName=="")
	  {
	  	$('#usernameLoading').hide();
	  
	  }else{
	 
	 $('#usernameLoading').show();
	  }
	 $('#usernameduplicate').hide();
	 $('#usernamedone').hide();
	 
  
    $.post("./duplicate.htm", {
    jobname: $('#jobname').val()
      }, function(response){
    	    	  
    	$('#usernameResult').fadeOut();
        setTimeout("finishAjax('usernameResult', '"+escape(response)+"')", 400);
      });
     return false;
 });
});

function finishAjax(id, response) {
	
  $('#usernameLoading').hide();
 
  var JobName = document.getElementById("jobname").value;   
  var checkvalid = /[^a-zA-Z0-9_-]/.test(JobName);

  if(JobName=="")
	  {
	  	$('#usernameLoading').hide();
	  
	  }else {
  
  if(checkvalid==true)
	  {
		  response = 'Invalid job name';
		  $('#usernameduplicate').show(); 
		  $('#'+id).html(unescape(response));
		  $('#'+id).fadeIn();
	}else{
  
  if(response=="Jobname%20Exists%20please%20enter%20another%20%3Cinput%20type%3D%27hidden%27%20name%3D%27check_job%27%20id%3D%27check_job%27%3E")
  {
  $('#usernamedone').hide();
  $('#usernameduplicate').show();
  }else { $('#usernameduplicate').hide(); $('#usernamedone').show(); }
	}
	  }
  $('#'+id).html(unescape(response));
  $('#'+id).fadeIn();
} //finishAjax



function showMe (it, box) {
  var vis = (box.checked) ? "block" : "none";
  document.getElementById(it).style.display = vis;
}


function showStarcopy (box) {
	
	if(box=='normal')
		{
		
		 $("#datepicker").removeAttr("disabled");
		 $("#datepicker1").removeAttr("disabled");
		 $("#starttime").removeAttr("disabled");
		 $("#endtime").removeAttr("disabled");
		 $("#fname").removeAttr("disabled");
		 $("#starttime").val("09:00:00");
		 $("#endtime").val("21:00:00");
		 var fullDate = new Date();
		 var twoDigitMonth = ((fullDate.getMonth().length+1) === 1)? (fullDate.getMonth()+1) : '0' + (fullDate.getMonth()+1);
		 var currentDate = twoDigitMonth + "/" + fullDate.getDate() + "/" + fullDate.getFullYear();
		 $("#datepicker").val(currentDate);
		 $("#datepicker1").val(currentDate);
		 $('#retrygroup').show();
		
		}else if(box=='statcopy'){
			
			$("#datepicker").attr("disabled", "disabled");
			  $("#datepicker").val("01/01/1900");
			  $("#datepicker1").attr("disabled", "disabled");
			  $("#datepicker1").val("01/01/2400");
			  $("#starttime").attr("disabled", "disabled");
			  $("#starttime").val("00:00:00");
			  $("#endtime").attr("disabled", "disabled");
			  $("#endtime").val("23:59:59");
			  $("#fname").attr("disabled", "disabled");
			  $('#retrygroup').show();
			
		}else if(box=='recordDedication'){
			
			$("#datepicker").attr("disabled", "disabled");
			  $("#datepicker").val("01/01/1900");
			  $("#datepicker1").attr("disabled", "disabled");
			  $("#datepicker1").val("01/01/2400");
			  $("#starttime").attr("disabled", "disabled");
			  $("#starttime").val("00:00:00");
			  $("#endtime").attr("disabled", "disabled");
			  $("#endtime").val("23:59:59");
			  $("#fname").attr("disabled", "disabled");
			  $('#retrygroup').hide();
			
		}
	
 
	  
	}


function getjsonfaliourresion()
{
var len=$('#retrygroup').find('input[type=checkbox]:checked').length;
if(len)
 {
	 $("#loading-div-background").show();
	 $("#content").css({"z-index":"-999999"});
	 $("#loading-div-background").css({ opacity: 0.8 });
	 $(".has-sub").css({"z-index":"0"});
	
   var dataString = '';
	  $.ajax({
       type: "POST",
       url: "./getObdFailureReasons",
       data: dataString,
       success: function(response){
    	   
    	   if(response=='')
    	   {
    		 $("#loading-div-background").hide();
      		 $("#content").css({"z-index":"0"});
      		 $("#loading-div-background").css({ opacity: 0 });
      		 $(".has-sub").css({"z-index":"1000"});
    		   
    		  alert('Retrry resion not loaded please try again');
    		  $('#checkretry').prop('checked', false);
    	   }else{
    	   
    	   var str = "";
    	   var max_retry="";
    	   var obj = $.parseJSON(response);
    	   for (var prop in obj) {
    	      //alert(prop + " is " + obj[prop]);
    		  str = str + "<label>"+ obj[prop] +": </label><input type='text' name='retry[]' /><input type='hidden' name='retryval[]' value='"+ prop +"'/>";
     	      }
    	   
    	   	 var lavel = "Max Retry";
    	   	 max_retry = max_retry + "<label>"+ lavel +": </label><input type='text' name='maxretry' id='max_retry' onkeypress='return isNumberKey(event)'/>";
    	   	
    	     $("#failureresion").html(max_retry+str);
    	     
    	   }
    	     
    	     $("#loading-div-background").hide();
    		 $("#content").css({"z-index":"0"});
    		 $("#loading-div-background").css({ opacity: 0 });
    		 $(".has-sub").css({"z-index":"1000"});
    	    
       }//response
     });
}else { var str=''; $("#failureresion").html(str);} 
}

