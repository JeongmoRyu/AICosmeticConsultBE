package ai.maum.mcl.skins.api.consult.service;

import ai.maum.mcl.skins.api.consult.mapper.ConsultMapper;
import ai.maum.mcl.skins.api.consult.model.*;
import ai.maum.mcl.skins.api.manager.model.Manager;
import jakarta.transaction.Transactional;
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
    public List<ConsultIndirect> getConsultIndirectInfo(String memberId, Integer year, Integer month) {
        Map<String, Object> params = new HashMap<>();
        if (memberId != null) {
            params.put("memberId", memberId);
        }
        if (year != null) {
            params.put("year", year);
        }
        if (month != null) {
            params.put("month", month);
        }

        return consultMapper.getConsultIndirectByUserKey(params);
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
        List<ConsultDirect> consults = consultMapper.getConsultDirectByMemberId(parameters);
        for (ConsultDirect consult : consults) {
            List<ConsultFeature> features = consultMapper.getFeaturesByConsultId(consult.getId());
            consult.setFeatures(features);
        }

        return consults;
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
        List<ConsultDirect> consults = consultMapper.getConsultDirectByMemberId(parameters);
        for (ConsultDirect consult : consults) {
            List<ConsultFeature> features = consultMapper.getFeaturesByConsultId(consult.getId());
            consult.setFeatures(features);
        }

        return consults;
    }

//    public void updateConsultDirect(ConsultDirect consultDirect) {
//        try {
//            log.info("before update: {}, {}", consultDirect.getFeatures(), consultDirect.getFeatureList());
//            consultMapper.updateConsultDirect(consultDirect);
//        } catch (DataAccessException e) {
//            log.error("Database access error occurred while updating", e);
//            throw e;
//        } catch (Exception e) {
//            log.error("Error updating consultation", e);
//            throw e;
//        }
//    }
    public void updateConsultDirect(ConsultDirect consultDirect) {
        consultMapper.updateConsultDirect(consultDirect);
        consultMapper.deleteConsultFeaturesByConsultId(consultDirect.getId());
        for (ConsultFeature feature : consultDirect.getFeatures()) {
            feature.setConsultId(consultDirect.getId());
            consultMapper.insertConsultFeature(feature);
        }
    }
    @Transactional
    public ConsultIndirect registIndirectSummary(ConsultIndirect consultIndirect) {
        log.debug("Attempting to insert consultindirect: {} :", consultIndirect);
        consultIndirect.setUserkey(consultIndirect.getUserkey());
        consultIndirect.setName(consultIndirect.getName());
        consultIndirect.setConsultTime(consultIndirect.getConsultTime());
        consultIndirect.setManager(consultIndirect.getManager());
        consultIndirect.setConsultData(consultIndirect.getConsultData());
        consultIndirect.setSignificant(consultIndirect.getSignificant());
        consultIndirect.setConsultType(consultIndirect.getConsultType());


        consultMapper.insertConsultIndirect(consultIndirect);
        log.debug("consultindirect inserted successfully: {} :", consultIndirect);

        return consultIndirect;
    }

}
