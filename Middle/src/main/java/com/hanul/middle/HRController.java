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

import middle.hr.EmployeeVO;

@RestController @RequestMapping("/hr")
public class HRController {
	@Autowired @Qualifier("hr") SqlSession sql;
	
		@RequestMapping(value="/list", produces = "text/html;charset=utf-8")
		public String list() {
			List<EmployeeVO> list = sql.selectList("employee.list");
			Gson gson = new Gson();
			
			return gson.toJson(list);
		}
}
