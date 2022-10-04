package com.kh.junspring.ajax.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.kh.junspring.member.domain.Member;

@Controller
public class AjaxController {
	
	@RequestMapping(value="/ajax/exercise.kh", method = RequestMethod.GET)
	public String showAjaxExercise() {
		
		return "ajax/ajaxExercise";
	}
	
	//응답값이 있으면 바디로 보내주라는 요청
	@ResponseBody
	@RequestMapping(value="/ajax/ex1.kh", method=RequestMethod.GET)
	public void exerciseAjax1(@RequestParam("msg") String msg) {
		System.out.println("전송받은 데이터 : " +msg);
	}
	
	@ResponseBody
	@RequestMapping(value="/ajax/ex2.kh",method = RequestMethod.GET , produces="text/pain;charset=utf-8")
	public String exerciseAjax2(HttpServletResponse response) {
//		response.setCharacterEncoding("utf-8"); 와 같은 동작을 하게하는 produces 로 인코딩가능
		return "서버에서 왔습니다.";
	}
	
	@ResponseBody
	@RequestMapping(value="/ajax/ex3.kh",method = RequestMethod.GET)
	public String exerciseAjax3(@RequestParam("num1") Integer num1, @RequestParam("num2") Integer num2) {
		Integer result = num1 + num2;
		return String.valueOf(result);
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ex4.kh" , produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	public String exerciseAjax4(@RequestParam("userid") String userid){
		ArrayList<Member> mList = new ArrayList<Member>();
		mList.add(new Member("khuser01","pass01"));
		mList.add(new Member("khuser02","pass02"));
		mList.add(new Member("khuser03","pass03"));
		mList.add(new Member("khuser04","pass04"));
		mList.add(new Member("khuser05","pass05"));
//		Member member = mList.get(0);
		JSONObject jsonObj = new JSONObject(); //json 객체 생성 -> {}생성 완료
		//{"userNo" : 1, "userName" : "일용자", "userAddr" : "서울시 중구"}
//		Member member = mService.printOneById(userId);
		//jsonObj.put("userNo", member.getmemberNo()); 이런식으로 하면 내가 원하는 부분을 가지고 데이터를 처리 할 수 있다.
//		jsonObj.put("userNo", 1);
//		jsonObj.put("userName", "일용자");
//		jsonObj.put("userAddr", "서울시 중구");
		String rPwd = null;
		Member member = null;
		for(Member mOne : mList) {
			System.out.println(mOne.getMemberId());
			System.out.println(mOne.getMemberId().equals(userid));
			if(mOne.getMemberId().equals(userid)) {
				System.out.println("성공");
				member = mOne; 
			}
		}
		
		for(int i = 0; i<mList.size(); i++) {
			if(mList.get(i).getMemberId().equals(userid)) {
				rPwd = mList.get(i).getMemberPwd();
			}
		}
		jsonObj.put("rPwd", rPwd);
		jsonObj.put("memberId", member.getMemberId());
		jsonObj.put("memberPwd", member.getMemberPwd());
		
		return jsonObj.toString();
	}
}
