package com.sxzhongf.ad.mysql;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.sxzhongf.ad.config.BinlogConfig;
import com.sxzhongf.ad.mysql.listener.AggregationListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * CustomBinlogClient for 自定义Binlog Client
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/27
 */
@Slf4j
@Component
public class CustomBinlogClient {

    private BinaryLogClient client;

    private final BinlogConfig config;
    private final AggregationListener listener;

    @Autowired
    public CustomBinlogClient(BinlogConfig config, AggregationListener listener) {
        this.config = config;
        this.listener = listener;
    }

    public void connect() {
        new Thread(() -> {
            client = new BinaryLogClient(
                    config.getHost(),
                    config.getPort(),
                    config.getUsername(),
                    config.getPassword()
            );

            if (!StringUtils.isEmpty(config.getBinlogName()) && !config.getPosition().equals(-1L)) {
                client.setBinlogFilename(config.getBinlogName());
                client.setBinlogPosition(config.getPosition());
            }

            try {
                log.info("connecting to mysql start...");
                client.connect();
                log.info("connecting to mysql done!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void disconnect() {
        try {
            log.info("disconnect to mysql start...");
            client.disconnect();
            log.info("disconnect to mysql done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
