<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="css/viewjob.css" />
<link rel="stylesheet" href="css/pagination.css" />
<script src="js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/viewjob.js"></script>

 <script type="text/javascript">

$(function() {

$(".delbutton").click(function(){
	
//Save the link in a variable called element
var element = $(this);

//Find the id of the link that was clicked
var del_id = element.attr("id");

//Built a url to send
var info = 'jobname=' + del_id;
 if(confirm("Sure you want to delete this job? There is NO undo!"))
		  {

 $.ajax({
   type: "GET",
   url: "./deletejob.htm",
   data: info,
   success: function(){

	  // alert("job has been deleted !");
   
   }
 });
  $(this).parents(".tbl-item").animate({ backgroundColor: "#fbc7c7" }, "fast")
  .animate({ opacity: "hide" }, "slow");

 }

return false;

});

});
</script>


<script type="text/javascript">

$(function() {
	
	

$(".stopbutton").click(function(){
	
//Save the link in a variable called element
var element = $(this);

//Find the id of the link that was clicked
var job_id = element.attr("id");
//Built a url to send
var info = 'jobname=' + job_id;
var datastring = "http://<%=request.getHeader("host") %>/SchedulerNew/stop/"+job_id+""; 
 if(confirm("Sure you want to change the status of this job? There is NO undo!"))
		  {
	 		
				 $("#loading-div-background").show();
				 $("#content").css({"z-index":"-999999"});
				 $("#loading-div-background").css({ opacity: 0.8 });
				 $(".has-sub").css({"z-index":"0"});
				
				 
	 
 $.ajax({
   type: "GET",
    url: datastring,
   data: info,
   success: function(response){
	   if(response=='ok')
		   {
		   alert("Job status has changed..");
		   window.location.reload();
	  	   }else { alert(response);}
	   
	   $("#loading-div-background").hide();
		 $("#content").css({"z-index":"0"});
		 $("#loading-div-background").css({ opacity: 0 });
		 $(".has-sub").css({"z-index":"1000"});
	   
   }
 });
 
 }
return false;

});

});
</script>



<script type="text/javascript">

$(function() {

$(".resumebutton").click(function(){
	
//Save the link in a variable called element
var element = $(this);

//Find the id of the link that was clicked
var job_id = element.attr("id");
//Built a url to send
var info = 'jobname=' + job_id;
var datastring = "http://<%=request.getHeader("host") %>/SchedulerNew/resume/"+job_id+""; 
 if(confirm("Sure you want to change the status of this job? There is NO undo!"))
		  {
	 $("#loading-div-background").show();
	 $("#content").css({"z-index":"-999999"});
	 $("#loading-div-background").css({ opacity: 0.8 });
	 $(".has-sub").css({"z-index":"0"});
 $.ajax({
   type: "GET",
    url: datastring,
   data: info,
   success: function(response){
	   if(response=='ok')
		   {
		   alert("Job status has changed..");
		   window.location.reload();
	  	   }else { alert(response);}
	   $("#loading-div-background").hide();
		 $("#content").css({"z-index":"0"});
		 $("#loading-div-background").css({ opacity: 0 });
		 $(".has-sub").css({"z-index":"1000"});
   }
 });
 
 }
return false;

});

});
</script>


<script type="text/javascript">

$(function() {

$(".pausebutton").click(function(){
	
//Save the link in a variable called element
var element = $(this);

//Find the id of the link that was clicked
var job_id = element.attr("id");
//Built a url to send
var info = 'jobname=' + job_id;
var datastring = "http://<%=request.getHeader("host") %>/SchedulerNew/pause/"+job_id+""; 
 if(confirm("Sure you want to change the status of this job? There is NO undo!"))
		  {
	 $("#loading-div-background").show();
	 $("#content").css({"z-index":"-999999"});
	 $("#loading-div-background").css({ opacity: 0.8 });
	 $(".has-sub").css({"z-index":"0"});
	
 $.ajax({
   type: "GET",
    url: datastring,
   data: info,
   success: function(response){
	   if(response=='ok')
		   {
		   alert("Job status has changed..");
		   window.location.reload();
	  	   }else { alert(response);}
	   $("#loading-div-background").hide();
		 $("#content").css({"z-index":"0"});
		 $("#loading-div-background").css({ opacity: 0 });
		 $(".has-sub").css({"z-index":"1000"});
   }
 });
 
 }
return false;

});

});
</script>


 
 
<script>
			$('document').ready(function(){
				$('#content').jplist({
				
					items_box: '.demo-tbl tbody' 
					,item_path: '.tbl-item' 
					,panel_path: '.panel'
					
					//cookies
					,cookies: true
					,expiration: -1 //cookies expiration in minutes (-1 = cookies expire when browser is closed)
					,cookie_name: 'jplist-table-2'
					
					,redraw_callback: function(){
						
						var row_list = $('.tbl-item');
						var row;
						
						for(var i=0; i<row_list.length; i++){
						
							row = row_list.eq(i);
							if(i%2 == 0){
								row.addClass('even');
							}
							else{
								row.addClass('odd');
							}
						}
					}
				});
			});
		</script>
		
 
