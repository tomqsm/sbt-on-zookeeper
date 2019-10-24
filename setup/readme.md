## Schema

### Schema from avdl (avro domain language) using avro tools

`java -jar C:\Users\e-tzkk\.m2\repository\org\apache\avro\avro-tools\1.9.1\avro-tools-1.9.1.jar idl2schemata  .\HttpRequestEvent.avdl .`

Schema naming in the Schema-Registry maps to an object name the following way: http-request-event == HttpRequestEvent. 

### Delete schmema from registry

#### Deletes the most recently registered schema under name "Kafka-value"
`curl -X DELETE http://localhost:8081/subjects/Kafka-value/versions/latest`

## kafkacat

### kafkacat example

`docker run -it --network=host edenhill/kafkacat:1.5.0 -b kafka1 -t http-request-events -s value=avro -r http://kafka1:8081`

