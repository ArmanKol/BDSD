package kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;

public class KafkaConsumerQuery2 extends Thread {
    private static Logger log = LoggerFactory.getLogger("kafkaConsumerQuery2");
    private final String topic;
    private final Boolean isAsync;
    private final KafkaConsumer<String, String> kafkaConsumer;
    private static final String KAFKA_SERVER_URL = "localhost";
    private static final int KAFKA_SERVER_PORT = 9092;
    private static final String CLIENT_ID = "kafkaConsumer2";
    private static final Transactions transactions = new Transactions();

    public KafkaConsumerQuery2(String topic, boolean isAsync) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT);
        properties.put("client.id", CLIENT_ID);
        properties.put("group.id", "queryTwo");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConsumer = new KafkaConsumer <String, String>(properties);
        this.topic = topic;
        this.isAsync = isAsync;
    }

    /**
     * This method will be run, because this Java file extends the Thread class.
     */
    public void run() {
        Set<String> topics = new HashSet<String>();
        topics.add(topic);
        kafkaConsumer.subscribe(topics);
        pollForNewRecords(kafkaConsumer);

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
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                
                for (ConsumerRecord<String, String> record : records)
                {
                	// data wordt in een jsonobject gezet om zo beter toegang te krijgen tot de data.
                    JSONObject jsonObject = new JSONObject(record.value());
                    Transaction t = new Transaction(Integer.parseInt(record.key()), jsonObject.getInt("productID"), jsonObject.getString("datum"), jsonObject.getInt("filiaalID"));
                    
                    // Transaction object wordt opgeslagen in een bestaande transactie lijst.
                    transactions.add(t);
                    
                    //Run query 2
                    resolve_query_2();
                }   
            }
        } finally {
            consumer.close();
        }
    }


}
