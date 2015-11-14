<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <script type="text/javascript" src="js/jquery.min.js"></script>
	<link href="css/viewservices.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="css/pagination.css" />
	<script src="js/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="js/viewjob.js"></script>
     <script type="text/javascript" src="js/stickytooltip.js">
	</script>
	<script type="text/javascript" src="js/managekeys.js"></script>
	
	<link rel="stylesheet" href="css/viewjob.css" />
 	<link rel="stylesheet" type="text/css" href="css/stickytooltip.css" />
 	<link rel="stylesheet" href="css/managekeys.css" />
 	


<script type="text/javascript">
    jQuery(document).ready(function($) {
      $('a[rel*=facebox]').facebox({//apply all anchor tag which has rel=facebox attribute
        loadingImage : 'css/img/loading.gif',
        closeImage   : 'src/closelabel.png'
      })
    })
  </script>
    
  
 
<style type="text/css">
.onoffswitch {
position: relative; width: 90px;
-webkit-user-select:none; -moz-user-select:none; -ms-user-select: none;
float:left;
}
.onoffswitch-checkbox {
display: none;
}
.onoffswitch-label {
display: block; overflow: hidden; cursor: pointer;
border: 2px solid #999999; border-radius: 20px;
}
.onoffswitch-inner {
width: 200%; margin-left: -100%;
-moz-transition: margin 0.3s ease-in 0s; -webkit-transition: margin 0.3s ease-in 0s;
-o-transition: margin 0.3s ease-in 0s; transition: margin 0.3s ease-in 0s;
}
.onoffswitch-inner:before, .onoffswitch-inner:after {
float: left; width: 50%; height: 30px; padding: 0; line-height: 30px;
font-size: 14px; color: white; font-family: Trebuchet, Arial, sans-serif; font-weight: bold;
-moz-box-sizing: border-box; -webkit-box-sizing: border-box; box-sizing: border-box;
}
.onoffswitch-inner:before {
content: "ON";
padding-left: 10px;
background-color: #2FCCFF; color: #FFFFFF;
}
.onoffswitch-inner:after {
content: "OFF";
padding-right: 10px;
background-color: #EEEEEE; color: #999999;
text-align: right;
}
.onoffswitch-switch {
width: 18px; margin: 6px;
background: #FFFFFF;
border: 2px solid #999999; border-radius: 20px;
position: absolute; top: 0; bottom: 0; right: 56px;
-moz-transition: all 0.3s ease-in 0s; -webkit-transition: all 0.3s ease-in 0s;
-o-transition: all 0.3s ease-in 0s; transition: all 0.3s ease-in 0s;
}
.onoffswitch-checkbox:checked + .onoffswitch-label .onoffswitch-inner {
margin-left: 0;
}
.onoffswitch-checkbox:checked + .onoffswitch-label .onoffswitch-switch {
right: 0px;
}
</style>
 
 
 <script>
			$('document').ready(function(){
				$('#content').jplist({
				
					items_box: '.demo-tbl tbody' 
					,item_path: '.tbl-item' 
					,panel_path: '.panel'
					
					//cookies
					,cookies: false
					,expiration: 1 //cookies expiration in minutes (-1 = cookies expire when browser is closed)
					,cookie_name: 'jplist-table-5'
					
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
	var height = $('#campaignlist').height();
	height = height + 170;
	document.getElementById('content').style.height= height+"px";
	
	 
}

</script>

<script type="text/javascript">
        $(document).ready(function() {
        	
        	var height = $('#campaignlist').height();
        	height = height + 170;
        	document.getElementById('content').style.height= height+"px";
        	
              //setInterval("location.reload();", 60000);
        });    
    </script>


 
<div id="content">

	<div id="contentHeader">
		<h1>View Services - IVR</h1>
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
								<ul Onclick="Setheight();" id="getiitem">
				
									<li><span data-number="all" data-default="true"> view all </span></li>

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
									data-path=".createdby" 
									type="text" 
									value="" 
									placeholder="Filter by created by" 
									data-control-type="textbox" 
									data-control-name="desc-filter" 
									data-control-action="filter" />							
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
						<table id="campaignlist"  class="demo-tbl">
							<thead>
								<tr>
									<th>Service name</th>
									<th>Created By</th>
									<th>Date</th>
									<th>Shortcode</th>
									<th>Action</th>


								</tr>
							</thead>
							<tbody>
								<!-- tr -->

								<c:forEach items="${mxgraphversionlist}" var="userList" varStatus="theCount"> 
								
								
									<script>
										$(document).ready(function(){
										$("#${userList.mxgraph.id}check").click(function(){
										var checkid = $(this).attr('id');
										var myonoffswitch=$("#${userList.mxgraph.id}check").val();
										if ($("#${userList.mxgraph.id}check:checked").length == 0)
										{
										var a=myonoffswitch;
										}
										else
										{
										var a="off";
										}
										
										
										if(a=='off')
											{
											 var urlpath='./release.htm';
											}
										
										if(a=='on')
											{
											  var urlpath='./remove.htm';
											}
										
																				
										var servicename = $("#${userList.mxgraph.id}name").val();
										var shortcode = $("#${userList.mxgraph.id}").val();
										var calltype = "IVR";
										var service = "true";
										var page="1";
										
										var dataString = 'servicename='+ servicename + '&shortcode='+ shortcode;
										
										 $.ajax({
												type: "POST",
												url: "./popup.htm",
												data: dataString,
												success: function(responselongcode){
													
													 var arrayOfStrings = responselongcode.split(',');
													 var len =  arrayOfStrings.length-1;
													 var  add_longcode="";
													 var  add_longcode_send="";
													 var conf;
													 for(var i=0;i<len;i++)
													 {
													 	longcode = arrayOfStrings[i];
													 	 var add_longcode = add_longcode +"Long Code: "+ longcode+"\n";
													 	 var add_longcode_send = add_longcode_send +"&longcode="+ longcode;
													 }
													 
													 if(add_longcode_send=='')
														 {
														 
														 add_longcode_send = "&longcode=";
														 
														 
														 if(a == 'off')
															 {
															 	conf = confirm("There are no longcodes for this service. The service will be DEPLOYED. Are you sure ?");
															 
															 }
														 else
															 {
															  	conf = confirm('There are no longcodes for this service. The service will be UNDEPLOYED. Are you sure ?'); 
															 }
														 														 	
														 }else { 
															 
															 if(a == 'off')
															 {
															 	conf = confirm("There are no longcodes for this service. The service will be DEPLOYED. Are you sure ?\n\n LONGCODE : " + add_longcode);
															 
															 }
														 else
															 {
															 conf = confirm('There are no longcodes for this service. The service will be UNDEPLOYED. Are you sure ?\n\n LONGCODE : ' + add_longcode);
															 }
														 }
													 
													if(conf==true)
														{
														
														var dataStringSend = 'servicename='+ servicename + '&shortcode='+ shortcode + '&calltype='+ calltype + add_longcode_send+'&Service='+service;
														
														$.ajax({
															url: urlpath,
															type: "POST",
															data: dataStringSend,
															success: function(html){
																
																if($("input."+html).filter(':checked').length == 1)
															        $("input."+html+":not(:checked)").attr('disabled', 'disabled');
															    else
															        $("input."+html).removeAttr('disabled');
																
														 	}
															
															});
														
														
														
														}else { 
															
															
															if(a=='off')
															{	
																$("#"+checkid).attr("checked",false);
															}
															
															if(a=='on')
															{
																//alert(checkid);
																
																$("#"+checkid).prop('checked', true);
																	
															}
																
														
														}
													 
													 
													
												}
												
												});
										 
										
								
										
										
										});
										
										 
										
										});
										
																				
										</script>
								
								<script>
								  $(document).ready(function() {
							    if($("input.${userList.mxgraph.shortcode}").filter(':checked').length == 1)
							        $("input.${userList.mxgraph.shortcode}:not(:checked)").attr('disabled', 'disabled');
							    else
							        $("input.${userList.mxgraph.shortcode}").removeAttr('disabled');
								
								    });    
								    </script>
								 
								 
								 
								 
								
								
								
									<tr class='tbl-item'>
									
									
								 
									
										<td class="servicename"><a href=" #" class="image" title="" data-tooltip ="sticky<c:out value="${theCount.count}" />"><c:out value="${userList.mxgraph.serviceName}" /></a></td>
										<td class="createdby"><c:out value="${username}" /></td>
										<td class="center"><c:out value="${userList.createdDate}" /></td>
										<td class="center"><c:out value="${userList.mxgraph.shortcode}" /></td>
									
										
										<td class="center" style="width:200px;"><span class='seprator'>
										
										
										<span class="onoffswitch">
										<input type="checkbox" name="onoffswitch" class="onoffswitch-checkbox ${userList.mxgraph.shortcode}" id="${userList.mxgraph.id}check" <c:if test="${userList.mxgraph.productionFlag== 'true'}">checked</c:if>>
										<label class="onoffswitch-label" for="${userList.mxgraph.id}check">
										<div class="onoffswitch-inner"></div>
										<span class="onoffswitch-switch"></span>
										</label>
										</span>
										<span id="${userList.mxgraph.serviceName}"></span>
									 
									 	<input type="hidden" name="hidden" id="${userList.mxgraph.id}name" value="${userList.mxgraph.serviceName}">
										<input type="hidden" name="hidden" id='${userList.mxgraph.id}' value="${userList.mxgraph.shortcode}">
									 	 
										</span><!-- seprator -->
										
										 
										
										&nbsp;<span class="reset-btnkey"><a href="managekeys.htm?servicename=${userList.mxgraph.serviceName}&shortcode=${userList.mxgraph.shortcode}&calltype=IVR" rel='facebox'>Manage Key</a></span>
										
										
										</td>
										
										
										
									</tr>
								</c:forEach>



							</tbody>
						</table>
						</div>
						
										
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

 
 <div id="mystickytooltip" class="stickytooltip">
<div style="padding:5px">

<c:forEach items="${mxgraphversionlist}" var="userList" varStatus="theCount">	
<div id="sticky<c:out value="${theCount.count}" />" class="atip" style="width:500px">
<img src="css/image/Drawing.png" /><br />
</div>
</c:forEach>
</div>
<div class="stickystatus"></div>
</div>
 