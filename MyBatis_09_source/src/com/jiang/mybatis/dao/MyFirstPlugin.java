package com.jiang.mybatis.dao;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.annotation.Target;
import java.util.Properties;

/**
 * @author jiang
 * @create 2021-09-29-9:07 下午
 */

/**
 * 完成插件签名
 */
@Intercepts(
        {
                @Signature(type = StatementHandler.class,method = "parameterize",args=java.sql.Statement.class)
        }
)
public class MyFirstPlugin implements Interceptor {

    /**
     * intercept:拦截
     *  拦截目标对象的目标方法的执行
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("MyFirstPlugin..intercept:"+invocation.getMethod());
        //动态改变sql的运行参数
        System.out.println("当前拦截到的对象"+invocation.getTarget());
        Object target = invocation.getTarget();
        //拿到target的元数据
        MetaObject metaObject = SystemMetaObject.forObject(target);
        metaObject.setValue("parameterHandler.parameterObject",4);
        //执行目标方法
        Object proceed = invocation.proceed();

        //返回执行后的返回值
        return proceed;
    }

    /**
     * plugin
     * 包装目标对象，也就是为目标对象创建一个代理对象
     * @param o
     * @return
     */
    @Override
    public Object plugin(Object o) {
        //借助Plugin类的wrap方法来使用当前Interceptor包装我们目标对象
        System.out.println("MyFirstPlugin..plugin");
        Object wrap = Plugin.wrap(o, this);
        return wrap;
    }


    /**
     * 将插件注册时的property属性设置进来
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("插件配置的信息："+properties);
    }
}
