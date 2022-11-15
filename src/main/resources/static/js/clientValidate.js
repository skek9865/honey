<!--
//
//  ===============================================================================================
//  Function Name : gf_checkSubmit(myLabel,myObj,m_mandatory,min_length,max_length,m_numCheck,m_lowValue,m_highValue,myFocus,myValue)
//  Description   : 폼을 submit하기전에 입력값의 유효성을 체크한다.
//  myLabel		  : 입력태그의 레이블 (오류를 출력할때 표시해준다)
//  myObj		  : 입력태그의 객체
//  m_mandatory	  : 필수입력태그인가 {0=NO | 1=YES}
//  min_length	  : 최소 입력 길이 {0=검사하지 않는다}
//  max_length	  : 최대 입력 길이 {0=검사하지 않는다}
//  m_numCheck	  : 검사할 유형 {
//                      0=검사하지 않는다 |
//                      1=Numeric |
//                      2=Date |
//                      3=영문자와 숫자 |
//                      4=Email |
//                      5=주민등록번호 |
//                      6=사업자번호 |
//                      7=영문자와 숫자(첫글자는 반드시 영문자) 
//                  }
//  m_lowValue	  : 숫자형 검사할때 최소 값 {0=검사하지 않는다}
//  m_highValue	  : 숫자형 검사할때 최대 값 {0=검사하지 않는다}
//  myFocus 	  : 오류처리후 myObj 대신 myFoucs로 포커스를 넘긴다.
//  myValue 	  : 주민등록번호, 사업자번호 처럼 myObj가 아닌 값으로 체크할때 그값을 지정한다.
//	return 		  : 처리결과 {true | false}
//  ===============================================================================================

function gf_checkSubmit(myLabel,myObj,m_mandatory,min_length,max_length,m_numCheck,m_lowValue,m_highValue,myFocus,myValue) {
	if (myFocus+"" == "undefined") {
		myFocus = myObj;
	}
	if (m_mandatory == 1) {
		if ( !gf_checkMandatory(myObj.value) ) {
			alert(myLabel +' : 필수 입력 항목입니다.');
			myFocus.focus();  /* 날짜 형식은 Focus 이동 불가 */
			return false;
		}
	}
	if ( !gf_checkMaxLength(myObj.value,max_length) ) {
		alert(myLabel + ': 입력값이 '+max_length+' 길이를 초과하였습니다.');
		myFocus.focus();
		return false;
	}
	if ( !gf_checkMinLength(myObj.value,min_length) ) {
		alert(myLabel + ': 입력값이 허용 길이에 못미칩니다.');
		myFocus.focus();
		return false;		
	}
	if ( m_numCheck == 1 ) {  /* 숫자 체크 */
		if ( !gf_checkNumeric(myObj.value) ) {
			alert(myLabel + ': 입력값이 숫자가 아닙니다.');
			myFocus.focus();
			return false;
		} 
		if ( !gf_checkLowHigh(myObj.value,m_lowValue,m_highValue) ) {
			alert(myLabel + ': 입력값이 허용 범위를 벗어났습니다.');
			myFocus.focus();
			return false;
		}
	} 
	else if ( m_numCheck == 2 ) {  /* 날짜 체크 */
		if ( !gf_checkDate(myObj.value) ) {
			alert(myLabel + ': 입력 날짜 형식이 틀렸습니다.');
			myFocus.focus();
			return false;
		}
	}
	else if ( m_numCheck == 3 ) {  /* AlphaNumeric 체크 (영문자와 숫자만 가능) */
		if ( !gf_checkAscii(myObj.value) ) {
			alert(myLabel + ': 영어와 숫자만 입력할 수 있습니다.');
			myFocus.focus();
			return false;
		}
	}
	else if ( m_numCheck == 4 ) {  /* Email ID 체크 */
		if ( !gf_checkEmail(myObj.value) ) {
			alert(myLabel + ': 이메일 형식이 틀렸습니다.');
			myFocus.focus();
			return false;
		}
	}
	else if ( m_numCheck == 5 ) {  /* 주민등록번호 체크 */
		if ( !gf_checkRegNo(myValue) && !gf_checkForeignNo(myValue) ) {
			alert(myLabel + ': 주민등록번호가 틀렸습니다.');
			myFocus.focus();
			return false;
		}
	}
	else if ( m_numCheck == 6 ) {  /* 사업자번호 체크 */
		if ( !gf_checkBizNo(myValue) ) {
			alert(myLabel + ': 사업자번호가 틀렸습니다.');
			myFocus.focus();
			return false;
		}
	}
	else if ( m_numCheck == 7 ) {  /* 영문자와 숫자만 가능(시작은 영문자) */
		if ( !gf_checkAlphaFirst(myObj.value) ) {
			alert(myLabel + ': 영어로 시작되고 영어와 숫자만 입력할 수 있습니다.');
			myFocus.focus();
			return false;
		}
	}
	return true;
}

