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

    // void updateChatTime(@Param("memberId") Long memberId, @Param("chatUpdated") Timestamp chatUpdated);
    void updateChatTime(@Param("memberId") Long memberId, @Param("chatUpdated") Timestamp chatUpdated);
    public List<MemberList> findListMemberById(Map<String, Object> params);

}

