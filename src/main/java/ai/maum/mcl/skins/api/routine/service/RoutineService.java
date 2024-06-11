package ai.maum.mcl.skins.api.routine.service;

import ai.maum.mcl.skins.api.member.mapper.MemberMapper;
import ai.maum.mcl.skins.api.member.model.MemberChatTime;
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

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoutineService {
    private final MemberMapper memberMapper;

    @Scheduled(fixedRate = 1*60*1000)
    public void executeRoutine() throws IOException {
        log.info("-------------- ROUTINE START ---------------------");
        List<MemberChatTime> allMembers = memberMapper.findMemberWithChatUpdated();
        log.info("allmembers: {}",allMembers);
        List<Long> idList = new ArrayList<>();
        Timestamp now = Timestamp.from(Instant.now());
        Timestamp twentyFourHoursAgo = Timestamp.from(Instant.now().minusSeconds(24*60*60));

        for (MemberChatTime member : allMembers) {
            Timestamp chatUpdated = member.getChatUpdated();
            if (chatUpdated != null && chatUpdated.after(twentyFourHoursAgo)) {
                Long selectedId = member.getId();
                idList.add(selectedId);
                List<ChatroomDetail> chatroomDetailList = new ArrayList<ChatroomDetail>();
                String strData = callProaiService.callProaiGet("/extapi/inner/chatroom/detail/" + selectedId);
                log.info("strData :{}",strData);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode data = objectMapper.readTree(strData).path("data");
                chatroomDetailList = objectMapper.readValue(
                        data.traverse(),
                        new TypeReference<List<ChatroomDetail>>() {});

            }
        }

        log.info("IDs with chat_updated less than 24 hours: {}", idList);
        for (Long id: idList) {

        }
        log.info("-------------- ROUTINE END ---------------------");
    }

}
