package ai.maum.mcl.skins.api.member.service;

import ai.maum.mcl.skins.api.member.mapper.MemberMapper;
import ai.maum.mcl.skins.api.member.model.MemberDetail;
import ai.maum.mcl.skins.api.member.model.MemberSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {
    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

        log.debug("loadUserByUsername!!!");

        MemberDetail member = null;
        try {
            member = memberMapper.findMemberById(Long.valueOf(memberId));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return member;
    }
    public List<MemberSearch> loadUsersList() {

        MemberDetail member = null;
        List<MemberSearch> allMembers = new ArrayList<>();
        try {
            allMembers = memberMapper.findSearchMemberById();
            log.debug(allMembers.toString());

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return allMembers;
    }
}
