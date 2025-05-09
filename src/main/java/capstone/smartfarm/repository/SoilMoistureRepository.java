package capstone.smartfarm.repository;

import capstone.smartfarm.model.entity.SoilMoisture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface SoilMoistureRepository extends JpaRepository<SoilMoisture, Integer> {
    Page<SoilMoisture> findAllByMeasuredAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
