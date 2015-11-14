<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	  
    <script type="text/javascript" src="js/jquery.min.js"></script>
	<link href="css/viewservices.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="css/pagination.css" />
	<script src="js/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="js/viewjob.js"></script>
     <script type="text/javascript" src="js/stickytooltip.js"></script>
      <script type="text/javascript" src="js/managekeys.js"></script>
    
	<link rel="stylesheet" href="css/viewjob.css" />
 	<link rel="stylesheet" type="text/css" href="css/stickytooltip.css" />
    <link rel="stylesheet" href="css/managekeys.css" />
 
 <script>
			$('document').ready(function(){
				$('#content').jplist({
				
					items_box: '.demo-tbl tbody' 
					,item_path: '.tbl-item' 
					,panel_path: '.panel'
					
					//cookies
					,cookies: false
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
		
  

<script type="text/javascript">
     $(document).ready(function() {
     	
     	var height = $('.grid-24').height();
     	height = height + 170;
     	document.getElementById('content').style.height= height+"px";
     	executeQuery();
     });    
    </script>
    
  <script type="text/javascript">
  function executeQuery() {

	var height = $('#campaignlist').height();
	height = height + 170;
	document.getElementById('content').style.height= height+"px";
	
	$.ajax({
    type: "POST",
    url: "show.htm",
    data: "",
    success: function(response){
    	
      $("#campaignlist .tbl-item").remove();
  	var i=1;
  	var addclass="";
    	var obj = $.parseJSON(response);
		$.each(obj, function(key,value) {
			
			if(i%2==0)
				{
				addclass = 'even';
				}else{addclass = 'odd'; }
		 	
			tr = $('<tr class="tbl-item '+ addclass +'" />');
            tr.append("<td class='servicename'>" + key + "</td>");
            tr.append("<td class='createdby'>" + value.ivrcode + "</td>");
            if(value.dtmf==10){
            	dtmf= '*';
            }
            else if(value.dtmf==11){
            	dtmf='#';
            }
            else
            dtmf = value.dtmf;
            tr.append("<td class='center'>" + dtmf + "</td>");
			tr.append("<td>" + value.aparty + "</td>");
			tr.append("<td>" + value.callstate + "</td>");
			tr.append("<td>" + value.calltype + "</td>");
            $('#campaignlist').append(tr);
			i++;
			});
		
		var height = $('#campaignlist').height();
		height = height + 170;
		document.getElementById('content').style.height= height+"px";
		
    }
  
    });
    
   var intervaltime =  $('#setrefreshtime').val();
   intervaltime = parseInt(intervaltime);
   setTimeout(executeQuery, intervaltime); // you could choose not to continue on failure...
  }
  </script>  
  
  
<style type="text/css">
 
div.tableContainer {
	clear: both;
	border: 1px solid #963;
	height: 500px;
	overflow: auto;
	width: 100%;
}

 
html>body div.tableContainer {
	overflow: hidden;
	width: 100%;
}

 
div.tableContainer table {
	float: left;
	width: 100%;
}

 
html>body div.tableContainer table {
	width: 100%;
}

 
thead.fixedHeader tr {
	position: relative
}

 
html>body thead.fixedHeader tr {
	display: block
}

 
thead.fixedHeader th {
	background: #C96;
	border-left: 1px solid #EB8;
	border-right: 1px solid #B74;
	border-top: 1px solid #EB8;
	font-weight: normal;
	padding: 10px 3px;
	text-align: left
}

 
thead.fixedHeader a, thead.fixedHeader a:link, thead.fixedHeader a:visited {
	color: #FFF;
	display: block;
	text-decoration: none;
	width: 100%
}

 
thead.fixedHeader a:hover {
	color: #FFF;
	display: block;
	text-decoration: underline;
	width: 100%
}

 
html>body tbody.scrollContent {
	display: block;
	height: 500px;
	overflow: auto;
	width: 100%
}
 
html>body thead.fixedHeader th {
	width: 201px;
}

html>body thead.fixedHeader th + th {
	width: 236px;
}

html>body thead.fixedHeader th + th + th {
	width: 262px;
}

html>body thead.fixedHeader th + th + th + th{
	width: 294px;
}

html>body thead.fixedHeader th + th + th + th + th{
	width: 308px;
}

html>body thead.fixedHeader th + th + th + th + th + th{
	width: 308px;
}


/* define width of TD elements: 1st, 2nd, and 3rd respectively.          */
/* All other non-IE browsers.                                            */
/* http://www.w3.org/TR/REC-CSS2/selector.html#adjacent-selectors        */
html>body tbody.scrollContent td {
	width: 210px;
}

html>body tbody.scrollContent td + td {
	width: 237px
}

html>body tbody.scrollContent td + td + td {
	width: 316px
}
-->
</style>
  
 
<div id="content">

	<div id="contentHeader">
		<h1>Telephony Engine Window</h1>
	</div>
	<!-- #contentHeader -->

	<div class="container">

		<FORM method="POST" name="listform" action="#" id="listform">
			<INPUT value="action_set" type="hidden" name="form_action">

			<div class="grid-24">
			
			<div style='color: #333333;font-size: 14px; font-weight: normal; margin:0px 0px 10px 2px;'>
				Page Interval time &nbsp;<select id="setrefreshtime" style='width:100px;'>
					<option value='1000'>1 Second </option>
					<option value='2000'>2 Seconds</option>
					<option value='3000'>3 Seconds</option>
					<option value='4000'>4 Seconds</option>
					<option value='5000'>5 Seconds</option>
					<option value='6000'>6 Seconds</option>
					<option value='7000'>7 Seconds</option>
					<option value='8000'>8 Seconds</option>
					<option value='9000'>9 Seconds</option>
					<option value='10000' selected="selected">10 Seconds</option>
					 
					</select>
			</div>
				<div class="widget widget-table">
				
				

			

					<div class="widget-content">
					
					<!-- panel -->
				
						 
					
						<div class="box text-shadow tableContainer">
						<table id="campaignlist"  class="demo-tbl scrollTable">
							<thead class="fixedHeader">
								<tr>
									<th>Channel</th>
									<th>A-Party</th>
									<th>IvrCode/B-Party</th>
									<th>Callstatus</th>
									<th>DTMF</th>
									<th>Calltype</th>

								</tr>
							</thead>
							<tbody class="scrollContent">
								<!-- tr -->

								 
								 
							<tr class='tbl-item' style="display:none;">
										<td class="servicename">1</td>
										<td class="center">Aparty</td>
										<td class="createdby">45654</td>
										<td class="center"> Status</td>
										<td class="center">DTMF</td>
										<td class="center"> fhdsf</td>
										
									</tr>	  
							
									
								 
								


							</tbody>
						</table>
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