<script>
function Setheight()
{
	var height = $('#campaignlistt').height();
	height = height + 170;
	document.getElementById('content').style.height= height+"px";	
}

</script>

<script type="text/javascript">
        $(document).ready(function() {
        	
        	var height = $('#campaignlistt').height();
        	height = height + 170;
        	document.getElementById('content').style.height= height+"px";
        	
              setInterval("location.reload();", 60000);
        });    
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
         background-color: #FFFFFF;
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

<div id="content" class="box jplist table-layout-2">

	<div id="contentHeader">
		<h1>View Jobs</h1>
	</div>
	<!-- #contentHeader -->

	<div class="container">

		<FORM method="POST" name="listform" action="#" id="listform">
			<INPUT value="action_set" type="hidden" name="form_action">

			<div class="grid-24">

				<div class="widget widget-table">

					<div class="widget-header"></div>

					<div class="widget-content">
					
					<!-- panel -->
						<div class="panel box panel-top">						
							
							<div class="reset-box left">
								<input 
									type="button" 
									class="reset-btn" 
									value="Reset" 
									data-control-type="reset" 
									data-control-name="reset" 
									data-control-action="reset"
								/>
							</div>
							
							<div 
								class="drop-down" 
								data-control-type="drop-down" 
								data-control-name="paging" 
								data-control-action="paging">
								<ul Onclick="Setheight();" id="getiitem" class="sagar">
									<li><span data-number="10" data-default="true"> 10 per page </span></li>
									<li><span data-number="25"> 25 per page </span></li>
									<li><span data-number="50"> 50 per page </span></li>
									<li><span data-number="100"> 100 per page </span></li>
									<li><span data-number="all"> view all </span></li>
								</ul>
							</div>
							
					 
							
							<!-- filter -->
							<div class="filter">	

								<!--[if IE]><div class="search-title left">Filter by title: </div><![endif]-->
								<input 
									data-path=".servicename" 
									type="text" 
									value="" 
									placeholder="Filter by Service Name" 
									data-control-type="textbox" 
									data-control-name="title-filter" 
									data-control-action="filter" />
								
								<!--[if IE]><div class="search-title left">Filter by description: </div><![endif]-->							
								<input 
									data-path=".jobname" 
									type="text" 
									value="" 
									placeholder="Filter by Job Name" 
									data-control-type="textbox" 
									data-control-name="desc-filter" 
									data-control-action="filter" />	
																								
							</div>
							
							<div 
								class="drop-down" 
								data-control-type="drop-down" 
								data-control-name="priority-filter" 
								data-control-action="filter">
								<ul>
									<li><span data-path="default">Filter by priority</span></li>
									<li><span data-path=".priority1">1</span></li>
									<li><span data-path=".priority2">2</span></li>
									<li><span data-path=".priority3">3</span></li>
									<li><span data-path=".priority4">4</span></li>
									<li><span data-path=".priority5">5</span></li>
									<li><span data-path=".priority6">6</span></li>
									<li><span data-path=".priority7">7</span></li>
									<li><span data-path=".priority8">8</span></li>
									<li><span data-path=".priority9">9</span></li>
									<li><span data-path=".priority10">10</span></li>
								</ul>
							</div>
								
								
								
								<div 
								class="drop-down" 
								data-control-type="drop-down" 
								data-control-name="status-filter" 
								data-control-action="filter">
								<ul>
									<li><span data-path="default">Filter by status</span></li>
									<li><span data-path=".statusprocessed">Processed</span></li>
									<li><span data-path=".statusscheduled">Scheduled</span></li>
									<li><span data-path=".statusexpired">Expired</span></li>
									<li><span data-path=".statusdeleted">Deleted</span></li>
									<li><span data-path=".statusstopped">Stopped</span></li>
									<li><span data-path=".statusrunning">Running</span></li>
									<li><span data-path=".statuspaused">Paused</span></li>
									
									
								</ul>
							</div>
					 
							
							<div 
								class="paging-results" 
								data-type="Page {current} of {pages}" 
								data-control-type="label" 
								data-control-name="paging" 
								data-control-action="paging">
							</div>
							
							<div 
								class="paging" 
								data-control-type="placeholder" 
								data-control-name="paging" 
								data-control-action="paging">
							</div>	
							
						</div>
						
					
						<div class="box text-shadow">
						<table id="campaignlistt"  class="demo-tbl">
							<thead>
								<tr>
									<th>Service name</th>
									<th>Short Code</th>
									<th>Job Name</th>
									<th>Start Date</th>
									<th>End Date</th>
									<th>Start Time</th>
									<th>End Time</th>
									<th>Priority</th>
									<th>Status</th>
									<th>Action</th>


								</tr>
							</thead>
							<tbody>
								<!-- tr -->

								<c:forEach items="${service}" var="servicelist">
									<tr class='tbl-item'>
										<td class="servicename"><c:out
												value="${servicelist.mxgraph.serviceName}" /></td>
										<td class="center"><c:out
												value="${servicelist.mxgraph.shortcode}" /></td>
										<td class="jobname"><c:out value="${servicelist.jobname}" /></td>
										<td class="center"><c:out
												value="${servicelist.startDate}" /></td>
										<td class="center"><c:out value="${servicelist.endDate}" /></td>
										<td class="center"><c:out
												value="${servicelist.startTime}" /></td>
										<td class="center"><c:out value="${servicelist.endTime}" /></td>
										<td class="priority${servicelist.priority}"><c:out value="${servicelist.priority}" /></td>
										<td class="status${servicelist.status}"><c:out value="${servicelist.status}" /></td>
										
										<c:if test="${servicelist.status== 'paused'}">
											
											<td class="center">
											<span class="ticket resume-job firstsep">
											<a href="#" id="${servicelist.jobname}" class="resumebutton" style="color: #FFF; text-decoration: none;">Resume</a>
											</span>
												
											<span class='seprator'> </span>
											&nbsp;<span class="ticket edit-job sep"><a href='./geteditjobrequest.htm?jobname=${servicelist.jobname}' style="color: #FFF; text-decoration: none;">Edit</a></span></td>


										</c:if>


										<c:if test="${servicelist.status== 'stopped'}">
											<td class="center"><span class="ticket delete-job firstsep"><a
													href="#" id="${servicelist.jobname}" class="delbutton" style="color: #FFF; text-decoration: none;">Delete
												</a></span><span class='seprator'> </span></td>


										</c:if>
										<c:if test="${servicelist.status== 'expired'}">
											<td class="center"><span class="ticket delete-job firstsep"><a
													href="#" id="${servicelist.jobname}" class="delbutton" style="color: #FFF; text-decoration: none;">Delete
												</a></span><span class='seprator'> </span></td>


										</c:if>
										<c:if test="${servicelist.status== 'scheduled'}">
											<td class="center"><span class="ticket stop-job firstsep">
											
											<a href="#" id="${servicelist.jobname}" class="stopbutton" style="color: #FFF; text-decoration: none;">Stop</a>
											
											 
												
												</span>&nbsp;<span class='seprator'> </span> &nbsp;<span class="ticket edit-job sep"><a href='./geteditjobrequest.htm?jobname=${servicelist.jobname}'
													style="color: #FFF; text-decoration:none;">Edit</a></span></td>


										</c:if>
										<c:if test="${servicelist.status== 'running'}">
											<td class="center"><span class="ticket stop-job firstsep " ><a href="#" id="${servicelist.jobname}" class="stopbutton" style="color: #FFF; text-decoration: none;">Stop</a>
												</span> <span class='seprator'> </span>&nbsp;
												<span class="ticket pause-job sep">
												
												<a href="#" id="${servicelist.jobname}" class='pausebutton' style="color: #FFF; text-decoration: none;">Pause</a></span></td>


										</c:if>
										<c:if test="${servicelist.status== 'processed'}">
											<td class="center"><span class="ticket delete-job firstsep"><a
													href="#" id="${servicelist.jobname}" class="delbutton" style="color: #FFF; text-decoration: none;">Delete
												</a></span><span class='seprator'> </span></td>


										</c:if>
										
										
											<c:if test="${servicelist.status== 'deleted'}">
											<td class="center">&nbsp;</td>


										</c:if>
										


									</tr>
									
									
									
									
								</c:forEach>



							</tbody>
						</table>
							</div>
						<!-- end of data -->
						
						<div class="box jplist-no-results text-shadow align-center">
							<p style='color: #FF0000;
    font-size: 14px;
    font-weight: bold;
    margin-top: 8px;
    padding-top: 5px;'>No results found</p>
						</div>
						
						<!-- panel -->
						<div class="panel box panel-bottom" style="margin-left:10px;">						
							
					 		<div 
								class="paging-results" 
								data-type="{start} - {end} of {all}" 
								data-control-type="label" 
								data-control-name="paging" 
								data-control-action="paging">
							</div>
							
							<div 
								class="paging" 
								data-control-type="placeholder" 
								data-control-name="paging" 
								data-control-action="paging">
							</div>	
							
						</div>
						
						
						
					</div><!-- widget-content -->
					
		<!-- .widget -->
		</div><!-- .grid -->
		
</div>
</FORM>
</div>
<!-- .widget-content -->



<div class="actions">
	 
</div><!-- .actions -->

</div>

<!-- .container -->
 


