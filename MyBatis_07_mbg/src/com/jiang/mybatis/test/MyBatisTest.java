package com.jiang.mybatis.test;


import com.jiang.mybatis.bean.Employee;
import com.jiang.mybatis.bean.EmployeeExample;
import com.jiang.mybatis.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;




public class MyBatisTest {

	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}


	@Test
	public void testMbg() throws Exception{
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File("mbg.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}

	@Test
	public void testMybatisSimple() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			List<Employee> employees = mapper.selectByExample(null);
			for (Employee employee : employees) {
				System.out.println(employee.getId());
			}
		}finally{
			openSession.close();
		}
	}


	@Test
	public void testMybatisS3() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			//xxxExample()就是封装查询条件的
			//1.查询所有
//			List<Employee> employees = mapper.selectByExample(null);
			//2、查询员工名字中有e字母，和员工性别是1的
			//封装员工查询条件的example
			EmployeeExample example = new EmployeeExample();
			//创建一个Criteria，这个Criteria就是拼装查询条件
			EmployeeExample.Criteria criteria = example.createCriteria();
			criteria.andLastNameLike("%a%");
			criteria.andGenderEqualTo("1");

			EmployeeExample.Criteria criteria2=example.createCriteria();
			criteria2.andEmailLike("%e%");

			example.or(criteria2);

			List<Employee> employees1 = mapper.selectByExample(example);

			for (Employee employee : employees1) {
				System.out.println(employee.getId());
			}
		}finally{
			openSession.close();
		}
	}

}