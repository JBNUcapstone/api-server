package capstone.smartfarm.repository;

import capstone.smartfarm.model.entity.Humidity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HumidityRepository extends JpaRepository<Humidity, Integer> {
}
