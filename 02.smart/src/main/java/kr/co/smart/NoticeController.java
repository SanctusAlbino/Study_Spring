package kr.co.smart;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller @RequestMapping("/notice")
public class NoticeController {

	//공지글 목록 화면 요청
	@RequestMapping("/list")
	public String list(Model model, HttpSession session) {
		session.setAttribute("category", "no");
		//DB에서 공지글 목록을 조회해와 목록화면에 출력할 수 있도록 
		// Model에 담는다.
		return "notice/list";
	}
	
}
