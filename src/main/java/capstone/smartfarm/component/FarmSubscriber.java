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
            MqttClient client = new MqttClient(BROKER_URL, CLIENT_ID, new MqttDefaultFilePersistence(persistenceDir));

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());

            client.connect(options);
            System.out.println("Hive MQ 에서 생성된 브로커에 연결 완료 client id: " + CLIENT_ID);

            client.subscribe("farm/humidity", (topic, message) -> {
                try {
                    String payload = new String(message.getPayload());
                    System.out.println(payload);
                    humidityRepository.save(new Humidity(Float.parseFloat(payload)));
                    //sseService.sendToClient("humidity", "humidity_data", payload);
                } catch (Exception e) {
                    System.err.println("farm/humidity 메시지 처리 중 오류: " + e.getMessage());
                    e.printStackTrace();
                }
            });

            client.subscribe("farm/soilMoisture", (topic, message) -> {
                try {
                    String payload = new String(message.getPayload());
                    System.out.println(payload);
                    soilMoistureRepository.save(new SoilMoisture(Float.parseFloat(payload)));
                    //sseService.sendToClient("soilMoisture", "soilMoisture_data", payload);
                } catch (Exception e) {
                    System.err.println("farm/soilMoisture 메시지 처리 중 오류: " + e.getMessage());
                    e.printStackTrace();
                }
            });

            client.subscribe("farm/temperature", (topic, message) -> {
                try {
                    String payload = new String(message.getPayload());
                    System.out.println(payload);
                    temperatureRepository.save(new Temperature(Float.parseFloat(payload)));
                    //sseService.sendToClient("temperature", "temperature_data", payload);
                } catch (Exception e) {
                    System.err.println("farm/temperature 메시지 처리 중 오류: " + e.getMessage());
                    e.printStackTrace();
                }
            });
        } catch (MqttException e) {
            System.err.println("MQTT 연결 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
