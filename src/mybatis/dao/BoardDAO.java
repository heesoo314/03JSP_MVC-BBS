package mybatis.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import mybatis.bbs.vo.BoardVO;
import mybatis.bbs.vo.CommentVO;
import mybatis.service.FactoryService;

public class BoardDAO {
	
	
	/////////////////////////////////////// 비즈니스 로직 ///////////////////////////////////////////
	
	// ----------------------------------------------------------------------------------------------
	
	// 1) 게시판 리스트 전체보기
	public static BoardVO[] getBoardList(int begin, int end){
		
		Map<String, Integer> map = new HashMap<>();
		map.put("begin", begin);
		map.put("end", end);
		
		SqlSession ss = FactoryService.getFactory().openSession(true);
		List<BoardVO> b_list = ss.selectList("board.board_list", map);
		ss.close();
		
		BoardVO[] b_ar = null;
		if(b_list != null && b_list.size() > 0){
			
			b_ar = new BoardVO[b_list.size()];
			b_list.toArray(b_ar);
			
		}		
				
		return b_ar;
	}
	
	
	// 1-2) 전체 게시물의 수를 반환
	public static int getTotalCount(){
		
		SqlSession ss = FactoryService.getFactory().openSession(true);
		int cnt = ss.selectOne("board.totalCnt");
		ss.close();
		
		return cnt;		

	}

	
	// 1-3) 처음과 마지막 게시물의 인덱스
	public static int getFirstIdx(){

		SqlSession ss = FactoryService.getFactory().openSession(true);
		int firstIdx = ss.selectOne("board.firstIdx");
		ss.close();

		return firstIdx;		
	}

	public static int getLastIdx(){

		SqlSession ss = FactoryService.getFactory().openSession(true);
		int lastIdx = ss.selectOne("board.lastIdx");
		ss.close();

		return lastIdx;	
	}

	// 1-4) 현재 보고 있는 글의 이전글과 다음글에 해당하는 게시물의 인덱스
	public static int getPreIdx(int b_idx){

		SqlSession ss = FactoryService.getFactory().openSession(true);
		int preIdx = ss.selectOne("board.preIdx", b_idx);
		ss.close();

		return preIdx;		
	}

	public static int getNextIdx(int b_idx){

		SqlSession ss = FactoryService.getFactory().openSession(true);
		int nextIdx = ss.selectOne("board.nextIdx", b_idx);
		ss.close();

		return nextIdx;	
	}

	
	
	// ----------------------------------------------------------------------------------------------
	
	// 2) 게시물 내용보기
	public static BoardVO getBoard(String b_idx){
		
		SqlSession ss = FactoryService.getFactory().openSession(true);
		BoardVO bvo = ss.selectOne("board.board_content", b_idx);
		ss.close();
		
		return bvo;
		
	}
	
	
	// 2-2) 글쓰기 (DB에 자원삽입)
	public static boolean insertBBS(String title, String writer, String content, 
			String file_name, String ori_name, String ip){
		
		boolean result = false;
		
		BoardVO bvo = new BoardVO(title, writer, content, file_name, ori_name, ip);
		
		SqlSession ss = FactoryService.getFactory().openSession();
		int cnt = ss.insert("board.addBoard", bvo);
		
		if(cnt == 1){
			result = true;
			ss.commit();
		} else 
			ss.rollback();
		
		ss.close();
		
		return result;
		
	}
	
	
	// 2-3) 글수정
	public static boolean editBBS(String b_idx, String title, String content, 
			String file_name, String ori_name, String ip){
		
		boolean result = false;
		
		Map<String, String> map = new HashMap<>();
		map.put("b_idx", b_idx);
		map.put("title", title);
		map.put("content", content);
		map.put("ip", ip);
		
		if(file_name != null){
			map.put("file_name", file_name);
			map.put("ori_name", ori_name);
		}
		
		SqlSession ss = FactoryService.getFactory().openSession();
		int cnt = ss.update("board.editBoard", map);
		
		if(cnt == 1){
			result = true;
			ss.commit();
		} else 
			ss.rollback();
		
		ss.close();
		
		return result;
			
	}
	
	
	// 2-4) 글삭제 (논리적삭제)
	public static boolean deleteBBS(String b_idx){
		
		boolean result = false;
		
		SqlSession ss = FactoryService.getFactory().openSession(true);
		int cnt = ss.update("board.delBoard", b_idx);
		if(cnt == 1)
			result = true;
		ss.close();
		
		return result;
	}
	
	
	// ----------------------------------------------------------------------------------------------------
	
	// 3) 댓글 리스트
	public static CommentVO[] getCommentList(String b_idx){
		
		SqlSession ss = FactoryService.getFactory().openSession(true);
		List<CommentVO> c_list = ss.selectList("board.comm_list", b_idx);
		
		CommentVO[] c_ar = null;
		
		if(c_list != null && c_list.size() > 0){			
			c_ar = new CommentVO[c_list.size()];
			c_list.toArray(c_ar);			
		}		
		
		ss.close();
		
		return c_ar;
		
	}
	
	
	// 3-2) 댓글 내용보기
		public static CommentVO getComment(String c_idx){
			
			SqlSession ss = FactoryService.getFactory().openSession(true);
			CommentVO cvo = ss.selectOne("board.comment_content", c_idx);
			ss.close();
			
			return cvo;
			
		}
		
		
	//3-3) 댓글 삽입
	public static boolean addAns(CommentVO cvo){
		
		boolean result = false;
		
		SqlSession ss = FactoryService.getFactory().openSession();
		int cnt = ss.insert("board.addAnswer", cvo);
		
		if(cnt == 1){
			result = true;
			ss.commit();
			
		} else
			ss.rollback();
		
		ss.close();
		
		return result;		
	}
	
	
	// 3-4) 댓글 삭제 (물리적삭제)
	public static boolean deleteAns(String c_idx){
		
		boolean result = false;
		
		SqlSession ss = FactoryService.getFactory().openSession(true);
		int cnt = ss.delete("board.delAnswer", c_idx);
		if(cnt == 1)
			result = true;
		
		return result;
	}
	
}
