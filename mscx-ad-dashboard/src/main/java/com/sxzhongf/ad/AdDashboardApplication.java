package com.sxzhongf.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * AdDashboardApplication for Hystrix Dashboard 启动类
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/8/15
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrixDashboard
public class AdDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdDashboardApplication.class, args);
    }
}
