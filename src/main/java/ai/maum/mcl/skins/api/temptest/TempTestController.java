package ai.maum.mcl.skins.api.temptest;

import ai.maum.mcl.skins.api.common.BaseResponse;
import ai.maum.mcl.skins.api.member.model.MemberDetail;
import ai.maum.mcl.skins.api.member.model.MemberResult;
import ai.maum.mcl.skins.api.temptest.model.TempTestDetail;
import ai.maum.mcl.skins.api.temptest.model.TempTestInfo;
import ai.maum.mcl.skins.api.temptest.service.TempTestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "temptest", description = "temptest")
@RequestMapping("api/temptest")
public class TempTestController {
    private final TempTestService tempTestService;

    @Operation(summary = "측정결과조회", description = "측정결과조회")
    @GetMapping("/first")
    public BaseResponse<List<TempTestInfo>> responseTempTestInfo(
            @AuthenticationPrincipal MemberDetail member
    ) {
        Long userKey = Long.valueOf(member.getUsername());
        if (userKey == null || userKey < 1L)
            return BaseResponse.failure(null, "사용자 key 오류");
        return BaseResponse.success(getTempTestInfo(userKey));
    }
    public List<TempTestInfo> getTempTestInfo(Long userKey) {
        return tempTestService.getTempTestInfoByUser(userKey);
    }

    @Operation(summary = "detail설명", description = "detail설명")
    @GetMapping("/second")
    public BaseResponse<List<TempTestDetail>> responseTempTestDetail(
            @AuthenticationPrincipal MemberDetail member
    ) {
        Long userKey = Long.valueOf(member.getUsername());

        if (userKey == null || userKey < 1L) {
            return BaseResponse.failure(null, "사용자 Key 오류");
        }

        List<TempTestInfo>TestInfoList = tempTestService.getTempTestInfoByUser(userKey);

        List<TempTestDetail> tempTestDetailList = new ArrayList<>();

        for (TempTestInfo tempTestInfo : TestInfoList) {
            String infoString = tempTestDetailToString(tempTestInfo);
            TempTestDetail detail = new TempTestDetail(infoString);
            tempTestDetailList.add(detail);
        }

        return BaseResponse.success(tempTestDetailList);
    }
    private String tempTestDetailToString(TempTestInfo tempTestDetail){
        String detail = String.format("T존:%s\nU존:%s\n",
                tempTestDetail.getTZoneResult(),
                tempTestDetail.getUZoneResult());
        return detail;
    }

}
