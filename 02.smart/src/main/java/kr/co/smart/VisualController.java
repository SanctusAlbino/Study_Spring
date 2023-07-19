package kr.co.smart;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import smart.visual.VisualDAO;

@RestController @RequestMapping("/visual")
public class VisualController {
	@Autowired private VisualDAO service;
	
	//부서원수 상위3위의 년도별/월별 채용인원수 조회 요청
		@RequestMapping("/hirement/top3/{unit}")
		public Object hirement_top3_year(@PathVariable String unit) {
			
			List<HashMap<String, Object>> list = unit.equals("year")? service.hirement_top3_year()
																	: service.hirement_top3_month();
			//x축의 각 데이터 카테고리로 사용할 정보
			//Maps: 순서유지X
			Object keys[] = list.get(0).keySet().toArray();
			Arrays.sort(keys);
			keys = Arrays.copyOfRange(keys, 0, keys.length-1);
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("unit", keys);
			return map;
		}		
	
	//년도별 채용인원수 조회 요청
	@RequestMapping("/hirement/year")
	public Object hirement_year() {
		return service.hirement_year();
	}
	
	//월별별 채용인원수 조회 요청
		@RequestMapping("/hirement/month")
		public Object hirement_month() {
			return service.hirement_month();
		}
	
	//부서별 사원수 조회 요청
	//@ResponseBody 
	@RequestMapping("/department")
	public List<HashMap<String, Object>> department() {
		//DB에서 부서별 사원수를 조회해와 응답 한다.
		
		return service.department();
	}
	
	
	
	
}
