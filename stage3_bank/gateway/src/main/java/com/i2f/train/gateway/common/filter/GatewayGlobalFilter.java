package com.i2f.train.gateway.common.filter;

import com.alibaba.fastjson.JSON;
import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.common.util.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-04-06 13:36
 **/
@Configuration
public class GatewayGlobalFilter implements GlobalFilter, Ordered {
    /**
     * 不需要验证token的白名单
     */
    private static final List<String> filterList = Arrays.asList(
            "/consumerUser/user/register",
            "/consumerUser/user/loginByPassword",
            "/consumerUser/user/loginByPhone",
            "/consumerUser/fyData",
            "/consumerFinance/finance/financeProductList",
            "/consumerProduction/production/list",
            "/consumerUser/user/upPassword",
            "/consumerAccount/account/listBankMoney",
            "/code/getImageCode",
            "/code/getPhoneCode"
    );
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取请求
        ServerHttpRequest request = exchange.getRequest();
        //获取响应
        ServerHttpResponse response = exchange.getResponse();
        String uri = request.getPath().toString();
        if (filterList.contains(uri)){
            return chain.filter(exchange);
        }
        //获取token
        String authorization = request.getHeaders().getFirst("Authorization");
        if ("undefined".equals(authorization)){
            return chain.filter(exchange);
        }
        //如果token为空
        if (StringUtils.isBlank(authorization)){
            Result<Object> result = Result.builder()
                    .code(CommonConstants.Request.TOKEN_ERROR)
                    .message(CommonConstants.Request.TOKEN_ERROR_MESSAGE)
                    .build();
            byte[] bytes = JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        }
        Result result = JwtUtils.checkToken(authorization);
        if (result != null){
            byte[] bytes = JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        }
        return chain.filter(exchange);
    }
    /**
     * 过滤器权重，数字越低，优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