//주민등록번호 포맷 확인
function gf_checkRegNo(s) {
	if (s.length == 0) return true;
	if (s.length != 13) return false;
	if (s.charAt(2) > 1) return false;
	if (s.charAt(4) > 3) return false;
	if (s.charAt(6) > 4 || s.charAt(6) == 0) return false;
	if (s.charAt(12) != (( 11 - ((s.charAt(0)*2+s.charAt(1)*3+s.charAt(2)*4
		+s.charAt(3)*5+s.charAt(4)*6+s.charAt(5)*7
		+s.charAt(6)*8+s.charAt(7)*9+s.charAt(8)*2
		+s.charAt(9)*3+s.charAt(10)*4+s.charAt(11)*5)
		% 11)))%10) {
		return false;
	} 
	return true; 
}

//외국인등록번호 포맷 확인
function gf_checkForeignNo(s){  // 외국인 등록번호
	if (s.length == 0) return true;
	if (s.length != 13) return false;
	var li_year  = 0;
	var li_month = 0;
	var li_date  = 0;
	if ((s.charAt(6) == "5") || (s.charAt(6) == "6")) li_year = 19
	else if ((s.charAt(6) == "7") || (s.charAt(6) == "8")) li_year = 20
	else if ((s.charAt(6) == "9") || (s.charAt(6) == "0")) li_year = 18
	else return false;

	li_year  = li_year * 100 + s.substr(0, 2);
	li_month = s.substr(2, 2) - 1;
	li_date  = s.substr(4, 2);
	var ld_birth = new Date(li_year, li_month, li_date);
	
	if ( ld_birth.getYear() % 100 != s.substr(0, 2) ||
		 ld_birth.getMonth() != li_month ||
		 ld_birth.getDate() != li_date) {
		return false;
	}
	
	return lf_checkForeignNoCheckSum(s);

    //외국인등록번호 포맷 확인
	function lf_checkForeignNoCheckSum(s) {
		var sum = 0;
		var odd = 0;
		
		buf = new Array(13);
		for (i = 0; i < 13; i++) buf[i] = parseInt(s.charAt(i));

		odd = buf[7]*10 + buf[8];
		
		if (odd%2 != 0) return false;
		if ((buf[11] != 6)&&(buf[11] != 7)&&(buf[11] != 8)&&(buf[11] != 9)) return false;
			
		multipliers = [2,3,4,5,6,7,8,9,2,3,4,5];
		for (i = 0, sum = 0; i < 12; i++) sum += (buf[i] *= multipliers[i]);

		sum=11-(sum%11);
		if (sum>=10) sum-=10;
		sum += 2;
		if (sum>=10) sum-=10;
		if ( sum != buf[12]) {
			return false;
		} else {
			return true;
		}
	}
}

