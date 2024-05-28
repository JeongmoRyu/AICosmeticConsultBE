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
        List<ChatroomDetail> chatroomChattingList = new ArrayList<ChatroomDetail>();
        try {
            chatroomChattingList = callProaiService.callProaiChatGet("/extapi/inner/chatroom/detail/" + userKey);
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            return BaseResponse.failure("Failed to fetch chatroom details");
        }
        return BaseResponse.success(chatroomChattingList);
    }

}
