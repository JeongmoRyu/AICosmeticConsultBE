package ai.maum.mcl.skins.api.consult.service;

import ai.maum.mcl.skins.api.common.BaseResponse;
import ai.maum.mcl.skins.api.consult.mapper.ConsultMapper;
import ai.maum.mcl.skins.api.consult.model.ConsultIndirect;
import ai.maum.mcl.skins.api.consult.model.ConsultInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        log.info("memberId: {}", memberId);

        return consultMapper.getConsultDirectByMemberId(memberId);
    }


    public void updateConsultDirect(ConsultDirect consultDirect, Long memberId, Long consultNumber) {
        try {
            consultDirectMapper.updateConsultDirect(consultDirect, memberId, consultNumber);
        } catch (DataAccessException e) {
            logger.error("Database access error occurred while updating", e);
            throw e;
        } catch (Exception e) {
            logger.error("Error updating consultation", e);
            throw e;
        }
    }
//    public List<ConsultInfo> getConsultInfoByUserKey(Long userKey, Integer consultNumber) {
//        List<ConsultInfo> consultList = new ArrayList<>();
//        List<ConsultInfo> tempList = consultMapper.getConsultInfoByUserKey(userKey);
//        if (consultNumber != null) {
//            if (consultNumber <= tempList.size()) {
//                log.debug(String.valueOf(tempList.size()));
//                consultList = tempList.subList(consultNumber, consultNumber + 1);;
//            } else {
//                BaseResponse.failure("차수보다 큰 숫자를 선택하셨습니다.");
//            }
//        } else {
//            consultList = tempList;
//        }
//        return consultList;
//    }
}
