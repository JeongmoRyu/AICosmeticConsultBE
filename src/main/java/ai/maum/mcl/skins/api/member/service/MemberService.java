package ai.maum.mcl.skins.api.member.service;

import ai.maum.mcl.skins.api.member.mapper.MemberMapper;
import ai.maum.mcl.skins.api.member.model.MemberDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {
    private final MemberMapper memberMapper;

    
    public List<MemberDetail> getAllMembers() {
        return memberMapper.findAllMembers();
    }

    public List<ConsultInfo> getConsultInfoByUserKey(Long userKey) {
        return consultService.getConsultInfoByUserKey(userKey);
    }
}
