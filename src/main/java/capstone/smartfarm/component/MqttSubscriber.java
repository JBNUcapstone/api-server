//package capstone.smartfarm.component;
//
//import capstone.smartfarm.model.entity.Temperature;
//import capstone.smartfarm.repository.TemperatureRepository;
//import jakarta.annotation.PostConstruct;
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MqttSubscriber {
//
//    @Autowired
//    private TemperatureRepository tempRepo;
//
//    @PostConstruct
//    public void init() throws MqttException {
//        //mqtt 커넥션
//        String brokerUrl = "tcp://localhost:1883"; //라즈베리파이상에 배포되었다 가정
//        String clientId = MqttClient.generateClientId();
//        MqttClient client = new MqttClient(brokerUrl, clientId);
//        client.connect();
//
//        //특정 topic 구독
//        client.subscribe("sensor/temperature", (topic, message) -> {
//            float value = Float.parseFloat(new String(message.getPayload()));
//            Temperature data = new Temperature();
//            data.updateValue(value);
//            tempRepo.save(data);//데이터베이스에 저장
//            //SSE로도 전송 로직
//        });
//    }
//}
