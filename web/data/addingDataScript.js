/**
 * 
 */

$(document).ready(function(){
	$(".to-hide").hide();
	
	$("button").click(function() {
		$(this).next().slideToggle(400);
	});

});

function createDialog() {
	var conf = confirm("დარწმუნებული ხართ რომ გსურთ ყველა ლექციის შესახებ მონაცემების წაშლა?");
	if(conf) {
		//lecture tables gasuftaveba
	}
}
