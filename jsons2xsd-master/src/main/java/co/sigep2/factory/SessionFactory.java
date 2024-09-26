package co.sigep2.factory;

import java.io.InputStream;
import java.io.Serializable;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


/**
 * @author Jose Viscaya.\
 * @version V1.0
 *
 */
public class SessionFactory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1514525337808713626L;
	/**
	 * 
	 */
	
	private static SessionFactory instance;
	private SqlSessionFactory sqlSessionFactory;
	
	public static synchronized SessionFactory getInstance() {
		if( instance == null ) {
			instance = new SessionFactory();
		}
		return instance;
	}
	
	
	public SessionFactory(){
		
		InputStream inputStream;
		inputStream = loadXmlMyBatisConf();
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	 
	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public InputStream loadXmlMyBatisConf(){
	   ClassLoader cl = this.getClass().getClassLoader();
       InputStream is = cl.getResourceAsStream("co/sigep2/factory/MyBatisConf.xml");
       return is;
       
	}
}

