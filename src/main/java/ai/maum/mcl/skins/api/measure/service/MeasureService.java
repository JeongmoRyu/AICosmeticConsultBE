package ai.maum.mcl.skins.api.measure.service;

import ai.maum.mcl.skins.api.measure.mapper.MeasureMapper;
import ai.maum.mcl.skins.api.measure.model.MeasureInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeasureService {
    private final MeasureMapper measureMapper;
    public List<MeasureInfo> getMeasureInfoByUserKey(Long userKey) {
        return measureMapper.getMeasureInfoByUserKey(userKey);
    }
}
