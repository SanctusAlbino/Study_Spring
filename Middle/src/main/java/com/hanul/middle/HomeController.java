package com.hanul.middle;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {
	//RestAPI : Page가 필요할때의 요청이 아니라 데이터가 필요할때 파라메터를 주고 데이터를 요청함.
	// 대부분 json이나 xml형태로 데이터를 return 해준다.
	//json {"key" : "value"} 
	// V=> M=>C
	//요청 => Controller => Database조회 (Model) => View (web)
	//Android 화면 => 요청 (Controller) => Model => Android화면
	
	//dto, list 조회 <= Customer
	// one.cu, list.cu
	// 각각 dto와 list를 조회하는 requestMapping을 만들고 조회해보기.
	@Autowired @Qualifier("hanul") private SqlSession sql;
	
	
	//@ResponseBody
	@RequestMapping("/")
	public String home() throws IOException {
		return "assdf";
		//int a =sql.selectOne("middle.hanul");
		//res.getWriter().println(a);
		//응답을 하고나서는 다시 응답을 하는것은 오류라고 인식함. (response가 응답을 이미처리함. page x)
	}
	
	/*
	 * @ResponseBody @RequestMapping("/one.cu") public void info(HttpServletResponse
	 * res) throws IOException {
	 * 
	 * MiddleVO vo = sql.selectOne("middle.info");
	 * 
	 * res.getWriter().println(vo.getEmail()); }
	 */
	/*
	 * @ResponseBody @RequestMapping("/list.cu") public void
	 * list(HttpServletResponse res) throws IOException { List<MiddleVO> vo =
	 * sql.selectList("middle.list");
	 * 
	 * 
	 * res.getWriter().println(vo.get(0).getId()); }
	 */
	
}
