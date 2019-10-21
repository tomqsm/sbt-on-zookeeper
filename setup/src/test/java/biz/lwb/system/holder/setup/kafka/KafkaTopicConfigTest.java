package biz.lwb.system.holder.setup.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ContextConfiguration(classes = SetupApp.class)
public class KafkaTopicConfigTest {

    @Test
    public void kafkaConsumerTest() {
        Logger log = LoggerFactory.getLogger(KafkaTopicConfigTest.class);
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka2.ojejek.com:9092,kafka3.ojejek.com:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "idea2");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // equivalent of --from-begginning in cli

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.subscribe(List.of("test2"));

        while(true){
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(1000));
            for(ConsumerRecord r : records){
                log.info("key: {}, value: {}", r.key(), r.value());
                log.info("partition: {}", r.partition());
            }

        }
    }

    @Test
    public void kafkaProducerTest(){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1.ojejek.com:9092,kafka2.ojejek.com:9092,kafka3.ojejek.com:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        ProducerRecord<String, String> producerRecord = new ProducerRecord("test2", "to jest posłane z Java 33");
        producer.send(producerRecord);

        //flush and close producer
        producer.close();
    }
}