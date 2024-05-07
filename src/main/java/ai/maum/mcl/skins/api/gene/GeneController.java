package ai.maum.mcl.skins.api.gene;

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
@Tag(name="유전자데이터API", description="유전자검사관련API")
@RequestMapping("/api/gene")
public class GeneController {
    @Operation(summary = "유전자검사정보조회", description = "유전자검사정보조회")
    @GetMapping("/info")
    public BaseResponse<String> getGeneInfo(
            @AuthenticationPrincipal MemberDetailVO member
    ) {

        return BaseResponse.success("geneInfo");
    }
}
