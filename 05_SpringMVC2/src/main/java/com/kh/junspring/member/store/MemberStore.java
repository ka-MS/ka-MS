package com.kh.junspring.member.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;

import com.kh.junspring.member.domain.Member;

public interface MemberStore {
	// selectLoginMember
	public Member selectLoginMember(SqlSession session, Member member);
	// insertMember
	public int insertMember(SqlSession session, Member member);
	// updateMember
	public int updateMember(SqlSession session, Member member);
	// deleteMember
	public int deleteMember(SqlSession session, String memberId);
	//selectOneById();
	public Member selectOneById(SqlSession session,String memberId);
	public List<Member> selectAllMember(SqlSession session);
}
