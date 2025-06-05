package capstone.smartfarm.controller;

import capstone.smartfarm.model.dto.AlertRequest;
import capstone.smartfarm.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alert")
public class AlertController {

    private final AlertService alertService;

    @Autowired
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    /**
     * IDS가 보내는 alert를 수신하는 엔드포인트
     *
     * 예시 요청:
     * POST http://localhost:8080/alert
     * Content-Type: application/json
     * {
     *   "type": "DoS",
     *   "timestamp": "2025-06-04T12:34:56.789012",
     *   "details": {
     *     "count_per_sec": 75
     *   }
     * }
     *
     * @param alertRequest 파싱된 AlertRequest DTO
     * @return HTTP 200(OK) 혹은 적절한 에러 상태
     */
    @PostMapping
    public ResponseEntity<String> receiveAlert(@RequestBody AlertRequest alertRequest) {
        // 서비스 레이어에 위임
        alertService.handleAlert(alertRequest);

        // 클라이언트(IDS)에게 OK 응답 반환
        return new ResponseEntity<>("Alert received", HttpStatus.OK);
    }
}