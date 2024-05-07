package ai.maum.mcl.skins.mybatis.mapper;

import ai.maum.mcl.skins.mybatis.vo.MemberDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberMapper {
    public MemberDetailVO findMemberById(Long id);
}
