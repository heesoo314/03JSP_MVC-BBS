package bbs.action.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.action.Action;
import mybatis.dao.MemDAO;
import mybatis.vo.MemVO;

public class LoginAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
	
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		if(id != null && pwd != null){
			
			MemVO vo = MemDAO.login(id, pwd);
			
			if(vo != null){
				
				//정상적인 정보를 가져왔다면 HttpSession에 저장하고 페이지 이동한다.
				//HttpSession은 request로 얻을 수 있다.
				HttpSession session = request.getSession(true);
				session.setAttribute("login_vo", vo);
				
			}
		}
		
		return "/index.jsp";
		
	}

}
