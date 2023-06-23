package kr.co.smart;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import smart.common.CommonUtility;
import smart.member.MemberDAO;
import smart.member.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberDAO service;
	@Autowired
	private BCryptPasswordEncoder pwEncoder;
	
	//아이디 중복확인 처리 요청
	@ResponseBody @RequestMapping("/useridCheck")
	public boolean useridCheck(String userid) {
		//화면에서 입력한 아이디가 DB에 있는지 여부를 확인
		//DB에 없는 아이디이면 사용가능, 있는 아이디이면 사용불가능
		return service.member_info(userid)==null ? true : false;
	}
	
	
	
	//회원가입 처리 요청
	@ResponseBody 
	@RequestMapping(value="/register", produces="text/html; charset=utf-8")
	public String join(MemberVO vo, HttpServletRequest request, MultipartFile file) {
		if( !file.isEmpty()) {//첨부파일이 있는 경우
			//서버의 물리적인 영역에 파일을 저장하는 처리
			vo.setProfile(common.fileUpload("profile", file, request));
			
		}
		
		//화면에서 입력한 정보로 DB에 신규회원정보 저장한 후
		//회원가입 성공 여부를 alert으로 띄운다.
		
		//비밀번호 암호화해서 담기
		vo.setUserpw(pwEncoder.encode(vo.getUserpw() ) );
		
		StringBuffer msg = new StringBuffer("<script>");
		if(service.member_join(vo)==1) {
			String welcomeFile =
			request.getSession().getServletContext()
								.getRealPath("resources/files/회원가입축하.pdf");
			common.sendWelcome(vo, welcomeFile);
			
			
			msg.append("alert('회원가입을 축하합니다!ㅇㅅㅇ'); location=' ") 
			.append(request.getContextPath())
			.append("'");
			
			//가입성공시 자동로그인 되게
			request.getSession().setAttribute("loginInfo", vo);
		}else {
			msg.append("alert('회원가입 실패;;;'); history.go(-1)");
		}
		msg.append("</script>");
		
		return msg.toString();
		
	}
	         
	//회원가입 화면 요청
	@RequestMapping("/join")
	public String join(HttpSession session) {
		session.setAttribute("category", "join");
		return "member/join";
	}
	
	
	// 로그아웃 처리 요청
	@RequestMapping("/logout")
	public String logout(HttpSession session, HttpServletRequest request) {
		MemberVO login = (MemberVO)session.getAttribute("loginInfo");
		session.removeAttribute("loginInfo");
		String social = login.getSocial();
	if(social !=null && social.equals("K")) {
			
		//curl -v -X GET "https://kauth.kakao.com/oauth/logout
		//?client_id=${YOUR_REST_API_KEY}
		//&logout_redirect_uri=${YOUR_LOGOUT_REDIRECT_URI}"
		StringBuffer url = new StringBuffer("https://kauth.kakao.com/oauth/logout");
		url.append("?client_id=").append(KAKAO_ID);
		url.append("&logout_redirect_uri=").append(common.appURL(request));
		return "redirect:" + url.toString();
	}else
			return "redirect:/";
	}

	// 새로운 비번 변경 저장 처리 요청
	@ResponseBody
	@RequestMapping("/updatePassword")
	public boolean update(MemberVO vo) {
		// 화면에서 입력한 새 비밀번호가 DB에 변경저장
		vo.setUserpw(pwEncoder.encode(vo.getUserpw()));
		return service.member_resetPassword(vo) == 1 ? true : false;
	}

	// 현재비밀번호 확인 요청
	@ResponseBody
	@RequestMapping("/confirmPassword")
	public int confirm(String userpw, String userid) {
		// 화면에서 입력한 현재 비밀번호가 DB에 있는지 확인
		MemberVO vo = service.member_info(userid);
		return pwEncoder.matches(userpw, vo.getUserpw()) ? 0 : 1;
	}

	// 비밀번호 변경화면 요청
	@RequestMapping("/changePassword")
	public String change(HttpSession session) {
		MemberVO vo = (MemberVO) session.getAttribute("loginInfo");
		if (vo == null)
			return "redirect:login";
		else
			return "member/change";
	}

	// 비밀번호 찾기 화면 요청
	@ResponseBody
	@RequestMapping(value = "/resetPassword", produces = "text/html; charset=utf-8")
	public String reset(MemberVO vo) {

		// 화면에서 입력한 아이디/이메일이 일치하는 회원에게 임시 비번을 발급한다.
		String name = service.member_userid_email(vo);
		StringBuffer msg = new StringBuffer("<script>");
		if (name == null) {
			msg.append("alert('아이디나 이메일이 맞지 않습ㄴ디ㅏ. \\n홗인하세요');");
			msg.append("location='findPassword'");
		} else {
			vo.setName(name);
			// 임시 비번을 생성한 후 DB회원정보로 저장 임시 비번을 이메일로 보내준다.
			String pw = UUID.randomUUID().toString(); // skjdhfk-sjkdhfk2-sdhkj3wkj영문과 숫자가 막 섞여있는 문자열이 길게 만들어진다.
			pw = pw.substring(pw.lastIndexOf("-") + 1);
			vo.setUserpw(pwEncoder.encode(pw)); // 암호화된 임시비번

			if (service.member_resetPassword(vo) == 1 && common.sendPassword(vo, pw)) {
				msg.append("alert('임시비번 발급됬으니 . \\n 이메일을 확인하세요');");
				msg.append("location='login'"); // 임시비번을 로그인하도록 로그인화면 연결
			} else {
				msg.append("alert('확sddfd인하세요');");
				msg.append("history.go(-1)");
			}

		}
		msg.append("</script>");
		return msg.toString();
	}

	// 비밀번호 찾기 화면 요청
	@RequestMapping("/findPassword")
	public String find() {
		return "default/member/find";
	}

	// id:uqcskjA0ukDtBv961qnJ
	// secret: P3B66DMMM6
	private String NAVER_ID = "uqcskjA0ukDtBv961qnJ";
	private String NAVER_SECRET = "P3B66DMMM6";
	private String KAKAO_ID = "2b5d34b73ad483e217c0e8749002b234"; 

	// 카카오로그인처리 요청
	@RequestMapping("/kakaoLogin")
	public String kakaologin(HttpSession session, HttpServletRequest request) {
		// https://kauth.kakao.com/oauth/authorize?response_type=code
		// &client_id=${REST_API_KEY}
		// &redirect_uri=${REDIRECT_URI}
		String state = UUID.randomUUID().toString();
		session.setAttribute("state", state);

		StringBuffer url = new StringBuffer("https://kauth.kakao.com/oauth/authorize?response_type=code");
		url.append("&client_id=").append(KAKAO_ID);

		url.append("&redirect_uri=").append(common.appURL(request)).append("/member/kakaoCallback");
		return "redirect:" + url.toString();
	}

