package com.kh.junspring.board.store.logic;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.junspring.board.domain.Board;
import com.kh.junspring.board.store.BoardStore;
import com.kh.junspring.member.domain.Reply;

// 이거 어노테이션 꼭 써줘야함 store에는 레포지토리
@Repository
public class BoardStoreLogic implements BoardStore{

	@Override
	public int insertBoard(SqlSession session, Board board) {
		int result = session.insert("BoardMapper.insertMember",board);
		return result;
	}

	@Override
	public List<Board> selectAllBoard(SqlSession session,int currentPage,int limit) {
		//rowbounds란 쿼리문을 변경하지 않고도 페이징을 처리할 수 있게해주는 클래스이며
		//offset과 limit값을 이용하여 동작함. offset은 currentPage에의해서 변하는 값이고
		//limit값은 고정값이다
		//offset은 currentPage에의해서ㅓ 변경되는값
		//1->1, 2_>11 3->21
		//limit는 한 페이지당 보여주고 싶은 게시물의 갯수
		//10
		int offset=(currentPage -1)*limit;
		RowBounds rowBounds = new RowBounds(offset,limit);
		List<Board> bList = session.selectList("BoardMapper.selectAllBoard",null,rowBounds);
		return bList;
	}

	@Override
	public int selectTotalCount(SqlSession session,String searchCondition, String searchValue) {
		HashMap<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("searchCondition", searchCondition);
		paramMap.put("searchValue", searchValue);
		int totalCount = session.selectOne("BoardMapper.selectTotalCount",paramMap);
		return totalCount;
	}

	@Override
	public Board selectOneByNo(SqlSession session, Integer boardNo) {
		Board board = session.selectOne("BoardMapper.selectOneByNo",boardNo);
		return board;
	}

	@Override
	public int deleteOneByNo(SqlSession session, int boardNo) {
		int result = session.delete("BoardMapper.deleteOneByNo",boardNo);
		return result;
	}

	@Override
	public int updateBoard(SqlSession session, Board board) {
		int result = session.update("BoardMapper.updateBoard",board);
		return result;
	}

	@Override
	public List<Board> selectAllByValue(SqlSession session, String searchCondition, String searchValue, int currentPage, int boardLimit) {
		int offset = (currentPage-1)*boardLimit;
		RowBounds rowBounds = new RowBounds(offset,boardLimit);
		HashMap<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("searchCondition", searchCondition);
		paramMap.put("searchValue", searchValue);
//		paramMap.put("rowBounds", rowBounds);
		List<Board> bList = session.selectList("BoardMapper.selectAllByValue",paramMap,rowBounds);
//		List<Board> bList = session.selectList("BoardMapper.selectAllByValue",searchCondition,searchValue);
		//마이바티스 할 때 두개의값을 넘겨줄 수 있느냐-> 클래스를 만들어서 사용했었음
		// 해쉬맵 사용하면 클래스 안만들어도됨
		return bList;
	}

	@Override
	public int updateBoardCount(SqlSession session, int boardNo) {
		int result = session.update("BoardMapper.updateBoardCount",boardNo);
		return result;
	}

	@Override
	public int insertReply(SqlSession session, Reply reply) {
		int result = session.insert("BoardMapper.insertReply",reply);
		return result;
	}

	@Override
	public List<Reply> selectAllReply(SqlSession session, int refBoardNo) {
		List<Reply> rList = session.selectList("BoardMapper.selectAllReply",refBoardNo);
		return rList;
	}

	@Override
	public int deleteReply(SqlSession session, Integer replyNo) {
		int result = session.delete("BoardMapper.deleteReply",replyNo);
		return result;
	}
	

}
