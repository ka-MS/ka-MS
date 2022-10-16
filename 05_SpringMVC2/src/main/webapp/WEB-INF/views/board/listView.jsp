<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판</title>
</head>
<body>
	<h1 align="center" onclick="location.href='/board/list.kh';">게시글목록</h1>
	<br>
	<br>
	<table align="center" border="1">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>날짜</th>
			<th>조회수</th>
			<th>첨부파일</th>
		</tr>
		<c:forEach items="${bList }" var="board" varStatus="i">
			<tr>
				<!-- 역순출력? 쿼리에 오더바이 주면됨 -->
				<td>${board.boardNo }</td>
				
				<!-- &page로 작석하면값을 param으로 보냄 -->
				<td><a href="/board/detail.kh?boardNo=${board.boardNo }&page=${currentPage}">${board.boardTitle }</a></td>
				<!-- &searchCondition=${searchCondition}&searchValue=${searchValue} -->
				<td>${board.boardWriter }</td>
				<td>${board.bCreateDate }</td>
				<td>${board.boardCount }</td>
				<td><c:if test="${!empty board.boardFilename }">
					O
				</c:if> <c:if test="${empty board.boardFilename }">
					X
				</c:if></td>
			</tr>
		</c:forEach>
		<!-- 페이징정리PDF페이지 참고 -->
		<tr align="center" height="20">
			<td colspan="6">
			<c:if test="${currentPage > 1 }">
					<a href="/board/${urlVal }.kh?page=1&searchCondition=${searchCondition}&searchValue=${searchValue}">[처음]</a>
				</c:if> <c:if test="${currentPage > 1 }">
					<a href="/board/${urlVal }.kh?page=${currentPage -1}&searchCondition=${searchCondition}&searchValue=${searchValue}">[이전]</a>
				</c:if> 
				
				<c:forEach var="p" begin="${startNavi }" end="${endNavi}">
					
					<c:if test="${currentPage eq p }">
						<b>${p }</b>
					</c:if>
					<c:if test="${currentPage eq null }">
						<b>없어</b>
					</c:if>
					<c:if test="${currentPage ne p and currentPage ne null}">
						<a href="/board/${urlVal }.kh?page=${p }&searchCondition=${searchCondition}&searchValue=${searchValue}">${p }</a>
					</c:if>
					
				</c:forEach>
				
				<c:if test="${maxPage > currentPage }">
					<a href="/board/${urlVal }.kh?page=${currentPage +1}&searchCondition=${searchCondition}&searchValue=${searchValue}">[다음]</a>
				</c:if> <c:if test="${currentPage != maxPage }">
					<a href="/board/${urlVal }.kh?page=${maxPage }&searchCondition=${searchCondition}&searchValue=${searchValue}">[마지막]</a>
				</c:if></td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<form action="/board/search.kh" method="get">
					<select name="searchCondition" >
						<option value="all" <c:if test="${searchCondition eq 'all' }">selected</c:if>>전체</option>
						<option value="writer"<c:if test="${searchCondition eq 'writer' }">selected</c:if>>작성자</option>
						<option value="title"<c:if test="${searchCondition eq 'title' }">selected</c:if>>제목</option>
						<option value="contents"<c:if test="${searchCondition eq 'contents' }">selected</c:if>>내용</option>
					</select> 
					<input type="text" name="searchValue" value="${searchValue }">
					<button type="submit">검색</button>
				</form>
			</td>
			<td colspan="2" align="center"><a href="/board/writeView.kh">글등록</a>
				<a href="/home.kh">홈화면</a></td>
		</tr>

	</table>
</body>
</html>