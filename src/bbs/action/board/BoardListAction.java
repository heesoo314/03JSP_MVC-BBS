package bbs.action.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.action.Action;
import mybatis.bbs.vo.BoardVO;
import mybatis.bbs.vo.PageVO;
import mybatis.dao.BoardDAO;
import mybatis.dao.MemDAO;
import mybatis.vo.MemVO;

public class BoardListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
	
		
		// 로그인 확인
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("login_vo");

		MemVO mvo = null;
		if(obj == null)
			return "/index.jsp";

		mvo = (MemVO) obj;	


		// 페이징기법에 필요한 PageVO 객체 생성
		PageVO pvo = new PageVO();


		// 총 게시물과 페이지의 수를 구해 pvo 변수에 담는다
		pvo.setTotalRecord( BoardDAO.getTotalCount() );

		int totalPage = (int)Math.ceil( (double)pvo.getTotalRecord() / pvo.getRecPerPage() );
		pvo.setTotalPage(totalPage);


		// 현재 페이지 값을 파라미터로 받는다
		String cPage = request.getParameter("cPage");

		if(cPage != null){
			pvo.setNowPage(Integer.parseInt(cPage));
			
			if(pvo.getNowPage() > pvo.getTotalPage())
				pvo.setNowPage( pvo.getTotalPage() );
		}
		
		// begin : (현재 페이지 - 1) * 10 + 1
		// end	 : 현재 페이지 * 10		
		int begin = ( pvo.getNowPage() - 1 ) * pvo.getRecPerPage() + 1;
		int end = pvo.getNowPage() * pvo.getRecPerPage();
		pvo.setBegin(begin);
		pvo.setEnd(end);
		

		// 현재 페이지 값에 의해 블록의 시작 페이지 값을 설정
		// 현재 페이지 1~5  -> 블록의 시작값 '1' -> 출력 : 이전으로 << 1 2 3 4 5 >> 다음으로
		// 현재 페이지 6~10 -> 블록의 시작값 '6' -> 출력 : 이전으로 << 6 7 8 9 10 >> 다음으로
		int startPage = (int)((pvo.getNowPage() - 1) / pvo.getPagePerBlock()) * pvo.getPagePerBlock() + 1;
		int endPage = startPage + pvo.getPagePerBlock()- 1;
		pvo.setStartPage(startPage);
		pvo.setEndPage(endPage);
		
		if(pvo.getEnd() > pvo.getTotalRecord())
			pvo.setEnd(pvo.getTotalRecord());


		BoardVO[] b_ar = BoardDAO.getBoardList(begin, end);


		// 페이징에 필요한 모든 값들을 pvo가 저장하고 있고,
		// DB에 저장된 값들을 담은 리스트인 e_ar과 함께
		// 이것을 필요로 하는 list.jsp에 전달
		request.setAttribute("cPage", cPage);
		request.setAttribute("page_vo", pvo);
		request.setAttribute("list", b_ar);


		return "/boardList.jsp";
	
		
		
	}

}
