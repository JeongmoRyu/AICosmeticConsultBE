package ai.maum.mcl.skins.mybatis.mapper;

import ai.maum.mcl.skins.mybatis.vo.ApiUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ApiUserMapper {
    public ApiUserVO getApiUserById(String vendorId);
}