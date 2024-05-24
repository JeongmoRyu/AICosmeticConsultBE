package ai.maum.mcl.skins.api.member.service;

import ai.maum.mcl.skins.api.member.mapper.MemberMapper;
import ai.maum.mcl.skins.api.member.model.Member;
import ai.maum.mcl.skins.api.member.model.MemberSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;

    public Member getMemberById(Long memberId) {
        return memberMapper.findMemberById(memberId);
    }

    public List<MemberSearch> loadUsersList() {

        List<MemberSearch> allMembers = new ArrayList<>();
        try {
            allMembers = memberMapper.findSearchMemberById();

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return allMembers;
    }
    public List<MemberList> loadUsersListByNameOrAge(@RequestParam String name, @RequestParam Integer age) {
        List<MemberList> allMembers = new ArrayList<>();
        try {
            allMembers = memberMapper.findListMemberById(name, age);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return allMembers;
    }
}
