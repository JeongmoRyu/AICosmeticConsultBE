package ai.maum.mcl.skins.api.member.mapper;

import ai.maum.mcl.skins.api.member.model.Member;
import ai.maum.mcl.skins.api.member.model.MemberSearch;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberMapper {
    public Member findMemberById(Long id);
    public List<MemberSearch> findSearchMemberById();
    public List<MemberList> findListMemberById(String name, Integer age);

}
