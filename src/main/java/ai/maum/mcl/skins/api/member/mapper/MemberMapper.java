package ai.maum.mcl.skins.api.member.mapper;

import ai.maum.mcl.skins.api.member.model.MemberSearch;
import ai.maum.mcl.skins.api.member.model.MemberDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberMapper {
    public MemberDetail findMemberById(Long id);
    // public MemberSearch findAllMember();
}
