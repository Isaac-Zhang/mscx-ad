package com.sxzhongf.ad.gateway.route;

import com.sxzhongf.ad.gateway.filter.AccessLogFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Configuration;

/**
 * GatewayRoutes for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang</a>
 * @since 2019/6/13
 */
@Slf4j
@Configuration
public class GatewayRoutes {

    /**
     * 通过Fluent API配置的其他路由断言
     * 等价于配置文件yml中的
     * {code}
     * cloud:
     * gateway:
     * routes:
     * - id: service_to_zuul_gateway #自定义路由id
     * uri: ad-gateway-zuul:1111
     * predicate:
     * - Path=/api-zuul/**
     * {/code}
     */
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                      .route("zuul-gateway-to-spring-gateway", r -> r.host("**.yi23.net")
                                                                     .and()
                                                                     .header("X-Next-Url")
                                                                     .uri("ad-gateway-zuul:1111")
                                                                     .filters(
                                                                             new AccessLogFilter() //注册自定义filter
                                                                     )
                      )
                      .route(r -> r.host("**.yi23.net")
                                   .and()
                                   .query("url")
                                   .uri("http://sxzhongf.com"))
                      .build();
    }
}
