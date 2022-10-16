package com.kh.junspring.board.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kh.junspring.board.domain.Board;
import com.kh.junspring.board.service.BoardService;
import com.kh.junspring.member.domain.Member;
import com.kh.junspring.member.domain.Reply;

//컨트롤러 어노테이션 사용
@Controller
public class BoardController {
	// 어노테이션 적기
	@Autowired
	private BoardService bService;

	// @Component 어노테이션으로 빈등록을 할 수 있는 이유 -> 스캔해주기 때문에 -> 스캔해주라는 명령어는 그럼 어디에?
	// ->/appServlet/servlet-context.xml 안에
	// <context:component-scan base-package="com.kh.junspring" /> 구문으로 가능하게함

	@RequestMapping(value = "/board/writeView.kh", method = RequestMethod.GET)
	public String showBoardWrite() {
		return "board/boardWriteForm";
	}

	@RequestMapping(value = "/board/register.kh", method = RequestMethod.POST)
	public ModelAndView registBoard(ModelAndView mv, @ModelAttribute Board board,
			@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile,
			HttpServletRequest request) {
		// required -> 필수인지아닌지 설정 썸네일은 true
		// 파일업로드를 위해서는 xml파일두개를 건드려야함 pom이랑 root
			
		System.out.println(board.getBoardTitle());
		try {
			String boardFilename = uploadFile.getOriginalFilename(); // 업로드한 파일 이름 추출
//			if (!uploadFile.getOriginalFilename().equals("")) {
			if (!boardFilename.equals("")) {
				String root = request.getSession().getServletContext().getRealPath("resources");
				String savePath = root + "\\buploadFiles";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmmss");
				String boardFileRename = sdf.format(new Date(System.currentTimeMillis())) + "."
						+ boardFilename.substring(boardFilename.lastIndexOf(".") + 1);
				// 1.png, img.png
				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdir();
				}
				uploadFile.transferTo(new File(savePath + "\\" + boardFileRename)); // 실제로 경로에 저장
				// 파일을 buploadFile경로에 저장하는메소드
//				String boardFilename = uploadFile.getOriginalFilename();
				// 파일을 db에 저장
				String boardFilepath = savePath + "\\" + boardFileRename;
				board.setBoardFilename(boardFilename);
				board.setBoardFileRename(boardFileRename);
				board.setBoardFilepath(boardFilepath);

			}
			System.out.println(board.getBoardFilename());
//			int result = bService.registerBoard(board);
			// if(result>0) {
			// 저장 후 돌아가는 행동을 할 떄 redirect써줌
			mv.setViewName("redirect:/board/writeView.kh");

			// }else {
			// mv.addObject("msg","뭔가 잘못됐어 콘솔창을 확인해봐");
			// mv.setViewName("common/errorPage");
			// }
		} catch (Exception e) {
			// 이걸 써주면 오류내용 콘솔창에서도 확인할 수 있음
			e.printStackTrace();
			mv.addObject("msg", e.getMessage());
			mv.setViewName("common/errorPage");
		}

