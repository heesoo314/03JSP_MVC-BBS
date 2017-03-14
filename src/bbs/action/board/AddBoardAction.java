package bbs.action.board;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import bbs.action.Action;
import mybatis.bbs.vo.BoardVO;
import mybatis.dao.BoardDAO;
import mybatis.vo.MemVO;

public class AddBoardAction implements Action {


	// 현재 메소드를 수행하는 경우는 2가지이다
	// 1. 목록에서 [글쓰기] 버튼 선택 	    -----> 값을 입력하는 form 화면으로 이동
	// 2. 글을 쓰고 [저장] 버튼 선택		-----> DB에 글 삽입		
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		// 로그인 확인
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("login_vo");

		MemVO mvo = null;
		if(obj == null)		
			return "/index.jsp";
		
		mvo = (MemVO) obj;	
		
		
		String viewPage = null;
		String cPage = request.getParameter("cPage");
		
		// 요청을 보낸 폼의 encType이 [null]인지 [multipart/form-data]를 판단하여 수행
		String cType = request.getContentType();
		System.out.println("cType: "+ cType);
		
		
		// 1번의 경우
		if(cType == null) {
			viewPage = "/addBoard.jsp";
		
		// 2번의 경우	
		} else {
			// 파일이 첨부되는 폼이므로 MultipartRequest로 처리
			
			//파일을 업로드할 절대경로 
			String path = request.getServletContext().getRealPath("/upload_file");
			
			try {

				// request 대신 MultipartRequest로 파라미터를 받아 파일올리기 수행
				MultipartRequest mr = new MultipartRequest(request, path, 5*1024*1024, "utf-8", new DefaultFileRenamePolicy());

				// DB에 저장할 자원받기
				String title = mr.getParameter("title");
				String writer = mr.getParameter("writer");
				String content = mr.getParameter("content");

				// 첨부된 파일의 이름을 얻기 위해 File 객체로 받는다
				File f = mr.getFile("file");
				String file_name = "";
				String ori_name = "";

				if(f != null){
					file_name = f.getName();
					ori_name = mr.getOriginalFileName("file");
				}

				// ip는 무조건 request로 받는다
				String ip = request.getRemoteAddr();

				
				// 글쓰기 (DB 삽입)
				BoardDAO.insertBBS(title, writer, content, file_name, ori_name, ip);
				
				viewPage = "re_boardList";

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		request.setAttribute("cPage", cPage);

		
		return viewPage;
	}

}
