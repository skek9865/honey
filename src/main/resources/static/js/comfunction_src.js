function gf_comm(as_action,as_val1,as_val2,as_val3) { // global 함수
	var form = fm_home; 
	var item;
	switch (as_action) {
		case "findgoods" : //품목검색
			gf_popup("700","500","_search_goods","search_goods.php?type="+as_val1+"&data2="+as_val2+"&data3="+as_val3+"&gubun=Y&gubun1=Y") ;
			break;
		case "findcust" : //거래처검색
			gf_popup("700","500","_cust_goods","search_cust.php?type="+as_val1+"&data2="+as_val2+"&data3="+as_val3) ;
			break;
		case "sdoc_type" : //제휴사검색
		case "vdoc_type" :
			document.all.IFrame_real.src='search_select.php?prg_type=' + as_action +'&doc_type=' + as_val1 + '&dec_lem=' + as_val2+ '&sel_type=' + as_val3;
			break;
	}
}
function gf_changeSaletype(reqtype,vatyn,row,sumcase) {
	var saletype = $("#"+reqtype).val();
	if(!saletype){
		alert("거래유형을 먼저 입력해주세요");
		return;
	}
	switch (sumcase){
		case "sum":
			if(saletype == "00002") {
				for(i = 0; i < row; i++) {
					$("#"+vatyn+i).val("N");
					gf_sum('qty_'+i,'price_'+i,'amt_'+i,'vat_'+i,'vatyn_'+i);
				}
			}
			else {
				for(i = 0; i < row; i++){
					$("#"+vatyn+i).val("Y");
					gf_sum('qty_'+i,'price_'+i,'amt_'+i,'vat_'+i,'vatyn_'+i);
				}
			}
			break;
		case "sum_vat":
			if(saletype == "00002") {
				for(i = 0; i < row; i++) {
					$("#"+vatyn+i).val("N");
					gf_sum_vat('qty_'+i,'price_'+i,'amt_'+i,'vat_'+i,'vatyn_'+i,'amtsum_'+i,'pricevat_'+i);
				}
			}
			else {
				for(i = 0; i < row; i++){
					$("#"+vatyn+i).val("Y");
					gf_sum_vat('qty_'+i,'price_'+i,'amt_'+i,'vat_'+i,'vatyn_'+i,'amtsum_'+i,'pricevat_'+i);
				}
			}
			break;
	}
	gf_totsum('qty','amt','vat',row,'qty_t','amt_t','vat_t','tot_t');
	return;
}

function gf_goodfind(custcd,goodscd,goodsnm,standard,qty,price,vatyn,pricevat) {

	var custcd_val = $("#"+custcd).val();
	var find_val = $("#"+goodscd).val();
	if (find_val == '') find_val = $("#"+goodsnm).val();
	if (typeof find_val == "undefined"){
		find_val = "";
	}
	if (custcd_val == "")
	{
		msg_box(1,"에러","거래처를 선택하세요!!");
	}else{
		gf_popup("700","500","_search_goods","search_goods.php?type=2&data2="+goodscd+"&find="+encodeURI(find_val)+"&data3="+goodsnm+"&data4="+standard+"&data5="+qty+"&data6="+price+"&data7="+custcd_val+"&data8="+vatyn+"&data9="+pricevat+"&gubun=Y&gubun1=Y") ;
	}
	return false;
}

