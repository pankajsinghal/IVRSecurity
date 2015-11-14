$(function(){
 
    // add multiple select / deselect functionality
    $("#selectall").click(function () {
          $('.case').attr('checked', this.checked);
    });
 
    // if all checkbox are selected, check the selectall checkbox
    // and viceversa
    $(".case").click(function(){
 
        if($(".case").length == $(".case:checked").length) {
            $("#selectall").attr("checked", "checked");
        } else {
            $("#selectall").removeAttr("checked");
        }
 
    });
    
});

function deleteservice() {
    // get the form values
	  someGlobalArray=[];
	    $('.case:checked').each(function() {
	        someGlobalArray.push($(this).val());
	    });
alert(someGlobalArray);
    $.ajax({
    type: "POST",
    url: "deleteMxGraph.htm",
    data: "id=" + someGlobalArray,
    success: function(response){
    // we have the response
 
    },
    error: function(e){
    alert('Error: ' + e);
    }
    });
    }

function productionservice() {
    // get the form values
	  someGlobalArray=[];
	    $('.case:checked').each(function() {
	        someGlobalArray.push($(this).val());
	    });
alert(someGlobalArray);
    $.ajax({
    type: "POST",
    url: "productionMxGraph.htm",
    data: "id=" + someGlobalArray,
    success: function(response){
    // we have the response
    	alert("success fully release for production ");
    },
    error: function(e){
    alert('Error: ' + e);
    }
    });
    }

