var main = function(){
	$(".btn-menu-sistema").click(function(){
		$(".dp-nav-sistema ul li").toggle("down");
		$(".cortina").fadeToggle();
	});
	
	$(".conteudo").fadeIn(500);
	
	$(".cortina, .dp-nav-sistema").click(function(){
		$(".dp-nav-sistema ul li").hide("up");
		$(".cortina").fadeOut();
	}).children().click(function(e){
		e.stopPropagation();
	});
}

$(document).ready(main);

