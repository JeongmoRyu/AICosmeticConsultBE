package ai.maum.mcl.skins.api.temptest.service;

import ai.maum.mcl.skins.api.temptest.mapper.TempTestMapper;
import ai.maum.mcl.skins.api.temptest.model.TempTestInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TempTestService {
    private final TempTestMapper tempTestMapper;
    public List<TempTestInfo> getTempTestInfoByUser(Long userKey) {
        return tempTestMapper.getTempTestInfoByUser(userKey);
    }
}
