package com.kh.junspring.board.service.logic;


import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.junspring.board.domain.Board;
import com.kh.junspring.board.service.BoardService;
import com.kh.junspring.board.store.BoardStore;
import com.kh.junspring.member.domain.Reply;

//어노테이션 꼭 써주기!!!
@Service
public class BoardServiceImpl implements BoardService{
	//어노테이션 꼭 써주기
	@Autowired
	private SqlSessionTemplate session;
	@Autowired
	private BoardStore bStore;
	@Override
	public int registerBoard(Board board) {
		int result = bStore.insertBoard(session,board);
		return result;
	}
	@Override
	public List<Board> printAllBoard(int currentPage,int limit) {
		List<Board> bList = bStore.selectAllBoard(session,currentPage,limit);
		return bList;
	}
	@Override
	public int getTotalCount(String searchCondition, String searchValue) {
		int totalCount = bStore.selectTotalCount(session,searchCondition,searchValue);
		return totalCount;
	}

	//조회수 증가 코드
	@Override
	public Board printOneByNo(Integer boardNo) {
		Board board = bStore.selectOneByNo(session, boardNo);
		int result=0;
		if(board != null) {
			result = bStore.updateBoardCount(session, boardNo);
		}
		return board;
	}
	
	@Override
	public int removeOneByNo(int boardNo) {
		int result = bStore.deleteOneByNo(session, boardNo);
		return result;
	}
	@Override
	public int modifyBoard(Board board) {
		int result = bStore.updateBoard(session,board);
		return result;
	}
	@Override
	public List<Board> printAllByValue(String searchCondition, String searchValue, int currentPage, int boardLimit) {
		List<Board> bList = bStore.selectAllByValue(session, searchCondition, searchValue,currentPage,boardLimit);
		return bList;
	}
	@Override
	public int registerReply(Reply reply) {
		int result = bStore.insertReply(session,reply);
		return result;
	}
	@Override
	public List<Reply> printAllReply(int refBoardNo) {
		List<Reply> rList = bStore.selectAllReply(session,refBoardNo);
		return rList;
	}

}
