package com.kh.junspring.member.store.logic;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.junspring.member.domain.Member;
import com.kh.junspring.member.store.MemberStore;

@Repository
public class MemberStoreLogic implements MemberStore{

	@Override
	public Member selectLoginMember(SqlSession session, Member member) {
		Member mOne = session.selectOne("MemberMapper.selectLoginOne",member);
		return mOne;
	}

	@Override
	public int insertMember(SqlSession session, Member member) {
		int result = session.insert("MemberMapper.insertMember",member);
		return result;
	}

	@Override
	public int updateMember(SqlSession session, Member member) {
		int result = session.update("MemberMapper.updateMember",member);
		return result;
	}

	@Override
	public int deleteMember(SqlSession session, String memberId) {
		int result = session.update("MemberMapper.deleteMember",memberId);
		return result;
	}

	@Override
	public Member selectOneById(SqlSession session, String memberId) {
		Member member = session.selectOne("MemberMapper.selectOneById",memberId);
		return member;
	}

	@Override
	public List<Member> selectAllMember(SqlSession session) {
		List<Member> mList = session.selectList("MemberMapper.selectAllMember");
		return mList;
	}
	

}