function gf_goodfind_ex(goodscd,goodsnm,standard,unit,qty,productnm,type) {

	var find_val = $("#"+goodscd).val();
	if (find_val == '') find_val = $("#"+goodsnm).val();
	if (typeof find_val == "undefined"){
		find_val = "";
	}
	gf_popup("700","500","_search_goods","search_goods.php?type="+type+"&data2="+goodscd+"&find="+encodeURI(find_val)+"&data3="+goodsnm+"&data4="+standard+"&data5="+unit+"&data6="+productnm+"&gubun=Y&gubun1=Y") ;
	return false;
}
function gf_goodfind_ex2(goodscd,goodsnm,standard,unit,qty,productnm,type,flag,flag_label) {
	if(!$("#"+flag).val()){
		alert(flag_label + " 먼저 선택해주세요")
		return false;
	}

	var find_val = $("#"+goodscd).val();
	if (find_val == '') find_val = $("#"+goodsnm).val();
	if (typeof find_val == "undefined"){
		find_val = "";
	}
	gf_popup("700","500","_search_goods","search_goods.php?type="+type+"&data2="+goodscd+"&find="+encodeURI(find_val)+"&data3="+goodsnm+"&data4="+standard+"&data5="+unit+"&data6="+productnm+"&gubun=Y&gubun1=Y") ;
	return false;
}
function gf_cust_pop(custcd,custnm,empno,outnm,zipcd,address,address1) {
	window.open('search_cust.php?type=1&data2='+custcd+'&data3='+custnm+'&data4='+empno+'&data5='+outnm+'&data6='+zipcd+'&data7='+address+'&data8='+address1+'&find='+encodeURI($("#"+custnm).val()),"search_cust","width=610, height=500, scrollbars=yes,location=no,status=yes ");
	return false;
}
function gf_cust_pop_1(custcd,custnm) {
	window.open('search_cust.php?type=2&data2='+custcd+'&data3='+custnm+'&find='+encodeURI($("#"+custnm).val()),"search_cust","width=610, height=500, scrollbars=yes,location=no,status=yes ");
	return false;
}
function gf_proj_pop(projcd,projnm) {
	window.open('search_proj.php?type=1&data2='+projcd+'&data3='+projnm,"search_proj","width=610, height=500, scrollbars=yes,location=no,status=yes ");
	return false;
}

function gf_rowdel(fname,ftype,fcnt,cfield) {
	for (var i = 0; i < fname.length; i++) {
		if(ftype[i] == "C") $("#"+fname[i]+fcnt).val("");
		else $("#"+fname[i]+fcnt).val(0);
	}
	return false;
}

function gf_sum(qty,price,amt,vat,vatyn) {
	var saletype = $("#vsaletype").val();
	if(!saletype && ($("#"+qty).val() != 0 || $("#"+price).val() != 0)){
		alert("거래유형을 먼저 입력해주세요");
		return;
	}
	var lprice = delComma($("#"+price).val()) ;
	var lvatyn = $("#"+vatyn).val() ;
	var lqty = delComma($("#"+qty).val()) ;
	if (lqty == '')	lqty = 0;
	var lamt = (parseFloat(lprice) * parseFloat(lqty)).toFixed(0);
	var lvat = Math.floor(lamt * 0.1);
	if (lvatyn == 'N')
	{
		lvat = 0;
	}
	$("#"+amt).val(AddComma(lamt));
	$("#"+vat).val(AddComma(lvat));
	return ;
}
function gf_sum_ext(qty,price,amt,vat,vatyn,amtsum) {
	var lprice = delComma($("#"+price).val()) ;
	var lvatyn = $("#"+vatyn).val() ;
	var lqty = delComma($("#"+qty).val()) ;
	if (lqty == '')	lqty = 0;
	var lamt = (parseFloat(lprice) * parseFloat(lqty)).toFixed(0);
	var lvat = lamt * 0.1 ;
	var lamtsum = (Math.floor((parseFloat(lamt) + parseFloat(lvat))/10)*10).toFixed(0);
	if (lvatyn == 'N')
	{
		lvat = 0;
	}
	$("#"+amt).val(AddComma(lamt));
	$("#"+vat).val(AddComma(lvat));
	$("#"+amtsum).val(AddComma(lamtsum));
	return ;
}

