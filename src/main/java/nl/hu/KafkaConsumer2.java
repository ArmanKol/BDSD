package nl.hu;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Hello world!
 *
 */
public class KafkaConsumer2 extends Thread {
    private static Logger log = LoggerFactory.getLogger("kafkaConsumer2");
    private final String topic;
    //private final Boolean isAsync;
    private final KafkaConsumer<String, String> kafkaConsumer;
    private static final String KAFKA_SERVER_URL = "localhost";
    private static final int KAFKA_SERVER_PORT = 9092;
    private static final String CLIENT_ID = "kafkaConsumer2";
    private static final Transactions transactions = new Transactions();

    public KafkaConsumer2(String topic, boolean isAsync) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT);
        properties.put("client.id", CLIENT_ID);
        properties.put("group.id", "differentGroup");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConsumer = new KafkaConsumer <String, String>(properties);
        this.topic = topic;
        //this.isAsync = isAsync;
    }

    /**
     * This method will be run, because this Java file extends the Thread class.
     */
    public void run() {
        Set<String> topics = new HashSet<String>();
        /* first subscribe to the topic */
        topics.add(topic);
        kafkaConsumer.subscribe(topics);
        pollForNewRecords(kafkaConsumer);

    }
    
    //TODO RESULTVALT WERKT NIET!
    private int resolve_query_1(Transaction transaction, int threshold) {
    	int resultval = 0;
    	Map<Integer, Integer> lijst = transactions.getCustomerWithSameProduct(transaction.getCustomerId());
    	
    	
    	for(Map.Entry<Integer, Integer> customer : lijst.entrySet()) {
    		log.info("Customer " + customer.getKey() + "  shares " + customer.getValue() + " identical products with customer " + transaction.getCustomerId());
			if(customer.getValue() > threshold) {
				resultval++;
			}
    	}
    	
    	return resultval;
    }
    
    private void resolve_query_2(){
    	Map<String, Integer> map = transactions.getTransactionProductBoughtSameTime();    	
    	LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
    	
    	map.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
    	
    	for(Map.Entry<String, Integer> item : reverseSortedMap.entrySet()) {
    		String[] split = item.getKey().split(":");
    		log.info("Product "+split[0]+" and product "+split[1]+" were bought "+item.getValue()+" times together");
    	} 	 	
    }
    

    /**
     * This method runs a poll job. It continuously asks for new data from Kafka.
     * @param consumer
     * @throws Exception 
     */
    private void pollForNewRecords(KafkaConsumer<String, String> consumer){
        int threshold = 3;
        try {
            while (true) {
                // for Java versions 1.8 and higher, use Duration.
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                
                for (ConsumerRecord<String, String> record : records)
                {
                    JSONObject jsonObject = new JSONObject(record.value());
                    Transaction t = new Transaction(Integer.parseInt(record.key()), jsonObject.getInt("productID"), jsonObject.getString("datum"), jsonObject.getInt("filiaalID"));
                    // add transaction to the list of known transactions
                    transactions.add(t);
                    // run query 1
                    //resolve_query_1(t, threshold);
                    //log.info("# of customers with threshold > " + threshold + ": " + resolve_query_1(t, threshold));
                    
                    //Run query 2
                    resolve_query_2();
                    int updatedCount = 1;
                }   
            }
        } finally {
            consumer.close();
        }
    }


}
