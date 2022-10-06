package com.kh.junspring.member.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kh.junspring.member.domain.Member;
import com.kh.junspring.member.service.MemberService;

@Controller
public class MemberController {
	@Autowired
	private MemberService mService;

//	private void doget() {
//		// TODO Auto-generated method stub
//		request.getRequestDispatcher("/WEB-INF/views/member/join.jsp").forward(request,response);
//	}
	@RequestMapping(value = "/member/joinView.kh", method = RequestMethod.GET)
	public String memberJoinView() {

		return "member/join";
		/// WEB-INF/views/member/join.jsp <- views까지는 context에 지정되어있음 21줄
	}
	
	@RequestMapping(value = "/member/register.kh", method = RequestMethod.POST)
	public String MemberJoin(
			// @Modelattribute 쓰려면 jsp의 인풋태그 네임값이 Member변수명과 같아야한다
			// 다른게있다면 Member객체에서 그값은 안들어감 set으로 넣어줄 수 있음
			@ModelAttribute Member member
//			@RequestParam("memberId") String memberId
//			,@RequestParam("memberPwd") String memberPwd
//			,@RequestParam("memberName") String memberName
//			,@RequestParam("memberEmail") String memberEmail
//			,@RequestParam("memberPhone") String memberPhone
			, @RequestParam("post") String post, 
			@RequestParam("address1") String address1, 
			@RequestParam("address2") String address2,
			Model model) {
		// 한글로 인코딩 해줘야함 request.setCharacterEncoding
		// web.xml에 filter추가해서도 가능

		// 전달값을 받음
		try {
//			Member member = new Member(memberId, memberPwd, memberName, memberEmail, memberPhone,
//					post + "," + memberAddress);
			member.setMemberAddr(post + "," + address1+ "," + address2);
			int result = mService.registerMember(member);
			if (result > 0) {
				return "redirect:/home.kh";
			} else {
				model.addAttribute("msg", "회원정보없음");
				return "common/errorPage";
				// errorPage.jsp로 안쓰는이유 ->5번다음 6번실행 servlet-context.xml에서 지정되어있음
				// view Resolver가 bean에 담겨있고 앞에는 /WEB-INF/ 뒤에는.JSP가 지정되어이씀

			}
		} catch (Exception e) {
			model.addAttribute("msg", "실패");
			return "common/errorPage";
		}
	}
	// ModelAndView로 바꿔서 사용 코드가 간결해짐
//	@RequestMapping(value = "/member/register.kh", method = RequestMethod.POST)
//	public ModelAndView MemberJoin(
//			@ModelAttribute Member member
//			, @RequestParam("post") String post, @RequestParam("address") String memberAddress, ModelAndView mv) {
//		try {
//			member.setMemberAddr(post + "," + memberAddress);
//			int result = mService.registerMember(member);
//			if (result > 0) {
//				mv.setViewName("redirect:/home.kh");
//			} else {
//				mv.addObject("msg", "회원정보없음");
//				mv.setViewName("common/errorPage");
//			}
//		} catch (Exception e) {
//			mv.addObject("msg", "실패");
//		}
//		return mv;
//	}

	@RequestMapping(value = "/member/login.kh", method = RequestMethod.POST)
	public ModelAndView memberLogin(@RequestParam("member-id") String memberId, @RequestParam("member-pwd") String memberPwd,
			ModelAndView mv, HttpServletRequest request) {
		// HttpSession session = request.getSession(); 말고
		// http로 세션 가져오기 어노테이션으로도 할 수 있음
		// model로 대체
		try {
			Member member = new Member(memberId, memberPwd);
			Member loginUser = mService.loginMember(member);

			if (loginUser != null) {
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", loginUser);
				
				mv.setViewName("redirect:/home.kh");
			} else {
				mv.addObject("msg", "회원정보없음");
				mv.setViewName("common/errorPage");
			}
		} catch (Exception e) {
			// request.setAttribute("msg","실패!");
			// request.getRequestDispatcher("/주소/.jsp").forward(request,response); 이걸 Model로
			// 대체할것임
			mv.addObject("msg", "실패");
			mv.setViewName("common/errorPage");
		}
		return mv;
	}

	@RequestMapping(value = "/member/logout.kh", method = RequestMethod.GET)
	public String memberLogout(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		if (session != null) {
			session.invalidate();
			return "redirect:/home.kh";
		} else {
			model.addAttribute("msg", "로그아웃 실패");
			return "common/errorPage";
		}
	}

	@RequestMapping(value = "/member/myPage.kh", method = RequestMethod.GET)
	public String showMyPage(HttpServletRequest request, Model model) {

		try {
			HttpSession session = request.getSession();
			// 로그인에서 set으로 세팅했으니 get으로 가져올 수 있다.
			//데이터 가져오는 3줄
			Member member = (Member) session.getAttribute("loginUser");
			String memberId = member.getMemberId();
			Member mOne = mService.printOneById(memberId);
			// 주소를 배열로 만들어서 자를것임
			String mAddr = mOne.getMemberAddr();
			String [] addrInfos = mAddr.split(",");
			model.addAttribute("member", mOne);
			model.addAttribute("addrInfos",addrInfos);
			return "member/myPage";
		} catch (Exception e) {
			model.addAttribute("msg",e.getMessage());
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value = "/member/modify.kh", method = RequestMethod.POST)
	public String modifyMember(
			@ModelAttribute Member member
			,@RequestParam("post") String post,
			@RequestParam("address1") String address1, 
			@RequestParam("address2") String address2
			,Model model) {
		try {
			member.setMemberAddr(post + "," + address1+ "," + address2);
			int result = mService.modifyMember(member);
			if(result>0) {
				//redirect
				return "redirect:/home.kh";
			}else {
				model.addAttribute("msg","회원정보 수정실패");
				return "common/errorPage";
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg",e.getMessage());
			return "common/errorPage";
		}
		
	}
	
	@RequestMapping(value="/member/remove.kh", method =RequestMethod.GET)
	public String removeMember(HttpSession session, Model model){
		try {
			Member member = (Member)session.getAttribute("loginUser");
			String memberId = member.getMemberId();
			int result = mService.removerMember(memberId);
			return "redirect:/member/logout.kh";
		}catch (Exception e) {
			model.addAttribute("msg",e.getMessage());
			return "common/errorPage";
		}
		
	}
	@ResponseBody
	@RequestMapping(value="/member/checkId.kh" , method = RequestMethod.GET)
	public String checkMemberId(
			@RequestParam(value = "insertid" ,required = false) String insertid) {
		//mapper에서 돌릴건지 controller에서 리스트를 모두 가져와서 돌릴건지? 여기서
		//여기서 돌리면 해당 아이디값도 반환할 수 있음 / ka0ka0ka는 사용중인 아이디입니다.
		List<Member> mList = mService.searchAllMember();
		for(Member mOne : mList) {
			if(mOne.getMemberId().equals(insertid)) {
				return insertid;
			}
		}
		return null;
	}
	
	
	
}
