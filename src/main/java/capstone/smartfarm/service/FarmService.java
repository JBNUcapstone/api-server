package capstone.smartfarm.service;

import capstone.smartfarm.model.dto.FarmRequest;
import capstone.smartfarm.model.dto.FarmResponse;
import capstone.smartfarm.model.entity.Humidity;
import capstone.smartfarm.repository.HumidityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FarmService {

    @Autowired
    private HumidityRepository humidityRepository;

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
}
