/**
 * 
 */

$(document).ready(function(){
	var clicked1 = false;
	var clicked2 = false;
	var clicked3 = false;
	var clicked4 = false;
	
	$(".to-hide").hide();
	
	$("button").mouseover(function() {
		if(!($(this).next().is(":visible"))) {
			$(this).next().slideToggle(400);
		}
	});
	
	$("#button1").click(function() {
		if(!(($(this).next().is(":visible")) && (clicked1 === false))) {
			$(this).next().slideToggle(400);
		}
		clicked1 = !clicked1;
	});
	
	$("#button1").mouseout(function() {
		if($(this).next().is(":visible") && (clicked1 === false)) {
			$(this).next().slideToggle(400);
		}
	});
	
	$("#button2").click(function() {
		if(!(($(this).next().is(":visible")) && (clicked2 === false))) {
			$(this).next().slideToggle(400);
		}
		clicked2 = !clicked2;
	});
	
	$("#button2").mouseout(function() {
		if($(this).next().is(":visible") && (clicked2 === false)) {
			$(this).next().slideToggle(400);
		}
	});
	
	$("#button3").click(function() {
		if(!(($(this).next().is(":visible")) && (clicked3 === false))) {
			$(this).next().slideToggle(400);
		}
		clicked3 = !clicked3;
	});
	
	$("#button3").mouseout(function() {
		if($(this).next().is(":visible") && (clicked3 === false)) {
			$(this).next().slideToggle(400);
		}
	});
	
	$("#button4").click(function() {
		if(!(($(this).next().is(":visible")) && (clicked4 === false))) {
			$(this).next().slideToggle(400);
		}
		clicked4 = !clicked4;
	});
	
	$("#button4").mouseout(function() {
		if($(this).next().is(":visible") && (clicked4 === false)) {
			$(this).next().slideToggle(400);
		}
	});
});
