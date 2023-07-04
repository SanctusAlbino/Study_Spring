package kr.co.smart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import smart.board.BoardDAO;
import smart.board.BoardVO;
import smart.common.CommonUtility;
import smart.common.PageVO;
import smart.member.MemberDAO;

@Controller @RequestMapping("/board")
public class BoardController {
	@Autowired private BoardDAO service;
	@Autowired private CommonUtility common;
	
	//방명록 신규저장처리 요청
	@RequestMapping("/register")
	public String register(BoardVO vo, MultipartFile file[], HttpServletRequest request) {
		//화면에서 입력한 정보를 DB에 신규저장한 후 응답화면 연결 - 목록
		//첨부된 파일목록을 BoardVO에 담기
		vo.setFileList(common.attachedFiles("board", file, request));
		
		service.board_register(vo);
		return "redirect:list";
	}
	
	//방명록 신규입력 화면 요청
	@RequestMapping("/new")
	public String board() {
		return "board/new";
	}
	
	@Autowired private MemberDAO member;
	@Autowired private BCryptPasswordEncoder pw;
	
	//선택한 방명록 정보 화면 요청
	@RequestMapping("/info")
	public String info(Model model, int id, PageVO page) {
		//선택한 방명록 글 정보를 DB에서 조회해와 화면에 출력할 수 있도록 Model에 담기
		model.addAttribute("vo", service.board_info(id));
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		return "board/info";
	}
	
	//방명록 목록 화면 요청
	@RequestMapping("/list")
	public String list(HttpSession session, PageVO page, Model model) {
		session.setAttribute("category", "bo");
		model.addAttribute("page", service.board_list(page));
		
		return "board/list";
	}
	
}
