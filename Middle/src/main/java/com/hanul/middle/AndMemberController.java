package com.hanul.middle;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import AndMember.AndMemberDAO;
import AndMember.AndMemberVO;

@RestController
public class AndMemberController {
@Autowired @Qualifier("hanul") SqlSession sql;

	@Autowired AndMemberDAO dao;
	@RequestMapping(value="/amlogin", produces = "text/html;charset=utf-8")
	public String amlogin(String id , String password ) {
	
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("password", password);
		AndMemberVO vo = dao.login(params);
		
		
		return new Gson().toJson(vo);
	}

	
}
