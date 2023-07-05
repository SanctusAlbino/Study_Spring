package kr.co.smart;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import smart.board.BoardDAO;
import smart.board.BoardVO;
import smart.board.FileVO;
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
	
	//선택한 방명록첨부파일 다운로드 요청
	@RequestMapping("/download")
	public void download(int file, HttpServletRequest request, 
								HttpServletResponse response) throws Exception {
		//해당 파일정보를 조회해 서버의 물리적 파일정보를 읽어와 클라이언트에 다운로드 처리
		FileVO vo = service.board_file_info(file);
		common.fileDownload(vo.getFilename(), vo.getFilepath()
							, request , response);
	}
	
	//삭제처리 후 화면 list
	//변경저장처리 후 화면 info
	
	//선택한 방명록 정보 수정화면 요청
	@RequestMapping("/modify")
	public String modify(Model model, int id, PageVO page) {
		//해당 글 정보를 DB에서 조회해와 수정화면에 출력
		model.addAttribute("vo", service.board_info(id));
		model.addAttribute("page", page);
		return "board/modify";
	}
	
	
	
	//선택한 방명록 정보 삭제처리 요청
	@RequestMapping("/delete")
	public String delete(PageVO page, int id, Model model, HttpServletRequest request) {
		//물리적 첨부파일을 삭제하기 위해 처부ㅍ 아링리 정보를 조횧둔다ㅣ
		BoardVO vo = service.board_info(id);
		List<FileVO> list = vo.getFileList();
		
		//해당 글을 DB에서 삭제한 후 응답화면연결-목록
		if(service.board_delete(id)==1) {
			//물리적영역에서 첨부파일 삭제
			for(FileVO file: list) {
				common.deletedFile(file.getFilepath(), request);
			}
		}
		
		
		model.addAttribute("url", "board/list");
		model.addAttribute("page", page);
		return "board/redirect";
		//return "redirect:list";
	}
	
	//선택한 방명록 정보 화면 요청
	@RequestMapping("/info")
	public String info(Model model, int id, PageVO page) {
		//조회수 증가 처리
		service.board_read(id);
		//선택한 방명록 글 정보를 DB에서 조회해와 화면에 출력할 수 있도록 Model에 담기
		model.addAttribute("vo", service.board_info(id));
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		model.addAttribute("page", page);
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
