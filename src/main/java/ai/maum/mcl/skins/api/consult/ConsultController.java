package ai.maum.mcl.skins.api.consult;

import ai.maum.mcl.skins.api.common.BaseResponse;
import ai.maum.mcl.skins.mybatis.vo.MemberDetailVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name="상담데이터API", description="상담관련API")
@RequestMapping("/api/consult")
public class ConsultController {
    @Operation(summary = "상담정보조회", description = "상담정보조회")
    @GetMapping("/info")
    public BaseResponse<String> getConsultInfo(
            @AuthenticationPrincipal MemberDetailVO member
    ) {

        return BaseResponse.success("consultInfo");
    }
}
