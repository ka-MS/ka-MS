<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
</head>
<body>
	<h1 align="center">회원가입</h1>
	<div align="center" class="">
		<form  action="/member/modify.tripkase" method="POST">
		<table>
			<tr>
				<td> * 아이디</td>
				<td>
					<input type="text" id="memberId" name="memberId" value="${mOne.memberId }" readonly>
				</td>
			</tr>
			<tr>
				<td> * 현재 비밀번호</td>
				<td>
					<input type="text" name="memberPwd" value="${mOne.memberPwd }" readonly>
				</td>
			 </tr>
			<tr>
				<td> * 닉네임</td> 
				<td>
					<input type="text" name="memberNick" value="${mOne.memberNick }" readonly>
				</td>
			</tr>
			<tr>
				<td> * 이름</td>
				<td>
					<input type="text" name="memberName" value="${mOne.memberName }" readonly>
				</td>
			</tr>
			<tr>
				<td> * 생년월일</td>
				<td>
					<input type="text" name="memberBd" value="${mOne.memberBd }" readonly>
				</td>
			</tr>
			<tr>
				<td> * 성별</td>
				<td>
					<input type="text" name="memberGender" value="${mOne.memberGender }" readonly>
				</td>
			</tr>
			<tr>
				<td> * 핸드폰번호</td>
				<td>
					<input type="text" name="memberPhone" value="${mOne.memberPhone }" readonly>
				</td>
			</tr>
			<tr>
				<td> * 이메일</td>
				<td>
					<input type="text" name="memberEmail" value="${mOne.memberEmail }" readonly>
				</td>
			</tr>
			<tr>
				<td> * 현재 나의 등급</td>
				<td>
					<input type="text" name="memberGrade" value="${mOne.memberGrade }" readonly>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<td><a href="/member/modify.tripkase">수정하기</a></td>
					<button type="button" onclick="removeMember();">탈퇴하기</button>
				</td>
			</tr>
		</table>
	</form>
	</div>
	<script>
		function removeMember() {
			if(confirm("탈퇴하시겠습니까?")){
			location.href="/member/remove.kh";
			}
		}
		
		$("#postcodify_search_button").postcodifyPopUp();
	</script>
</body>
</html>