package kr.co.smart;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import smart.member.MemberDAO;
import smart.member.MemberVO;
import smart.notice.NoticeDAO;
import smart.notice.NoticeVO;

@Controller @RequestMapping("/notice")
public class NoticeController {
	@Autowired private NoticeDAO service;
	
	//신규 공지글 등록 처리 요청
	@RequestMapping("/register")
	public String register(NoticeVO vo) {
		//화면에 입력한 정보로 DB에 신규저장
		service.notice_regist(vo);
		//응답화면 연결 - 목록
		return "redirect:list";
	}
	
	//신규 공지글 등록 화면 요청
	@RequestMapping("/new")
	public String regist() {
		return "notice/new";
	}
	
	@Autowired private MemberDAO member;
	@Autowired private BCryptPasswordEncoder pw;
	
	//공지글 목록 화면 요청
	@RequestMapping("/list")
	public String list(Model model, HttpSession session) {
		// 임시 로그인 처리(테스트 후 삭제/주석)---------
		String userid = "admin";
		String userpw = "manager";
		MemberVO login = member.member_info(userid);
		if(pw.matches(userpw, login.getUserpw())){
			session.setAttribute("loginInfo", login);
		}
		//---------------
		
		
		session.setAttribute("category", "no");
		//DB에서 공지글 목록을 조회해와 목록화면에 출력할 수 있도록 
		List<NoticeVO> list = service.notice_list();
		// Model에 담는다.
		model.addAttribute("list",list);
		return "notice/list";
	}
	
}
