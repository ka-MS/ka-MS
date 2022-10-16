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
		<tr>
			<td colspan="4"><b id="rCount"></b></td>
		</tr>
	</table>



	<!-- <form action="/board/addReply.kh" method="post"> -->
	<input type="hidden" name="page" value="${page }">
	<input type="hidden" name="refBoardNo" value="${board.boardNo }">
	<!-- 댓글목록 -->
	<table align="center" width="500" border="1" id="rtb">

	</table>
	<!-- 댓글등록 -->
	<table align="center" width="500" border="1">
		<tr>
			<td><textarea rows="3" cols="55" name="replyContents"
					id="replyContents"></textarea></td>
			<td>
				<button id="rSubmit">등록하기</button> <!-- ajax사용할거니까 form태그는 필요없다 -->
			</td>
		</tr>
	</table>
	<!-- </form> -->

	<table>

	</table>
	<script>
	getReplyList();
	function getReplyList(){
			var boardNo = "${board.boardNo}";
		$.ajax({
			
			url : "/board/replyList.kh",
			type : "get",
			data : {"boardNo" : boardNo}, //보드넘버넘김
			success : function(rList){
				var $tableBody = $("#rtb tbody")
				$tableBody.html(""); // 댓글등록시 초기화 해주는 함수 안해주면 append로 쌓임
				$("#rCount").text("댓글(" + rList.length + ")");
				var str = "";
				if(rList != null){
					
					/* for(var i = 0; i<rList.length ; i++){ */
					for(var i in rList){
						str += "<tr><td width='100'>" + rList[i].replyWriter +  "</td>"
						str += "<td>" +  rList[i].replyContents +  "</td>"
						str += "<td>" +  rList[i].rUpdateDate +  "</td>"
						str += "<td><a href='javascript:void(0);' onclick='modifyReply(this,\""+rList[i].replyContents+"\","+rList[i].replyNo+")'>수정</a>/" 
						str += "<a href='javascript:void(0);' onclick='removeReply(this,\""+rList[i].replyWriter+"\","+rList[i].replyNo +")'>삭제</a></td></tr>"
					//javascript:void(0);을 사용하면 눌리지 않는다
					//"<a href='javascript:void(0);' onclick='modifyView(this,\""+rList[i].replyContents+"\","+rList[i].replyNo+")'>수정</a> "

					}
						$("#rtb").html(str);
// 					for(var i in rList){
// 						var $tr = $("<tr>"); //받는태그는 따로 안찍어도 만들어줌
// 						var $rWriter = $("<td width='100'>").text(rList[i].replyWriter); //html로 쓰면 태그가 먹히고 text로 쓰면 태그 안먹힘
// 						var $rContent = $("<td>").text(rList[i].replyContents);
// 						var $rCreateDate = $("<td width='100'>").text(rList[i].rCreateDate);
// 						var $btnArea = $("<td width='80'>").append("<a href='#'>수정</a>/").append("<a href='#'>삭제</a>");
						
// 						$tr.append($rWriter);
// 						$tr.append($rContent);
// 						$tr.append($rCreateDate);
// 						$tr.append($btnArea);
// 						$tableBody.append($tr);
// 					}
					
				}
				
			},
			error : function(){
				console.log("서버요청 실패!")
			}
			
		});
		}
		
		function removeReply(obj,replyWriter,replyNo){
			if(confirm("댓글을 삭제하시겠습니까?")){
			}
			$.ajax({
				url : "/board/replyRemove.kh",
				type : "post",
				data : {
					"replyNo" : replyNo,
					"replyWriter" : replyWriter
				},
				success : function(result){
					if(result == 2){
						alert("작성자만 삭제가능");
					}
					else if(result == 1){
					alert("삭제 성공");
					console.log("삭제 성공");
					getReplyList();
					}else{
						alert("삭제 실패");
					}
				},
				error : function(){
					console.log("서버 요청 실패")
				}
			})
			
		}
		function modifyReply(obj,rNo){
		var inputTag = $(obj).parent().prev().children();
		console.log(inputTag.val());
		var replyContents = inputTag.val();
		$.ajax({
			url : "/member/replyModify.kh",
			data : {
				"replyNo": rNo ,
				"replyContents": replyContents
			},
			
			type : "post",
			success : function(){
				if(result == "success"){
					getReplyList();
				}else{
					alert("댓글 수정 실패");
				}
				
			},
			error : function(){
				
			}
			
		})
		
		}
		
		
		function boardRemove(page) {
			event.preventDefault();//하이퍼링크 이동 방지
			if(confirm("게시물을 삭제?")){
				location.href="/board/remove.kh?page="+page;
			}
		}
		$("#rSubmit").click(function(){
			console.log("${board.boardNo}");
			var replyContents = $("#replyContents").val();
			var refBoardNo = "${board.boardNo}";
			$.ajax({
				url : "/board/replyAdd.kh",
				type : "post",
				data : {
					"refBoardNo" : refBoardNo,
					"replyContents" : replyContents
					}, // key값 : 넘기는값 ctrl shift - 하면 칸나누기가능
				async: true,
				success : function(result){
					if(result == "success"){
						alert("댓글등록 성공!");
						$("#replyContents").val(""); //작성 후 내용 초기화
						getReplyList();
// 						location.href="/board/detail.kh?boardNo="+ ${board.boardNo} +"&page=" + ${page};
						//dom조작 함수호출등 가능
					}else if(result == "noneId"){
						alert("로그인 하세여");
						location.href="/home.kh";
					}else{
						alert("댓글등록 실패!");
					}
				},
				error : function(){
					console.log("등록실패");
				}
				
				
				
			})
		});
	</script>
</body>
</html>