package capstone.smartfarm.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SseSendRequest {
    private String eventName;
    private Object data;
}
