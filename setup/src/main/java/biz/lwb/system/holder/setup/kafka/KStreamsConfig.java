package biz.lwb.system.holder.setup.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.processor.FailOnInvalidTimestamp;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.apache.commons.lang.StringUtils.contains;

@Configuration
public class KStreamsConfig {

    @Value("${delivery-stats.stream.threads:1}")
    private int threads;

    @Value("${delivery-stats.kafka.replication-factor:1}")
    private int replicationFactor;

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String brokersUrl;


    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public StreamsConfig kStreamsConfigs() {
        Map<String, Object> config = new HashMap<>();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "default");
        setDefaults(config);
        return new StreamsConfig(config);
    }


    public void setDefaults(Map<String, Object> config) {
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, brokersUrl);
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, FailOnInvalidTimestamp.class);
    }

    @Bean("app1StreamBuilder")
    public StreamsBuilderFactoryBean app1StreamBuilderFactoryBean() {
        Map<String, Object> config = new HashMap<>();
        setDefaults(config);
        config.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "app1");
        config.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 30000);
        config.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, threads);
        config.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, replicationFactor);
        return new StreamsBuilderFactoryBean(new KafkaStreamsConfiguration(config));

    }

    @Bean("app2StreamBuilder")
    public StreamsBuilderFactoryBean app2StreamBuilderFactoryBean() {
        Map<String, Object> config = new HashMap<>();
        setDefaults(config);
        config.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "app2");
        config.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 30000);
        config.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, threads);
        config.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, replicationFactor);
        return new StreamsBuilderFactoryBean(new KafkaStreamsConfiguration(config));
    }

    @SuppressWarnings("unchecked")
    @Bean("app1StreamTopology")
    public KStream<String, String> startProcessing(@Qualifier("app1StreamBuilder") StreamsBuilder builder) {

        KStream<String, String> kStream = builder.stream("test2", Consumed.with(Serdes.String(), Serdes.String()));
        kStream.selectKey((key, value) -> value.split(" ")[0])
                .filter((s, s2) -> s2 != null && contains(s2, "ERROR"))
                .map((key, value) -> {

                    int dummyValue = new Random().nextInt();
                    KeyValue<String, String> pair = new KeyValue<>(key, value);

                    if (key == null) {
                        pair = KeyValue.pair(dummyValue + key, value);
                    }

                    return KeyValue.pair(key, value);
                }).to("log-error", Produced.with(Serdes.String(), Serdes.String())); // send downstream to another topic

//        builder.table("log-error")
        return kStream;
    }
}
