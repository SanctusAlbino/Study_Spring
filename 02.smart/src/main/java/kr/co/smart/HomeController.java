package kr.co.smart;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	//오류처리
	@RequestMapping("/error")
	public String error(HttpSession session, HttpServletRequest request, Model model) {
		session.setAttribute("category", "error");
		//header, footer없이
		//Object --> Integer --> int
		int code = (Integer)request.getAttribute("javax.servlet.error.status_code"); //오류코드
		model.addAttribute("code", code);
		model.addAttribute("method", request.getMethod());
		//오류내용:500
		if(code==500) {
			Throwable exception
			=(Throwable)request.getAttribute("javax.servlet.error.exception");
			model.addAttribute("error", exception.getMessage());
		}
		return "default/error/"+(code == 404 ? 404 : "common");
	}
	
	//시각화 화면 요청
	@RequestMapping("/visual/list")
	public String list(HttpSession session) {
		session.setAttribute("category", "vi");
		return "visual/list";
	}
	
	@RequestMapping("/xml")
	public String test() {
		
		return "ajax/ex/drink";
	}
	
	@RequestMapping("/test")
	public String test1(String name, int price, Model model) {
		model.addAttribute("name", name);
		model.addAttribute("price", price);
		
		return "ajax/ex/test";
	}
	
	
	
	
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpSession session) {
		session.setAttribute("now", new java.util.Date().getTime());
//		session.setAttribute("category", "");
		session.removeAttribute("category");
		
		
		
		
		//		org.springframework.web.servlet.view.tiles3.TilesViewResolver
		
		
		
		
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
}
