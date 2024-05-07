package ai.maum.mcl.skins.api.member;

import ai.maum.mcl.skins.api.common.BaseResponse;
import ai.maum.mcl.skins.mybatis.vo.MemberDetailVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name="사용자(고객)", description="사용자관리API")
@RequestMapping("/api/member")
public class MemberController {
    @Operation(summary = "고객정보조회", description = "고객정보조회")
    @GetMapping("/info")
    public BaseResponse<MemberDetailVO> getMemberInfo(
            @AuthenticationPrincipal MemberDetailVO member
    ) {
        log.debug("user_key:" + member.getUsername());
        log.debug("name:" + member.getName());
        log.debug("sex:" + member.getSex());
        log.debug("age:" + member.getAge());

        return BaseResponse.success(member);
    }
}
