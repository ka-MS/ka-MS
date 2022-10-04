<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1 align="center">회원가입</h1>
	<div class="">
		<form action="/member/register.kh" method="post">
			<table>
				<tr>
					<td>* 아이디</td>
					<td><input type="text" id="memberId" name="memberId">
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

	</script>

</body>
</html>