function gf_sum_vat(qty,price,amt,vat,vatyn,amtsum,pricevat) {
	var saletype = $("#vsaletype").val();
	if(!saletype && ($("#"+qty).val() != 0 || $("#"+price).val() != 0)){
		alert("거래유형을 먼저 입력해주세요");
		return;
	}
	var lprice = delComma($("#"+price).val()) ;
	var lvatyn = $("#"+vatyn).val() ;
	var lqty = delComma($("#"+qty).val()) ;
	if (lqty == '')	lqty = 0;
	var lamt = parseFloat(lprice) * parseFloat(lqty);
	var lvat = Math.round(lamt * 0.1) ;
	var lpricevat =  parseFloat(lprice) + (parseFloat(lvat)/lqty);
	if (lvatyn == 'N')
	{
		lvat = 0;
		lpricevat =  lprice;
	}
	var lamtsum = Math.floor((parseFloat(lamt) + parseFloat(lvat))/10)*10;
	$("#"+amt).val(AddComma(lamt));
	$("#"+vat).val(AddComma(lvat));
	$("#"+amtsum).val(AddComma(lamtsum));
	$("#"+pricevat).val(AddComma(lpricevat));
	return ;
}

function gf_sum_vat_1(qty,price,amt,vat,vatyn,amtsum,pricevat) {
/*
수량	
단가	    : 입력금액 / 1.1
공급가액	  : 단가 * 수량
부가세 합계	:(입력금액-단가)*수량
합계	    :(단가*수량) + (입력금액-단가)*수량
단가(vat)	:단가 + (입력금액-단가)
*/
	var lqty   = parseFloat(delComma($("#"+qty).val())) ; //수량
	var lpricef= parseFloat(delComma($("#"+price).val())) ; //입력금액
	var lprice = Math.round(lpricef / 1.1) ; //단가 : 입력금액 / 1.1
	var lamt   = Math.round(lprice * lqty) ; //공급가액 : 단가 * 수량
	var lvat   = Math.round((lpricef - lprice) * lqty) ;//부가세:(입력금액-단가)*수량
	var lamtsum= lamt + lvat;//합계:(단가*수량) + (입력금액-단가)*수량
	var lpricevat =  Math.round(lprice + (lpricef - lprice)); //단가(vat)	:단가 + (입력금액-단가)

	$("#"+qty).val(AddComma(lqty));
	$("#"+price).val(AddComma(lprice));
	$("#"+amt).val(AddComma(lamt));
	$("#"+vat).val(AddComma(lvat));
	$("#"+amtsum).val(AddComma(lamtsum));
	$("#"+pricevat).val(AddComma(lpricevat));
	return ;
}
function gf_totsum(qty,amt,vat,imax,qty_t,amt_t,vat_t,tot_t){
	var lqty ,lamt ,lvat ,tqty ,tamt ,tvat ,ttot;
	tqty = tamt = tvat = ttot = 0;
	for (i=0;i<imax ;i++ )
	{
		lqty = delComma($("#"+qty+"_"+i).val()) ;
		if (lqty =="") lqty = 0;
		tqty += parseFloat(lqty);

		lamt = delComma($("#"+amt+"_"+i).val()) ;
		if (lamt =="") lamt = 0;
		tamt += parseFloat(lamt);

		lvat = delComma($("#"+vat+"_"+i).val()) ;
		if (lvat =="") lvat = 0;
		tvat += parseFloat(lvat);
	}
	ttot = Math.floor((parseFloat(tamt) + parseFloat(tvat))/10)*10 ;
	$("#"+qty_t).val(AddComma(tqty));
	$("#"+amt_t).val(AddComma(tamt));
	$("#"+vat_t).val(AddComma(tvat));
	$("#"+tot_t).val(AddComma(ttot));

}
function gf_totsum_qty(qty,amt,vat,imax,qty_tot,amt_t,vat_t,tot_t){
	var lqty ,lamt ,lvat ,tqty ,tamt ,tvat ,ttot;
	tqty = tamt = tvat = ttot = 0;

	for (i=0;i<imax ;i++ )
	{
		lqty = delComma($("#"+qty+"_"+i).val()) ;

		if (lqty =="") lqty = 0;
		tqty += parseFloat(lqty);

		lamt = delComma($("#"+amt+"_"+i).val()) ;
		if (lamt =="") lamt = 0;
		tamt += parseFloat(lamt);

		lvat = delComma($("#"+vat+"_"+i).val()) ;
		if (lvat =="") lvat = 0;
		tvat += parseFloat(lvat);
	}
	ttot = parseFloat(tamt) + parseFloat(tvat);
	$("#"+qty_tot).val(AddComma(tqty));
	$("#"+amt_t).val(AddComma(tamt));
	$("#"+vat_t).val(AddComma(tvat));
	$("#"+tot_t).val(AddComma(ttot));

}
function gf_qtysum(qty,qty_tot,imax){
	var lqty ,tqty ;
	tqty = 0;

	for (i=0;i<imax ;i++ )
	{
		lqty = delComma($("#"+qty+"_"+i).val()) ;

		if (lqty =="") lqty = 0;
		tqty += parseFloat(lqty);
	}
	$("#"+qty_tot).val(AddComma(tqty));
}
function gf_qtynrqtysum(qty,rqty,qty_tot,rqty_tot,imax){
	var lqty ,lrqty ,tqty, trqty;
	tqty = 0;
	trqty = 0;

	for (i=0;i<imax ;i++ )
	{
		lqty = delComma($("#"+qty+"_"+i).val()) ;
		lrqty = delComma($("#"+rqty+"_"+i).val()) ;

		if (lqty =="") lqty = 0;
		if (lrqty =="") lrqty = 0;

		tqty += parseFloat(lqty);
		trqty += parseFloat(lrqty);
	}
	$("#"+qty_tot).val(AddComma(tqty));
	$("#"+rqty_tot).val(AddComma(trqty));
}

