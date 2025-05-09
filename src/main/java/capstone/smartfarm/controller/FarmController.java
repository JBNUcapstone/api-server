package capstone.smartfarm.controller;

import capstone.smartfarm.model.dto.FarmRequest;
import capstone.smartfarm.model.dto.FarmResponse;
import capstone.smartfarm.service.FarmService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*") // 또는 프론트 호스트 명시
public class FarmController {

    @Autowired
    private FarmService farmService;

    @PostMapping("/farm/humidity")
    @Operation(description = "startTime,endtime 생략하면 전체조회 정렬타입은 acs,desc 중 하나")
    public Page<FarmResponse> getHumidity(@RequestBody FarmRequest request) {
        Page<FarmResponse> farmResponses = farmService.selectHumidity(request);
        return farmResponses;
    }

    @PostMapping("/farm/soil")
    @Operation(description = "토양 습도 조회 API (startTime, endTime 생략시 전체)")
    public Page<FarmResponse> getSoilMoisture(@RequestBody FarmRequest request) {
        return farmService.selectSoilMoisture(request);
    }

    @PostMapping("/farm/temperature")
    @Operation(description = "온도 조회 API (startTime, endTime 생략시 전체)")
    public Page<FarmResponse> getTemperature(@RequestBody FarmRequest request) {
        return farmService.selectTemperature(request);
    }
}
