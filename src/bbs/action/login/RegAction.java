package bbs.action.login;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.action.Action;
import mybatis.dao.MemDAO;

public class RegAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		
		String[] phone = request.getParameterValues("phone");
		String cellphone = makeString(phone, "-"); 
		
		String email = request.getParameter("email");
		String addr = request.getParameter("addr");
		
		
		boolean check = MemDAO.regMember(id, pwd, name, email, cellphone);
		
		
		// DB저장이 완료되었을 때 /members/(사용자id) 폴더 생성
		if(check){
			
			// 절대경로
			ServletContext application = request.getServletContext();
			String path = application.getRealPath("/members/" + id);
			
			File f = new File(path);
			if( !f.exists() )
				f.mkdirs();
			
		}
		
		return "/index.jsp";
	}
	
	
	public String makeString(String[] ar, String delim){
		
		StringBuffer sb = new StringBuffer();
		
		for(int i=0; i<ar.length; i++){
			sb.append(ar[i]);
			
			if(i < ar.length-1)//지금 반복회차가 마지막이 아닐경우
				sb.append(delim);
		}
		
		return sb.toString();
	}
	
	

}
