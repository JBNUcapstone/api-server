package capstone.smartfarm.repository;

import capstone.smartfarm.model.entity.SoilMoisture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoilMoistureRepository extends JpaRepository<SoilMoisture, Integer> {
}
