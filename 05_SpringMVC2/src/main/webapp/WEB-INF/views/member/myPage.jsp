<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
</head>
<body>
	<h1 align="center">정보수정</h1>
	<div class="">
		<form action="/member/modify.kh" method="post">
			<table>
				<tr>
					<td>* 아이디</td>
					<td><input type="text" id="memberId" name="memberId"
						value="${member.memberId }" readonly></td>
				</tr>
				<tr>
					<td>* 비밀번호</td>
					<td><input type="password" name="memberPwd" value=""></td>
				</tr>
				<tr>
					<td>* 이름</td>
					<td><input type="text" name="memberName"
						value="${member.memberName }" readonly></td>
				</tr>
				<tr>
					<td>* 이메일</td>
					<td><input type="text" name="memberEmail"
						value="${member.memberEmail }"></td>
				</tr>
				<tr>
					<td>* 전화번호</td>
					<td><input type="text" name="memberPhone"
						value="${member.memberPhone }"></td>
				</tr>
				<tr>
					<td>* 우편번호</td>
					<td><input type="text" name="post" value="${addrInfos[0] }"></td>
				</tr>
				<tr>
					<td>* 도로명 주소</td>
					<td><input type="text" name="address1" value="${addrInfos[1] }"></td>
				</tr>
				<tr>
					<td>* 상세 주소</td>
					<td><input type="text" name="address2" value="${addrInfos[2] }"></td>
				</tr>
				<tr>
					<td><input type="submit" value="수정하기">
						<button type="button" onclick="removeMember();">탈퇴하기</button></td>
					<td><input type="reset" value="취소"></td>
				</tr>
			</table>
			<a href="/home.kh">홈화면으로</a>
		</form>
	</div>
	<script>
		//버튼에 type="button"을 넣어주지않으면 404 에러가 뜬다 주의!
		function removeMember() {
			if (confirm("탈퇴하시겠습니까?")) {
				location.href = "/member/remove.kh"

			}
		}
	</script>
</body>
</html>