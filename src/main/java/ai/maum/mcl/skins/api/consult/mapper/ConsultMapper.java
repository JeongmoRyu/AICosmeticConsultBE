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
public List<ConsultDirect> getConsultDirectByMemberId(Long memberId);

public List<ConsultIndirect> getConsultIndirectInfo(Long id);
public List<SignificantGroup> getConsultSignificantGroup();

}
