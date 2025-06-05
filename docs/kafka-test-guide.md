# Kafka 기본 기능 테스트 가이드

Kafka 토픽 생성 및 메시지 송수신 과정을 단계별로 안내합니다.

---

## 1. Kafka 컨테이너에 접속

실행 중인 Kafka 컨테이너 내부로 진입합니다.

```bash
$ docker exec -it KafkaChat-kafka bash
```

---

## 2. 토픽 생성

컨테이너 쉘에서 아래 명령어로 `topic-test` 토픽을 생성합니다.

```bash
$ kafka-topics.sh --bootstrap-server localhost:9092 --create --topic topic-test --partitions 1 --replication-factor 1
```

---

## 3. 토픽 메시지 소비 \(Consumer 실행\)

새로운 터미널을 열고, 컨테이너에 접속하지 않고 바로 아래 명령어를 실행합니다.

```bash
$ docker exec -it KafkaChat-kafka kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic-test --from-beginning
```

---

## 4. 메시지 발행 \(Producer 실행\)

1번에서 접속한 컨테이너 쉘에서 아래 명령어를 실행합니다.

```bash
$ kafka-console-producer.sh --bootstrap-server localhost:9092 --topic topic-test
```

메시지를 입력하고 Enter를 누르면 해당 메시지가 `topic-test` 토픽으로 전송됩니다.

---
