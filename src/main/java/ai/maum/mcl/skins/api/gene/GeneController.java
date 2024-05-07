package ai.maum.mcl.skins.api.gene;

import ai.maum.mcl.skins.api.common.BaseResponse;
import ai.maum.mcl.skins.api.gene.model.GeneInfo;
import ai.maum.mcl.skins.api.gene.service.GeneService;
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
@Tag(name="유전자데이터API", description="유전자검사관련API")
@RequestMapping("/api/gene")
public class GeneController {
    private final GeneService geneService;
    @Operation(summary = "유전자검사결과조회", description = "유전자검사결과조회")
    @GetMapping("/info")
    public BaseResponse<List<GeneInfo>> responseGeneInfo(
//            @AuthenticationPrincipal MemberDetail member
            @PathVariable(name = "member_id", required = false) @Parameter(name = "member_id", required = true) Long userKey
    ) {
//        Long userKey = Long.valueOf(member.getUsername());
//        if(userKey == null || userKey < 1L)
//            return BaseResponse.failure(null, "사용자 key 오류");
        return BaseResponse.success(getGeneInfo(userKey));
    }

    public List<GeneInfo> getGeneInfo(Long userKey) {
        return geneService.getGeneInfoByUserKey(userKey);
    }
}
