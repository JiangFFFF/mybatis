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
	 * 1.根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象
	 * 有数据源一些运行环境信息
	 * 2.sql映射文件：配置了每一个sql，以及sql的封装规则等
	 * 3.将sql映射文件注册在全局配置文件中
	 * 4.写代码：
	 * 	1）：根据全局配置文件得到SqlSessionFactory
	 * 	2）：使用SqlSessionFactory工厂，获取到sqlSession对象使用他来执行增删改查
	 * 		一个sqlSession就是代表和数据库的一次会话，用完关闭
	 * 	3):使用sql的唯一标识来告诉MyBatis执行哪个sql，sql都是保存在sql映射文件中
	 * @throws IOException
	 */
	
	@Test
	public void test() throws IOException {
		String resource = "mybatis-config.xml";
	    InputStream inputStream = Resources.getResourceAsStream(resource);
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	    
	    //2.获取SqlSession实例，能直接执行已经映射的sql语句
	    SqlSession openSession = sqlSessionFactory.openSession();
	    try {
	    	Employee employee = openSession.selectOne("com.jiang.mybayis.EmployeeMapper.selectEmp",1);
	    	System.out.println(employee);
	    }finally {
	    	openSession.close();
	    }

	}
	
	@Test
	public void Test2() throws IOException {
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

}
