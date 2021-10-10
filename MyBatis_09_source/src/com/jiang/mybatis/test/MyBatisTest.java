package com.jiang.mybatis.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.jiang.mybatis.bean.Employee;
import com.jiang.mybatis.dao.EmployeeMapper;

/**
 * 1.接口式编程
 * 原生： dao----》daoImpl
 * mybatis：Mapper---》xxMapper.xml
 * 
 * 2.sqlSession代表和数据库的一次会话,用完需要关闭
 * 3.sqlSession和connection一样都是非线程安全的。每次使用都应该去获取新的对象
 * 4.mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象
 * 5.两个重要的配置文件：
 * 		mybatis的全局配置文件：包含数据库连接池信息，事务管理器等。。。系统运行环境信息
 * 		sql映射文件：保存了每一个sql语句的映射信息(将sql抽取出来)
 * 
 * 
 * @Description
 * @author jiang Email:1820920433@qq.com
 * @date 2021年9月26日下午2:47:02
 * @type_name MyBatisTest
 * @return
 */

public class MyBatisTest {


	/**
	 * 1.获取SqlSessionFactory对象
	 * 		解析文件的每一个信息保存在Configuration，返回包含Configuration的DefaultSqlSessionFactory
	 * 		注意：MapperStatement:代表一个增删改查的详细信息
	 * 2.获取SqlSession对象
	 *		返回一个DefaultSqlSession对象，包含Executor和Configuration
	 *		这一步会创建Executor对象
	 * 3.获取接口的代理对象（MapperProxy）
	 * 		getMapper：使用MapperProxyFactory创建一个MapperProxy的代理对象
	 * 				代理对象里包含了DefaultSqlSession(Execute)
	 * 4.执行增删改查方法
	 *
	 * @throws IOException
	 */
	@Test
	public void Test1() throws IOException {
		//1.获取sqlHessionFactory
		String resource = "mybatis-config.xml";
	    InputStream inputStream = Resources.getResourceAsStream(resource);
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

	    //2.获取sqlSession对象
	    SqlSession openSession = sqlSessionFactory.openSession();

	    try {
		    //3.获取接口的实现类
	    	//只要将接口和xml文件动态绑定，mybatis会为接口自动地创建一个代理对象,代理对象执行增删改查
		    EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
		    Employee employee = mapper.getEmployeeId(1);
		    System.out.println(employee);
	    }finally {
	    	openSession.close();
	    }

	}

	/**
	 * 插件编写：
	 * 1、编写Interceptor的实现类
	 * 2、使用@Intercepts注解完成插件签名
	 * 3、将写好的插件注册到全局配置文件中
	 */
	@Test
	public void testPlugin(){

	}

}
