package bbs.action.board;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import bbs.action.Action;
import mybatis.bbs.vo.BoardVO;
import mybatis.bbs.vo.CommentVO;
import mybatis.dao.BoardDAO;
import mybatis.dao.MemDAO;
import mybatis.vo.MemVO;

public class EditBoardAction implements Action {

	// 현재 메소드를 수행하는 경우는 2가지이다
	// 1. 원글에서 [수정] 버튼 선택 	    -----> 값을 입력하는 form 화면으로 이동
	// 2. 글을 수정고 [저장] 버튼 선택		-----> DB에 글 수정		
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		// 로그인 확인
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("login_vo");

		MemVO mvo = null;
		if(obj == null)
			return "/index.jsp";

		mvo = (MemVO)obj;

		
		// 파라미터 받기
		// 1번의 경우 ContentType = null인 곳에서 오기 때문에 request에서 바로 받지만
		// 2번의 경우 MultipartForm에서 오기 때문에 mr 객체로 받는다.
		String b_idx = null;
		BoardVO bvo = null;	
		
		String cPage = request.getParameter("cPage");		
		String edit_chk = request.getParameter("edit_chk");
		System.out.println("edit_chk : " + edit_chk);

		String viewPage = null;
		
		// 1번의 경우
		if(edit_chk != null) {			
			
			b_idx = request.getParameter("b_idx");
			bvo = BoardDAO.getBoard(b_idx);
			
			request.setAttribute("bvo", bvo);
			request.setAttribute("edit_chk", edit_chk);			
			viewPage = "/editBoard.jsp";	
		
		// 2번의 경우	
		} else {

			// 파일이 첨부되는 폼이므로 MultipartRequest로 처리

			//파일을 업로드할 절대경로 
			String path = request.getServletContext().getRealPath("/upload_file");

			try {

				// request 대신 MultipartRequest로 파라미터를 받아 파일올리기 수행
				MultipartRequest mr = new MultipartRequest(request, path, 5*1024*1024, "utf-8", new DefaultFileRenamePolicy());

				// DB에 저장할 자원받기
				cPage = mr.getParameter("cPage");
				b_idx = mr.getParameter("b_idx");
				
				String title = mr.getParameter("title");
				String content = mr.getParameter("content");
				String ip = request.getRemoteAddr();
				

				// 첨부된 파일의 이름을 얻기 위해 File 객체로 받는다
				File f = mr.getFile("file");
				String file_name = null;
				String ori_name = null;

				if(f != null){
					file_name = f.getName();
					ori_name = mr.getOriginalFileName("file");
				} 


				// 글수정 (DB 수정)
				BoardDAO.editBBS(b_idx, title, content, file_name, ori_name, ip);
				
				
				// 원글과 댓글에 관한 정보 얻기		
				bvo = BoardDAO.getBoard(b_idx);						// 수정된 원글 정보
				MemVO mvo2 = MemDAO.getMemInfo(bvo.getWriter());	// 원글 작성자의 정보		
				CommentVO[] c_list = BoardDAO.getCommentList(b_idx);// 댓글리스트
				
				request.setAttribute("bvo", bvo);
				request.setAttribute("writer_vo", mvo2);
				request.setAttribute("c_list", c_list);
				
				//viewPage = "Controller?type=boardContent";
				viewPage = "/boardContent.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		request.setAttribute("cPage", cPage);	

		return viewPage;
	}

}
