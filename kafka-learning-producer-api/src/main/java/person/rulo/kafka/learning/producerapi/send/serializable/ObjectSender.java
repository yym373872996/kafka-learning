package person.rulo.kafka.learning.producerapi.send.serializable;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import person.rulo.kafka.learning.common.utils.PropertiesUtils;
import person.rulo.kafka.learning.common.pojo.Person;

import java.util.Properties;

/**
 * @Author rulo
 * @Date 2020/5/9 20:06
 */
public class ObjectSender {

    public static void main(String[] args) throws Exception {
        Properties props = PropertiesUtils.getProperties("producer.properties");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "person.rulo.kafka.learning.common.serialization.ObjectSerializer");

        Producer<String, Object> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 10; i++) {
            String topic = "topic-object";
            String key = Integer.toString(i);
            Person value = new Person("人员" + i, i);

            ProducerRecord producerRecord = new ProducerRecord<String, Object>(topic, key, value);
            // 调用 send() 发送消息后，再通过 get() 方法等待返回结果
            RecordMetadata recordMetadata = producer.send(new ProducerRecord<String, Object>(topic, key, value)).get();
            System.out.println("发送消息到队列" + topic + ": key=" + key + ", value=" + value);
            if (recordMetadata != null) {
                System.out.println("消息发送成功，当前offset: " + recordMetadata.offset());
            }
        }
        // 关闭生产者对象
        producer.close();
    }

}
