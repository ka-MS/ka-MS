package com.kh.junspring.board.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;

import com.kh.junspring.board.domain.Board;
import com.kh.junspring.member.domain.Reply;

public interface BoardStore {

	public int insertBoard(SqlSession session, Board board);
	
	public List<Board> selectAllBoard(SqlSession session, int currentPage, int limit);

	public int selectTotalCount(SqlSession session,String searchCondition, String searchValue);
	
	public Board selectOneByNo(SqlSession session, Integer boardNo);
	
	public int deleteOneByNo(SqlSession session, int boardNo);

	public int updateBoard(SqlSession session, Board board);

	public List<Board> selectAllByValue(SqlSession session,String searchCondition, String searchValue,int currentPage, int boardLimit);
	
	// 조회수 업데이트 코드
	public int updateBoardCount(SqlSession session, int boardNo);
	//댓글 코드
	public int insertReply(SqlSession session, Reply reply);

	public List<Reply> selectAllReply(SqlSession session, int refBoardNo);

}
