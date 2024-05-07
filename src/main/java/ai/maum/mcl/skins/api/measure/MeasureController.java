package ai.maum.mcl.skins.api.measure;

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
@Tag(name="측정데이터API", description="측정관련API")
@RequestMapping("/api/measure")
public class MeasureController {
    @Operation(summary = "피부/두피측정정보조회", description = "피부/두피측정정보조회")
    @GetMapping("/info")
    public BaseResponse<String> getMeasureInfo(
            @AuthenticationPrincipal MemberDetailVO member
    ) {

        return BaseResponse.success("measureInfo");
    }
}
