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

import com.jiang.mybatis.bean.Employee;
import org.junit.Test;


public class MyBatisTest {

	public SqlSessionFactory getSqlSessionFactory() throws IOException{
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}

	/**
	 * 两级缓存：
	 * 一级缓存：（本地缓存）sqlSession级别的缓存，一直开启：sqlSession级别的一个Map
	 * 			与数据库同一次会话期间查询到的数据会放在本地缓存中
	 * 			以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库
	 * 	一级缓存失效情况(没有使用到当前一级缓存的情况，效果就是还要向数据库发出查询)：
	 * 		1.sqlSession不同
	 * 		2.sqlSession相同，查询条件不同（当前一级缓存中还没有这个数据）
	 * 		3.sqlSession相同，两次查询之间执行了增删改操作（这次增删改可能对当前数据有影响）
	 * 		4.sqlSession相同，手动清除了一级缓存 openSession.clearCache()
	 *
	 *
	 * 二级缓存：（全局缓存）：基于namespace级别的缓存，一个namespace对应一个二级缓存
	 *		工作机制：
	 *		1、一个会话，查询一条数据，这个数据就会被放在当前会话的一级缓存中
	 *		2、如果会话关闭，一级缓存中的数据会被保存到二级缓存中,新的会话查询信息，就可以参照二级缓存中的内容
	 *		3、不同namespace查出的数据会放在自己对应的缓存（map）中
	 *
	 * 		效果：数据会从二级缓存中获取
	 * 		查出的数据都会被默认放在一级缓存中，只有会话提交或者关闭以后，一级缓存中的数据才会转移到二级缓存中
	 *
	 *		使用：
	 *			1、开启全局二级缓存配置<setting name="cacheEnabled" value="true"/>
	 *			2、去mapper.xml中配置使用二级缓存<cache></cache>
	 *			3、pojo需要实现序列化接口
	 *
	 * 		和缓存有关的设置
	 * 			1、cahceEnabled=true：false：关闭缓存（二级缓存关闭，一级缓存一直可用）
	 * 			2、每个select标签都有useCache="true"
	 * 					false：不使用缓存（一级缓存依然可用，二级缓存不使用）
	 * 			3、每个增删改标签的 flushCache=true
	 * 				增删改执行完成后就会清除缓存（一级缓存和二级缓存都会被清除）
	 * 				查询标签中flushCache默认是false，如果是true则每次查询之前都会清空缓存
	 * 			4、sqlSession.clearCache();只是清除当前session的一级缓存
	 * 			5、localCacheScope：本地缓存作用域：（一级缓存SESSION），当前会话的所有数据保存在会话缓存中
	 */

	@Test
	public void testFirstLevelCache() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();

		try{
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee emp01 = mapper.getEmployeeId(1);
			System.out.println(emp01);
			Employee emp02 = mapper.getEmployeeId(1);
			System.out.println(emp02);
			System.out.println(emp01==emp02);

		}finally{
			openSession.close();
		}
	}



	@Test
	public void testSecondLevelCache() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		SqlSession openSession2 = sqlSessionFactory.openSession();
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			EmployeeMapper mapper2 = openSession2.getMapper(EmployeeMapper.class);

			Employee emp01 = mapper.getEmployeeId(1);
			System.out.println(emp01);
			openSession.close();

			//第二次查询是从二级缓存中拿到的数据，并没有发送新的sql
			Employee emp02 = mapper2.getEmployeeId(1);
			System.out.println(emp02);
			openSession2.close();


		}finally {
		}
	}


}
