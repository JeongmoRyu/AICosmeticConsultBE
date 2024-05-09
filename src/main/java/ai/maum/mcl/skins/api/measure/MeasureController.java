package ai.maum.mcl.skins.api.measure;

import ai.maum.mcl.skins.api.common.BaseResponse;
import ai.maum.mcl.skins.api.measure.model.MeasureInfo;
import ai.maum.mcl.skins.api.measure.service.MeasureService;
import ai.maum.mcl.skins.api.member.model.MemberDetail;
import ai.maum.mcl.skins.api.member.model.MemberInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name="측정데이터API", description="측정관련API")
@RequestMapping("/api/measure")
public class MeasureController {
    private final MeasureService measureService;
    @Operation(summary = "측정결과조회", description = "측정결과조회")
    @GetMapping("/info")
    public BaseResponse<List<MeasureInfo>> responseMeasureInfo(
            @AuthenticationPrincipal MemberDetail member
    ) {
        Long userKey = Long.valueOf(member.getUserName());
        if (userKey == null || userKey < 1L)
            return BaseResponse.failure(null, "사용자 key 오류");
        return BaseResponse.success(getMeasureInfo(userKey));
    }
    public List<MeasureInfo> getMeasureInfo(Long userKey) {
        return measureService.getMeasureInfoByUserKey(userKey);
    }
}
