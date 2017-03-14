package mybatis.bbs.vo;

import java.util.List;

public class BoardVO {
	
	private String b_idx, title, writer, content, file_name, ori_name, w_date, ip, hit, status;
	
	// 하나의 원글에 여러개 댓글을 가짐 (1:N 관계)
	// mapper - <resultMap>에서 <collection>으로 받게 된다.
	private List<CommentVO> c_list;
	private int c_size;
	
	// 생성자
	public BoardVO(){
		
	}
	
	// 글 삽입용 생성자
	public BoardVO(String title, String writer, String content, String file_name, String ori_name, String ip) {
		super();
		this.title = title;
		this.writer = writer;
		this.content = content;
		this.file_name = file_name;
		this.ori_name = ori_name;
		this.ip = ip;
	}

	// Getter & Setter
	public String getB_idx() {
		return b_idx;
	}

	public void setB_idx(String b_idx) {
		this.b_idx = b_idx;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getOri_name() {
		return ori_name;
	}

	public void setOri_name(String ori_name) {
		this.ori_name = ori_name;
	}

	public String getW_date() {
		return w_date;
	}

	public void setW_date(String w_date) {
		this.w_date = w_date;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHit() {
		return hit;
	}

	public void setHit(String hit) {
		this.hit = hit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<CommentVO> getC_list() {
		return c_list;
	}

	public void setC_list(List<CommentVO> c_list) {
		this.c_list = c_list;
		setC_size(c_list.size());
	}

	public int getC_size() {
		return c_size;
	}

	public void setC_size(int size) {
		this.c_size = size;
	}

}
