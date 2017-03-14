package mybatis.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import mybatis.bbs.vo.BoardVO;
import mybatis.service.FactoryService;
import mybatis.vo.MemVO;

public class MemDAO {
	
	
	/////////////////////////////////////// 비즈니스 로직 ///////////////////////////////////////////
	
	// 1) 로그인
	public static MemVO login(String id, String pwd){
		
		Map<String, String> map = new HashMap<>();		
		map.put("id", id);
		map.put("pwd", pwd);
		
		SqlSession ss = FactoryService.getFactory().openSession(true);
		MemVO vo = ss.selectOne("mem.login", map);
		ss.close();
		
		return vo;
		
	}
	
	// 2) 회원가입
	public static boolean regMember(String id, String pwd, String name, String email, String phone){
		
		MemVO vo = new MemVO();
		vo.setId(id);
		vo.setPwd(pwd);
		vo.setName(name);
		vo.setEmail(email);
		vo.setPhone(phone);
		
		SqlSession ss = FactoryService.getFactory().openSession(true);
		
		boolean v1 = getMember(id);
		boolean v2 = false;
		
		if(!v1){
			
			int cnt = ss.insert("mem.regMem", vo);
			
			if(cnt == 1)
				v2 = true;
			
			ss.close();
			
		}
		
		return v2;
		
	}
	
	
	// 3) 아이디 중복확인
	public static boolean getMember(String id){
		
		boolean value = false;
		
		SqlSession ss = FactoryService.getFactory().openSession(true);		
		MemVO vo = ss.selectOne("mem.getMem", id);
		
		if(vo != null)
			value = true;
		
		ss.close();
		
		return value;
		
	}
	
	
	// 4) 회원정보
	public static MemVO getMemInfo(String id){
		
		SqlSession ss = FactoryService.getFactory().openSession(true);
		MemVO vo = ss.selectOne("mem.getMem", id);
		return vo;
	}
}
