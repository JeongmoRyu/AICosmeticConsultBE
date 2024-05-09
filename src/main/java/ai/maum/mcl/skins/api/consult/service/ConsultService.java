package ai.maum.mcl.skins.api.consult.service;

import ai.maum.mcl.skins.api.consult.mapper.ConsultMapper;
import ai.maum.mcl.skins.api.consult.model.ConsultInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultService {
    private final ConsultMapper consultMapper;
    public List<ConsultInfo> getConsultInfoByUserKey(Long userKey) {
        return consultMapper.getConsultInfoByUserKey(userKey);
    }
}
