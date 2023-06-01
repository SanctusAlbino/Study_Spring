package com.hanul.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {
	
	//로그인처리요청
	@RequestMapping("/loginResult")
	public String login(@RequestParam String id, String pw) {
		//로그인 성공: home화면으로 연결
		//로그인 실패: 다시 로그인하도록 로그인화면으로 연결
		// asdf/1234 인 경우만 로그인 성공으로 간주
		if(id.equals("asdf") && pw.equals("1234")) {
			//return "home"; //forward방식
			return "redirect:/"; //redirect
		}else {
			//return "member/login"; //forward방식
			return "redirect:login"; //redirect방식
		}
	}
	
	
	
	//로그인화면요청
	@RequestMapping("/login")
	public String login() {
		return "member/login";
	}

}
