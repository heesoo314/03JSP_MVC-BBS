package mybatis.bbs.vo;

public class PageVO {

	int begin, end;
	int nowPage = 1; 		// 현재 페이지의 기본값을 1로 초기화
	
	int totalRecord = 0;	// 총 게시물의 수
	int recPerPage = 5;	// 한 페이지에 표시되는 게시물의 수

	int totalPage = 0; 		// 총 페이지 수
	int pagePerBlock = 5;	// 화면에 표시되는 페이지의 수
	
	// 현재 페이지 값에 의해 블록의 시작 페이지 값을 설정
	// 현재 페이지 1~5  -> 블록의 시작값 '1' -> 출력 : 이전으로 << 1 2 3 4 5 >> 다음으로
	// 현재 페이지 6~10 -> 블록의 시작값 '6' -> 출력 : 이전으로 << 6 7 8 9 10 >> 다음으로
	int startPage = (int)((nowPage - 1) / pagePerBlock) * pagePerBlock+ 1;
	int endPage = startPage + pagePerBlock - 1;

	
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public int getRecPerPage() {
		return recPerPage;
	}
	public void setRecPerPage(int recPerPage) {
		this.recPerPage = recPerPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPagePerBlock() {
		return pagePerBlock;
	}
	public void setPagePerBlock(int pagePerBlock) {
		this.pagePerBlock = pagePerBlock;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	
}
