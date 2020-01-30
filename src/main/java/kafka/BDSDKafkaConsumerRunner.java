package kafka;

public class BDSDKafkaConsumerRunner {
    public static final String TOPIC = "transactie";

    public static void main(String[] args) {
        boolean isAsync = false;
        KafkaProducerTransactie producerTransactieThread = new KafkaProducerTransactie();
        KafkaConsumerQuery1 consumerQuery1_thread = new KafkaConsumerQuery1(TOPIC, isAsync);
        KafkaConsumerQuery2 consumerQuery2_thread = new KafkaConsumerQuery2(TOPIC, isAsync);
        
        //Start de consumers
        consumerQuery1_thread.start();
        consumerQuery2_thread.start();
        
        //Start de producer
        producerTransactieThread.start();
    }
}
