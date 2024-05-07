package ai.maum.mcl.skins.api.apiuser.service;

import ai.maum.mcl.skins.mybatis.mapper.ApiUserMapper;
import ai.maum.mcl.skins.mybatis.vo.ApiUserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiUserService {
    private final ApiUserMapper apiUserMapper;

    public ApiUserVO getApiUser(String vendorId) {
        return apiUserMapper.getApiUserById(vendorId);
    }
}
