package ai.maum.mcl.skins.api.consult.service;

import ai.maum.mcl.skins.api.consult.mapper.ConsultMapper;
import ai.maum.mcl.skins.api.consult.model.*;
import ai.maum.mcl.skins.api.manager.model.Manager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultService {
    private final ConsultMapper consultMapper;
    public List<ConsultIndirect> getConsultIndirectInfo(Long id) {
        return consultMapper.getConsultIndirectInfo(id);
    }
    public List<ConsultInfo> getConsultInfoByUserKey(Long userKey) {
        return consultMapper.getConsultInfoByUserKey(userKey);
    }

    public List<SignificantGroup> getSignificantGroup() {
        return consultMapper.getConsultSignificantGroup();
    }

    public List<ConsultDirect> getConsultDirectByMemberId(Long memberId) {
        Map<String, Object> parameters = new HashMap<>();
        if (memberId != null) {
            parameters.put("memberId", memberId);
        }
        log.info("parameters: {}",parameters);

        return consultMapper.getConsultDirectByMemberId(parameters);
    }
    public List<ConsultDirect> getConsultDirectByConsultNumber(Long memberId, Long consultNumber) {
        Map<String, Object> parameters = new HashMap<>();
        if (memberId != null) {
            parameters.put("memberId", memberId);
        }
        if (consultNumber != null) {
            parameters.put("consultNumber", consultNumber);
        }
        log.info("parameters: {}",parameters);
        return consultMapper.getConsultDirectByMemberId(parameters);
    }

    public void updateConsultDirect(ConsultDirect consultDirect) {
        try {
            log.info("before update: {}, {}", consultDirect.getFeatures(), consultDirect.getFeatureList());
            consultMapper.updateConsultDirect(consultDirect);
        } catch (DataAccessException e) {
            log.error("Database access error occurred while updating", e);
            throw e;
        } catch (Exception e) {
            log.error("Error updating consultation", e);
            throw e;
        }
    }

    public ConsultIndirect registIndirectSummary(ConsultIndirect consultIndirect) {
        consultMapper.insertConsultIndirect(consultIndirect);
        return consultIndirect;
    }

}
