package person.rulo.kafka.learning.consumerapi.rec.thread;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import person.rulo.kafka.learning.common.utils.PropertiesUtils;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author rulo
 * @Date 2020/5/16 20:14
 */
public class MultiThreadReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(MultiThreadReceiver.class);

    // 最大线程数
    private final static int MAX_THREAD_SZIE = 6;
    private KafkaConsumer<String, String> consumer;
    private ExecutorService executorService;

    public MultiThreadReceiver() {
        try {
            consumer = new KafkaConsumer<>(setProps());
        } catch (Exception e) {
            e.printStackTrace();
        }
        consumer.subscribe(Arrays.asList("topic-async-thread"));
    }

    public Properties setProps() throws Exception {
        Properties props = PropertiesUtils.getProperties("consumer.properties");
        // key的反序列化方式
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // value的反序列化方式
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // 指定消费者组
        props.put("group.id", "cg-thread");
        return props;
    }

    public void execute() {
        // 初始化线程池
        executorService = Executors.newFixedThreadPool(MAX_THREAD_SZIE);
        // 实时消费标识
        boolean flag = true;
        while (flag) {
            // 获取主题消息数据
            ConsumerRecords<String, String> records = consumer.poll(100);
            // 执行多线程消费
            executorService.submit(new SubscribeReceiverThread(records));
        }

    }

    public void shutdown() {
        try {
            if (consumer != null) {
                consumer.close();
            }
            if (executorService != null) {
                executorService.shutdown();
            }
            if (! executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                LOG.error("kafka 消费者线程池关闭超时");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        MultiThreadReceiver multiThreadReceiver = new MultiThreadReceiver();
        try {
            multiThreadReceiver.execute();
        } catch (Exception e) {
            LOG.error("kafka 消费者线程池异常：" + e.getMessage());
            multiThreadReceiver.shutdown();
        }

    }


}
