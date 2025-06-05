package capstone.smartfarm.service;

import capstone.smartfarm.model.dto.AlertRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AlertService {
    private static final Logger logger = LoggerFactory.getLogger(AlertService.class);

    /**
     * IDS에서 전송한 alert를 처리한다.
     * 현재는 단순히 로그로 남기도록 구현.
     * 필요에 따라 DB 저장, 알림 전송, 통계 집계 등으로 확장 가능.
     *
     * @param alertRequest IDS가 보낸 AlertRequest DTO
     */
    public void handleAlert(AlertRequest alertRequest) {
        // 1. 로그로 출력
        logger.warn("Received IDS Alert: {}", alertRequest);

        // 2. 실제 비즈니스 로직
        //    - DB에 기록 (JPA / MyBatis 등)
        //    - 관리자에게 이메일·Slack 알림 발송
        //    - SIEM/로그 수집 시스템으로 전송
        //    - UI 대시보드 업데이트용 Redis 캐시 적재 등
    }
}