package com.kh.junspring.ajax.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kh.junspring.member.domain.Member;

@Controller
public class AjaxController {

	@RequestMapping(value = "/ajax/exercise.kh", method = RequestMethod.GET)
	public String showAjaxExercise() {

		return "ajax/ajaxExercise";
	}

	// 응답값이 있으면 바디로 보내주라는 요청
	@ResponseBody
	@RequestMapping(value = "/ajax/ex1.kh", method = RequestMethod.GET)
	public void exerciseAjax1(@RequestParam("msg") String msg) {
		System.out.println("전송받은 데이터 : " + msg);
	}

	@ResponseBody
	@RequestMapping(value = "/ajax/ex2.kh", method = RequestMethod.GET, produces = "text/pain;charset=utf-8")
	public String exerciseAjax2(HttpServletResponse response) {
//		response.setCharacterEncoding("utf-8"); 와 같은 동작을 하게하는 produces 로 인코딩가능
		return "서버에서 왔습니다.";
	}

	@ResponseBody
	@RequestMapping(value = "/ajax/ex3.kh", method = RequestMethod.GET)
	public String exerciseAjax3(@RequestParam("num1") Integer num1, @RequestParam("num2") Integer num2) {
		Integer result = num1 + num2;
		return String.valueOf(result);
	}

	@ResponseBody
	@RequestMapping(value = "/ajax/ex4.kh", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	public String exerciseAjax4(@RequestParam("userid") String userid) {
		ArrayList<Member> mList = new ArrayList<Member>();
		mList.add(new Member("khuser01", "pass01"));
		mList.add(new Member("khuser02", "pass02"));
		mList.add(new Member("khuser03", "pass03"));
		mList.add(new Member("khuser04", "pass04"));
		mList.add(new Member("khuser05", "pass05"));
//		Member member = mList.get(0);
		JSONObject jsonObj = new JSONObject(); // json 객체 생성 -> {}생성 완료
		// {"userNo" : 1, "userName" : "일용자", "userAddr" : "서울시 중구"}
//		Member member = mService.printOneById(userId);
		// jsonObj.put("userNo", member.getmemberNo()); 이런식으로 하면 내가 원하는 부분을 가지고 데이터를 처리
		// 할 수 있다.
//		jsonObj.put("userNo", 1);
//		jsonObj.put("userName", "일용자");
//		jsonObj.put("userAddr", "서울시 중구");
		String rPwd = null;
		Member member = null;
		for (Member mOne : mList) {
			if (mOne.getMemberId().equals(userid)) {
				System.out.println("성공");
				member = mOne;
			}
		}

		for (int i = 0; i < mList.size(); i++) {
			if (mList.get(i).getMemberId().equals(userid)) {
				rPwd = mList.get(i).getMemberPwd();
			}
		}
		jsonObj.put("rPwd", rPwd);
		jsonObj.put("memberId", member.getMemberId());
		jsonObj.put("memberPwd", member.getMemberPwd());

		return jsonObj.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/ajax/ex5.kh", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	public String exerciseAjax5(@RequestParam("userId") String userId) {
		ArrayList<Member> mList = new ArrayList<Member>();
		mList.add(new Member("khuser01", "pass01"));
		mList.add(new Member("khuser02", "pass02"));
		mList.add(new Member("khuser03", "pass03"));
		mList.add(new Member("khuser04", "pass04"));
		mList.add(new Member("khuser05", "pass05"));
		mList.add(new Member("khuser06", "pass06"));
		// 이걸 하나씩꺼내서 json array로 만들어 줄거임
		// 왜냐면 mList.tostring으로쓰면 그냥 주소값이나와서 사용할 수 없음
		JSONArray jsonArr = new JSONArray(); // ==> []배열생성
//		jsonArr.add(new JSONObject()); //json어레이에 제이슨객체를 넣을것임 json객체는 그냥 빈{}
		// ==> {"memberId" : "khuser01" , "memberPwd" : "pass01"}.. 이렇게 베열에 객체 한개가 들어간것
		// 반복문으로 모두넣어주기
		boolean checkOne = false; //같은값이 나왔을경우 더이상 진행되지 못하도록 막는 트리거
		for (Member mOne : mList) { //포문하나안에서 돌리려고 하면 아닌경우에도 담아버림
			JSONObject jsonObj = new JSONObject(); // ==> {}객체생성 ==> 초기화 후 다시넣고 초기화후 다시넣고 반복
			if (mOne.getMemberId().equals(userId)) {
				jsonObj.put("memberId", mOne.getMemberId());
				jsonObj.put("memberPwd", mOne.getMemberPwd());
				jsonArr.add(jsonObj);
				checkOne = true;
			}
		}
		if (!checkOne) {
			for (Member mOne : mList) {
				JSONObject jsonObj = new JSONObject(); // ==> {}객체생성 ==> 초기화 후 다시넣고 초기화후 다시넣고 반복
				jsonObj.put("memberId", mOne.getMemberId());
				jsonObj.put("memberPwd", mOne.getMemberPwd());
				jsonArr.add(jsonObj);
			}
		}
//		for(int i = 0; i< mList.size() ; i++) {
//			JSONObject jsonObj = new JSONObject();
//			if(mList.get(i).getMemberId().equals(userId)) {
//				jsonObj.put("memberId", mList.get(i).getMemberId());
//				jsonObj.put("memberPwd", mList.get(i).getMemberPwd());
//				jsonArr.add(jsonObj);
//				return jsonArr.toString();
//			}
//		}

		return jsonArr.toString();
//		return jsonArr.toJSONString();
//		return mList.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/ajax/ex6.kh" ,produces = "application/json;charset=utf-8" , method = RequestMethod.GET)
	public String exerciseAjax6() {
		ArrayList<Member> mList = new ArrayList<Member>();
		mList.add(new Member("khuser01", "pass01"));
		mList.add(new Member("khuser02", "pass02"));
		mList.add(new Member("khuser03", "pass03"));
		mList.add(new Member("khuser04", "pass04"));
		mList.add(new Member("khuser05", "pass05"));
		mList.add(new Member("khuser06", "pass06"));
		//Gson으로 해 볼것임
		//코드를 간결하게 줄일 수 있는데 pom.xml에 라이브러리를 추가해 주어야 사용할 수 있음
		//반대로 json에서 자바의 오브젝트로 바꾸는데는 jackson을 씀
		Gson gson = new Gson();
		return gson.toJson(mList);
		
		
		
	}
	
	
}
