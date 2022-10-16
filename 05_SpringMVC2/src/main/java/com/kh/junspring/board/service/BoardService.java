package com.kh.junspring.board.service;


import java.util.List;

import com.kh.junspring.board.domain.Board;
import com.kh.junspring.member.domain.Reply;



public interface BoardService {
	// registerBoard
	public int registerBoard(Board board);
	//printAllBoard <= SELECT * FROM BOARD_TBL WHERE B_STATUS - 'Y' 
	// -> 결과값이 여러개 나오는구나! 
	public List<Board> printAllBoard(int currentPage,int limit);
	public int getTotalCount(String searchCondition, String searchValue);
	
	//printOneByNo
	// BOARD_TBL <-SELECT * FROM BOARD_TBL WHERE BOARD_NO = ?
	public Board printOneByNo(Integer boardNo);
	//removeOneByNo
	//성공여부는 인트로
	public int removeOneByNo(int boardNo);
	public int modifyBoard(Board board);
	public List<Board> printAllByValue(String searchCondition, String searchValue, int currentPage, int boardLimit);
	public int registerReply(Reply reply);
	public List<Reply> printAllReply(int refBoardNo);
	public int removeReply(Integer replyNo);
	public int modifyReply(Reply reply);

}
