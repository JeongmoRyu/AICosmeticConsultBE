package ai.maum.mcl.skins.api.consult.mapper;

import ai.maum.mcl.skins.api.consult.model.*;
import ai.maum.mcl.skins.api.manager.model.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ConsultMapper {
//    public MemberDetail findMemberById(Long id);
    public List<ConsultInfo> getConsultInfoByUserKey(Long userKey);
    public List<ConsultIndirect> getConsultIndirectByUserKey(Map<String, Object> params);
    public List<ConsultDirect> getConsultDirectByMemberId(Map<String, Object> params);
    public  List<ConsultFeature> getFeaturesByConsultId(Long consultId);


    public List<SignificantGroup> getConsultSignificantGroup();

    void updateConsultDirect(ConsultDirect consultDirect);
    void deleteConsultFeaturesByConsultId(Long consultId);
    void insertConsultFeature(ConsultFeature consultFeature);
    public void insertConsultIndirect(ConsultIndirect consultIndirect);


}
