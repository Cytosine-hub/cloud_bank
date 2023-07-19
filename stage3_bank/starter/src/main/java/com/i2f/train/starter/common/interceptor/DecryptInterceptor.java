package com.i2f.train.starter.common.interceptor;

import com.i2f.train.starter.annoation.CryptData;
import com.i2f.train.starter.common.interceptor.crypt.DecryptUtil;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;


@Component
@Intercepts(@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = Statement.class))
public class DecryptInterceptor implements Interceptor {
    @Autowired
    DecryptUtil decryptUtil;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object proceed = invocation.proceed();
        if (Objects.isNull(proceed)) {
            return null;
        }
        if (proceed instanceof List) {
            List list = (ArrayList) proceed;
            if (null != list && list.size() > 0) {
                if (list.get(0).getClass().isAnnotationPresent(CryptData.class)) {
                    for (Object o : list) {
                        decryptUtil.decrypt(o);
                    }
                }
            }
        } else {
            if (proceed.getClass().isAnnotationPresent(CryptData.class)) {
                decryptUtil.decrypt(proceed);
            }
        }
        return proceed;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
