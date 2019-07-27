package com.sxzhongf.ad.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * GatewayApplication for zuul网关启动类
 * {@link SpringCloudApplication} 是下面三个注解组合
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang</a>
 * @see SpringBootApplication
 * @see EnableDiscoveryClient
 * @see EnableCircuitBreaker
 * @since 2019/6/13
 */
@SpringCloudApplication
@EnableZuulProxy
//@EnableScheduling
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Scheduled(fixedRate = 2000)
    private void print(){
        System.out.println("hello scheduled.");
    }
}
