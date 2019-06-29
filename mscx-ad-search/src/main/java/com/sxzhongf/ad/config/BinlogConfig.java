package com.sxzhongf.ad.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * BinlogConfig for 定义监听Binlog的配置信息
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/27
 */
@Component
@ConfigurationProperties(prefix = "adconf.mysql")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BinlogConfig {

    private String host;
    private Integer port;
    private String username;
    private String password;

    private String binlogName;
    private Long position;
}
