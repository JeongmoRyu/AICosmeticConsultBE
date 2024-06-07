package ai.maum.mcl.skins.api.consult;

import ai.maum.mcl.skins.api.common.BaseResponse;
import ai.maum.mcl.skins.api.consult.model.ConsultIndirect;
import ai.maum.mcl.skins.api.consult.model.ConsultInfo;
import ai.maum.mcl.skins.api.consult.service.ConsultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name="상담데이터API", description="상담관련API")
@RequestMapping("/api/consult")
public class ConsultController {
    private final ConsultService consultService;
    @Operation(summary = "상담정보조회", description = "consult_number가 없으면 전체, 이후 index로")
    @GetMapping({"/info/{member_id}", "/info/{member_id}/{consult_number}"})
    public BaseResponse<List<ConsultInfo>> responseConsultInfo(
//            @AuthenticationPrincipal MemberDetail member
            @PathVariable(name = "member_id", required = false) @Parameter(name = "member_id", required = true) Long userKey
//            ,@PathVariable(name = "consult_number", required = false) @Parameter(name = "consult_number") Integer consultNumber
    ) {
//        Long userKey = Long.valueOf(member.getUsername());
//        if(userKey == null || userKey < 1L)
//            return BaseResponse.failure(null, "사용자 key 오류");
        return BaseResponse.success(getConsultInfo(userKey));
//        return BaseResponse.success(getConsultInfo(userKey, consultNumber));
    }

    public List<ConsultInfo> getConsultInfo(Long userKey) {
        return consultService.getConsultInfoByUserKey(userKey);
    }
    
    @Operation(summary = "대면상담정보조회", description = "개별 대면 상담정보조회")
    @GetMapping("/direct/{memberId}/{consultNumber}")
    public BaseResponse<List<ConsultDirect>> responseConsultDirect(
            @PathVariable(name = "member_id", required = true) @Parameter(name = "member_id", required = true) Long memberId,
            @PathVariable(name = "consult_number", required = false) @Parameter(name = "consult_number", required = false) Long consultNumber
    ) {
        log.info("memberId: {}", memberId);

        if (consultNumber != null) {
            List<ConsultDirect> directConsult = consultService.getConsultDirectByConsultNumber(memberId, consultNumber);
            return BaseResponse.success(directConsult);
        } else {
            List<ConsultDirect> allDirectConsults = consultService.getConsultDirectByMemberId(memberId);
            return BaseResponse.success(allDirectConsults);
        }
    }

//    public List<ConsultInfo> getConsultInfo(Long userKey, Integer consultNumber) {
//        return consultService.getConsultInfoByUserKey(userKey, consultNumber);
//    }
    @Operation(summary = "비대면상담기록(해당이름전체)", description = "비대면상담기록(비대면상담기록(전체))")
    @GetMapping("/indirect/{member_id}")
    public BaseResponse<List<ConsultIndirect>> responseConsultIndirectInfo(
            @PathVariable(name = "member_id", required = false) @Parameter(name = "member_id", required = true) Long id
    ) {

        return BaseResponse.success(getConsultIndirectInfo(id));
    }

    public List<ConsultIndirect> getConsultIndirectInfo(Long id) {
        return consultService.getConsultIndirectInfo(id);
    }


    @Operation(summary = "상담정보조회", description = "전체 상담정보조회")
    @GetMapping("/group")
    public BaseResponse<List<SignificantGroup>> responseSignificantGroup(
    ) {

        return BaseResponse.success(getSignificantGroup());
    }

    public List<SignificantGroup> getSignificantGroup() {
        return consultService.getSignificantGroup();
    }

    @Operation(summary = "대면 상담 수정", description = "대면상담 특정 차수 수정하기")
    @PutMapping("/direct/{memberId}/{consultNumber}")
    public BaseResponse<String> updateConsultDirect(
            @PathVariable Long memberId,
            @PathVariable Long consultNumber,
            @RequestBody ConsultDirect consultDirect
    ) {
        try {
            if (consultDirect.getFeatureList() != null) {
                log.info("member_id, consultNubmer: {}", memberId + consultNumber);
                log.info("data: {}", consultDirect.getFeatureList());
                consultDirect.setFeatures(consultDirect.serializeFeatures(consultDirect.getFeatureList()));
            }
            consultService.updateConsultDirect(consultDirect);
            return BaseResponse.success("Consultation updated successfully.");
        } catch (DataAccessException e) {
            log.error("Database access error occurred", e);
            return BaseResponse.failure("Failed to update consultation due to database error.: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error updating consultation", e);
            return BaseResponse.failure("Failed to update consultation: {}", e.getMessage());
        }
    }

}
