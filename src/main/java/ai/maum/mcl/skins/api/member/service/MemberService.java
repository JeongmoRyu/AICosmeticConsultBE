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

    public List<MemberList> loadUsersList(String order) {

        List<MemberList> allMembers = new ArrayList<>();
        try {
            allMembers = memberMapper.findSearchMemberById(order);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return allMembers;
    }
    public List<MemberList> loadUsersListByName(@RequestParam String name, @RequestParam String order) {
        List<MemberList> allMembers = new ArrayList<>();
        try {
            allMembers = memberMapper.findListMemberByName(name, order);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return allMembers;
    }
    public List<MemberList> loadUsersList(String order, String name, Integer age) {
        Map<String, Object> params = new HashMap<>();
        params.put("order", order);
        params.put("name", name);
        params.put("age", age);

        List<MemberList> allMembers = new ArrayList<>();
        try {
            allMembers = memberMapper.findListMemberById(params);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return allMembers;
    }

    public List<MemberList> loadUsersList(String orderBy, String order, String name, Integer age) {
        Map<String, Object> params = new HashMap<>();
        if (orderBy != null) {
            params.put("orderBy", orderBy);
        }
        if (order != null) {
            params.put("order", order);
        }
        if (name != null) {
            params.put("name", name);
        }
        if (age != null) {
            params.put("age", age);
        }
//        params.put("orderBy", orderBy);

//        params.put("order", order);
//        params.put("name", name);
//        params.put("age", age);

        List<MemberList> allMembers = new ArrayList<>();
        try {
            log.info("param:{}", params);
            log.info("allmember: {}", memberMapper.findListMemberById(params));
            allMembers = memberMapper.findListMemberById(params);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return allMembers;
    }

    // public Member registChatTime(Long memberId) {
    //     Timestamp chatUpdated = new Timestamp(System.currentTimeMillis());
    //     memberMapper.updateChatTime(memberId, chatUpdated);
    //     return memberMapper.findById(memberId); 
    // }
    public void registChatTime(Long memberId) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        memberMapper.updateChatTime(memberId, currentTime);
    }

}
