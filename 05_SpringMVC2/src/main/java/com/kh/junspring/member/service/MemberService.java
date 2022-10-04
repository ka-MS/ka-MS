package com.kh.junspring.member.service;

import com.kh.junspring.member.domain.Member;

public interface MemberService {
	// loginMember
	public Member loginMember(Member member);
	// registerMember
	public int registerMember(Member member);
	// modifyMember
	public int modifyMember(Member member);
	// removeMember
	public int removerMember(String memberId);
	//printOneById
	public Member printOneById(String memberId);
}