function lf_exeurl(as_url,as_data1,as_data2,as_data3) {
	$.ajax({
	url: as_url,
	type:"post",
	data: {data1 : as_data1,data2 : as_data2,data3 : as_data3},
	dataType:"html",
	complete: function(req,stat) {
		if(stat=="success") {
			alert('작업완료');
		}
	},
	error: function( req, errortype) {
		if (typeof view_error == 'function')
			view_error(req.status+" : "+req.statusText+"<br/>"+req.responseText,errortype);
		else
			alert(req.status+" : "+req.statusText+" "+req.responseText,errortype)
	}
	});
}

function asyncData(as_url,id,index,max,as_val1,as_val2){
	if(index == -1) {
		min_i = 0;
		max_i = max;
	}
	else{
		min_i = index;
		max_i = index;
	}
	wcd = $("#"+as_val1).val();
	for(i = min_i; i <= max_i; i++){
		gcd = $("#"+as_val2+i).val();
		$.ajax({
			url:as_url,
			type:"GET",
			async: false,
			data: {whousecd : wcd, goodscd : gcd},
			dataType:"text",
			success: function(req,stat) {
				if(stat=="success") {result = parseInt(req);}
			},
			error: (log)=>{alert("실패" + log)},
		});
		$("#"+id+i).val(result);
	}
}

function asyncSelectData(as_url,fstid,fstval,scdid,scdval){
	if(fstid != '') {
		fstval = $("#"+fstid).val();
		scdval = '';
	}
	$.ajax({
		url:as_url,
		type:"GET",
		data: {val : fstval, selectid : scdid},
		dataType:"json",
		success: function(data) {
			$("select#"+scdid+" option").remove();
			$("#"+scdid).append("<option value=''>선택</option>");
			for(i = 0; i < data.length; i++){
				if(data[i].scdid == scdval){
					$("#"+scdid).append("<option value='"+data[i].scdid+"' selected>"+data[i].menunm+"</option>");
				}
				else{
					$("#"+scdid).append("<option value='"+data[i].scdid+"'>"+data[i].menunm+"</option>");
				}
			}
		},
		error: (log)=>{alert("실패" + log)},
	});
}