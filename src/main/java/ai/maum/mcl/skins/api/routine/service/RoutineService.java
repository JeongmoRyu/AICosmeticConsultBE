package ai.maum.mcl.skins.api.routine.service;

import ai.maum.mcl.skins.api.chat.model.Chat;
import ai.maum.mcl.skins.api.chat.model.ChatHistory;
import ai.maum.mcl.skins.api.member.mapper.MemberMapper;
import ai.maum.mcl.skins.api.member.model.MemberChatTime;
import ai.maum.mcl.skins.api.member.model.MemberList;
import ai.maum.mcl.skins.api.proai.model.ChatroomDetail;
import ai.maum.mcl.skins.api.proai.service.CallProaiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoutineService {
    private final MemberMapper memberMapper;
    private final CallProaiService callProaiService;


//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(fixedRate = 2*60*1000)
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
//                log.info("strData :{}",strData);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode data = objectMapper.readTree(strData).path("data");
                chatroomDetailList = objectMapper.readValue(
                        data.traverse(),
                        new TypeReference<List<ChatroomDetail>>() {});
                List<ChatroomDetail> filteredChatroomDetails = new ArrayList<>();

                for (ChatroomDetail detail : chatroomDetailList) {
                    if ((detail.getCreated_at() != null && detail.getCreated_at().after(twentyFourHoursAgo)) ||
                            (detail.getUpdated_at() != null && detail.getUpdated_at().after(twentyFourHoursAgo))) {
                        filteredChatroomDetails.add(detail);
                    }
                }
                log.info("filteredChatroomDetails: {}", filteredChatroomDetails);
                Map<Long, ChatHistory> chatHistoryMap = new HashMap<>();
                for (ChatroomDetail detail : filteredChatroomDetails) {
                    Long seq = detail.getSeq();
                    ChatHistory chatHistory = chatHistoryMap.getOrDefault(seq, new ChatHistory(seq, null, null));
                    if ("user".equals(detail.getRole())) {
                        chatHistory.setInput(detail.getContent());
                    } else if ("assistant".equals(detail.getRole())) {
                        chatHistory.setOutput(detail.getContent());
                    }
                    chatHistoryMap.put(seq, chatHistory);
                }

                List<ChatHistory> chatHistoryList = new ArrayList<>(chatHistoryMap.values());
                List<Chat> chatList = chatHistoryList.stream()
                        .map(chatHistory -> new Chat(chatHistory.getInput(), chatHistory.getOutput()))
                        .collect(Collectors.toList());
                log.info("chatList: {}", chatList);
            }

        }

        log.info("IDs with chat_updated less than 24 hours: {}", idList);

        log.info("-------------- ROUTINE END ---------------------");
    }

}
