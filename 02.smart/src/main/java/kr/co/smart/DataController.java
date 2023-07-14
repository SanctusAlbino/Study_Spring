package kr.co.smart;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import smart.common.CommonUtility;

@Controller @RequestMapping("/data")
public class DataController {
	private String key ="MxcNJbdAGfqSjnwUIXZvGP5wCZfhGTP6c3NXq083QTOUYn5nckZQeDeilXjplmc3aDkpHOsfNQbVt2RjzqJDYw%3D%3D";
	
	
	private String animalURL = "http://apis.data.go.kr/1543061/abandonmentPublicSrvc/";
	
	
	
	@Autowired private CommonUtility common;
	//약국목록 조회 요청
//	@ResponseBody @RequestMapping(value="/pharmacy", produces="text/html; charset=utf-8")
//	@ResponseBody @RequestMapping("/pharmacy")
//	public String pharmacy_list() {
//		StringBuffer url = new StringBuffer("http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList");
//		url.append("?ServiceKey=").append(key);
//		url.append("&_type=json");
//		return common.requestAPI(url.toString());
//		
//	}
	
//	//유기동물 조회 요청
//	@ResponseBody@RequestMapping("animal/list")
//	public Object animal_list(int pageNo, int rows) {
//		StringBuffer url = new StringBuffer(animalURL);
//		url.append("abandonmentPublic?serviceKey=").append(key);
//		url.append("&_type=json");
//		url.append("&pageNo=").append(pageNo);
//		url.append("&numOfRows=").append(rows);
//		return new Gson().fromJson(common.requestAPI(url.toString())
//						, new TypeToken<HashMap<String, Object>>(){}.getType());
//		
//	}
	
//	//유기동물 조회 요청
//	@RequestMapping("/animal/list")
//	public Object animal_list(int pageNo, int rows, Model model) {
//		StringBuffer url = new StringBuffer(animalURL);
//		url.append("abandonmentPublic?serviceKey=").append(key);
//		url.append("&_type=json");
//		url.append("&pageNo=").append(pageNo);
//		url.append("&numOfRows=").append(rows);
//		model.addAttribute("list", common.requestAPIResultInfo(url.toString()));
//		
//		return "data/animal/animal_list";
//	}
	
	//유기동물 시군구 요청
	@RequestMapping("/animal/sigungu")
	public String animal_sigungu(Model model, String sido) {
		StringBuffer url = new StringBuffer(animalURL);
		url.append("sigungu?serviceKey=").append(key);
		url.append("&_type=json");
		url.append("&upr_cd=").append(sido);
		model.addAttribute("list", common.requestAPIResultInfo(url));
		return "data/animal/sigungu";
	}
	
	
	//유기동물 시도조회 요청
	@RequestMapping("/animal/sido")
	public String animal_sido(Model model) {
		StringBuffer url = new StringBuffer(animalURL);
		url.append("sido?serviceKey=").append(key);
		url.append("&_type=json");
		url.append("&numOfRows=30");
		model.addAttribute("list", common.requestAPIResultInfo(url));
		return "data/animal/sido";
	}
	
	//보호소 조회 요청
	@RequestMapping("/animal/shelter")
	public String animal_shelter(String sido, String sigungu, Model model) {
		StringBuffer url = new StringBuffer(animalURL);
		url.append("shelter?serviceKey=").append(key);
		url.append("&_type=json");
		url.append("&upr_cd=").append(sido);
		url.append("&org_cd=").append(sigungu);
		model.addAttribute("list", common.requestAPIResultInfo(url));
		return "data/animal/shelter";
	}
	
	//품종 조회 요청
	@RequestMapping("/animal/kind")
	public String animal_kind(String upkind, Model model) {
		StringBuffer url = new StringBuffer(animalURL);
		url.append("kind?serviceKey=").append(key);
		url.append("&_type=json");
		url.append("&up_kind_cd=").append(upkind);
		model.addAttribute("list", common.requestAPIResultInfo(url));
		return "data/animal/kind";
	}
	
	
	//유기동물 조회 요청
	// jsp에서 보낸 json 파라미터는 바로 데이터객체에 담기지 않는다.
		@RequestMapping("/animal/list")
		public Object animal_list(@RequestBody HashMap<String, Object> map , Model model) {
			StringBuffer url = new StringBuffer(animalURL);
			url.append("abandonmentPublic?serviceKey=").append(key);
			url.append("&_type=json");
			url.append("&pageNo=").append(map.get("curPage"));
			url.append("&numOfRows=").append(map.get("pageList"));
			url.append("&upr_cd=").append(map.get("sido"));
			url.append("&org_cd=").append(map.get("sigungu"));
			url.append("&care_reg_no=").append(map.get("shelter"));
			url.append("&upkind=").append(map.get("upkind"));
			url.append("&kind=").append(map.get("kind"));
			model.addAttribute("list", common.requestAPIResultInfo(url.toString()));
			
			return "data/animal/animal_list";
		}
	
	
	
	
	@ResponseBody @RequestMapping("/pharmacy")
	public Object pharmacy_list(int pageNo, int rows, String name) throws UnsupportedEncodingException {
		StringBuffer url = new StringBuffer("http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList");
		url.append("?ServiceKey=").append(key);
		url.append("&_type=json");
		url.append("&pageNo=").append(pageNo);
		if( !name.isEmpty() ) url.append("&yadmNm=").append( URLEncoder.encode(name, "utf-8") );
		url.append("&numOfRows=").append(rows);
		HashMap<String, Object>map = new Gson().fromJson(common.requestAPI(url.toString())
									, new TypeToken<HashMap<String,Object>>(){}.getType());
		return map;
	}
	
	//공공데이터 목록화면 요청
	@RequestMapping("/list")
	public String list(HttpSession session) {
		session.setAttribute("category", "da");
		return "data/list";
	}
}
