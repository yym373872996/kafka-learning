package person.rulo.kafka.learning.producerapi.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @Author rulo
 * @Date 2020/5/9 20:31
 */
public class HashCodePartitioner implements Partitioner {
    /**
     * 实现基于 hash code 的主题分区索引算法
     * @param topic
     * @param key
     * @param keyBytes
     * @param value
     * @param valueBytes
     * @param cluster
     * @return
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        int partition = 0;
        String k = (String) key;
        // 通过对 hashCode() 绝对值取模计算分区
        partition = Math.abs(k.hashCode()) % cluster.partitionCountForTopic(topic);
        return partition;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
