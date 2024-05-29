package ai.maum.mcl.skins.api.proai;

import ai.maum.mcl.skins.api.common.BaseResponse;
import ai.maum.mcl.skins.api.proai.model.ChatroomDetail;
import ai.maum.mcl.skins.api.proai.service.CallProaiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name="아모레내부시스템용API", description="아모레내부시스템용API")
@RequestMapping("/extapi/inner")
public class InnerSystemController {
    private final CallProaiService callProaiService;

    @Operation(summary = "채팅내용조회", description = "특정 채팅룸의 채팅 내용 조회")
    @ResponseBody
    @GetMapping({"/chatroom/{user_id}"})
    public BaseResponse<List<ChatroomDetail>> getChatroomDetail (
            @PathVariable(name = "user_id", required = false) @Parameter(description = "사용자Key", required = true) String userKey
    ) {
        List<ChatroomDetail> chatroomDetailList = new ArrayList<ChatroomDetail>();
        try {
//            BaseResponse<List<ChatroomDetail>> response = callProaiService.callProaiChatGet("/extapi/inner/chatroom/detail/" + userKey);
            chatroomDetailList = callProaiService.callProaiChatGet("/extapi/inner/chatroom/detail/" + userKey);
        } catch (Exception e) {
            log.error("Error in api call", e.getMessage());
        }
        return BaseResponse.success(chatroomDetailList);
    }

    

    @Operation(summary = "채팅내용조회", description = "특정 채팅룸의 채팅 내용 조회")
    @ResponseBody
    @GetMapping({"/chat/{user_id}"})
    public BaseResponse<List<ChatroomDetail>> getChatDetail (
            @PathVariable(name = "user_id", required = false) @Parameter(description = "사용자Key", required = true) String userKey
    ) {
        List<ChatroomDetail> chatroomDetailList = new ArrayList<ChatroomDetail>();
        try {
            String strData = callProaiService.callProaiGet("/extapi/inner/chatroom/detail/" + userKey);
            log.info("strData :{}",strData);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode data = objectMapper.readTree(strData).path("data");
            chatroomDetailList = objectMapper.readValue(
                    data.traverse(),
                    new TypeReference<List<ChatroomDetail>>() {});
        } catch (Exception e) {
            log.error("Error in api call", e.getMessage());
        }
        return BaseResponse.success(chatroomDetailList);
    }

    
    @Operation(summary = "채팅내용조회 (POST)", description = "특정 채팅룸의 채팅 내용 조회 (POST)")
    @ResponseBody
    @PostMapping("/chat/{user_id}")
    public BaseResponse<List<ChatroomDetail>> getChatPost(
            @PathVariable(name = "user_id", required = true) @Parameter(description = "사용자Key") String userKey,
            @RequestBody @Parameter(name="챗보내기", required=true) ChatEntity chat
        ) {

        List<ChatroomDetail> chatDetail = new ArrayList<>();
        try {
            String strData = callProaiService.callProaiPost("/extapi/inner/chatroom/detail/" + userKey, chat);
            log.info("strData: {}", strData);
            // ObjectMapper objectMapper = new ObjectMapper();
            // JsonNode data = objectMapper.readTree(strData).path("data");
            // chatDetail = objectMapper.readValue(
            //         data.traverse(),
            //         new TypeReference<List<ChatroomDetail>>() {});
        } catch (Exception e) {
            log.error("Error in API call: {}", e.getMessage(), e);
        }
        return BaseResponse.success(chatDetail);
    }                                                                                                                                                        
}
