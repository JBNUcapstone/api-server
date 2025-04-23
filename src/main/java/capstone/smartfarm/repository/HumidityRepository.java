package capstone.smartfarm.repository;

import capstone.smartfarm.model.entity.Humidity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HumidityRepository extends JpaRepository<Humidity, Integer> {
    Page<Humidity> findAllByMeasuredAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
