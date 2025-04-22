package capstone.smartfarm.component;

import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MqttSubscriberTest {

    @Value("${SPRING_BROKER_URL}")
    private String BROKER_URL;

    @Value("${HIVE_CLUSTER_PASSWORD}")
    private String password;

    @Value("${HIVE_CLUSTER_USERNAME}")
    private String username;

    private static final String TOPIC = "topic/spring";
    private static final String CLIENT_ID = MqttClient.generateClientId();

    @PostConstruct
    public void start() {
        try {

            String persistenceDir = "/mqttlogs";
            MqttClient client = new MqttClient(BROKER_URL, CLIENT_ID, new MqttDefaultFilePersistence(persistenceDir)); //클라이언트 객체 생성

            MqttConnectOptions options = new MqttConnectOptions(); //접속에 필요한 정보를 적을 option 객체 생성
            options.setUserName(username); // HiveMQ 클라우드 콘솔에서 설정한 계정
            options.setPassword(password.toCharArray());

            client.connect(options); //해당 option을 통해 특정 broker 연결
            System.out.println("Hive MQ 에서 생성된 브로커에 연결 완료");

            client.subscribe(TOPIC, (topic, message) -> {
                String payload = new String(message.getPayload());
                System.out.printf("메시지 받았음 Topic: %s, Payload: %s%n", topic, payload);
                // TODO: 데이터 저장 및 SSE 전송 등 추가 처리 가능
            });
        } catch (MqttException e) {
            System.err.println("연결실패" + e.getMessage());
            e.printStackTrace();
        }
    }
}
