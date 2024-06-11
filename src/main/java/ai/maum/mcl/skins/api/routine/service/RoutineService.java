package ai.maum.mcl.skins.api.routine.service;

import ai.maum.mcl.skins.api.member.mapper.MemberMapper;
import ai.maum.mcl.skins.api.member.model.MemberList;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoutineService {
    private final MemberMapper memberMapper;

    @Scheduled(fixedRate = 1 * 60 * 1000)
    public void executeRoutine() {
        log.info("-------------- ROUTINE START ---------------------");
        Map<String, Object> param = new HashMap<>();
        List<MemberList> allMembers = memberMapper.findListMemberById(param);
        
        List<Long> idList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (MemberList member : allMembers) {
            if (member.getChatUpdated() != null && member.getChatUpdated().isBefore(now.minusHours(24))) {
                idList.add(member.getId());
            }
        }

        log.info("IDs with chat_updated older than 24 hours: {}", idList);
        log.info("-------------- ROUTINE END ---------------------");
    }
}

