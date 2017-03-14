package mybatis.service;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


// 여러개의 DAO를 만들어도 factory는 하나를 사용
public class FactoryService {

	private static SqlSessionFactory factory;

	static {		
		try {

			Reader r = Resources.getResourceAsReader("mybatis/config/config.xml");
			factory = new SqlSessionFactoryBuilder().build(r);
			r.close();

		} catch (Exception e) {
			e.printStackTrace();			
		}		
	} // static 초기화


	public static SqlSessionFactory getFactory(){
		return factory;
	}
	
}
