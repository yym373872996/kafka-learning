package person.rulo.kafka.learning.producerapi.send.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author rulo
 * @Date 2020/5/9 17:14
 */
public class MultiThreadSender {

    // 最大线程数
    private final static int MAX_THREAD_SZIE = 6;

    public static void main(String[] args) {
        // 创建一个固定线程数量的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD_SZIE);
        // 提交任务
        executorService.submit(new AsyncSenderThread());
        // 关闭线程池
        executorService.shutdown();
    }


}