//사업자등록번호 포맷 확인
function gf_checkBizNo(s) {
	if (s.length == 0) return true;
	if (s.length != 10) return false;
	
	var t1 = s.charAt(0)*1+s.charAt(1)*3+s.charAt(2)*7+s.charAt(3)*1+s.charAt(4)*3;
	t1+= s.charAt(5)*7+s.charAt(6)*1+s.charAt(7)*3;
	var t2 = parseInt( s.charAt(8)*5/10, 10);
	var t3 = t2 % 10;
	var tt = 10 - ((t1 + t2 + t3) % 10);
	if (tt == s.charAt(9) ) {
		return true;
	} else {
		return false;
	}
}

//이메일 포맷 확인
function gf_checkEmail(s) {
	if (s.length == 0) return true;
	var exclude=/[^@\-\.\w]|^[_@\.\-]|[\._\-]{2}|[@\.]{2}|(@)[^@]*\1/;
	var check=/@[\w\-]+\./;
	var checkend=/\.[a-zA-Z]{2,3}$/;
	if ( (s.search(exclude) != -1) || (s.search(check) == -1) || (s.search(checkend) == -1) ) {
		return false;
	}
	else {
		return true;
	}
}

//필수 입력
function gf_checkMandatory(str) {
	if (str.length == 0) {
		return false;
	} else {
		return true;
	}
}

//길이 확인
function gf_checkLength(str,min,max) {
	if ( (min == 0) && (max == 0) ) {  
		return true;
	}
	var k = 0;
	var realLen = 0;
	for (var i=0 ; i<str.length ; i++) {
		readLen++;
		if (Number(str.charCodeAt(k++)) > 10000) realLen++;  /* 한글인 경우 2자리로 계산 */
	}
	if ( (max > 0) && (realLen > max) ) {
		return false;
	} else if ( (min > 0) && (realLen < min) ) {
		return false;
	} else {
		return true;
	}
}

//최대 길이
function gf_checkMaxLength(str,max) {
	if (max == 0) return true;
	var k = 0;
	var realLen = 0;
	for (var i=0 ; i<str.length ; i++) {
		realLen++;
		if (Number(str.charCodeAt(k++)) > 10000) realLen++;  /* 한글인 경우 2자리로 계산 */
	}
	if (realLen > max) {
		return false;
	} else {
		return true;
	}
}

//최소 길이
function gf_checkMinLength(str,min) {
	if (min == 0) return true;
	if (str.length < min) {
		return false;
	} else {
		return true;
	}
}

//숫자 확인
function gf_checkNumeric(str) {
	if (str.length == 0) return true;
	if( isNaN(str) ) {
		return false;
	} else {
		return true;
	}
}

//일자 포맷 확인
function gf_checkDate(mDate) {
	if (mDate.length == 0) return true;
	if (mDate.length != 10) return false;
	mYear  = Number(mDate.substr(0,4));
	mMonth = Number(mDate.substr(5,2));
	mDay   = Number(mDate.substr(8,2));
	if (mYear < 0) {
		return false;
	}
	if ( (mMonth < 1) || (mMonth > 12) ) {
		return false;
	}
	if (mMonth == 1 || mMonth == 3 || mMonth == 5 || mMonth == 7 || mMonth == 8 || mMonth == 10 || mMonth == 12) {
		if (mDay < 1 || mDay > 31) {
			return false;
		}
	} 
	else if (mMonth == 2)	{
		if ( (mYear % 4) == 0 && (mYear % 100) != 0) {
			if (mDay < 1 || mDay > 29) {
				return false;
			}			
		} else {
			if (mDay < 1 || mDay > 28) {
				return false;
			}						
		}
	} 
	else {
		if (mDay <1 || mDay > 30) {
			return false;
		}
	}
	return true;
}

//영숫자만 입력 가능
function gf_checkAscii(str) {
	for (var i=0 ; i < str.length ; i++) {
		if (str.charCodeAt(i) < 48) return false;
		if (str.charCodeAt(i) > 57 && str.charCodeAt(i) < 65) return false;
		if (str.charCodeAt(i) > 90 && str.charCodeAt(i) < 97) return false;
		if (str.charCodeAt(i) > 122) return false;
	}
	return true;
}


