package com.hanul.middle;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import Crud.CrudDAO;
import Crud.CrudVO;

@RestController
public class CrudController {
	@Autowired CrudDAO dao;;
	
	
	@RequestMapping(value="/list.crud", produces = "text/html;charset=utf-8")
	public String list() {
		
		return new Gson().toJson(dao.select());
	}
	
	@RequestMapping(value="/insert.crud", produces = "text/html;charset=utf-8")
	public String insert(CrudVO vo) {
		return new Gson().toJson(dao.insert(vo));
	}
	
	@RequestMapping(value="/update.crud", produces = "text/html;charset=utf-8")
	public String update(CrudVO vo) {
		return new Gson().toJson(dao.update(vo));
	}
	
	@RequestMapping(value="/delete.crud", produces = "text/html;charset=utf-8")
	public String delete(CrudVO vo) {
		return new Gson().toJson(dao.delete(vo));
	}
}
