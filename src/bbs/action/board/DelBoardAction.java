package bbs.action.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.action.Action;
import mybatis.bbs.vo.BoardVO;
import mybatis.dao.BoardDAO;
import mybatis.vo.MemVO;

public class DelBoardAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {


		// 로그인 확인
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("login_vo");

		MemVO mvo = null;
		if( obj ==  null )
			return "/index.jsp";


		mvo = (MemVO) obj;
		
		String cPage = request.getParameter("cPage");
		String b_idx = request.getParameter("b_idx");
		
		String viewPage = null;

		BoardVO bvo = BoardDAO.getBoard(b_idx);
		
		if(mvo.getId().equals(bvo.getWriter())){			
			
			if(BoardDAO.deleteBBS(b_idx)){
				// 삭제 성공
				
				viewPage = "re_boardList";
				return viewPage;
				
			} else {
				// 삭제실패
				request.setAttribute("del_chk", "1");
				
			}
		}

		return viewPage;
	}

}
