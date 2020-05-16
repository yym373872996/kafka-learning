package person.rulo.kafka.learning.producerapi.send.partitioner;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import person.rulo.kafka.learning.common.utils.PropertiesUtils;

import java.util.Properties;

/**
 * @Author rulo
 * @Date 2020/5/9 20:44
 */
public class HashCodePartitionerSender {

    public static void main(String[] args) throws Exception {

        Properties props = PropertiesUtils.getProperties("producer.properties");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("partitioner.class", "person.rulo.kafka.learning.producerapi.partitioner.HashCodePartitioner");
        // 用 String 格式发送消息
        Producer<String, String> producer = new KafkaProducer<>(props);
        String topic = "topic-hashcode-partitioner";
        for (int i = 0; i < 100; i++) {
            String key = Integer.toString(i);
            String value = "msg" + Integer.toString(i);
            // 调用 send() 发送消息后，再通过 get() 方法等待返回结果
            RecordMetadata recordMetadata = producer.send(new ProducerRecord<String, String>(topic, key, value)).get();
            System.out.println("发送消息到队列" + topic + ": key=" + key + ", value=" + value);
            if (recordMetadata != null) {
                System.out.println("消息发送成功，当前offset: " + recordMetadata.offset());
            }
        }
        // 关闭生产者对象
        producer.close();
    }

}
