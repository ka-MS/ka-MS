<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Api 샘플 화면</title>
</head>
<body>
	<h1>Api 샘플</h1>
	<h2>1. 다음(카카오) 주소찾기</h2>
	<input type="text" id="postCode" placeholder="우편번호">
	<input type="button" id="" onclick="addSearch()" value="우편번호 찾기"><br>
	<input type="text" id="roadAddr" placeholder="도로명주소">
	<input type="text" id="jibunAddr" placeholder="지번정보">
	<input type="text" id="detailAddr" placeholder="상세정보">
	
	<h2>2. 네이버 지도</h2>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script type="text/javascript">
		function addSearch(){
			new daum.Postcode({
				
			}).open();
			
		}
	
	</script>
	<script>
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
            // 예제를 참고하여 다양한 활용법을 확인해 보세요.
        }
    }).open();
    
	</script>
</body>
</html>