		return mv;
	}

	// /**는 메소드에 주석을 달아주는 기능
	/**
	 * 게시글 전체조회
	 * 
	 * @param mv
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/board/list.kh", method = RequestMethod.GET)
	public ModelAndView boardListView(ModelAndView mv, @RequestParam(value = "page", required = false) Integer page) {
		// 페이징처리
		int currentPage = (page != null) ? page : 1;
		int totalCount = bService.getTotalCount("", "");
		int boardLimit = 10;
		int naviLimit = 5;
		int maxPage;
		int startNavi;
		int endNavi;
		// 23/5=4.8+0.9=5(.7)
		maxPage = (int) ((double) totalCount / boardLimit + 0.9);
		startNavi = ((int) ((double) currentPage / naviLimit + 0.9) - 1) * naviLimit + 1;
		endNavi = startNavi + naviLimit - 1;
		if (maxPage < endNavi) {
			endNavi = maxPage;
		}
		List<Board> bList = bService.printAllBoard(currentPage, boardLimit);
//		for(Board s : bList) {
//			System.out.println(s.getBoardNo());
//		}
//		System.out.println(bList);
		String title=bList.get(0).getBoardTitle();
		if (!bList.isEmpty()) {
			// url을 변경해 줄 수 있도록 변경
			mv.addObject("urlVal", "list");
			mv.addObject("startNavi", startNavi);
			mv.addObject("endNavi", endNavi);
			mv.addObject("maxPage", maxPage);
			mv.addObject("currentPage", currentPage);
			mv.addObject("bList", bList);
		}
		mv.addObject("title",title);
		mv.setViewName("board/listView");
		return mv;
	}
	
	@RequestMapping(value = "/board/detail.kh", method = RequestMethod.GET)
	public ModelAndView boradDetailView(@RequestParam("page") Integer page, @RequestParam("boardNo") Integer boardNo,
			ModelAndView mv, HttpSession session) {
		// integer로 하면 null체크가 가능

		try {
//			List<Reply> rList = bService.printAllReply(boardNo);
			Board board = bService.printOneByNo(boardNo);
			session.setAttribute("boardNo", board.getBoardNo());
			// 세션에 보듬넘버 저장 ->삭제하기 위해서
//			if(board != null) {
//			mv.addObject("rList",rList);
			mv.addObject("board", board);
			//삭제한페이지로 돌아가도록 구현하기위해 page처리
			mv.addObject("page", page);
			
//			mv.addObject("searchValue", searchValue);
//			mv.addObject("searchCondition", searchCondition);
			
			mv.setViewName("board/detailView");
//			}
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", e.getMessage());
			mv.setViewName("common/errorPage");

		}

		return mv;
	}

	// 링크눌러서 들어가는건 get방식
	@RequestMapping(value = "/board/modifyView.kh", method = RequestMethod.GET)
	public ModelAndView boardModifyView(ModelAndView mv, HttpSession session,
			@RequestParam("boardNo") Integer boardNo,@RequestParam("page") Integer page) {
		try {

			Board board = bService.printOneByNo(boardNo);
			mv.addObject("board", board);
			mv.addObject("page", page);
			mv.setViewName("board/modifyForm");
		} catch (Exception e) {
			mv.addObject("msg", e.toString());
			mv.setViewName("common/errorPage");
			// TODO: handle exception
		}

		return mv;
	}

	/**
	 * 게시글 수정
	 * 
	 * @param board
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "/board/modify.kh", method = RequestMethod.POST)
	public ModelAndView boardModify(@ModelAttribute Board board, ModelAndView mv,
			@RequestParam(value = "reloadFile", required = false) MultipartFile reloadFile,
			@RequestParam("page") Integer page,
			HttpServletRequest request) {
		try {
			String boardFilename = reloadFile.getOriginalFilename();
			if (reloadFile != null && !boardFilename.equals("")) {
				// 수정, 1. 대체(replace) 2. 삭제 후 저장->더 편함
				// 파일삭제
				String root = request.getSession().getServletContext().getRealPath("resources");
				String savedPath = root + "\\buploadFiles";
//				Board bOne = bService.printOneByNo(board.getBoardNo()); 
				// 이렇게 데이터 다시 뒤질 필요없이 jsp에 board.으로 저장되어있는 데이터를 재 활용하자
				File file = new File(savedPath + "\\" + board.getBoardFileRename());
				if (file.exists()) {
					file.delete();
				}
				// 파일다시저장
				SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddhhmmss");
				String boardFileRename = sdf.format(new Date(System.currentTimeMillis())) + "."
						+ boardFilename.substring(boardFilename.lastIndexOf(".") + 1);
				String boardFilePath = savedPath + "\\" + boardFileRename;
				reloadFile.transferTo(new File(boardFilePath));
				board.setBoardFilename(boardFilename);
				board.setBoardFileRename(boardFileRename);
				board.setBoardFilepath(boardFilePath);
			}
			int result = bService.modifyBoard(board);
			mv.setViewName("redirect:/board/list.kh?page="+page);
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", e.toString()).setViewName("common/errorPage");

		}
		return mv;
	}

	@RequestMapping(value = "/board/remove.kh", method = RequestMethod.GET)
	public String boardRemove(HttpSession session, Model model, @RequestParam("page") Integer page
			) {
		try {
			int boardNo = (int) session.getAttribute("boardNo");
			int result = bService.removeOneByNo(boardNo);
			if (result > 0) {
				session.removeAttribute("boardNo");
				// 세션삭제
			}
			//삭제한페이지로 돌아가도록 구현
			return "redirect:/board/list.kh?page="+page;
//			+"&searchCondition="+searchCondition+"&searchValue="+searchValue
		} catch (Exception e) {
			model.addAttribute("msg", e.toString());
			return "common/errorPage";
		}
	}

	@RequestMapping(value = "/board/search.kh", method = RequestMethod.GET)
	public ModelAndView boardSearchList(ModelAndView mv, @RequestParam("searchCondition") String searchCondition,
			@RequestParam("searchValue") String searchValue,
			@RequestParam(value = "page", required = false) Integer page) {
		// BOARD_TBL <-SELECT * FROM BOARD_TBL WHERE B_STATUS='Y' AND BOARD_TITLE LIKE
		// '%'||#{searchValue}||'%'
		// 와일드카드의 종류 %,_ %글% 하면 글이라는 글씨가 포함된 모든 검색결과 연결연산자||사용
		// BOARD_TBL <-SELECT * FROM BOARD_TBL WHERE B_STATUS='Y' AND BOARD_CONTENTS
		// LIKE = #{searchValue}
		// BOARD_TBL <-SELECT * FROM BOARD_TBL WHERE B_STATUS='Y' AND BOARD_WRITER LIKE
		// = #{searchValue}
		// 동적spl로 조건문사용하여 구현
		try {

			// 페이징처리
			int currentPage = (page != null) ? page : 1;
			int totalCount = bService.getTotalCount(searchCondition, searchValue);
			int boardLimit = 10;
			int naviLimit = 5;
			int maxPage;
			int startNavi;
			int endNavi;
			// 23/5=4.8+0.9=5(.7)
			maxPage = (int) ((double) totalCount / boardLimit + 0.9);
			startNavi = ((int) ((double) currentPage / naviLimit + 0.9) - 1) * naviLimit + 1;
			endNavi = startNavi + naviLimit - 1;
			if (maxPage < endNavi) {
				endNavi = maxPage;
			}
//			List<Board> bList = bService.printAllBoard(currentPage, boardLimit);
			List<Board> bList = bService.printAllByValue(searchCondition, searchValue, currentPage, boardLimit);
			if (!bList.isEmpty()) {
				mv.addObject("bList", bList);
			}
			//////
			mv.addObject("urlVal", "search");
			mv.addObject("startNavi", startNavi);
			mv.addObject("endNavi", endNavi);
			mv.addObject("maxPage", maxPage);
			mv.addObject("currentPage", currentPage);
			mv.addObject("searchValue", searchValue);
			mv.addObject("searchCondition", searchCondition);
			mv.setViewName("board/listView");
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", e.toString()).setViewName("common/errorPage");
		}

		return mv;
	}
	
//	@PostMapping("/board/addReply.kh")
//	public ModelAndView addBoardReply(
//			ModelAndView mv,
//			@ModelAttribute Reply reply,
//			@RequestParam("page") Integer page,
//			HttpSession session) {
//		//INSERT INTO REPLY_TBL VALUES(#{replyNo},#{refBoardNo},#{replyContents},#{replyWriter},#{rCreateDate},#{rUpdateDate},#{rStatus})
//		Member member = (Member)session.getAttribute("loginUser");
////		Board board = (Board)session.getAttribute("boardNo");
//		//로그인할때 set으로 세션에 뭐라고저장했는지 기입!
//		String replyWriter= member.getMemberId();
////		int boardNo = board.getBoardNo();
//		int boardNo=reply.getRefBoardNo();
//		System.out.println(boardNo);
//		reply.setReplyWriter(replyWriter);
////		reply.setRefBoardNo(boardNo);
//		int result = bService.registerReply(reply);
//		if(result>0) {
//			mv.setViewName("redirect:/board/detail.kh?boardNo="+boardNo+"&page="+page);
//		}
//		
//		return mv;
//	}
	
	//ajax로 댓글기능 구현해보기
	@ResponseBody
	@RequestMapping(value = "/board/replyAdd.kh" , method = RequestMethod.POST)
	public String boardReplyAdd(
			@ModelAttribute Reply reply
			,@RequestParam(value = "page" , required = false) Integer page
			,HttpSession session) {
		Member member = (Member) session.getAttribute("loginUser");
		if(session.getAttribute("loginUser") == null) {
			return "noneId";
		}
		String reWriter = member.getMemberId();
		reply.setReplyWriter(reWriter);
		int result = bService.registerReply(reply);
		if(result > 0) {
			return "success";
			//html의 body부분에 이 값을 보내겠다 responsebody
		}else {
			return "fail";
		}
		
		
	}
	@ResponseBody
	@RequestMapping(value = "/board/replyList.kh" , produces = "application/json;charset=utf-8" , method = RequestMethod.GET)
	public String boardReplyList(
			@RequestParam("boardNo") Integer boardNo
			,ModelAndView mv) {
		int bNo = (boardNo == 0 ) ? 1:boardNo;
		List<Reply> rList = bService.printAllReply(bNo);
		mv.addObject("rList",rList);
		if(!rList.isEmpty()) {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			return gson.toJson(rList);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/board/replyRemove.kh" , method = RequestMethod.POST)
	public int replyRemove(
			@RequestParam("replyNo") Integer replyNo
			,@RequestParam("replyWriter") String replyWriter
			,HttpSession session) {
		Member member = (Member) session.getAttribute("loginUser");
		System.out.println(replyWriter);
		if(member==null) {
			return 2;
		}
		if(!member.getMemberId().equals(replyWriter)) {
			return 2;
		}
		int result = bService.removeReply(replyNo);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/member/replyModify.kh" , method = RequestMethod.POST)
	public String modifyReply(
//			@RequestParam("replyNo") Integer replyNo,
			@ModelAttribute Reply reply) {
		int result = bService.modifyReply(reply);
		
		if(result>0) {
			return "success";
			
		}else {
			return "fail";
		}
		
	}
	
}
