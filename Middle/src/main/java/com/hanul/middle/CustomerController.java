package com.hanul.middle;



import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;



@RestController
public class CustomerController {
	//어노테이션 == 주석?
	//@영어 <= 어노테이션 == 기계가 해석하는 주석. (Tag)
	//@ ctrl +space 누르면 나오는 모든것들은 어노테이션임. 어노테이션은 밑에 있는 메소드나 또는 변수, 객체등의
	//역할을 정해주는 기능을 담당한다.
	// class (어떤 요청을 받기 위한 객체 x)
	// @Controller class (어떤 요청을 받는 객체 ==> 컴퓨터 인식(spring)) org.spring.... 어노테이션 종류
	
	// json / xml
	// json <= String으로 되어있는데 key와 value가 존재하고 list같은 자료구조도 [] 등으로 표현이 가능한 데이터 타입
	//요소 하나 (Object, DTO)=> 기호:{}
	
	@Autowired @Qualifier("hanul") SqlSession sql;
	
	@RequestMapping(value="/list.cu", produces = "text/html;charset=utf-8")
	public String cu(CustomerVO vo, String param) {
		System.out.println("누군가 다녀감"+ param);
		List<CustomerVO> vvo = sql.selectList("test.list", vo);
		Gson gson = new Gson();
		//Object(List, DTO등 ) => String json으로 바꾸는 메소드 toJson
		return gson.toJson(vvo);
	}
	
	@RequestMapping(value="/obj.cu", produces = "text/html;charset=utf-8")
	public String obj() {
		CustomerVO vo = new CustomerVO();
		vo.setEmail("email");
		vo.setName("이름이름");
		return new Gson().toJson(vo);
	}
	
	@RequestMapping(value="/delete.cu", produces = "text/html;charset=utf-8")
	public String delete(int id) {
		
		int result = sql.delete("test.delete", id);
		System.out.println("성공여부:"+ result );
		return "aas";
	}
	
	@RequestMapping(value="/insert.cu", produces = "text/html;charset=utf-8")
	public String insert(CustomerVO vo) {
		int result = sql.insert("test.insert", vo );
		System.out.println("들어갔나?"+ result);
		return "aaa";
	}
	
	@RequestMapping(value="/update.cu", produces = "text/html;charset=utf-8")
	public String update(CustomerVO vo) {
		int result = sql.insert("test.update", vo );
		System.out.println("수정됬나?"+ result);
		return "bbbb";
	}
	
	
//	@Autowired TestBean bean1;
//	TestBean bean2;
//	
//	@RequestMapping("/test.bean")
//	public void test() {
//		System.out.println(bean1);
//		System.out.println(bean2);
//	}
//	
	
}
