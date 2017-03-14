package bbs.action.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.action.Action;
import mybatis.bbs.vo.BoardVO;
import mybatis.bbs.vo.CommentVO;
import mybatis.dao.BoardDAO;
import mybatis.dao.MemDAO;
import mybatis.vo.MemVO;

public class BoardContentAction implements Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) {

		// 로그인 확인
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("login_vo");

		MemVO mvo = null;
		if(obj == null)
			return "/index.jsp";

		mvo = (MemVO) obj;	
		
		// 파라미터 받기
		String cPage = request.getParameter("cPage");
		String b_idx = request.getParameter("b_idx");
		String del_chk = request.getParameter("del");		
	
		
		// 원글과 댓글에 관한 정보 얻기
		BoardVO bvo = BoardDAO.getBoard(b_idx);				// 원글 정보
		MemVO mvo2 = MemDAO.getMemInfo(bvo.getWriter());	// 원글 작성자의 정보		
		CommentVO[] c_list = BoardDAO.getCommentList(b_idx);// 댓글리스트
		

		// 이전글과 다음글로 이동할 수 있도록 하기 위함		
		int first = BoardDAO.getFirstIdx();
		int last = BoardDAO.getLastIdx();
		//System.out.println("first: " + first + " / last: " + last);

		BoardVO bvo2 = null;
		BoardVO bvo3 = null;
		
		if(Integer.parseInt(b_idx) != first){
			int pre = BoardDAO.getPreIdx(Integer.parseInt(b_idx));
			bvo2 = BoardDAO.getBoard(String.valueOf(pre));
		}
		
		if(Integer.parseInt(b_idx) != last){
			int next = BoardDAO.getNextIdx(Integer.parseInt(b_idx));		
			bvo3 = BoardDAO.getBoard(String.valueOf(next));
		}
				
		
		// jsp에서 사용할 수 있도록 변수 설정
		request.setAttribute("cPage", cPage);		
		request.setAttribute("bvo", bvo);
		request.setAttribute("writer_vo", mvo2);
		request.setAttribute("c_list", c_list);		
		request.setAttribute("bvo2", bvo2);
		request.setAttribute("bvo3", bvo3);
		
		
		return "/boardContent.jsp";
	}

}
