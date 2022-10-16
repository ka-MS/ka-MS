<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	span.guide {
		display : none;
		font-size : 12px;
		top : 12px;
		right : 10px;
	}
	span.ok{color : green}
	span.error{color : red}
</style>
</head>
<body>
	<h1 align="center">회원가입</h1>
	<div class="">
		<form action="/member/register.kh" method="post">
			<table>
				<tr>
					<td>* 아이디</td>
					<td><input type="text" id="memberId" name="memberId" placeholder="아이디는 5자 이상"> <span id="p4"></span>
						<span class="guide ok">이 아이디는 사용 가능합니다</span>
						<span class="guide error">이 아이디는 사용할 수 없습니다.</span>
						<!-- $(".guide.ok").show(); 를 사용하면 보여줄 수 있음-->
						<!-- $(".guide.ok").hide(); 를 사용하면 보여줄 수 있음-->
					
					</td>
				</tr>
				<tr>
					<td>* 비밀번호</td>
					<td><input type="password" name="memberPwd"></td>
				</tr>
				<tr>
					<td>* 이름</td>
					<td><input type="text" name="memberName"></td>
				</tr>
				<tr>
					<td>* 이메일</td>
					<td><input type="text" name="memberEmail"></td>
				</tr>
				<tr>
					<td>* 전화번호</td>
					<td><input type="text" name="memberPhone"></td>
				</tr>
				<tr>
					<td>* 우편번호</td>
					<td><input type="text" name="post" class="postcodify_postcode5"
						value="${addrInfos[0] }">
						<button type="button" id="postcodify_search_button">검색</button>
						</td>
				</tr>
				<tr>
					<td>* 도로명 주소</td>
					<td><input type="text" name="address1" class="postcodify_address"
						value="${addrInfos[1] }"></td>
				</tr>
				<tr>
					<td>* 상세 주소</td>
					<td><input type="text" name="address2" class="postcodify_details"
						value="${addrInfos[2] }"></td>
				</tr>
				<tr>
					<td><input type="submit" value="가입하기"></td>
					<td><input type="reset" value="취소"></td>
				</tr>
			</table>
				<a href="/home.kh">홈화면으로</a>
		</form>
	</div>
	<!-- 주소api가져와서 쓰기!!!!!좋음 -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
	<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
	<script >
	$("#postcodify_search_button").postcodifyPopUp();
// 	$("#memberId").on("input",function(){ //이렇게도 가능
	$("#memberId").keyup(function(){
		var insertid = $("#memberId").val();
		var regID = /[a-z0-9_]{5,16}$/;
		var regID2 = /[a-z0-9]{1,4}$/;
		console.log(insertid =="")
// 		if(regID2.test(insertid)){
// 			$("#p4").html("아이디는 5자 이상이어야 합니다.")
// 		}
		if(insertid ==""){
			$("#p4").html("");
		}
		else if(!regID.test(insertid)){
		$("#p4").html("아이디는 5자이상 영문소문자, 숫자,특수문자(_)만 사용 가능합니다.");
			
		}else{
			
		
		$.ajax({
			url : "/member/checkId.kh",
			type : "get",
			data : {"insertid" : insertid},
			success : function(data){
				if(data != ""){
					var str2 = "<span class='error'>["+data+"] 이미 사용중인 아이디 입니다.</span>";
					$("#p4").html(str2)
				}else{
					var str = "<span class='ok'>이 아이디는 사용 가능합니다</span>"
					$("#p4").html(str)
				}
				console.log("연결 성공")
			},
			error : function(){
				console.log("서버 연결에 실패")
			}
			
		})
		}
	})
	</script>

</body>
</html>