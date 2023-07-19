package com.i2f.train.starter.common.interceptor;


import com.i2f.train.starter.annoation.CryptData;
import com.i2f.train.starter.common.interceptor.crypt.EncryptUtil;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Properties;

/**
 * 我们使用了 ParameterHandler.setParamters()方法，拦截mapper.xml中paramsType的实例
 * （即在每个含有paramsType属性mapper语句中，都执行该拦截器，对paramsType的实例进行拦截处理）
 */
@Component
@Intercepts(@Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class))
public class EncryptInterceptor implements Interceptor {
    @Autowired
    EncryptUtil encryptUtil;
    @Override
    //核心拦截逻辑
    public Object intercept(Invocation invocation) throws Throwable {
        //@Signature 指定了 type= parameterHandler 后，这里的 invocation.getTarget() 便是parameterHandler
        ParameterHandler target = (ParameterHandler) invocation.getTarget();
        // 获取参数对像，即 mapper 中 paramsType 的实例
        Field parameterObject = target.getClass().getDeclaredField("parameterObject");
        parameterObject.setAccessible(true);
        //取出实例对象
        Object o = parameterObject.get(target);
        if (!Objects.isNull(o)) {
            Class<?> aClass = o.getClass();
            if (aClass.isAnnotationPresent(CryptData.class)) {
                //获取所有的字段
                Field[] declaredFields = aClass.getDeclaredFields();
                //将字段传入加密类
                encryptUtil.encrypt(declaredFields, o);
            }
        }
        //放行
        return invocation.proceed();
    }


    @Override
    //拦截器链
    public Object plugin(Object target) {//将当前拦截器放入拦截器链中
        return Plugin.wrap(target, this);
    }
    //自定义配置文件操作
    @Override
    public void setProperties(Properties properties) {
    }
}