//		네이티브 앱 키	efcc6b00dbfd59b15528cb3685843a34
//		REST API 키	2b5d34b73ad483e217c0e8749002b234
//		JavaScript 키	763b2f25e869323e2defa8db23b8b900
//		Admin 키	90032db7806d2a705ee0fde9b66da743

	// 카카오로그인 콜백처리
	@RequestMapping("/kakaoCallback")
	public String kakaoCallback(String code, HttpSession session) {
		if (code == null)
			return "redirect:/";

		StringBuffer url = new StringBuffer("https://kauth.kakao.com/oauth/token?");
		url.append("grant_type=authorization_code");
		url.append("&client_id=").append(KAKAO_ID);
		url.append("&code=").append(code);

		String kacall = common.requestAPI(url.toString());
		JSONObject json = new JSONObject(kacall);
		String token = json.getString("access_token");
		String type = json.getString("token_type");

		// 접근 토큰을 이용하여 프로필 API 호출하기

		url = new StringBuffer("https://kapi.kakao.com/v2/user/me");
		kacall = common.requestAPI(url.toString(), type + " " + token);
		json = new JSONObject(kacall);

		MemberVO vo = new MemberVO();
		vo.setSocial("K");
		vo.setUserid(json.get("id").toString());
		// 별칭 이 있으면 별칭을, 없으면 name필드에 담자
		JSONObject kakao = json.getJSONObject("kakao_account");
		JSONObject kaka = kakao.getJSONObject("profile");
		vo.setName(hasKey(kaka, "nickname"));
		if (vo.getName().isEmpty()) {
			vo.setName(hasKey(kakao, "name", "아무개"));
		}
		vo.setEmail(hasKey(kakao, "email"));

//				vo.setProfile(hasKey(kaka,"nickname"));
		vo.setProfile(hasKey(kaka, "thumbnail_image_url"));
		vo.setGender(hasKey(kakao, "gender", "male").equals("male") ? "남" : "여"); // M/F -->남/여
		vo.setPhone(hasKey(kakao, "phone_number"));

		if (service.member_info(vo.getUserid()) == null) {
			service.member_join(vo);
		} else {
			service.member_update(vo);
		}
		session.setAttribute("loginInfo", vo);
		return "redirect:/";
	}

	// 네이버로그인처리 요청
	@RequestMapping("/naverLogin")
	public String naverlogin(HttpSession session, HttpServletRequest request) {
		// 네이버 로그인 연동 URL 생성하기

		// https://nid.naver.com/oauth2.0/authorize?response_type=code
		// &client_id=CLIENT_ID
		// &state=STATE_STRING
		// &redirect_uri=CALLBACK_URL
		String state = UUID.randomUUID().toString();
		session.setAttribute("state", state);

		StringBuffer url = new StringBuffer("https://nid.naver.com/oauth2.0/authorize?response_type=code");
		url.append("&client_id=").append(NAVER_ID);
		url.append("&state=").append(state);
		url.append("&redirect_uri=").append(common.appURL(request)).append("/member/naverCallback");
		// http://localhost:8080/smart
		return "redirect:" + url.toString();
	}

	// 네이버 콜백처리
	@RequestMapping("/naverCallback")
	public String naverCallback(String code, String state, HttpSession session) {
		String storedState = (String) session.getAttribute("state");
		if (code == null || !state.equals(storedState))
			return "redirect:/";

		// 접근 토큰 발급 요청
		// https://nid.naver.com/oauth2.0/token?grant_type=authorization_code
		// &client_id=jyvqXeaVOVmV
		// &client_secret=527300A0_COq1_XV33cf
		// &code=EIc5bFrl4RibFls1
		// &state=9kgsGTfH4j7IyAkg
		StringBuffer url = new StringBuffer("https://nid.naver.com/oauth2.0/token?grant_type=authorization_code");
		url.append("&client_id=").append(NAVER_ID);
		url.append("&client_secret=").append(NAVER_SECRET);
		url.append("&code=").append(code);
		url.append("&state=").append(state);

		String response = common.requestAPI(url.toString());
		// 문자열 --> JSON
		JSONObject json = new JSONObject(response);
		String token = json.getString("access_token");
		String type = json.getString("token_type");

		// 접근 토큰을 이용하여 프로필 API 호출하기

		url = new StringBuffer("https://openapi.naver.com/v1/nid/me");
		response = common.requestAPI(url.toString(), type + " " + token);
		json = new JSONObject(response);
		if (json.getString("resultcode").equals("00")) {
			json = json.getJSONObject("response");
			MemberVO vo = new MemberVO();
			vo.setSocial("N");
			vo.setUserid(json.getString("id"));
			// 별칭 이 있으면 별칭을, 없으면 name필드에 담자
			vo.setName(hasKey(json, "nickname"));
			if (vo.getName().isEmpty()) {
				vo.setName(hasKey(json, "name", "아무개"));
			}
			vo.setEmail(hasKey(json, "email"));
			vo.setProfile(hasKey(json, "profile_image"));
			vo.setGender(hasKey(json, "gender", "M").equals("M") ? "남" : "여"); // M/F -->남/여
			vo.setPhone(hasKey(json, "mobile"));

			// DB에 네이버로그인 정보 저장하기 - 존재여부를 확인하여 신규/변경 저장
			if (service.member_info(vo.getUserid()) == null) {
				service.member_join(vo);
			} else {
				service.member_update(vo);
			}
			session.setAttribute("loginInfo", vo);

		}

		/*
		 * { "resultcode": "00", "message": "success", "response": { "email":
		 * "openapi@naver.com", "nickname": "OpenAPI", "profile_image":
		 * "https://ssl.pstatic.net/static/pwe/address/nodata_33x33.gif", "age":
		 * "40-49", "gender": "F", "id": "32742776", "name": "오픈 API", "birthday":
		 * "10-01" } }
		 */

		return "redirect:/";
	}

	private String hasKey(JSONObject json, String key) {
		return json.has(key) ? json.getString(key) : "";
	}

	// 기본값을 지정해야 하는 경우
	private String hasKey(JSONObject json, String key, String value) {
		return json.has(key) ? json.getString(key) : value;
	}

	// 로그인처리 요청
	@RequestMapping(value = "/smartLogin")
	public String login(String userid, String userpw, HttpSession session, HttpServletRequest request,
			RedirectAttributes redirect) {

		// 화면에서 입력한 아이디, 비번이 일치하는 회원정보가 DB에 있는지 확인
		// 입력한 아이디에 해당하는 회원정보 조회
		MemberVO vo = service.member_info(userid);
		boolean match = false;
		if (vo != null) { // 아이디가 일치하는 회원정보가 있고
			match = pwEncoder.matches(userpw, vo.getUserpw()); // 비번일치여부 확인
		}
		if (match) {
			session.setAttribute("loginInfo", vo); // 세션에 로그인한 회원정보 담기
			return "redirect:/";
		} else {
			redirect.addFlashAttribute("fail", true);
			return "redirect:login"; // 로그인화면 다시 요청
		}
	}

	/*
	 * @ResponseBody @RequestMapping( value="/smartLogin",
	 * produces="text/html; charset=utf-8" ) public String login(String userid,
	 * String userpw , HttpSession session , HttpServletRequest request) { //화면에서
	 * 입력한 아이디, 비번이 일치하는 회원정보가 DB에 있는지 확인 //입력한 아이디에 해당하는 회원정보 조회 MemberVO vo =
	 * service.member_info(userid); boolean match = false; if( vo !=null ) { //아이디가
	 * 일치하는 회원정보가 있고 match = pwEncoder.matches( userpw, vo.getUserpw() ); // 비번일치여부
	 * 확인 }
	 * 
	 * StringBuffer msg = new StringBuffer("<script>"); if( match ) { //로그인되는 경우
	 * session.setAttribute("loginInfo", vo); //세션에 로그인한 회원정보 담기 msg.append(
	 * "location='" ) .append( common.appURL(request) ) .append( "'" ); }else {
	 * //로그인되지 않는 경우 msg.append( "alert('아이디나 비밀번호가 일치하지 않습니다!'); history.go(-1); "
	 * ); } msg.append("</script>"); return msg.toString(); }
	 */

	@Autowired
	private CommonUtility common;

	// 로그인화면 요청
	@RequestMapping("/login")
	public String login(HttpSession session) {
		session.setAttribute("category", "login");
		return "default/member/login";
	}
}
