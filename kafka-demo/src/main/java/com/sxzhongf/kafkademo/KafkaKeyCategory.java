package com.sxzhongf.kafkademo;

/**
 * KafkaKeyCategory for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/8/14
 */
public enum KafkaKeyCategory {

    asyn(0, "demo-asyn-key"),
    normal(1, "demo-key"),
    sync(2, "demo-sync-key");

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    private int code;
    private String value;

    KafkaKeyCategory(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
