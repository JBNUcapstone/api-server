package capstone.smartfarm.component;

import capstone.smartfarm.model.entity.Humidity;
import capstone.smartfarm.model.entity.SoilMoisture;
import capstone.smartfarm.model.entity.Temperature;
import capstone.smartfarm.repository.HumidityRepository;
import capstone.smartfarm.repository.SoilMoistureRepository;
import capstone.smartfarm.repository.TemperatureRepository;
import capstone.smartfarm.service.SSEService;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FarmSubscriber {

    @Autowired
    private HumidityRepository humidityRepository;
    @Autowired
    private SoilMoistureRepository soilMoistureRepository;
    @Autowired
    private TemperatureRepository temperatureRepository;
    @Autowired
    private SSEService sseService;

    private static final String CLIENT_ID = MqttClient.generateClientId();

    @Value("${SPRING_BROKER_URL}")
    private String BROKER_URL;
    @Value("${HIVE_CLUSTER_PASSWORD}")
    private String password;
    @Value("${HIVE_CLUSTER_USERNAME}")
    private String username;

    @PostConstruct
    public void start() {
        try {
            String persistenceDir = "/mqttlogs";
            MqttClient client = new MqttClient(BROKER_URL, CLIENT_ID, new MqttDefaultFilePersistence(persistenceDir)); //클라이언트 객체 생성

            MqttConnectOptions options = new MqttConnectOptions(); //접속에 필요한 정보를 적을 option 객체 생성
            options.setUserName(username); // HiveMQ 클라우드 콘솔에서 설정한 계정
            options.setPassword(password.toCharArray());

            client.connect(options); //해당 option을 통해 특정 broker 연결
            System.out.println("Hive MQ 에서 생성된 브로커에 연결 완료 client id: " + CLIENT_ID);

            client.subscribe("farm/humidity", (topic, message) -> {
                String payload = new String(message.getPayload());// 클라이언트 전송
                sseService.sendToClient("humidity","humidity_data",payload);// DB에 저장
                humidityRepository.save(new Humidity(Float.parseFloat(payload)));
            });

            client.subscribe("farm/soilMoisture", (topic, message) -> {
                String payload = new String(message.getPayload());
                sseService.sendToClient("soilMoisture","soilMoisture_data",payload);
                soilMoistureRepository.save(new SoilMoisture(Float.parseFloat(payload)));
            });

            client.subscribe("farm/temperature", (topic, message) -> {
                String payload = new String(message.getPayload());
                sseService.sendToClient("temperature","temperature",payload);
                temperatureRepository.save(new Temperature(Float.parseFloat(payload)));
            });
        } catch (MqttException e) {
            System.err.println("연결실패" + e.getMessage());
            e.printStackTrace();
        }
    }
}
