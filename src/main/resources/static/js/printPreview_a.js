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
function imgPopup(url){
	var imgObj = new Image();
	imgObj.src = url;
	imageWin = window.open("","profile_popup","width="+imgObj.width+"px,height="+imgObj.height+"px");
	imageWin.document.write("<html><head><style>@page  {size: A4; margin: 0;}</style></head>");
	imageWin.document.write("<script src=\"/WEB_ERP/js/printPreview_a.js\"></script>");
	imageWin.document.write("<button id=\"btnPrint\" onclick=\"this.style.display='none'; pagePrintPreview(); this.style.display='block'; \">미리보기</button>")
	imageWin.document.write("<body style='margin: 0'>");
	imageWin.document.write("<a href=javascript:window.close()><img src='"+imgObj.src+"'border=0></a>");
	imageWin.document.write("</body></html>");
	imageWin.document.title=imgObj.src;
}
