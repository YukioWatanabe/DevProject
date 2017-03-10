function main(){
	$('.dp-btn-menu').click(function(){
		$('.dp-menu ul li').toggle("down");
	});
	
	scrollAnimate( $('#buttonDevproject'), $('#devproject') );
	scrollAnimate( $('#buttonCaracteristicas'), $('#caracteristicas') );
	scrollAnimate( $('#buttonQuem'), $('#quem') );
	
	$(window).scroll(function(){
		if($(this).scrollTop() > $('header img').height()){
			$('.dp-menu').addClass('dp-menu-fixed');
		}else{
			$('.dp-menu').removeClass('dp-menu-fixed');
		}
	});
}

function scrollAnimate( button, target ){
	button.click(
		function (e) {
			$('html, body').animate({scrollTop: target.position().top-$('.dp-menubar').height() }, 500);
		}
	);
}

$(document).ready(main);
