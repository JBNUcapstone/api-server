package capstone.smartfarm.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "soil_moisture_data", indexes = {
        @Index(name = "idx_soil_moisture_measured_at", columnList = "measuredAt")
})// 시간으로 조회 빈번하므로 인덱싱
public class SoilMoisture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private float value;

    @Column(nullable = false)
    private LocalDateTime measuredAt = LocalDateTime.now();

    public void updateValue(float value) {
        this.value = value;
    }
}
