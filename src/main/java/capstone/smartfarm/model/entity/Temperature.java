package capstone.smartfarm.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "temperature_data", indexes = {
        @Index(name = "idx_temperature_measured_at", columnList = "measuredAt")
})// 시간으로 조회 빈번하므로 인덱싱
public class Temperature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private float value;

    @Column(nullable = false)
    private LocalDateTime measuredAt = LocalDateTime.now();

    public Temperature() {}

    public Temperature(float value) {
        this.value = value;
    }

    public void updateValue(float value) {
        this.value = value;
    }
}
