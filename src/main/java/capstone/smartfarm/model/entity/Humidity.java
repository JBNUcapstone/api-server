package capstone.smartfarm.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "humidity_data", indexes = {
        @Index(name = "idx_humidity_measured_at", columnList = "measuredAt")
})// 시간으로 조회 빈번하므로 인덱싱
public class Humidity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private float value;

    @Column(nullable = false)
    private LocalDateTime measuredAt = LocalDateTime.now();

    public Humidity() {}

    public Humidity(float value) {
        this.value = value;
    }
    public void updateValue(float value) {
        this.value = value;
    }
}

