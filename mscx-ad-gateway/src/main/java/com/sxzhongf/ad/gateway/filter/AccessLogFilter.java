package com.sxzhongf.ad.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * AccessLogFilter for 实现请求调用前记录时间
 *
 * 在上面的代码中，Ordered中的int getOrder()方法是来给过滤器设定优先级别的，值越大则优先级越低。
 * 还有有一个filter(exchange,chain)方法，在该方法中，先记录了请求的开始时间，
 * 并保存在ServerWebExchange中，此处是一个“pre”类型的过滤器，
 * 然后再chain.filter的内部类中的run()方法中相当于"post"过滤器，在此处打印了请求所消耗的时间。
 * 然后将该过滤器注册到router中，代码如下：
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang</a>
 * @since 2019/6/13
 */
@Slf4j
@Component
public class AccessLogFilter implements GatewayFilter,Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put("api_request_time", System.currentTimeMillis());
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    Long startTime = exchange.getAttribute("api_request_time");
                    if (startTime != null) {
                        log.info(exchange.getRequest().getURI().getRawPath() + ": " + (System.currentTimeMillis() - startTime) + "ms");
                    }
                })
        );
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
