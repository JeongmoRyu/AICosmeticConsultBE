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
    public List<MemberSearch> findListMemberById(String order);
    public List<MemberList> findListMemberByName(String name, String order);

    public void insertChatTime(Member member);

}

