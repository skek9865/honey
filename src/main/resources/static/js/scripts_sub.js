$(document).ready( function() {
	//해당 DIV 사이즈 조정

	$("#tab_footer").css("margin-left", $('#layoutSidenav_nav').width());
	$("#Copyright_footer").css("margin-left", $('#layoutSidenav_nav').width() );
	window.dispatchEvent(new Event('resize'));

	//datepicker 초기화
	$('.input-daterange input').each(function() {
		if ( !$("#"+$(this).attr('id')).is('[readonly]') &&  $(this).attr('type') == 'text') {
			$(this).datepicker({
				calendarWeeks: false,
				todayHighlight: true,
				autoclose: true,
				format: "yyyy-mm-dd",
				language: "ko"
			});
		}
	});

	//datepicker 초기화
	$('.input-daterangeYM input').each(function() {
		if ( !$("#"+$(this).attr('id')).is('[readonly]') &&  $(this).attr('type') == 'text') {
			$(this).datepicker({
				calendarWeeks: false,
				todayHighlight: true,
				autoclose: true,
				format: "yyyy-mm",
				language: "ko"
			});
		}
	});

	//사원검색
	$('#vuserid1').click(function(){
		gf_popup("700","620","<?=$gs_tpid?>_searchbox","search_user.php?type=11") ;
	});
	$('#vuserid2').click(function(){
		gf_popup("700","620","<?=$gs_tpid?>_searchbox","search_user.php?type=12") ;
	});
	$('#vuserid3').click(function(){
		gf_popup("700","620","<?=$gs_tpid?>_searchbox","search_user.php?type=13") ;
	});


	$('#vusernm_od').click(function(){
		gf_popup("700","620","<?=$gs_tpid?>_searchbox","search_user.php?type=10") ;
	});
	$('#suserid_21').click(function(){
		gf_popup("700","620","<?=$gs_tpid?>_searchbox","search_user.php?type=10") ;
	});
	$('#suserid_2').click(function(){
		gf_popup("700","620","<?=$gs_tpid?>_searchbox","search_user.php?type=1") ;
	});
	$('#suserid').click(function(){
		gf_popup("700","620","<?=$gs_tpid?>_searchbox","search_user.php?type=9") ;
	});
		$('#vouserid').click(function(){
		gf_popup("700","620","<?=$gs_tpid?>_searchbox","search_user.php?type=14") ;
	});

	// $('#vuserid').click(function(){
	// 	gf_popup("700","620","<?=$gs_tpid?>_searchbox","search_user.php?type=2") ;
	// });
	$('#vauserid').click(function(){
		gf_popup("700","620","<?=$gs_tpid?>_searchbox","search_user.php?type=3") ;
	});
	$('#vpuserid').click(function(){
		gf_popup("700","620","<?=$gs_tpid?>_searchbox","search_user.php?type=4") ;
	});
	$('#vpuserid1').click(function(){
		gf_popup("700","620","<?=$gs_tpid?>_searchbox","search_user.php?type=5") ;
	});
	$('#vuserid_dn').click(function(){
		gf_popup("700","620","<?=$gs_tpid?>_searchbox","search_user.php?type=6") ;
	});
	$('#vuserid_od').click(function(){
		gf_popup("700","620","<?=$gs_tpid?>_searchbox","search_user.php?type=7") ;
	});
	$('#vuserid_up').click(function(){
		gf_popup("700","620","<?=$gs_tpid?>_searchbox","search_user.php?type=8") ;
	});

	//주소검색
	$('#vzipcode').click(function(){
		openZipSearch('vzipcode','vaddress','vaddress1');
	});
	$('#vtax_zipcode').click(function(){
		openZipSearch('vtax_zipcode','vtax_add1','vtax_add2');
	});

	//data table sort
	$("#datatable_1").tablesorter({
		theme : 'blue',
		// sort on the first column and second column in ascending order
		sortList: [[0,0]]
	});
	var tlen = 0;
	var min = 20;
	$('#datatable_1 th').each(function() {
		if ($(this).width() > min)
		{
			tlen += $(this).width() - min;
		}else{
			tlen += $(this).width();
		}
	});
	tlen += 300;
	//$("#datatable_1").width(tlen+"px");

	$(".input_number").each(function() {
		$("#"+$(this).attr('id')).number(true);
	});
	$(".input_float").each(function() {
		$("#"+$(this).attr('id')).number(true,3);
	});
});
