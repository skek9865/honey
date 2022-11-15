/*!
    * Start Bootstrap - SB Admin v7.0.3 (https://startbootstrap.com/template/sb-admin)
    * Copyright 2013-2021 Start Bootstrap
    * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
    */
    // 
// Scripts
// 

window.addEventListener('DOMContentLoaded', event => {

    // Toggle the side navigation
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {
        // Uncomment Below to persist sidebar toggle between refreshes
        // if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
        //     document.body.classList.toggle('sb-sidenav-toggled');
        // }
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
						if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
							$("#tab_footer").css("margin-left", 1);
							$("#Copyright_footer").css("margin-left", 1);
						}else{
							$("#tab_footer").css("margin-left", $('#layoutSidenav_nav').width());
							$("#Copyright_footer").css("margin-left", $('#layoutSidenav_nav').width() );
						}
        });
    }

	$(window).resize( function() {
		var lc_height = $('#layoutSidenav_nav').height(); //컨텐츠 전체 높이
		var tb_height = $('#titleBar').height(); //타이틀 높이
		var sb_height = $('#searchBar').height(); //검색 높이
		var pb_height = $('#pageBar').height(); //페이지바 높이

		var tw_height = lc_height - ( tb_height + sb_height + pb_height + 170);
		$('#tableWrapper').height(tw_height) ;
	});

	$(document).ready( function() {
		//해당 DIV 사이즈 조정
		$('#loading').hide();
		$('#query').submit(function(){
			$('#loading').show();
			return true;
		});
		$('#query').click(function(){
	//		$('#loading').show();
			return true;
		});
		$('#pageBar_nav li').each(function (index, item) {
			$(this).click(function(e){
				$('#loading').show();
			});
		});

		$("#tab_footer").css("margin-left", $('#layoutSidenav_nav').width() -1 );
		$("#tab_footer").css("margin-top", 5);
		$("#Copyright_footer").css("margin-left", $('#layoutSidenav_nav').width() );
		window.dispatchEvent(new Event('resize'));
	});
});

