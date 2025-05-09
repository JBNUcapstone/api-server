package capstone.smartfarm.repository;

import capstone.smartfarm.model.entity.Temperature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TemperatureRepository extends JpaRepository<Temperature, Integer> {
    Page<Temperature> findAllByMeasuredAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}