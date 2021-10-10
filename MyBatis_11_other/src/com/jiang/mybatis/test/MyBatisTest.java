package com.jiang.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiang.mybatis.bean.EmpStatus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
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
			Page<Object> page = PageHelper.startPage(1, 5);
			List<Employee> emps = mapper.getEmps();

			//传入要连续显示多少页
			PageInfo<Employee> info = new PageInfo<>(emps,5);

			for (Employee emp : emps) {

				System.out.println(emp);
			}
//			System.out.println("当前页码:"+page.getPageNum());
//			System.out.println("总记录数:"+page.getTotal());
//			System.out.println("每页的记录:"+page.getPageSize());
//			System.out.println("总页码:"+page.getPages());
			System.out.println("当前页码:"+info.getPageNum());
			System.out.println("总记录数:"+info.getTotal());
			System.out.println("每页的记录:"+info.getPageSize());
			System.out.println("总页码:"+info.getPages());
			System.out.println("是否第一页:"+info.isIsFirstPage());
			System.out.println("是否最后一页:"+info.isIsLastPage());
			System.out.println("连续显示的页码：");
			int[] nums = info.getNavigatepageNums();
			for(int i=0;i<nums.length;i++){
				System.out.println(nums[i]);
			}
		}finally {
	    	openSession.close();
	    }
		
	}

	@Test
	public void testBatch() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		//2.获取SqlSession实例，能直接执行已经映射的sql语句
		//可以执行批量操作的sqlSession
		SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			for(int i=0;i<10000;i++){
				mapper.addEmp(new Employee(UUID.randomUUID().toString().substring(0,5),"0","1"));
			}
			openSession.commit();
		}finally{
			openSession.close();
		}




	}


	@Test
	public void testEnum() throws Exception{
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		//2.获取SqlSession实例，能直接执行已经映射的sql语句
		SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee employee=new Employee("test_enum","enum@qq.com","1");
			mapper.addEmp(employee);
			openSession.commit();
		}finally{
			openSession.close();
		}
	}

	@Test
	public void testEnumUse(){
		EmpStatus login = EmpStatus.valueOf("LOGIN");
//		EmpStatus login = EmpStatus.LOGIN;
		System.out.println("枚举的索引："+login.ordinal());
		System.out.println("枚举的名字："+login.name());
		System.out.println("枚举的状态码："+login.getCode());
		System.out.println("枚举的提示消息："+login.getMsg());
		EmpStatus[] values = EmpStatus.values();
		System.out.println(Arrays.toString(values));
	}

}
