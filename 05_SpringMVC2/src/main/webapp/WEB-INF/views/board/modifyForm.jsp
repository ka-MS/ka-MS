<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정 페이지</title>
</head>
<body>

	<h1>${board.boardNo }번게시글수정하기</h1>
	<br>
	<form action="/board/modify.kh" method="post" enctype="multipart/form-data">
	<!-- 모디파이로 page값 보내줌 쿼리스트링말고 input으로 보내줌-->
		<input type="hidden" name="page" value="${page }">
		<input type="hidden" name="boardNo" value="${board.boardNo }">
		<input type="hidden" name="boardFilename" value="${board.boardFilename} ">
		<input type="hidden" name="boardFileRename" value="${board.boardFileRename} ">
		<!-- 보드넘버를 파일수정도 마찬가지 넘겨야하기때문에 여기서 히든처리로 데이터 받아줌 -->
		<table align="center" border="1">
			<tr>
				<td>제목</td>
				<td><input type="text" name="boardTitle"
					value="${board.boardTitle }"></td>
			</tr>
			<tr>
				<td>작성자</td>
				<td><input type="text" name="boardWriter"
					value="${board.boardWriter }" readonly="readonly"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea cols="40" rows="15" name="boardContents">${board.boardContents }</textarea></td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<td><input type="file" name="reloadFile"> <a
					href="/resources/buploadFiles/${board.boardFileRename }">${board.boardFilename }</a>
				</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="수정"> <input
					type="reset" value="취소"> <a href="/board/list.kh">리스트</a> <a
					href="/board/detail.kh?boardNo=${board.boardNo }">이전 페이지</a></td>

			</tr>
		</table>
	</form>
</body>
</html>