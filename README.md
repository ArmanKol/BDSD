# Voorbeeld-consumer voor Kafka
De code in deze repo biedt een voorbeeld voor een consumer van Kafka.

Je moet eerst Kafka geïnstalleerd hebben. 

Clone deze repo. De Java-code gaat uit Java versie 7. 

Start eerst Kafka (en Zookeeper) op. Je kunt de consumer draaien dmv:

`mvn package exec:java
`

Het eerste argument (`package`) zorgt er voor dat de code wordt gecompileerd, het tweede argument (`exec:java`) voert het uit.

Als alles goed is, zie je vervolgens de output van de consumer:

```
(base) RAOs-Mac:bdsd19 roelant$ mvn exec:java
[INFO] Scanning for projects...
[WARNING] 
[WARNING] Some problems were encountered while building the effective model for nl.hu:bdsd19:jar:1.0-SNAPSHOT
[WARNING] 'build.pluginManagement.plugins.plugin.(groupId:artifactId)' must be unique but found duplicate declaration of plugin org.apache.maven.plugins:maven-jar-plugin @ line 92, column 17
[WARNING] 
[WARNING] It is highly recommended to fix these problems because they threaten the stability of your build.
[WARNING] 
[WARNING] For this reason, future Maven versions might no longer support building such malformed projects.
[WARNING] 
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] Building bdsd19 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] >>> exec-maven-plugin:1.2.1:java (default-cli) > validate @ bdsd19 >>>
[INFO] 
[INFO] <<< exec-maven-plugin:1.2.1:java (default-cli) < validate @ bdsd19 <<<
[INFO] 
[INFO] 
[INFO] --- exec-maven-plugin:1.2.1:java (default-cli) @ bdsd19 ---
2019-12-19 16:06:50 INFO  ConsumerConfig - ConsumerConfig values: 
	allow.auto.create.topics = true
	auto.commit.interval.ms = 5000
	auto.offset.reset = latest
	bootstrap.servers = [localhost:9092]
	check.crcs = true
	client.dns.lookup = default
	client.id = BDSDKafkaProducer
	client.rack = 
	connections.max.idle.ms = 540000
	default.api.timeout.ms = 60000
	enable.auto.commit = true
	exclude.internal.topics = true
	fetch.max.bytes = 52428800
	fetch.max.wait.ms = 500
	fetch.min.bytes = 1
	group.id = mygroup
	group.instance.id = null
	heartbeat.interval.ms = 3000
	interceptor.classes = []
	internal.leave.group.on.close = true
	isolation.level = read_uncommitted
	key.deserializer = class org.apache.kafka.common.serialization.StringDeserializer
	max.partition.fetch.bytes = 1048576
	max.poll.interval.ms = 300000
	max.poll.records = 500
	metadata.max.age.ms = 300000
	metric.reporters = []
	metrics.num.samples = 2
	metrics.recording.level = INFO
	metrics.sample.window.ms = 30000
	partition.assignment.strategy = [class org.apache.kafka.clients.consumer.RangeAssignor]
	receive.buffer.bytes = 65536
	reconnect.backoff.max.ms = 1000
	reconnect.backoff.ms = 50
	request.timeout.ms = 30000
	retry.backoff.ms = 100
	sasl.client.callback.handler.class = null
	sasl.jaas.config = null
	sasl.kerberos.kinit.cmd = /usr/bin/kinit
	sasl.kerberos.min.time.before.relogin = 60000
	sasl.kerberos.service.name = null
	sasl.kerberos.ticket.renew.jitter = 0.05
	sasl.kerberos.ticket.renew.window.factor = 0.8
	sasl.login.callback.handler.class = null
	sasl.login.class = null
	sasl.login.refresh.buffer.seconds = 300
	sasl.login.refresh.min.period.seconds = 60
	sasl.login.refresh.window.factor = 0.8
	sasl.login.refresh.window.jitter = 0.05
	sasl.mechanism = GSSAPI
	security.protocol = PLAINTEXT
	security.providers = null
	send.buffer.bytes = 131072
	session.timeout.ms = 10000
	ssl.cipher.suites = null
	ssl.enabled.protocols = [TLSv1.2, TLSv1.1, TLSv1]
	ssl.endpoint.identification.algorithm = https
	ssl.key.password = null
	ssl.keymanager.algorithm = SunX509
	ssl.keystore.location = null
	ssl.keystore.password = null
	ssl.keystore.type = JKS
	ssl.protocol = TLS
	ssl.provider = null
	ssl.secure.random.implementation = null
	ssl.trustmanager.algorithm = PKIX
	ssl.truststore.location = null
	ssl.truststore.password = null
	ssl.truststore.type = JKS
	value.deserializer = class org.apache.kafka.common.serialization.StringDeserializer

2019-12-19 16:06:50 WARN  ConsumerConfig - The configuration 'value.serializer' was supplied but isn't a known config.
2019-12-19 16:06:50 WARN  ConsumerConfig - The configuration 'key.serializer' was supplied but isn't a known config.
2019-12-19 16:06:50 INFO  AppInfoParser - Kafka version: 2.4.0
2019-12-19 16:06:50 INFO  AppInfoParser - Kafka commitId: 77a89fcf8d7fa018
2019-12-19 16:06:50 INFO  AppInfoParser - Kafka startTimeMs: 1576768010640
2019-12-19 16:06:50 INFO  KafkaConsumer - [Consumer clientId=BDSDKafkaProducer, groupId=mygroup] Subscribed to topic(s): transactie
2019-12-19 16:06:51 INFO  Metadata - [Consumer clientId=BDSDKafkaProducer, groupId=mygroup] Cluster ID: VgwXs729TA6EA-_KXK-8MQ
2019-12-19 16:06:51 INFO  AbstractCoordinator - [Consumer clientId=BDSDKafkaProducer, groupId=mygroup] Discovered group coordinator raos-mac.fritz.box:9092 (id: 2147483647 rack: null)
2019-12-19 16:06:51 INFO  AbstractCoordinator - [Consumer clientId=BDSDKafkaProducer, groupId=mygroup] (Re-)joining group
2019-12-19 16:06:51 INFO  AbstractCoordinator - [Consumer clientId=BDSDKafkaProducer, groupId=mygroup] (Re-)joining group
2019-12-19 16:06:54 INFO  AbstractCoordinator - [Consumer clientId=BDSDKafkaProducer, groupId=mygroup] Successfully joined group with generation 6
2019-12-19 16:06:54 INFO  ConsumerCoordinator - [Consumer clientId=BDSDKafkaProducer, groupId=mygroup] Adding newly assigned partitions: 
```

De consumer luistert naar topic `transactie`. Om die topic te vullen vanuit de voorbeeld-MySQL-database, kun je de standaard console-producer gebruiken:

`bin/kafka-console-producer.sh --broker-list localhost:9092 --topic transactie --property "parse.key=true" --property "key.separator=: /tmp/output.large`

De producer vraagt vervolgens om een key-value pair: `[klant]:[product id]`. Voorbeeld om klanten 1, 2, en 3 toe te voegen die respectievelijk producten 4, 4 en 5 kochten:

```
(base) RAOs-Mac:kafka_2.12-2.4.0 roelant$ bin/kafka-console-producer.sh --broker-list localhost:9092 --topic transactie --property "parse.key=true" --property "key.separator=:"
>1:4
>2:4
>3:5
>
```

Let op: deze producer maakt in het geheugen structuren aan (zie de class `Transactions`) om het resultaat van de query te berekenen. In het echt schaalt dit natuurlijk niet, en zou je daar een andere Kafka-topic of een andere persistentie-oplossing voor gebruiken. 

