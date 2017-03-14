package bbs.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import bbs.action.Action;

/**
 * Servlet implementation class Controller
 */
@WebServlet(
		urlPatterns = { "/Controller" }, 
		initParams = { 
				@WebInitParam(name = "myParam", value = "/WEB-INF/props/action.properties")
		})

public class Controller extends HttpServlet {

	private static final long serialVersionUID = 1L;
    private Map<String, Action> actionMap;   
    
	public Controller() {
        super();
        actionMap = new HashMap<>();
    }

	
	// 한번만 수행
	public void init() throws ServletException {
	
		String param = getInitParameter("myParam");
		
		ServletContext application = getServletContext();		
		String path = application.getRealPath(param);
		
		Properties props = new Properties();
		
		FileInputStream fis = null;
		try{				
			fis = new FileInputStream(path);
			props.load(fis);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			
			try {
				if(fis!=null)
					fis.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}

		/////////////////////////////////////////////////////
		// 이제 클래스의 경로를 로드하여 Properties에 저장된 것을
		// 하나씩 가져와서 생성한 후 그 생성된 객체의 주소를 actionMap에 저장한다.

		Iterator<Object> it = props.keySet().iterator();
		
		while(it.hasNext()){
			
			String key = (String)it.next(); 
			String value = props.getProperty(key);
			
			try {				
				Object obj = Class.forName(value).newInstance();
				actionMap.put(key, (Action)obj);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

	}

	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String type = request.getParameter("type");
		System.out.println("type: " + type);
		
		
		if(type == null)
			type = "index"; // 초기화
		
		Action action = actionMap.get(type);
		
		String viewPage = action.execute(request, response);
		RequestDispatcher disp = request.getRequestDispatcher(viewPage);
		
		if(viewPage.equals("re_boardList"))
			response.sendRedirect("Controller?type=boardList");
		 
		else 	
			disp.forward(request, response);
		
		
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
