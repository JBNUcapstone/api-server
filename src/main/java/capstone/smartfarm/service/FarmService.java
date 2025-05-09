package capstone.smartfarm.service;

import capstone.smartfarm.model.dto.FarmRequest;
import capstone.smartfarm.model.dto.FarmResponse;
import capstone.smartfarm.model.entity.Humidity;
import capstone.smartfarm.model.entity.SoilMoisture;
import capstone.smartfarm.model.entity.Temperature;
import capstone.smartfarm.repository.HumidityRepository;
import capstone.smartfarm.repository.SoilMoistureRepository;
import capstone.smartfarm.repository.TemperatureRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FarmService {

    private final HumidityRepository humidityRepository;
    private final SoilMoistureRepository soilMoistureRepository;
    private final TemperatureRepository temperatureRepository;

    public Page<FarmResponse> selectHumidity(FarmRequest farmRequest) {
        // 기본 정렬: measuredAt 기준, 최신순
        Sort.Direction direction = "asc".equalsIgnoreCase(farmRequest.getSort())
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(
                farmRequest.getPage(),
                farmRequest.getSize(),
                Sort.by(direction, "measuredAt")
        );

        Page<Humidity> humidities;

        //기간 선택 없으면 전체조회
        if (farmRequest.getStartTime() != null && farmRequest.getEndTime() != null) {
            humidities = humidityRepository.findAllByMeasuredAtBetween(
                    farmRequest.getStartTime(),
                    farmRequest.getEndTime(),
                    pageable
            );
        } else {
            humidities = humidityRepository.findAll(pageable);
        }

        //Entity to DTO
        return humidities.map(h -> new FarmResponse(
                h.getValue(),
                h.getMeasuredAt()
        ));
    }

    public Page<FarmResponse> selectTemperature(FarmRequest farmRequest) {
        Sort.Direction direction = "asc".equalsIgnoreCase(farmRequest.getSort())
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(farmRequest.getPage(), farmRequest.getSize(), Sort.by(direction, "measuredAt"));
        Page<Temperature> tempData;

        if (farmRequest.getStartTime() != null && farmRequest.getEndTime() != null) {
            tempData = temperatureRepository.findAllByMeasuredAtBetween(farmRequest.getStartTime(), farmRequest.getEndTime(), pageable);
        } else {
            tempData = temperatureRepository.findAll(pageable);
        }

        return tempData.map(t -> new FarmResponse(t.getValue(), t.getMeasuredAt()));
    }

    public Page<FarmResponse> selectSoilMoisture(FarmRequest farmRequest) {
        Sort.Direction direction = "asc".equalsIgnoreCase(farmRequest.getSort())
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(farmRequest.getPage(), farmRequest.getSize(), Sort.by(direction, "measuredAt"));
        Page<SoilMoisture> soilData;

        if (farmRequest.getStartTime() != null && farmRequest.getEndTime() != null) {
            soilData = soilMoistureRepository.findAllByMeasuredAtBetween(farmRequest.getStartTime(), farmRequest.getEndTime(), pageable);
        } else {
            soilData = soilMoistureRepository.findAll(pageable);
        }

        return soilData.map(s -> new FarmResponse(s.getValue(), s.getMeasuredAt()));
    }
}
