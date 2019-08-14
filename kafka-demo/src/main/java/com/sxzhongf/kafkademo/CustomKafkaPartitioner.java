package com.sxzhongf.kafkademo;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.record.InvalidRecordException;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;

/**
 * CustomKafkaPartitioner for 自定义消息分配器
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/8/14
 */
public class CustomKafkaPartitioner implements Partitioner {

    /**
     * 决定消息将被写入哪个分区
     *
     * @param cluster kafka集群信息
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {

        //获取集群中topic对应的所有的partition
        List<PartitionInfo> partitionInfoList = cluster.partitionsForTopic(topic);
        int partitionCount = 0;
        //获取partition 个数
        if (null != partitionInfoList && partitionInfoList.size() > 0) {
            partitionCount = partitionInfoList.size();
        }

        //自定义一个分区规则，必须传递消息的key，如果不传，则报错
        if (null == keyBytes || !(key instanceof String)) {
            throw new InvalidRecordException("Kafka message key is invalid.");
        }
        if (partitionCount == 1) {
            return 0;
        }
        switch (String.valueOf(key)) {
            case "demo-key":
            case "demo-asyn-key":
            case "demo-sync-key":
            case "demo-partitioner-key":
                return partitionCount - 1;
        }

        //使用kafka默认分区算法，murmur2获取字节数组hash值，然后对分区数取余
        return Math.abs(Utils.murmur2(keyBytes)) % (partitionCount - 1);
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
