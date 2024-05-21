package ai.maum.mcl.skins.api.consult;

import ai.maum.mcl.skins.api.common.BaseResponse;
import ai.maum.mcl.skins.api.consult.model.ConsultInfo;
import ai.maum.mcl.skins.api.consult.service.ConsultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name="상담데이터API", description="상담관련API")
@RequestMapping("/api/consult")
public class ConsultController {
    private final ConsultService consultService;
    @Operation(summary = "상담정보조회", description = "상담정보조회")
    @GetMapping("/info/{member_id}")
    public BaseResponse<List<ConsultInfo>> responseConsultInfo(
//            @AuthenticationPrincipal MemberDetail member
            @PathVariable(name = "member_id", required = false) @Parameter(name = "member_id", required = true) Long userKey
    ) {
//        Long userKey = Long.valueOf(member.getUsername());
//        if(userKey == null || userKey < 1L)
//            return BaseResponse.failure(null, "사용자 key 오류");
        return BaseResponse.success(getConsultInfo(userKey));
    }

    public List<ConsultInfo> getConsultInfo(Long userKey) {
        return consultService.getConsultInfoByUserKey(userKey);
    }
}
