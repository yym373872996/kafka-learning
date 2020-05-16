package person.rulo.kafka.learning.consumerapi.rec.thread;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import person.rulo.kafka.learning.common.utils.PropertiesUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;


/**
 * @Author rulo
 * @Date 2020/5/16 18:27
 */
public class SubscribeReceiverThread implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(SubscribeReceiverThread.class);
    private ConsumerRecords<String, String> records;

    public SubscribeReceiverThread(ConsumerRecords<String, String> records) {
        this.records = records;
    }

    @Override
    public void run() {

        for (TopicPartition partition : records.partitions()) {
            // 获取消费记录数据集
            List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
            // 打印消费记录
            for (ConsumerRecord<String, String> record : partitionRecords) {
                LOG.info("当前线程 ID：" + Thread.currentThread().getId());
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
        }

    }
}
