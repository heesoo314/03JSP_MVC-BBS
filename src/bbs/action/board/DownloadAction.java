package bbs.action.board;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.action.Action;
import mybatis.bbs.vo.BoardVO;
import mybatis.bbs.vo.CommentVO;
import mybatis.dao.BoardDAO;
import mybatis.dao.MemDAO;
import mybatis.vo.MemVO;

public class DownloadAction implements Action {

	@Override
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
		String fname = request.getParameter("fname");
		
		
		// 업로드된 파일의 절대경로
		String path = request.getServletContext().getRealPath("/upload_file/" + fname);

		File f = new File(path);

		if( f.exists() ){
			long f_size = f.length();	// 파일크기

			//byte[] buf = new byte[(int)f_size];
			byte[] buf = new byte[2048];	// 파일의 값을 읽을 때 임시로 담을 공간의 사이즈
			
			int size = -1; // 파일로부터 읽어들인 바이트수
			
			
			// 필요한 I/O 스트림들 
			BufferedInputStream bis = null;
			InputStream fis = null;
			
			BufferedOutputStream bos = null;
			ServletOutputStream fos = null;
			// 서버를 요청한 클라이언트에게 파일을 보내기 위해 response 객체를 통하여 스트림을 얻어냄
			
			
			// 다운로드 대화창
			try{
				
				// 1) 화면 표시 설정
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition", "attachment;filename=" 
					+ new String(fname.getBytes(), "utf-8")); 	// 파일명이 한글일 때를 대비하여 String 객체를 생성
				
				System.out.println(new String(fname.getBytes(), "utf-8"));
				
				// 2) 파일의 자원을 읽을 InputStream과 클라이언트에게 보낼 OutputStream을 생성				
				fis = new FileInputStream(f);
				bis = new BufferedInputStream(fis);
								
				fos = response.getOutputStream();
				bos = new BufferedOutputStream(fos);
				
				
				// 3) 파일 입출력처리
				// bis.read() : 한바이트씩 읽는다(r)
				// bis.read(byte[]) : 한바이트씩 읽은 값을 byte[]형 배열에 담는다
				while( (size = bis.read(buf)) != -1 ){
					// 더이상 읽을 값이 없다면 size = -1를 반환하고 반복문 탈출
							
					bos.write(buf, 0, size);	// buf 배열의 0번지부터 size 갯수만큼을 뽑아서 쓰기(w)
					
					//bos.flush();				
				}			
				
			} catch(Exception e){
				e.printStackTrace();
				
			} finally {
				
				// 4) 스트림 닫기
				try{
					if(fis != null)
						fis.close();
					
					if(bis != null)
						bis.close();
					
					if(fos != null)
						fos.close();
					
					if(bos != null)
						bos.close();				
					
				} catch(Exception e){
					e.printStackTrace();
				}
			
			}

		}

		// 원글과 댓글에 관한 정보 얻기
		BoardVO bvo = BoardDAO.getBoard(b_idx);				// 원글 정보
		MemVO mvo2 = MemDAO.getMemInfo(bvo.getWriter());	// 원글 작성자의 정보		
		CommentVO[] c_list = BoardDAO.getCommentList(b_idx);// 댓글리스트


		// jsp에서 사용할 수 있도록 변수 설정
		request.setAttribute("cPage", cPage);
		request.setAttribute("bvo", bvo);
		request.setAttribute("writer_vo", mvo2);
		request.setAttribute("c_list", c_list);


		return "Controller?type=boardContent";
		
	}

}
