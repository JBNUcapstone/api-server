package capstone.smartfarm.model.dto;

import java.util.HashMap;
import java.util.Map;

public class AlertRequest {
    //탐지된 위협 종류 ("DoS", "Replay", "DataInjection_InvalidJSON", "UnauthorizedTopic", 등)
    private String type;

    //탐지 시각(ISO8601 형식의 문자열)
    private String timestamp;

    //추가 세부 정보(키-값 쌍으로 유동적인 필드를 담기 위해 Map으로 선언)
    private Map<String, Object> details = new HashMap<>();

    @Override
    public String toString() {
        return "AlertRequest{" +
                "type='" + type + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", details=" + details +
                '}';
    }
}