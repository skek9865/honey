function gf_modal(as_url,as_acd,as_bcd,as_ccd,as_action,as_val,as_val1) {
	console.log(as_acd);
	console.log(as_bcd);
	console.log(as_ccd);
	$.ajax({
		url:as_url,
		type:"GET",
		data: { fstId : as_acd ,scdId : as_bcd ,thdId : as_ccd ,action : as_action ,vseq : as_val,vseq1 : as_val1},
		dataType:"html",
		success: function(req,stat) {
			if(stat=="success") {
				$("#modalhtml").html(req);
			}
		},
		error: (log)=>{alert("실패" + log.value)},
	});
}

function gf_menu(as_acd,as_bcd,as_ccd,as_empno) {
	var purl = "menu";
	$.ajax({
	url:purl,
	type:"GET",
	data: { fstId : as_acd ,scdId : as_bcd ,thdId : as_ccd ,userId : as_empno},
	dataType:"html",
	complete: function(req,stat) {
		if(stat=="success") {
			$('#sb-sidenav-menu').html(req.responseText);
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

function gf_mainsrc(as_acd,as_bcd,as_ccd,as_empno,as_qs) {
	var purl = "src_" + as_acd + as_bcd + as_ccd + ".php";
	var err_page = "src_" + as_acd + as_bcd + as_ccd + ".php";

	$.ajax({
	url:purl,
	type:"GET",
	data: { ACD : as_acd ,BCD : as_bcd ,CCD : as_ccd ,sj_userid : as_empno},
	dataType:"html",
	contentType: 'application/x-www-form-urlencoded; charset=utf-8',
	complete: function(req,stat) {
		if(stat=="success") {
			$('#layoutSidenav_content').html(req.responseText);
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

function gf_tab(as_acd,as_bcd,as_ccd,as_userid,as_qs) {
	var purl = "sub_tab.php";

	$.ajax({
	url:purl,
	type:"GET",
	data: { ACD : as_acd ,BCD : as_bcd ,CCD : as_ccd ,sj_userid : as_userid,querys : as_qs},
	dataType:"html",
	contentType: 'application/x-www-form-urlencoded; charset=utf-8',
	complete: function(req,stat) {
		if(stat=="success") {
			alert(req.status+" : "+req.statusText+" "+req.responseText,errortype);
		}
			alert(req.status+" : "+req.statusText+" "+req.responseText,errortype);
	},
	error: function( req, errortype) {
		if (typeof view_error == 'function')
			view_error(req.status+" : "+req.statusText+"<br/>"+req.responseText,errortype);
		else
			alert(req.status+" : "+req.statusText+" "+req.responseText,errortype);
		}
	});
}

function get_smenu(obj,divid,val,empno){
	obj.src="sub_frame.php?mode=1&divid="+divid+"&fstid="+val+"&empno="+empno;
	return;
} 

function gf_popup(as_width,as_height,as_name,as_url) {
	var specs = "width="+as_width+",height="+as_height;
	specs += ",menubar=no,toolbar=no,location=no,status=yes,titlebar=no,scrollbars=yes,resizable=no";
	window.open(as_url,as_name,specs);
	return;
}
function openZipSearch(as_zip,as_addr1,as_addr2) {
	new daum.Postcode({
		oncomplete: function(data) {
			$('[name='+as_zip+']').val(data.zonecode);
			$('[name='+as_addr1+']').val(data.address);
			$('#'+as_addr2).focus();
		}
	}).open();
}
function selectall(val,name,doc) {
	var cbox = doc.getElementsByName(name);  
	len = cbox.length;
	var i=0;
	for( i=0 ; i<len ; i++) {
		cbox[i].checked=val.checked;
	}
}  
function poly_money(val){
s=n=0;
put_str=new String(val);
s=put_str.length%3;     // 2 2 0
n=(put_str.length-s)/3; // 0 1 3

if(s==0){
	s=3;
	n=n-1;
}
	get_str=put_str.substring(0,s);
	i=1;
	while(n>0 && i<=n){
		get_str+=","+put_str.substring((i-1)*3+s,i*3+s);
		i++;
	}
	return get_str;
}
function lf_poly_money(Fobj){
	s=n=0;
	put_str=Fobj.value;     // 2 5 9
	s=put_str.length%3;     // 2 2 0
	n=(put_str.length-s)/3; // 0 1 3

	if(s==0){
		s=3;
		n=n-1;
	}

	get_str=put_str.substring(0,s);
	i=1;
	while(n>0 && i<=n){
		get_str+=","+put_str.substring((i-1)*3+s,i*3+s);
		i++;
	}
	Fobj.value=get_str;
}
function lf_depoly_money(Fobj){
		var Re = /\,/g; 
		put_str=Fobj.value;     //  3  4  5  6  7  8  9  10
		put_str = put_str.replace(Re,'');
		Fobj.value=put_str;
}
function Sms_ChkLen(id,len,obj) { 
	var msgtext, msglen; 

	msgtext = obj.value; 
	msgtext_cnt = document.getElementById(id);
	msglen = msgtext_cnt.innerText; 

	var i=0,l=0; 
	var temp,lastl; 

	//���̸� ���Ѵ�. 
	while(i < msgtext.length) 
	{ 
		temp = msgtext.charAt(i); 

		if (escape(temp).length > 4) 
		l+=2; 
		else if (temp!='\r') 
		l++; 
		// OverFlow 
		if(l>len) 
		{ 
			alert("�޽��������� �ѱ� 40��, ����80�ڱ����� ���� �� �ֽ��ϴ�."); 
			obj.value = msgtext.substr(0,i); 
			l = lastl; 
			break; 
		} 
		lastl = l; 
		i++; 
	}
	msgtext_cnt.innerText = l;
} 

function AddComma(num) 
{

	var regexp = /\B(?=(\d{3})+(?!\d))/g;
	return num.toString().replace(regexp, ',');

}
function delComma(num) 
{

	var regexp = /,/g
	var Tnum = num.toString().replace(regexp, '');
	var Tnumf = parseFloat(Tnum);
	return Tnumf;

}
