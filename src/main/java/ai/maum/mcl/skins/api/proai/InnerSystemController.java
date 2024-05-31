package ai.maum.mcl.skins.api.proai;

import ai.maum.mcl.skins.api.common.BaseResponse;
import ai.maum.mcl.skins.api.proai.model.ChatDetail;
import ai.maum.mcl.skins.api.proai.model.ChatroomDetail;
import ai.maum.mcl.skins.api.proai.service.CallProaiService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@Tag(name="아모레내부시스템용API", description="아모레내부시스템용API")
@RequestMapping("/api/inner")
public class InnerSystemController {
    private final CallProaiService callProaiService;

    @Autowired
    public InnerSystemController(CallProaiService callProaiService) {
        this.callProaiService = callProaiService;
    }


    @Operation(summary = "test", description = "test")
    @ResponseBody
    @GetMapping("/proai/test")
    public String callProAi(
            @RequestParam(value="url") String url
    ) {
        return callProaiService.callProaiGet(url);
    }


    @Operation(summary = "채팅내용조회", description = "특정 채팅룸의 채팅 내용 조회")
    @ResponseBody
    @GetMapping({"/chatroom/{user_id}"})
    public BaseResponse<List<ChatroomDetail>> getChatDetail (
            @PathVariable(name = "user_id", required = false) @Parameter(description = "사용자Key", required = true) String userKey
    ) {
        List<ChatroomDetail> chatroomDetailList = new ArrayList<ChatroomDetail>();
        try {
            log.info("*********Hello********* : {}", userKey);
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



    @Operation(summary = "채팅보내기(POST)", description = "채팅보내기(POST)")
    @ResponseBody
    @PostMapping("/chat/{user_id}")
    public BaseResponse<String> getChatPost(
            @PathVariable(name = "user_id", required = true) @Parameter(description = "사용자Key") String userKey,
            @RequestBody @Parameter(name="message", required=true) ChatDetail messageJson
    ) {
        String data = new String();
        try {
            ObjectMapper object = new ObjectMapper();
            JsonNode json = object.valueToTree(messageJson);
            String message = json.path("message").asText();

            String strData = callProaiService.callProaiPost("/extapi/inner/sendtalk/" + userKey, message);
            log.info("strData: {}", strData);
            data = strData;

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(strData);

            boolean result = jsonNode.path("result").asBoolean();

            if (result) {
                return BaseResponse.success(data);
            } else {
                return BaseResponse.failure("Failure", data);
            }
        } catch (Exception e) {
            log.error("Error in API call: {}", e.getMessage(), e);
            return BaseResponse.failure("error occurred posting message: " + e.getMessage(), data);
        }
    }
//
//    @Operation(summary = "채팅보내기(POST)", description = "채팅보내기(POST)")
//    @ResponseBody
//    @PostMapping("/chat/{user_id}")
//    public BaseResponse<String> getChatPost(
//            @PathVariable(name = "user_id", required = true) @Parameter(description = "사용자Key") String userKey,
//            @RequestBody @Parameter(name="message", required=true) String message
//    ) {
//        String data = new String();
////        List<ChatroomDetail> chatDetail = new ArrayList<>();
//        try {
//            String strData = callProaiService.callProaiPost("/extapi/inner/sendtalk/" + userKey, "/?message="+message);
//            log.info("strData: {}", strData);
////            try {
////                if (strData["result"] == true) {
////
////                }
////            }
//            data = strData;
//
//        } catch (Exception e) {
//            log.error("Error in API call: {}", e.getMessage(), e);
//        }
//        return BaseResponse.success("success", data);
//    }
}
