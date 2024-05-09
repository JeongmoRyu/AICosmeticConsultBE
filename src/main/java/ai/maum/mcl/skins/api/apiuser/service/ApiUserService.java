package ai.maum.mcl.skins.api.apiuser.service;

import ai.maum.mcl.skins.api.apiuser.mapper.ApiUserMapper;
import ai.maum.mcl.skins.api.apiuser.model.ApiUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiUserService {
    private final ApiUserMapper apiUserMapper;

    public ApiUser getApiUser(String vendorId) {
        return apiUserMapper.getApiUserById(vendorId);
    }
}
