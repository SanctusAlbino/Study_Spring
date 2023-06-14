package smart.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;

import smart.member.MemberVO;

@Service
public class CommonUtility {
	
	private void emailServerConnect(HtmlEmail email) {
		email.setHostName("smtp.naver.com"); //메일 서버 지정..
		email.setAuthentication("ghk1998", "rlarjsgh0301");//아이디/비번 으로 로그인
		email.setSSLOnConnect(true);//로그인 버튼 클릭
		
		
	}
	
	private String EMAIL_ADDRESS="ghk1998@naver.com";
	
	// 이메일 보내기
	public boolean sendPassword(MemberVO vo, String pw) {
		boolean send = true;
		HtmlEmail email = new HtmlEmail();
		email.setCharset("utf-8");
		email.setDebug(true); //이메일전송 과정 Console에서 로그로 확인
		
		emailServerConnect(email);
		try {
			email.setFrom(EMAIL_ADDRESS, "주고싶은데로 주는 관리자");//관리자가 보내는 이
			email.addTo(vo.getEmail(), vo.getName());//받는사람 지정
			
			
			email.setSubject("임시비번이요~~");//제목쓰기
			
			StringBuffer content = new StringBuffer();
			content.append("<h3>[").append(vo.getName()).append("]비번 까먹지 마라 제발</h3>");
			content.append("<div>)아이디:").append(vo.getUserid()).append("</div");
			content.append("<div>)임시비번:").append(pw).append("</div");
			content.append("<div>임시번호 확인하고 안까먹을 비번으로 고쳐!</div>");
			email.setHtmlMsg(content.toString()); //내용쓰기
			email.send();// 보내기버튼 클릭(전송)
			
			
		}catch(Exception e) {
			send= false;
		}
		return send;
	}
	
	
// context root url 지정
	public String appURL( HttpServletRequest request ) {
		//http://localhost:8080/smart
		StringBuffer url = new StringBuffer("http://");
		//localhost = 127.0.0.1 = 본인ip
		url.append( request.getServerName() ).append(":"); //  http://localhost:
		url.append( request.getServerPort() );  //  http://localhost:8080
		url.append( request.getContextPath() ); //  http://localhost:8080/smart
		return url.toString();
	}
}
