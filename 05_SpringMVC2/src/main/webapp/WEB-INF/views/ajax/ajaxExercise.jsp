<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ajax 알아보기</title>
<script src="http://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>
	<h1>Ajax 개요</h1>
	<p>
		Ajax는 Asynchronous Javascript And XML이란 용어로<br> 서버로부터 데이터를 가져와 전체
		페이지를 새로 고치지 않고 일부만 로드할 수 있게 하는 기법<br> 비동기식 요청을 함.
	</p>
	<h3>동기식/비동기식이란?</h3>
	<p>
		동기식은 서버와 클라이언트가 동시에 통신하여 프로세스를 수행 및 종료까지 같이하는 방식<br> 이에 반해 비동기식은
		페이지 리로딩없이 서버요청 사이사이 추가적인 요청과 처리 가능
	</p>
	<h3>Ajax 구현(Javascript)</h3>
	<h4>1. ajax로 서버에 전송값 보내기</h4>
	<p>버튼 클릭시 전송값을 서버에서 출력</p>
	<input type="text" id="msg-1">
	<button onclick="jsFunc();">보내기(JS)</button>
	<h3>Ajax 구현(jQuery)</h3>
	<h4>2. ajax(jQuery)로 서버에 전송값 보내기</h4>
	<p>버튼 클릭시 전송값을 서버에서 출력</p>
	<input type="text" id="msg-2">
	<button onclick="jqueryFunc();">보내기(jQuery)</button>
	<h3>3. 버튼 클릭시 서버에서 보낸 값 수신</h3>
	<button id="jq-btn3">서버에서 보낸값 확인</button>
	<p id="confirm-area"></p>
	<h4>4. 서버로 전송값 보내고 결과 문자열 받아서 처리</h4>
	<p>숫자 2개를 전송하고 더한 값 받기</p>
	첫번째 수 :
	<input type="text" id="num-1">
	<br> 두번째 수 :
	<input type="text" id="num-2">
	<br>
	<button id="jq-btn4">전송 및 결과확인</button>
	<p id="p4"></p>
	<h1>JSON 개요</h1>
	<p>
		Ajax 서버 통신시 데이터 전송을 위한 포맷 <br> JSON(Javscript Object Notation) :
		자바스크립트 객체 표기법 <br> JSON을 사용하면 모든 데이터형을 서버와 주고 받을 수 있다.(사용목적) <br>
		숫자, 문자열, boolean, 배열, 객체, null <br> '{'으로 시작하여 '}'로 끝나며 그 속에 데이터를
		표기하고 'key : value(값)' 쌍으로 구성된다.
	<pre>
		{
			"name" : "이순신",
			"age" : 27,
			"birth" : "1990-01-01",
			"gender" : "남",
			"marry" : true,
			"address" : "서울특별시 중구 인사동",
			"family" : {  
						"father" : "아버지",
						"mother" : "어머니",
						"brother" : "동생"
					}
		}
	</pre>
	</p>
	<h4>5. 서버로 전송값 보내고 결과 JSON으로 받아서 처리</h4>
	유저 번호 입력 :
	<input type="text" id="user-num">
	<br>
	<p id="p5">비밀번호 :</p>
	<button id="jq-btn5">실행 및 결과확인</button>
	<h4>6. 서버로 전송값을 보내고 JSONArray로 결과 받아서 처리</h4>
	<p>유저 번호를 보내서 해당 유저를 가져오고, 없는 경우 전체리스트 가져오기</p>
	유저 번호 입력 :
	<input type="text" id="find-num">
	<br>
	<p id="p6"></p>
	<button id="jq-btn6">실행 및 결과확인</button>
	<h4>7. GSON을 이용한 List 변환</h4>
	<p>전체리스트 가져오기</p>
	<p id="p7"></p>
	<button id="jq-btn7">실행 및 결과확인</button>
	example
	<input type="text" id="data1">
	<input type="text" id="data2">
	<button id="asJson">asJson</button>






	<script>
		$("#asJson").on("click", function() {
// 	var data1 = $("#data1").val()
// 	var data2 =$("#data2").val()
			$.ajax({
				url : "asJson",
// 				type : "get",
// dataType:"json", 데이터타입 지정 혹은 controller에 produce 지정 해 주어야 jsontype으로 받을 수 있음
				data : {
					data1 : $("#data1").val(), data2 : $("#data2").val()
				},
				success : function(result) {
					console.log(result)
					console.log(result.data1 + " : " + result.data2);
				}
			})
		})

		//input 문자가 change 되었을때 ajax로 확인

		$("#jq-btn7").on(
				"click",
				function() {
					$.ajax({
						url : "/ajax/ex6.kh",
						type : "get",
						data : {},
						success : function(data) {
							console.log(data);
							var str = "";
							for (var i = 0; i < data.length; i++) {
								console.log(data[i].memberId);
								str += data[i].memberId + ","
										+ data[i].memberPwd + "<br>";
								$("#p7").html(str);
							}
						},
						error : function() {
							console.log("출력 실패")
						}
					});
				})

		$("#jq-btn6").on(
				"click",
				function() {
					var userId = $("#find-num").val()
					console.log(userId);
					$.ajax({
						url : "/ajax/ex5.kh",
						type : "get",
						data : {
							"userId" : userId
						},//데이터, key값
						success : function(data) {
							console.log(data);
							var str = ""; //아주중요!! 데이터를 누적복붙하는방법! Q) 어느정도 길이까지 한번에 담을수 있나 ?
							for (var i = 0; i < data.length; i++) {
								str += data[i].memberId + ","
										+ data[i].memberPwd + "<br>"
								$("#p6").html(str);
							}
							// 					str += 1 + "<br>";
							// 					str += 2 + "<br>";
							// 					str += 3 + "<br>";
							// 					str += 4 + "<br>";
							// 					$("#p6").append(str);

						},
						error : function() {
							console.log("전송실패");
						}

					});
				});

		$("#jq-btn5").on("click", function() {
			var userid = $("#user-num").val();
			$.ajax({
				url : "/ajax/ex4.kh",
				type : "get",
				data : {
					"userid" : userid
				},
				success : function(result) {
					console.log(result);
					$("#p5").html("비밀번호 : " + result.memberPwd);
					console.log(result.rPwd)
					// 					console.log(result.userNo);
					// 					console.log(result.userName);
					// 					console.log(result.userAddr);
				},
				error : function() {
					console.log("서버처리 실패");
				}
			})
		})
		$("#jq-btn4").on("click", function() {
			var num1 = $("#num-1").val();
			var num2 = $("#num-2").val();
			$.ajax({
				url : "/ajax/ex3.kh",
				type : "get",
				data : {
					"num1" : num1,
					"num2" : num2
				},//데이터를 제이슨으로 보내는 상태
				success : function(result) { //result는 받는것 html에서 데이터 받을것임
					$("#p4").html("연산결과 : " + result);
				},
				error : function() {
					console.log("서버처리실패")
				}
			})
		})
		$("#jq-btn3").on("click", function() {
			$.ajax({
				url : "/ajax/ex2.kh",
				type : "get",
				success : function(data) { //data는 보내는것 서버값을 보냈음
					$("#confirm-area").html(data);
					console.log("처리 성공");
				},
				error : function() {
					console.log("처리 실패");
				}
			})

		});
		function jqueryFunc() {
			var message = $("#msg-2").val();
			$.ajax({
				url : "/ajax/ex1.kh",
				data : {
					"msg" : message
				},
				type : "get",
				success : function() {//콜백함수 응답값을가지고 출력
					console.log("서버 전송 성공");
				},
				error : function() {
					console.log("서버 전송 실패");
				}

			});
		}
		function jsFunc() {
			// 1. XMLHttpRequest 객체 생성
			var xhttp = new XMLHttpRequest();
			var msg = document.querySelector("#msg-1").value;
			// 2. 요청정보 설정
			xhttp.open("GET", "/ajax/ex1.kh?msg=" + msg, true);
			// 3. 테이터 처리에 따른 동작함수 설정
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					//status -> 200(요청성공), 404(페이지 url없음), 
					// 500(서버오류 발생), 403(접근거부), 400(쿼리스트링갯수오류)
					console.log("서버전송 성공")
				} else if (this.readyState == 4 && this.status == 404) {
					console.log("서버전송 실패")
				}
			}
			// 4. 전송
			xhttp.send();
		}
	</script>
</body>
</html>