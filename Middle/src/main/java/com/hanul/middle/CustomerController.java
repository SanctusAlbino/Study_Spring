package com.hanul.middle;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
	@Autowired @Qualifier("hanul") SqlSession sql;
	
	
	@RequestMapping("/one.cu")
	public String info(int id, String name)  { // <= paramter 부. 사용자가 어ㄸ너 요청을 할때 데이터를 넘겨주는 부분.
		//one <= sql.selectOne(객체하나, 무조건 Row가 하나여야함)
		//1.id, name값의 파라메터를 가져옴. (Controller)
		//2. mapper에 전송. selectOne(String), selectOne(String, Object);
		MiddleVO tempVo = new MiddleVO();
		tempVo.setId(id);
		tempVo.setName(name);
		MiddleVO vo = sql.selectOne("cu.one", tempVo );
		System.out.println(vo.getName());
		return "one";
	}
	
	@RequestMapping("/list.cu")
	public String list()  {
		List<MiddleVO> list = sql.selectList("cu.list");
		System.out.println(list.size());
		return "list";
	}
}