//영문자와 숫자만 입력 가능(첫글자는 반드시 영문자) 
function gf_checkAlphaFirst(str) { 
	for (var i=0 ; i < str.length ; i++) {
		if (str.charCodeAt(i) < 48) return false;
		if (str.charCodeAt(i) > 122) return false;
		if (str.charCodeAt(i) > 57 && str.charCodeAt(i) < 65) return false;
		if (str.charCodeAt(i) > 90 && str.charCodeAt(i) < 97) return false;
		if ( (i==0) && ( (str.charCodeAt(i) >= 49) && (str.charCodeAt(i) <= 58) ) ) return false;
	}
	return true;
} 

//최대, 최소값 확인
function gf_checkLowHigh(str,m_lowValue,m_highValue) {
	if (str.length == 0) return true;
	if ( (m_lowValue != 0) && (Number(str) < m_lowValue) ) {
		return false;
	}
	if ( (m_highValue != 0) && (Number(str) > m_highValue) ) {
		return false;
	}
	return true;
}

//
//  ===============================================================================================
//  Function Name : gf_checkSize(myLabel1,myLabel2,myValue1,myOper,myValue2,myFocus)
//  Description   : 폼을 submit하기전에 입력값의 유효성을 체크한다.
//  myLabel1	  : 크기를 비교할 앞에값 이름
//  myLabel2	  : 크기를 비교할 뒤에값 이름
//  myValue1	  : 크기를 비교할 앞에값
//  myOper		  : 비교 연산자 (이 조건이 true 면 입력오류 처리한다)
//  myValue2	  : 크기를 비교할 연산자
//  myFocus 	  : 오류처리후 myFoucs로 포커스를 넘긴다.
//	return 		  : 처리결과 {true | false}
//  ===============================================================================================
function gf_checkSize(myLabel1,myLabel2,myValue1,myOper,myValue2,myFocus) {
	var ret=true;
	if ( (myValue1 == "") || (myValue2 == "") ) return true;
	if ( (myValue1 == 0 ) || (myValue2 == 0 ) ) return true;
	if ( isNaN(myValue1) || isNaN(myValue2) ) {
		eval("ret = ('" + myValue1 + "' " + myOper + " '" + myValue2 + "');");
	}
	else {
		eval("ret = ( " + myValue1 + "  " + myOper + "  " + myValue2 + " );");
	}
	if (ret) {
		alert("입력값 비교 : " + myLabel1 + " " + myOper + " " + myLabel2 + " 일 수 없습니다.");
		myFocus.focus();
		return false;
	}
	else {
		return true;
	}
}

//0으로 체우기
function gf_zfill(obj) {
	if (obj.value.length != 1) return true;
	obj.value = '0'+obj.value;
	return true;
}

//0으로 체우기
function gf_zfill1(obj) {
	if (obj.value.length != 0) return true;
	obj.value = '0';
	return true;
}

//숫자 확인
function gf_checkNum(str) {
	if (str.length == 0) return false;
	if( isNaN(str) ) {
		return false;
	} else {
		return true;
	}
}

//콤마찍기 
function NumberFormat(num) { 
	var pattern = /(-?[0-9]+)([0-9]{3})/; 
	
	tmp = parseInt(num) ;
	//alert(tmp);
	num = tmp.toString();
	while(pattern.test(num)) { 
		num = num.replace(pattern,"$1,$2"); 
	} 
	return num; 
} 
 
 
//콤마제거 
function unNumberFormat(num) { 
	return (num.replace(/,/g,"")); 
}

//콤마제거 후 숫자처리 
function NumberPass(obj) { 
	obj = unNumberFormat(obj) ;
	if (gf_checkNum(obj)==false)
	{
		obj = "0"
	}
	return obj;
}

// -->
