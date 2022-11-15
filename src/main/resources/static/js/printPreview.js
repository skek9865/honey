function pagePrintPreview(){
	var browser = navigator.userAgent.toLowerCase();
	if ( -1 != browser.indexOf("chrome") ){
		 window.print();
	}else if ( -1 != browser.indexOf("trident") ){
	 try{
		//참고로 IE 5.5 이상에서만 동작함
		//웹 브라우저 컨트롤 생성
		var webBrowser = "<OBJECT ID=\'previewWeb\' WIDTH=0 HEIGHT=0 CLASSID=\'CLSID:8856F961-340A-11D0-A96B-00C04FD705A2\'></OBJECT>";
		//웹 페이지에 객체 삽입
		document.body.insertAdjacentHTML("beforeEnd", webBrowser);
		//ExexWB 메쏘드 실행 (7 : 미리보기 , 8 : 페이지 설정 , 6 : 인쇄하기(대화상자))
		previewWeb.ExecWB(7, 1);
		//객체 해제
		previewWeb.outerHTML = "";
	 }catch (e) {
		alert("* 도구 > 인터넷 옵션 > 보안 탭 > 신뢰할 수 있는 사이트 선택\n   1. 사이트 버튼 클릭 > 사이트 추가\n   2. 사용자 지정 수준 클릭 > 스크립팅하기 안전하지 않은 것으로 표시된 ActiveX 컨트롤 (사용)으로 체크\n\n※ 위 설정은 프린트 기능을 사용하기 위함임");
	 }
	}
}
;(function ( $ ) {
    $.fn.printPreview = function( options ) {
        var elem = this;
        var opt = $.extend({
            obj2print:'body',
            style:'',
            width:'670',
            height:screen.height-105,
            top:0,
            left:'center',
            resizable : 'yes',
            scrollbars:'yes',
            status:'no',
            title:'Print Preview'
        }, options );
        if(opt.left == 'center'){
            opt.left=(screen.width/2)-(opt.width/2);
        }
        $(opt.obj2print+" input").each(function(){
            $(this).attr('value',$(this).val());
        });
        $(opt.obj2print+" textarea").each(function(){
            $(this).html($(this).val());
        });
        return elem.bind("click.printPreview", function () {
            var headString = '<style>table.report_type {border-collapse: separate;border-spacing: 0;text-align: left;line-height: 2;border-top: 1px solid #ccc;border-left: 1px solid #ccc;}table.report_type th {padding: 1px;font-size:12px;font-weight: bold;vertical-align: middle;text-align:center;border-right: 1px solid #ccc;border-bottom: 1px solid #ccc;border-top: 1px solid #fff;border-left: 1px solid #fff;background: #eee;}table.report_type td {padding: 1px;font-size:12px;vertical-align: middle;border-right: 1px solid #ccc;border-bottom: 1px solid #ccc;}table.report_type td {padding: 1px;font-size:12px;vertical-align: middle;border-right: 1px solid #ccc;border-bottom: 1px solid #ccc;}table.report_type td.aright {text-align:right;}table.report_type td.acenter {text-align:center;}table.report_type td.cred {color:red;}div {width:100%;} .h_title{width:1500px;color:black;display:block;} .h_title_s{width:800px;color:black;display:block;} .h_title_t{	width:100%;	height:50px;	text-align:center;	font-size:40px;	vertical-align:middle;	display:block;} .h_title_c{	width:100%;	text-align:left;	height:00px;	font-size:12px;	vertical-align:bottom;}</style>';
            //headString = $("head").html();
						var js_str = '<script language="javascript" type="text/javascript" src="/ems/js/jquery.min.js"></script><SCRIPT language="javascript" type="text/javascript" src="/ems/js/printPreview_a.js"></SCRIPT>';
            var str = '<!DOCTYPE html><html><head>'+headString+opt.style+'</head><body><button id="btnPrint" onclick="this.style.display=\'none\'; pagePrintPreview(); this.style.display=\'block\'; ">미리보기</button>';
            str+=js_str+$(opt.obj2print)[0].outerHTML+"</body></html>";
            //top open multiple instances we have to name newWindow differently, so getting milliseconds
            var d = new Date();
            var n = 'newWindow'+d.getMilliseconds();
            var newWindow = window.open(
                    "", 
                    n, 
                    "width="+opt.width+
                    ",top="+opt.top+
                    ",height="+opt.height+
                    ",left="+opt.left+
                    ",resizable="+opt.resizable+
                    ",scrollbars="+opt.scrollbars+
                    ",status="+opt.status
                    );
            newWindow.document.write(str);
            newWindow.document.title = opt.title;
						//newWindow.document.close();
						//pagePrintPreview();

//						newWindow.print();
        });
    };
}( jQuery ));
