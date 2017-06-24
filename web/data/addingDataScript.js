/**
 * 
 */

$(document).ready(function(){
	$(".to-hide").hide();
	
	$("button").click(function() {
		$(this).next().slideToggle(400);
	});

});

