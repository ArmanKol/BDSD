package nl.hu;

/**
 * Created by roelant on 19/12/2019.
 */
public class BDSDKafkaConsumerRunner {
    public static final String TOPIC = "transactie";

    public static void main(String[] args) {
        boolean isAsync = false;
        KafkaProducerTransactie producerTransactieThread = new KafkaProducerTransactie();
        BDSDKafkaConsumer consumerThread = new BDSDKafkaConsumer(TOPIC, isAsync);
        KafkaConsumer2 consumerThread2 = new KafkaConsumer2(TOPIC, isAsync);
        // start the producer
        consumerThread.start();
        //consumerThread2.start();
        producerTransactieThread.start();
    }
}
