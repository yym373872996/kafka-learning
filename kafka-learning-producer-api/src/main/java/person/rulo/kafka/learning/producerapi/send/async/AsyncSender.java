package person.rulo.kafka.learning.producerapi.send.async;

import org.apache.kafka.clients.producer.*;
import person.rulo.kafka.learning.common.utils.PropertiesUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Author rulo
 * @Date 2020/5/8 18:32
 *
 * 异步发送
 */
public class AsyncSender {

    public static void main(String[] args) throws Exception {

        Properties props = PropertiesUtils.getProperties("producer.properties");
        props.put("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        // 用 byte[] 的方式发送消息
        Producer<byte[], byte[]> producer = new KafkaProducer<>(props);
        String topic = "topic-async";
        for (int i = 0; i < 100; i++) {
            String key = Integer.toString(i);
            String value = "msg" + Integer.toString(i);
            System.out.println("发送消息到队列" + topic + ": key=" + key + ", value=" + value);
            producer.send(new ProducerRecord<byte[], byte[]>(topic, key.getBytes(), value.getBytes()),
                    new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                            if (e != null) {
                                e.printStackTrace();
                            } else {
                                System.out.println("消息发送成功，当前offset: " + recordMetadata.offset());
                            }
                        }
                    });
        }
        // 间隔 3 秒以等待异步任务返回结果
        Thread.sleep(3000);
        // 关闭生产者对象
        producer.close();
    }
}
