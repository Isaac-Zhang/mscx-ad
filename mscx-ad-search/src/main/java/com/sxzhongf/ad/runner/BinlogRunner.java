package com.sxzhongf.ad.runner;

import com.sxzhongf.ad.mysql.CustomBinlogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * BinlogRunner for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/27
 */
@Slf4j
@Component
public class BinlogRunner implements CommandLineRunner {

    private final CustomBinlogClient binlogClient;

    @Autowired
    public BinlogRunner(CustomBinlogClient binlogClient) {
        this.binlogClient = binlogClient;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("BinlogRunner is running...");
        binlogClient.connect();
    }
}
