package ai.maum.mcl.skins.api.apiuser.mapper;

import ai.maum.mcl.skins.api.apiuser.model.ApiUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ApiUserMapper {
    public ApiUser getApiUserById(String vendorId);
}
