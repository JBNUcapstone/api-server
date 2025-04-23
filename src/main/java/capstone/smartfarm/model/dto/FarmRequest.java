package capstone.smartfarm.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FarmRequest {
    @Schema(description = "페이지 사이즈", example = "10")
    private Integer size;
    @Schema(description = "페이지 오프셋", example = "0")
    private Integer page;
    @Schema(description = "날짜 정렬 타입 오름차순(asc)/내림차순(desc)", example = "desc")
    private String sort="desc";
    @Schema(description = "시간 필터링,널값이면 전체조회")
    private LocalDateTime startTime;//조회 시작
    @Schema(description = "시간 필터링,널값이면 전체조회")
    private LocalDateTime endTime;//조회 끝
}
