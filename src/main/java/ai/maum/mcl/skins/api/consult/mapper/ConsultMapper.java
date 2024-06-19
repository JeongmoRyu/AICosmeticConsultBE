package ai.maum.mcl.skins.api.consult.mapper;

import ai.maum.mcl.skins.api.consult.model.ConsultIndirect;
import ai.maum.mcl.skins.api.consult.model.ConsultInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ConsultMapper {
  //    public MemberDetail findMemberById(Long id);
  public List<ConsultInfo> getConsultInfoByUserKey(Long userKey);
  public List<ConsultDirect> getConsultDirectByMemberId(Map<String, Long> params);

  public List<ConsultIndirect> getConsultIndirectByUserKey(Map<String, Object> params);
  public List<SignificantGroup> getConsultSignificantGroup();
  void updateConsultDirect(ConsultDirect consultDirect);

  public void insertConsultIndirect(ConsultIndirect consultIndirect);
  public  List<ConsultDirect> getConsultDirectByMemberId(Map<String, Object> params);
  public  List<Feature> getFeaturesByConsultId(Long consultId);
}
