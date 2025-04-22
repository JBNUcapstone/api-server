package capstone.smartfarm.repository;

import capstone.smartfarm.model.entity.Temperature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemperatureRepository extends JpaRepository<Temperature, Integer> {

}