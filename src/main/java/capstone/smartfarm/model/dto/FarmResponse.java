package capstone.smartfarm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class FarmResponse {
    private float value;
    private LocalDateTime measuredAt;
}
