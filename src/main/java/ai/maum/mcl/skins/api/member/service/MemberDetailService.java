package ai.maum.mcl.skins.api.member.service;

import ai.maum.mcl.skins.mybatis.mapper.MemberMapper;
import ai.maum.mcl.skins.mybatis.vo.MemberDetailVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {
    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

        log.debug("loadUserByUsername!!!");

        MemberDetailVO member = null;
        try {
            member = memberMapper.findMemberById(Long.valueOf(memberId));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return member;
    }
}
