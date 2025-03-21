package com.xx;

import cn.hutool.json.JSONUtil;
import com.xx.xagentsdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.xx.xagentsdk.model.User;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 全局过滤
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("全局过滤器拦截请求成功！");
        // 1. 用户发送请求到API网关

        // 2. 请求日志
        ServerHttpRequest request =  exchange.getRequest();
        log.info("请求URI：" + request.getURI());
        log.info("请求方法：" + request.getMethod());
        log.info("请求来源地址：" + request.getRemoteAddress());
        String sourceIP = request.getRemoteAddress().getHostString();
        log.info("请求来源ip：" + sourceIP);
        log.info("请求头：" + request.getHeaders());


        // 3. 黑白名单
        ServerHttpResponse response = exchange.getResponse();
        if(!IP_WHITE_LIST.contains(sourceIP)){
            // 不在白名单内，抛出403 Forbidden 禁止访问错误
            response.setStatusCode(HttpStatus.FORBIDDEN);

            // 返回处理完成的响应
            return response.setComplete();
        }
        // 4. 用户鉴权（判断ak，sk是否合法）

        // 从请求头中获取参数
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String body = headers.getFirst("body");
        String sign = headers.getFirst("sign");
        // 从请求头中获取名为“secretKey”的值
        // String secretKey = request.getHeader("secretKey");


        // 校验accessKey
        if(!accessKey.equals("123"))
        {
            throw new RuntimeException("无权限");
        }

        // 校验随机数
        if(Long.parseLong(nonce) > 10000){
            throw new RuntimeException("无权限");
        }
        log.info("传递随机数是：" + nonce);


        // 请求签名
        if(!SignUtils.genSign(body,"456").equals(sign)){
            return handleNoAuth(response);
        }
        log.info("服务端签名是：" + SignUtils.genSign(body,"456"));
        log.info("传递签名是：" + sign);

        // 5. 请求的模拟接口是否存在
        // 6. 请求转发，调用模拟接口
        Mono<Void> filter = chain.filter(exchange);
        // 7. 响应日志
        log.info("响应：" + response.getStatusCode());
        return handleResponse(exchange,chain);
        // 8. 调用成功，接口调用次数+1
//        if(response.getStatusCode() == HttpStatus.OK){
//            log.info("响应成功！");
//        }else{
//            return handleInvokeError(response);
//        }
//        // 9. 调用失败，返回一个规范的错误码
//        return filter;
    }

    /**
     * 返回403错误
     * @param response
     * @return
     */
    public Mono<Void> handleNoAuth(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }
    /**
     * 返回服务端错误
     * @param response
     * @return
     */
    public Mono<Void> handleInvokeError(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }

    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            // 获取原始的响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 获取数据缓冲工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 获取响应的状态码
            HttpStatus statusCode = originalResponse.getStatusCode();

            // 判断状态码是否为200 OK(按道理来说,现在没有调用,是拿不到响应码的,对这个保持怀疑 沉思.jpg)
            if(statusCode == HttpStatus.OK) {
                // 创建一个装饰后的响应对象(开始穿装备，增强能力)
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                    // 重写writeWith方法，用于处理响应体的数据
                    // 这段方法就是只要当我们的模拟接口调用完成之后,等它返回结果，
                    // 就会调用writeWith方法,我们就能根据响应结果做一些自己的处理
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        // 判断响应体是否是Flux类型
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 返回一个处理后的响应体
                            // (这里就理解为它在拼接字符串,它把缓冲区的数据取出来，一点一点拼接好)
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // 读取响应体的内容并转换为字节数组
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                //rspArgs.add(requestUrl);
                                String data = new String(content, StandardCharsets.UTF_8);//data
                                sb2.append(data);
                                // 打印日志
                                log.info("响应结果：" + data);
                                log.info(sb2.toString(), rspArgs.toArray());//log.info("<-- {} {}\n", originalResponse.getStatusCode(), data);
                                // 将处理后的内容重新包装成DataBuffer并返回
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 对于200 OK的请求,将装饰后的响应对象传递给下一个过滤器链,并继续处理(设置repsonse对象为装饰过的)
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            // 对于非200 OK的请求，直接返回，进行降级处理
            return chain.filter(exchange);
        }catch (Exception e){
            // 处理异常情况，记录错误日志
            log.error("网关处理异常：" + e);
            return chain.filter(exchange);
        }
    }



}
