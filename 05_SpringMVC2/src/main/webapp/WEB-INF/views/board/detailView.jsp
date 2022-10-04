<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세 정보</title>
<script src="/resources/js/jquery-3.6.1.min.js"></script>

</head>
<body>
	<h1 align="center">${board.boardNo }번게시글상세보기</h1>
	<br>
	<br>
	<table align="center" width="500" border="1">
		<tr>
			<td>제목</td>
			<td>${board.boardTitle }</td>
		</tr>
		<tr>
			<td>작성자</td>
			<td>${board.boardWriter }</td>
		</tr>
		<tr>
			<td>작성날짜</td>
			<td>${board.bCreateDate }</td>
		</tr>
		<tr>
			<td>조회수</td>
			<td>${board.boardCount }</td>
		</tr>
		<tr height="300">
			<td>내용</td>
			<td>${board.boardContents }</td>
		</tr>
		<tr height="300">
			<td>첨부파일</td>
			<td><img alt="본문이미지"
				src="/resources/buploadFiles/${board.boardFileRename }" width=300
				height=300></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<%-- ${sessionScope.loginUser.memberId } --%> <c:if
					test="${board.boardWriter eq sessionScope.loginUser.memberName }"></c:if>
				<a
				href="/board/modifyView.kh?boardNo=${board.boardNo }&page=${page}">수정페이지로</a>
				<a href="#" onclick="boardRemove(${page})">삭제하기</a> <a
				href="/board/list.kh">리스트</a>
			</td>
		</tr>
	</table>



	<form action="/board/addReply.kh" method="post">
	<input type="hidden" name="page" value="${page }">
	<input type="hidden" name="refBoardNo" value="${board.boardNo }">
	<!-- 댓글목록 -->
	<table align="center" width="500" border="1">
		<c:forEach items="${rList }" var="reply" >
		<tr>
			<td width="100">${reply.replyWriter }</td>
			<td>${reply.replyContents }</td>
			<td>${reply.rUpdateDate }</td>
			<td><a href="">수정</a> <a href="">삭제</a></td>
		</tr>
		</c:forEach>
	</table>
		<!-- 댓글등록 -->
		<table align="center" width="500" border="1">
			<tr>
				<td><textarea rows="3" cols="55" name="replyContents"></textarea></td>
				<td>
					<button>등록하기</button>
				</td>
			</tr>
		</table>
	</form>

	<table>

	</table>
	<script>
		function boardRemove(page) {
			event.preventDefault();//하이퍼링크 이동 방지
			if(confirm("게시물을 삭제?")){
				location.href="/board/remove.kh?page="+page;
			}
		}
	</script>
</body>
</html>