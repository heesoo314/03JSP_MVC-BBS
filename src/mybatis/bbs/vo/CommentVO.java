package mybatis.bbs.vo;

public class CommentVO {

	private String c_idx, writer, content, w_date, ip, b_idx;

	// 생성자
	public CommentVO() {
		// TODO Auto-generated constructor stub
	}
	
	public CommentVO(String writer, String content, String ip, String b_idx) {
		super();
		this.writer = writer;
		this.content = content;
		this.ip = ip;
		this.b_idx = b_idx;
	}


	public String getC_idx() {
		return c_idx;
	}

	public void setC_idx(String c_idx) {
		this.c_idx = c_idx;
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

	public String getB_idx() {
		return b_idx;
	}

	public void setB_idx(String b_idx) {
		this.b_idx = b_idx;
	}
	
	
}
