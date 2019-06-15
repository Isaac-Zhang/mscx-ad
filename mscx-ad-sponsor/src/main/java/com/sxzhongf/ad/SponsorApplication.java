package com.sxzhongf.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * SponsorApplication for 广告赞助商/投递服务启动类
 * 添加注解{@link EnableFeignClients}之后，当前微服务就可以调用别的微服务，
 * 但是当前服务是广告提供，不需要调用别的微服务，在此只是为了在dashboard中监控
 *
 * {@link EnableCircuitBreaker} 也是为了dashboard监控
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang</a>
 * @since 2019/6/15
 */
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients
@SpringBootApplication
public class SponsorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SponsorApplication.class, args);
    }
}
