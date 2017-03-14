package bbs.action.board;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import bbs.action.Action;

public class UploadImgAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		// 이미지를 저장할 곳을 절대경로화 시킨다.
		String path = request.getServletContext().getRealPath("/editor_img");

		String f_name = null;		

		try {

			MultipartRequest mr = new MultipartRequest(request, path, 1024*1024*5, "utf-8", 
					new DefaultFileRenamePolicy());

			File f = mr.getFile("upload_file");
			if( f != null)
				f_name = f.getName(); // 업로드하는 파일의 이름을 DB에 저장

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 해당 jsp에서 사용할 url을 request에 저장
		// 여러 클라이언트가 볼 수 있게 서버에 사진이 저장되는 주소
		String url = "http://localhost:9090" + request.getContextPath() + "/editor_img/" + f_name;
		request.setAttribute("url", url);
		request.setAttribute("f_name", f_name);

		return "/editorImgUpload.jsp";
	}

}
