/**
 * @author: Anjali Sarkar
 */
function formValidation()  
{  
	var sname = document.getElementById("servicename").value;  
	var sdate = document.getElementById("datepicker").value;  
	var edate = document.getElementById("datepicker1").value;  
	var stime = document.getElementById("starttime").value;  
	var etime = document.getElementById("endtime").value;  
	//var dndc = document.getElementById("check").value;  
	var cli = document.getElementById("sname").value;  
	//var msisdn = document.getElementById("fname").value;  
	var upriority = document.getElementById("priority").value;
	
	alert(sname);
	
	
	
	
	if(servicename_validation(sname))  
	{  
		if(date_validation(sdate,edate))  
		{  
			if(time_validation(stime,etime))  
			{  
				if(clicheack_validation(cli))  
				{ 
					if(priority_validation(upriority))
						{
						}
				}  
			}   
		}  
	}   

return false;

}  



function servicename_validation(sname)  
{  
	if(sname.value == "Default")  
	{  
		alert('Select your service from the list');  
		document.getElementById("servicename").focus();
		
		return false;  
	}  
		return true;  
	  
}  
function date_validation(sdate,edate)  
{  
	var s = Date.parse(sdate.value);
	var e = Date.parse(edate.value);
	if(e<s)
	{
		alert('End Time should be greater than Start Time');
		edate.focus();
		return false;
	}
	else
		return true;
}  
function time_validation(stime,etime)  
{   
	var timeformat=	/^(?:2[0-3]|[01]?[0-9]):[0-5][0-9]:[0-5][0-9]$/;
	if(stime.value.match(timeformat)|(etime.value.match(timeformat)))
		return true;
	else 
	{
		alert('time not in correct format');
		stime.focus();
		return false;
	}
}  
function clicheack_validation(cli)  
{ 
	var temp=0;
	var clinumber = cli.value.spilt(",");
	for(var i=0;i<clinumber.length;i++)
	{
		if(clinumber[i].length!=10)
		{
			temp=1;
			break;
		}
		else
			continue;
	}
	if(temp==1)
	{
		alert('numbers not in correct format: number should be 10 digit long');
		cli.focus;
		return false;
		break;
	}
	else
		return true;
}
function priority_validation(upriority)  
{  
	if(upriority.value == "Default")  
	{  
		alert('Select your priority from the list');  
		upriority.focus();  
		return false;  
	}  
	else  
	{  
		return true;  
	}  
}  

