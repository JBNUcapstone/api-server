package capstone.smartfarm.controller;

import capstone.smartfarm.model.dto.SseSendRequest;
import capstone.smartfarm.service.SSEService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 또는 프론트 호스트 명시
public class SSEController {
    private final SSEService sseService;

    @GetMapping("/subscribe/{id}")
    @Operation(description = "특정 id에 대한 sseEmiiter 반환, SSE 채널 id: temperature/humidity/soilMoisture")
    public SseEmitter subscribe(@PathVariable String id) {
        return sseService.subscribe(id);
    }

    @PostMapping("/send/{id}")
    public void sendAlarm(@PathVariable String id, @RequestBody SseSendRequest sseSendRequest) {
        sseService.sendToClient(id, sseSendRequest.getEventName(), sseSendRequest.getData());
    }
}