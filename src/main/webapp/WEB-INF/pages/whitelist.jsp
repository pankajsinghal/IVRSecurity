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
 
 <script>
 function formValidation()
 {
	 
	var file =  $("#fname").val();
	
	alert(file);
	return true;
	 
	
 }
 
 </script>

<div id="stylized" class="myform">

 <form:form method="POST" commandName="upload" enctype="multipart/form-data" onSubmit="return formValidation();" autocomplete="off">
  <h1>Whitelist Number</h1>
     
 <label>WHITELIST NUMBER
 <span class="small">Upload Numbers</span>
	</label>
 <form:input path="whitelist" type="file" id="fname" />
 
 <label>MSISDN
 <span class="small"></span>
	</label>
 <form:radiobutton path="isSeries" value="0"/>
 <label>SERIES
 <span class="small"></span>
	</label>
<form:radiobutton path="isSeries" value="1"/>
 
 
 <div id="submitbtn">    <input  type="submit" name="submit" value="Add whitelist" class=addjobbtn > </div>
 <!--  <div id="submitload" class="succesloading"><span>Please Wait.......</span><img src="css/image/ajax-loader.gif" alt="Ajax Indicator" /></div>-->
 
 <div class="spacer"></div>
 
 	</form:form>
 </div>
 
<!--  <script type='text/javascript'>
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

<script  src="js/bootstrap-datetimepicker.min.js"></script>  -->
 