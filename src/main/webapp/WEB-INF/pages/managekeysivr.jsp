<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<script src="js/jquery.js"></script>


<h1 class='managekeys'>Manage Keys <br> Service Name (<%= request.getParameter("servicename") %>)</h1>


<script type="text/javascript">
 
$(document).ready(function(){
 
    var counter = -999;
 
    $("#addButton").click(function () {
 
	if(counter>100000000){
            alert("Only 10 textboxes allow");
            return false;
	}   
 
	var newTextBoxDiv = $(document.createElement('div'))
	     .attr("id", 'TextBoxDiv' + counter);
 
	newTextBoxDiv.after().html('<br><label>Long Code : </label>' +
	      '<input type="text" name="longcode[]" id="textbox' + counter + '" onChange="checkduplicate(this.value);">' + '<a href="javascript:removeElement('+counter+')" id="offerpeopleremove"></a>');
 
	newTextBoxDiv.appendTo("#TextBoxesGroup");
 
 
	counter++;
     });
 
     
  });
</script>
 

<script>
 function removeElement(counter){
	     $("#TextBoxDiv" + counter).remove();
		counter--;

     }
 
 </script>
 
 
 <script>
 
 function checkduplicate(longcode)
 {
	
	 $("#addlongcode").hide();
	 var service_name = $('#servicename').val();
     var short_code = $('#shortcode').val();
     var call_type = $('#calltype').val();
     var dataString = 'servicename='+ service_name + '&shortcode='+ short_code + '&calltype='+ call_type + '&longcode=' + longcode;
    
	  $.ajax({
        type: "POST",
        url: "./check.htm",
        data: dataString,
        success: function(msg){
        	
        	if(msg=='ok')
        	{
        		 $("#addlongcode").show();
        		 $('input[type="submit"]').removeAttr('disabled');
        		 $('#duplicatemsg').html(unescape(""));
        	}else {
        		$('#duplicatemsg').html(unescape("Long code already exists please try another"));
        		$("#addlongcode").hide();
        		 $('input[type="submit"]').attr('disabled','disabled');
        	}
        	
        }
      });
	  
	 
 }
  
 </script>
 
 
 <script>
 
 function addLongcode()
 { 
	
	 var chks = document.getElementsByName('longcode[]');                  //here rr[] is the name of the textbox
		for (var i = 0; i < chks.length; i++)
		{
			var num = chks[i].value;
			if (isNaN(num) || num=="")
			{
			alert("Please Enter Longcode only numbers !");
			chks[i].focus();
			return false;
			}
		}
	 
	 var $textboxes = $('input[name="longcode[]"]');
	 
	 var len = $textboxes.length;
	 
	 for(var i=0;i<len;i++)
	    {
		 var value1 = $textboxes.eq(i).val();	
		 
		 $("#longcodeitem").append('&longcode=' + value1);
	  }
	 
	 
	 var data = $('input[name="longcode[]"]').map(function(){
		  return  this.value;
		});
		var lenlong = data.length;
		for(var j=0;j<lenlong;j++)
			{
			
				if(data[j]==data[j+1])
					{
						alert("You have entered duplicate long code, please  Enter another long code !");
						return false;
					}
				
			}
	 
	 
	 
	 
	 var long_code = $('#longcodeitem').text(); 
	 var service_name = $('#servicename').val();
     var short_code = $('#shortcode').val();
     var call_type = $('#calltype').val();
     var service_type= $('#service').val();
     
     var dataString = 'servicename='+ service_name + '&shortcode='+ short_code + '&calltype='+ call_type + long_code+'&Service='+service_type;
	 
	  $.ajax({
        type: "POST",
        url: "./release.htm",
        data: dataString,
        success: function(msg){
          alert( "Long Code Has Added successfull" ); //Anything you want
        }
      });
	  
	  $("#longcodeitem").empty();
 }
 
 
 </script>
 
 
 
  <script>
 
 function deleteLongcode()
 {
	
	 
	 if($('#TextBoxesGroup').find('input[type=checkbox]:checked').length == 0)
	    {
	        alert('Please select long code which you want to delete');
	        return false;
	    }
	 
	
	 var checkboxName = 'deletelongcode';
	 var  checkboxVals = [];
	    $('input:checkbox[name='+checkboxName+']:checked').each(function(index){
	        checkboxVals.push($(this).val());
	         
	    });
	    
	   
	    
	    var selected;
		selected = checkboxVals.join('&longcode=') + "";
		var long_code = "&longcode=" + selected;
	   
		 var service_name = $('#servicename').val();
	     var short_code = $('#shortcode').val();
	     var call_type = $('#calltype').val();
	     var service_type= $('#service').val();
	     var dataString = 'servicename='+ service_name + '&shortcode='+ short_code + '&calltype='+ call_type + long_code+'&Service='+service_type;
     	
	  $.ajax({
        type: "POST",
        url: "./remove.htm",
        data: dataString,
        success: function(msg){
        	 
        	  $('input[type=checkbox]:checked').each(function(){
      	    	var the_id =$(this).attr('id');
      	    	 $('.item#'+the_id).remove(); //DELETES ITEMS HERE
            	 $("#deletelongcoede").hide();
      	    });

        	
        	
        }
      });
	  
	  
	 
 }
 
 
 $("input[type=checkbox]").click(function(){
	 var x = $("input[type=checkbox]:checked").length;
	 var t = $("input[type=checkbox]").length;
	  
	 			if(t==x){
	 			 
	 			$("#deletelongcoede").show();
	 			} else {
	  				 $("#deletelongcoede").show();
	 				if(x==0){
	 				$("#deletelongcoede").hide();
	 				}
	 			}
	 });
	  
  
 </script>
 
 
 
 <style>
 #deletelongcoede
 {
 display: none;
 }
 </style>
 
 
 <div id='TextBoxesGroup'>
	<div id="TextBoxDiv11">
		<label>Short Code  : </label>
		 
		<input type='hidden' name="servicename" value="<%= request.getParameter("servicename") %>" id='servicename'>
		<input type='text' name="shortcode" value="<%= request.getParameter("shortcode") %>" id='shortcode' readonly ><input type='button' value='' id='addButton'>
		<input type='hidden' name="calltype" value="<%= request.getParameter("calltype") %>" id='calltype' >
		<input type='hidden' name="service" value="false" id='service' >
		<div id="longcodeitem" style="display:none"> </div>
		
	</div>
	
	<c:forEach items="${longcodes}" var="userList" varStatus="theCount"> 
	
	
	<div class="item" id="TextBoxDiv${userList.id}"> 
	<br>
	<label>Long Code : </label> 
	<input type="text" name="longcode1[]" id="textbox${userList.id}" value="${userList.shortcode}"> <input type='checkbox' name='deletelongcode' value='${userList.shortcode}' id="TextBoxDiv${userList.id}">
	<!--  <a href="javascript:removeElement(${userList.id})" id='offerpeopleremove'>Remove</a> -->
	</div>
	
	</c:forEach>
	
</div>
<div id="duplicatemsg" style="color: #800020;
    font-size: 13px;
    font-weight: bold;
    padding: 13px 0 0 27px;"></div>

<input type="submit" name="submit" value="Add" id="addlongcode" onClick='addLongcode();'>	

<input type="submit" name="submit" value="Delete" id="deletelongcoede" onClick='deleteLongcode();'>


 
 
	
