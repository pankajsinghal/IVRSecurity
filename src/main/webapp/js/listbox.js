$(function () {
    $("#button1").click(function () {
        $("#list1 > option:selected").each(function () {
            $(this).remove().appendTo("#list2");
        });
    });

    $("#button2").click(function () {
        $("#list2 > option:selected").each(function () {
            $(this).remove().appendTo("#list1");
            var opts = $('#list1 option').get();

            $('#list1 ').html(opts.sort(optionSort));
        });
    });

    function optionSort(a, b) {
        return $(a).val() > $(b).val();
    }
    $("#btn1").click(function(){
        $("p").append('<p1><div class="listbox"><label>Country</label><form:select path="country" class="other"><c:forEach items="${servicelist}" var="countrylist"><option><c:out value="${countrylist.countryName}" /></option></c:forEach></form:select></div><div class="listbox"><label>Operator</label><select id="list1" multiple="multiple" rows="2" class="other"><c:forEach items="${service}" var="operatorlist"><option><c:out value="${operatorlist.operatorName}" /></option></c:forEach></select><input id="button1" type="button" value="+" /><input id="button2" type="button" value="-" /><form:select path="operator" id="list2" multiple="multiple" rows="2"></form:select></div></p1>');
    	return false;
         });
        $("#btn2").click(function() {
    	 $("p1 > :last").remove();
    	 return false;
    	 });
    
});