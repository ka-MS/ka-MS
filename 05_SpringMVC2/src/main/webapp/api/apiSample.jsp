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
	<input type="text" id="postCode" placeholder="우편번호" readonly>
	<input type="button" id="" onclick="addSearch()" value="우편번호 찾기"><br>
	<input type="text" id="roadAddr" placeholder="도로명주소"readonly>
	<input type="text" id="jibunAddr" placeholder="지번정보"readonly>
	<input type="text" id="detailAddr" placeholder="상세정보">
	
	<h2>2. 네이버 지도</h2>
	<div id="map" style="width:100%;height:500px;"></div>
	
	
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=2i99b6lxe8&submodules=geocoder"></script>
	<script type="text/javascript">
		function addSearch(){
			new daum.Postcode({
		        oncomplete: function(data) {
		            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
		            // 예제를 참고하여 다양한 활용법을 확인해 보세요.
		            console.log(data)
		            document.querySelector("#postCode").value = data.zonecode;
		            document.querySelector("#roadAddr").value = data.roadAddress;
		            document.querySelector("#jibunAddr").value = data.jibunAddress;
		        }
		    }).open();
		}
		var mapOptions = {
			    center: new naver.maps.LatLng(37.5674, 126.9830),
			    zoom: 20,
			    zoomControl : true,
			    zoomControlOption : {
			    	position : naver.maps.Position.TOP_RIGHT,
			    	style : naver.maps.ZoomControlStyle.SMALL
			    }
			};
		//mapOptions를 아래 변수에 넣어서 구문을 하나로 합칠수도있음
		var map = new naver.maps.Map('map', mapOptions); //지도생성
		
		var markerOptions = {};
		//마커 찍기
		var marker = new naver.maps.Marker({//마커생성
			position : new naver.maps.LatLng(37.56793990753354, 126.98311454509121), //옵션설정
			map : map
		});
		
		//마커에 정보창 표시
		var contentStr = "<div><h3>kh정보교육원</h3><p>서울시 중구 남대문로 120 대일빌딩 3F</p></div>";
		var infoWindow = new naver.maps.InfoWindow({
			content : contentStr
		});
		
		
		//말풍선창 제어
		infoWindow.open(map,marker);
		naver.maps.Event.addListener(marker,"click",function(e){
			//true면 열려있고 fales면 닫혀있고
			if(infoWindow.getMap()){
			infoWindow.close();
				
			}else{
				infoWindow = new naver.maps.InfoWindow({
					content : contentStr
				});
				infoWindow.open(map,marker);
			}
		});
		
		naver.maps.Event.addListener(map,"click", function(e) {
         marker.setPosition(e.coord);
         if(infoWindow.getMap()) {
            infoWindow.close();
         }
         naver.maps.Service.reverseGeocode({  // < JSON으로 넘겨줌
            location : new naver.maps.LatLng(e.coord.lat(),e.coord.lng()) //위도 경도값 넘겨줌
         }, function(status, response) {
        	 console.log(response)
            var data = response.result;
            var items = data.items;
            var address = items[1].address; 
            contentStr = "<div><p>" + address + "</p></div>";
         });
      });
	</script>
</body>
</html>