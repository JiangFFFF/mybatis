package com.jiang.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.jiang.mybatis.bean.Department;
import com.jiang.mybatis.dao.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.jiang.mybatis.bean.Employee;

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

	@Test
	public void test3() throws IOException {
		//1.获取sqlHessionFactory
		String resource="mybatis-config.xml";
		InputStream resourceAsStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapperAnnotation mapper = openSession.getMapper(EmployeeMapperAnnotation.class);
			Employee employee = mapper.getEmployeeId(1);
			System.out.println(employee);
		}finally{
			openSession.close();
		}
	}


	/**
	 * 测试增删改
	 *
	 *1.mybatis允许增删改直接定义以下类型返回值
	 * Integer ,Long,Boolean,void
	 *2.需要手动提交数据-->sqlSessionFactory.openSession()
	 * 	自动提交数据-->sqlSessionFactory.openSession(true)
	 */
	@Test
	public void test4() throws IOException {
		String resource="mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		//1.获取到的SqlSession不会自动提交数据
		SqlSession openSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			//测试添加
//			Employee employee = new Employee(null, "admin", "121312@qq.com", "1");
//			mapper.addEmp(employee);
//			System.out.println(employee.getId());

			//测试修改
//			boolean lalalal = mapper.updateEmp(new Employee(4, "lalalal", "22323@gmail.com", "0"));
//			System.out.println(lalalal);

			//测试删除
//			mapper.deleteEmpById(3);
			//2.手动提交数据
			openSession.commit();
		}finally {
			openSession.close();
		}
	}

	@Test
	public void test5() throws IOException {
		String resource="mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		//1.获取到的SqlSession不会自动提交数据
		SqlSession openSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
//			Employee employee = mapper.getEmpByIdAndLastName(5, "jiang");
//			Map<String,Object> map=new HashMap<>();
//			map.put("id",5);
//			map.put("lastName","jiang");
//			Employee employee = mapper.getEmpMap(map);
//			System.out.println(employee);

//			List<Employee> empsByLastNameLike = mapper.getEmpsByLastNameLike("%a%");
//			System.out.println(empsByLastNameLike);

//			Map<String, Object> empByIdReturnMap = mapper.getEmpByIdReturnMap(5);
//			System.out.println(empByIdReturnMap);

			Map<Integer, Employee> empByLastNameLikeReturnMap = mapper.getEmpByLastNameLikeReturnMap("%a%");
			System.out.println(empByLastNameLikeReturnMap);
			openSession.commit();
		}finally {
			openSession.close();
		}
	}


	@Test
	public void test6() throws IOException {
		String resource="mybatis-config.xml";
		InputStream resourceAsStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
		SqlSession openSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);
//			Employee empById = mapper.getEmpById(5);
//			System.out.println(empById);

//			Employee empAndDept = mapper.getEmpAndDept(1);
//			System.out.println(empAndDept);
//			System.out.println(empAndDept.getDept());

			Employee empBuIdStep = mapper.getEmpBuIdStep(5);
			System.out.println(empBuIdStep);
			System.out.println(empBuIdStep.getDept());
			openSession.commit();
		}finally{
			openSession.close();
		}
	}

	@Test
	public void test7() throws IOException {
		String resource="mybatis-config.xml";
		InputStream resourceAsStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
		SqlSession openSession = sqlSessionFactory.openSession();
		try{
			DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
			Department departById = mapper.getDepartById(2);
			System.out.println(departById);
			System.out.println(departById.getDepartmentName());
			openSession.commit();
		}finally{
			openSession.close();
		}
	}

	@Test
	public void test8() throws IOException {
		String resource="mybatis-config.xml";
		InputStream resourceAsStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
		SqlSession openSession = sqlSessionFactory.openSession();
		try{
			DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
//			Department deptByIdPlus = mapper.getDeptByIdPlus(1);
//			System.out.println(deptByIdPlus);
//			System.out.println(deptByIdPlus.getEmps());

			Department deptByIdStep = mapper.getDeptByIdStep(1);
			System.out.println(deptByIdStep);
			System.out.println(deptByIdStep.getEmps());
//			openSession.commit();
		}finally {
			openSession.close();
		}
	}

	@Test
	public void testDynamicSql() throws IOException {
		String resource="mybatis-config.xml";
		InputStream resourceAsStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
		SqlSession openSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			//测试if/where
//			Employee employee=new Employee(null,"%o%",null,null);
//			List<Employee> emps = mapper.getEmpsByConditionIf(employee);
//			System.out.println(emps);

			//测试Trim
//			Employee employee=new Employee(1,"%o%","1820920433@qq.com",null);
//			List<Employee> emps = mapper.getEmpsByConditionTrim(employee);
//			System.out.println(emps);

			//测试choose
//			Employee employee=new Employee(null,null,null,null);
//			List<Employee> emps = mapper.getEmpsByConditionTChoose(employee);
//			for(Employee emp:emps){
//				System.out.println(emp);
//			}

			//测试set
//			Employee employee=new Employee(1,"kkkv",null,null);
//			mapper.updateEmp(employee);

			//测试foreach
			List<Employee> list = mapper.getEmpsByConditionForeach(Arrays.asList(1, 2, 3, 4));
			for(Employee emp:list){
				System.out.println(emp);
			}
			openSession.commit();
		}finally {
			openSession.close();
		}
	}


	@Test
	public void testBatchSave() throws IOException {
		String source="mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(source);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession openSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			List<Employee> emps=new ArrayList<>();
			emps.add(new Employee(null,"smith","smith@qq.com","1",new Department(1)));
			emps.add(new Employee(null,"allen","allen@qq.com","0",new Department(1)));
			mapper.addEmps(emps);
			openSession.commit();
		}finally{
			openSession.close();
		}
	}

	@Test
	public void testInnerParam() throws IOException {
		String source="mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(source);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession openSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			Employee employee=new Employee();
			employee.setLastName("%e%");
			List<Employee> empsTestInnerParameter = mapper.getEmpsTestInnerParameter(employee);
			empsTestInnerParameter.forEach(System.out::println);
			openSession.commit();
		}finally {
			openSession.close();
		}
	}



}
