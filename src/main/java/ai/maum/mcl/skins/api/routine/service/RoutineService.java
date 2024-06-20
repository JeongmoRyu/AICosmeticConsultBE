package ai.maum.mcl.skins.api.routine.service;

//import ai.maum.mcl.skins.api.chat.model.Chat;
import ai.maum.mcl.skins.api.chat.model.ChatHistory;
import ai.maum.mcl.skins.api.consult.model.ConsultIndirect;
import ai.maum.mcl.skins.api.consult.mapper.ConsultMapper;
import ai.maum.mcl.skins.api.consult.service.ConsultService;
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
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ai.maum.mcl.skins.api.chat.handler.ChatGrpcConnectionHandler;
import rag_service.rag_module.RagServiceGrpc;
import rag_service.rag_module.RagServiceGrpc.*;
import rag_service.rag_module.Rag;
import rag_service.rag_module.Rag.Chat;
import rag_service.rag_module.Rag.*;





import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoutineService {
    private final MemberMapper memberMapper;
    private final CallProaiService callProaiService;
    private final ChatGrpcConnectionHandler connectionHandler;
    private final ConsultService consultService;



    private String basePrompt = "당신은 고객의 상담내용을 다듬고 요약하는 문장 요약기이다. 주어진 채팅들을 주어진 형식과 예시와 같이 요약해줘. 결과 데이터의 형식은" +
            "consult_type: 전체를 아우르는 10글자 이내의 타입, consult_data: 상대 내용의 요약 내용 글자수는 500자 이내로 해줘, significant: 상담 내용 중 특이사항이라고 판단되는 것들을 추려내줘 " +
            "이러한 내용을 담긴 요약본을 문장으로 만들어서줘. 다른 정보와 데이터는 필요없고 정확하게 주어진 상담 및 채팅의 내용으로만 consult_type, consult_data, significant와 그에 속하는 data로만 구성된 답변이면 될 것 같아" +
            "한번 더 예시를 보여주면 consult_type: 건조한 피부 트러블 고민, consult_data: 평소에도 건조한 피부에 대해서 고민이 많았는데 요즘 날씨에 피부 트러블이 더 심해져서 고민이 더 심해지고 있어 해결할 수 있는 밥법을 찾는 고객과 그에 따른 답변 내용의 요약, significant: 날씨에 따라 피부 트러블이 심해짐";


    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(fixedRate = 2*60*1000)
    public void executeRoutine() throws IOException {
        log.info("-------------- ROUTINE START ---------------------");
        List<MemberChatTime> allMembers = memberMapper.findMemberWithChatUpdated();
        log.info("allmembers: {}", allMembers);
        List<Long> idList = new ArrayList<>();
        Timestamp now = Timestamp.from(Instant.now());
//        Timestamp twentyFourHoursAgo = Timestamp.from(Instant.now().minusSeconds(2*60*60));
        Timestamp twentyFourHoursAgo = Timestamp.from(Instant.now().minusSeconds(24*60*60));




        for (MemberChatTime member : allMembers) {
            Timestamp chatUpdated = member.getChatUpdated();
            if (chatUpdated != null && chatUpdated.after(twentyFourHoursAgo)) {
                Long selectedId = member.getId();
                idList.add(selectedId);
                List<ChatroomDetail> chatroomDetailList = new ArrayList<>();
                String strData = callProaiService.callProaiGet("/extapi/inner/chatroom/detail/" + selectedId);
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
//                log.info("chatroomDetailList: {}", filteredChatroomDetails);

                //  그 당일 중 가장 빠른 일자 추출
                Optional<Timestamp> fastedTimeOptional = filteredChatroomDetails.stream()
                        .map(ChatroomDetail::getCreated_at)
                        .filter(Objects::nonNull)
                        .min(Comparator.naturalOrder());
                Timestamp fastTime = fastedTimeOptional.get();

                // grpc 형태에 맞춰서 chat으로 제작
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
//                log.info("chatHistoryMap: {}", chatHistoryMap);

                List<ChatHistory> chatHistoryList = new ArrayList<>(chatHistoryMap.values());
//                log.info("chatHistoryList: {}", chatHistoryList);



                // gRPC 연결해서 Summarize 요청 보내기
                try {
                    List<Chat> chatList = new ArrayList<>();
                    for (ChatHistory item : chatHistoryList) {
                        Chat chat = Chat.newBuilder().setInput(item.getInput()).setOutput(item.getOutput()).build();
                        chatList.add(chat);
                    }

                    log.info("chatList: {}", chatList);
                    ChatGrpcConnectionHandler.SummarizeRequestObject requestObject = connectionHandler.createSummarizeRequestObject(selectedId);
                    RagServiceGrpc.RagServiceBlockingStub blockingStub = requestObject.getStub();

                    LLMParameters llmParams = LLMParameters.newBuilder()
                            .setPromptFormat(basePrompt)
                            .build();

                    SummRequest summRequest = SummRequest.newBuilder()
                            .setLlmParams(llmParams)
                            .addAllChatHistory(chatList)
                            .build();

                    // 답변 받아오기(string으로 나올 예정)
                    // data 정리를 통해서 consult_type, consult_data, significant 분류
                    SummResponse summResponse = blockingStub.chatSummarize(summRequest);

                    log.info("Summarize response: {}", summResponse.getResponse());

                    // 여기서 summResponse.getResponse()를 파싱하여 필요한 데이터로 변환
                    Map<String, String> resultGrpc = parseData(summResponse.getResponse());
                    log.info("resultGrpc: {},{},{}", resultGrpc.get("consult_data"), resultGrpc.get("consult_type"),resultGrpc.get("significant") );


                    // indirect 형식에 맞춰서 나머지 데이터 넣기
                    // 정렬된 indirect model에 맞춰 insert indirect data
                    ConsultIndirect consultIndirect = new ConsultIndirect();

                    consultIndirect.setUserkey(selectedId.toString());
                    consultIndirect.setName(member.getName());
                    consultIndirect.setConsultTime(fastTime);
                    consultIndirect.setConsultData(resultGrpc.get("consult_data"));
                    consultIndirect.setManager("AI");
                    consultIndirect.setConsultType(resultGrpc.get("consult_type"));
                    consultIndirect.setSignificant(resultGrpc.get("significant"));
                    log.info("consutlIndirect: {}" , consultIndirect);

                    consultService.registIndirectSummary(consultIndirect);


                } catch (Exception e) {
                    log.error("[GRPC_SUMMARIZE] Exception {}", e.getMessage());
                }
            }
        }

        log.info("IDs with chat_updated less than 24 hours: {}", idList);
        log.info("-------------- ROUTINE END ---------------------");
    }


    public static Map<String, String> parseData(String data) {
        Map<String, String> resultMap = new HashMap<>();

        String[] keys = {"consult_type", "consult_data", "significant"};

        for (String key : keys) {
            String pattern = key + ":(.*?)(?=,\\s*\\bconsult_type:|,\\s*\\bconsult_data:|,\\s*\\bsignificant:|$)";
            Pattern regexPattern = Pattern.compile(pattern);
            Matcher matcher = regexPattern.matcher(data);

            if (matcher.find()) {
                String value = matcher.group(1).trim();
                resultMap.put(key, value);
            } else {
                resultMap.put(key, "");
            }
        }
        log.info("resultMap: {}", resultMap);
        return resultMap;
    }

}
