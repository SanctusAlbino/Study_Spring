package com.hanul.middle;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticeController {
	@Autowired @Qualifier("hanul") SqlSession sql;
	//NOTICE<=
	// one.nt => 데이터를 한건 조회하는데 글의 제목, 글의 번호를 입력받아서 조회가 가능하게 만들면 된다.
	
	// list.nt => 여러건 조회임
	@RequestMapping("/one.nt")
	public String one(NoticeVO Nvo) {
		
		NoticeVO vo = sql.selectOne("nt.one", Nvo);
		System.out.println(vo.getTitle());
		
		return "sfdsfsdf";
	}
	
	@RequestMapping("/list.nt")
	public String list() {
		List<NoticeVO> list = sql.selectList("nt.list");
		System.out.println(list.size());
		
		return "sdfsdfsd";
	}
	
}
