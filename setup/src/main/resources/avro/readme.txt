Generate schema from avdl (avro domain language) using avro tools:
java -jar C:\Users\e-tzkk\.m2\repository\org\apache\avro\avro-tools\1.9.1\avro-tools-1.9.1.jar idl2schemata  .\HttpRequestEvent.avdl .

docker run -it --network=host edenhill/kafkacat:1.5.0 -b kafka1 -t http-request-events -s value=avro -r http://kafka1.ojejek.com:8081

docker run --interactive --rm \
        confluentinc/cp-kafkacat \
        kafkacat -b kafka1:19092 \
                -t rest2 \
                -K: \
                -P <<EOF