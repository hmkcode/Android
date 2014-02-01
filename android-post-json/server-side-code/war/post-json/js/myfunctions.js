function sendAjax() {
	$("#alert").css("display","none");
	if($.trim($('#name').val()).length == 0){
		$("#alert").css("display","block");
		return;
	}
	// get inputs
	var person = new Object();
	person.name = $('#name').val();
	person.country = $('#country').val();
	person.twitter = $('#twitter').val();
	
	$.ajax({
		url: "/jsonservlet",
		type: 'POST',
		dataType: 'json',
		data: JSON.stringify(person),
		contentType: 'application/json',
		mimeType: 'application/json',
		
		success: function (data) {
			$("tr:has(td)").remove();

			$.each(data, function (index, person) {
		    	
		        if(person.name.length > 0 )		         
		        $("#added-articles").append($('<tr/>')
		        		.append($("<td/>").append($("<span class='label label-default' style='margin:4px;padding:4px'/>").text(person.name)))
		        		.append($("<td/>").append($("<span class='label label-success' style='margin:4px;padding:4px'/>").text(person.country)))
		        		.append($("<td/>").append($("<span class='label label-info' style='margin:4px;padding:4px'/>").text(person.twitter)))

		        );
		    
		        
		    }); 
		},
		error:function(data,status,er) {
			alert("error: "+data+" status: "+status+" er:"+er);
		}
	});
}
//--------------------------------------
function getAjax() {
	 
	$("#alert").css("display","none");

	
	$.ajax({
		url: "/jsonservlet",
		type: 'GET',
		dataType: 'json',
		contentType: 'application/json',
		mimeType: 'application/json',
		
		success: function (data) {
			$("tr:has(td)").remove();

			$.each(data, function (index, person) {
		    	
		       
		        $("#added-articles").append($('<tr/>')
		        		.append($("<td/>").append($("<span class='label label-default' style='margin:4px;padding:4px'/>").text(person.name)))
		        		.append($("<td/>").append($("<span class='label label-success' style='margin:4px;padding:4px'/>").text(person.country)))
		        		.append($("<td/>").append($("<span class='label label-info' style='margin:4px;padding:4px'/>").text(person.twitter)))

		        );
		    
		        
		    }); 
		},
		error:function(data,status,er) {
			alert("error: "+data+" status: "+status+" er:"+er);
		}
	});
}

//--------------------------------------
