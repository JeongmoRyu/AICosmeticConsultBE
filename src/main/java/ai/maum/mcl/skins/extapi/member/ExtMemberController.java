package ai.maum.mcl.skins.extapi.member;

import ai.maum.mcl.skins.api.apiuser.model.ApiUser;
import ai.maum.mcl.skins.api.common.BaseResponse;
import ai.maum.mcl.skins.api.consult.model.ConsultInfo;
import ai.maum.mcl.skins.api.consult.service.ConsultService;
import ai.maum.mcl.skins.api.gene.model.GeneInfo;
import ai.maum.mcl.skins.api.gene.service.GeneService;
import ai.maum.mcl.skins.api.measure.model.MeasureInfo;
import ai.maum.mcl.skins.api.measure.service.MeasureService;
import ai.maum.mcl.skins.api.member.model.Member;
import ai.maum.mcl.skins.api.member.model.MemberResult;
import ai.maum.mcl.skins.api.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name="외부시스템용-사용자(고객)", description="외부시스템용-사용자(고객)정보 관련 API")
@RequestMapping("/extapi/member")
public class ExtMemberController {
    private final ConsultService consultService;
    private final MeasureService measureService;
    private final GeneService geneService;
    private final MemberService memberService;

    @Operation(summary = "고객정보조회(전체)", description = "고객정보조회(상담결과/유전자검사결과/측정결과통합)")
    @GetMapping("/result/{member_id}")
    public BaseResponse<MemberResult> getMemberResult(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable(name = "member_id", required = false) @Parameter(name = "member_id", required = true) Long memberId
    ) {

        log.info("CALL EXT API MEMBER INFO RESULT-GET MEMBERINFO:" + user.getUsername());

        Member member = memberService.getMemberById(memberId);

        log.info("MEMBER_INFO:" + member.getId() + ":" + member.getName() + ":" + member.getSex() + ":" + member.getAge());

        MemberResult memberResult = new MemberResult();
//        Long userKey = Long.valueOf(member.getUsername());

//        if(userKey == null || userKey < 1L)
//            return BaseResponse.failure(null, "사용자 Key 오류");

        log.info("GET CONSULT INFO..." + member.getId() + ":" + member.getName());
        List<ConsultInfo> consultInfoList = consultService.getConsultInfoByUserKey(memberId);
        log.info("GET MEASURE INFO..." + member.getId() + ":" + member.getName());
        List<MeasureInfo> measureInfoList = measureService.getMeasureInfoByUserKey(memberId);
        log.info("GET GENE INFO..." + member.getId() + ":" + member.getName());
        List<GeneInfo> geneInfoList = geneService.getGeneInfoByUserKey(memberId);

        log.info("CONVER MEMBERINFO TO STRING..." + member.getId() + ":" + member.getName());
        String memberInfo = memberInfoToString(member);
        log.info("CONVER MEASUREINFO TO STRING..." + member.getId() + ":" + member.getName());
        String measureInfo = measureInfoToString(measureInfoList);
        log.info("CONVER GENEINFO TO STRING..." + member.getId() + ":" + member.getName());
        String geneInfo = geneInfoToString(geneInfoList);
        log.info("CONVER CONSULTINFO TO STRING..." + member.getId() + ":" + member.getName());
        String consultInfo = consultInfoToString(consultInfoList);

        memberResult = new MemberResult(memberInfo, consultInfo, measureInfo, geneInfo);

        return BaseResponse.success(memberResult);
    }

    private String memberInfoToString(Member member) {
        String name = member.getName();
        String sex = switch (member.getSex()) {
            case ("F") -> "여성";
            case ("M") -> "남성";
            default -> "";
        };
        Integer age = member.getAge();

        return String.format("이름:%s\n성별:%s\n나이%d", name, sex, age);
    }

    private String consultInfoToString(List<ConsultInfo> consultInfos) {
        String result = new String();

        int index = 1;
        for(ConsultInfo consultInfo:consultInfos) {
            if(consultInfos.size() > 1)
                result += (result.isBlank()?"[":"\n[") + index + "차 상담]\n";
            if(consultInfo.getConsultData() != null && !consultInfo.getConsultData().isBlank())
                result += String.format("상담결과-\"%s\",", consultInfo.getConsultData());
            if(consultInfo.getConcern1() != null && !consultInfo.getConcern1().isBlank())
                result += String.format("피부고민1-%s,", consultInfo.getConcern1());
            if(consultInfo.getConcern2() != null && !consultInfo.getConcern2().isBlank())
                result += String.format("피부고민2-%s,", consultInfo.getConcern2());
            if(consultInfo.getProduct() != null && !consultInfo.getProduct().isBlank())
                result += String.format("추천제품-\"%s\",", consultInfo.getProduct());
            if(consultInfo.getSignificant() != null && !consultInfo.getSignificant().isBlank())
                result += String.format("특이사항-\"%s\",", consultInfo.getSignificant());
            if(consultInfo.getEtc() != null && !consultInfo.getEtc().isBlank())
                result += String.format("기타(메모)-\"%s\",", consultInfo.getEtc());
            index++;
        }

        return result;
    }

    private String measureInfoToString(List<MeasureInfo> measureInfos) {
        String result = new String();

        int index = 1;
        for(MeasureInfo measureInfo:measureInfos) {
            if(measureInfos.size() > 1)
                result += result.isBlank()?index + "차결과,":"\n" + index + "차결과,";

            result += String.format("피부점수:%d점, 관리항목:%s\n", measureInfo.getSkinScore(), measureInfo.getSkinGomin())
                    + String.format("T존:%s\nU존:%s\n", measureInfo.getTZoneResult(), measureInfo.getUZoneResult())
                    + String.format("피부등급:%s, 분석결과:\"%s\", 스킨케어팁:\"%s\"\n", measureInfo.getSolutionTypeNumber(), measureInfo.getSolutionTypeResult(), measureInfo.getSolutionTypeTip())
                    + String.format("민감등급:%s, 분석결과:\"%s\", 스킨케어팁:\"%s\"\n", measureInfo.getSensitiveTypeNumber(), measureInfo.getSensitiveTypeResult(), measureInfo.getSensitiveTypeTip())
                    + String.format("피부고민 - 1.피부의 노화관련 특성 \"모공:%s,주름:%s,미래주름:%s,탄력:%s\" 2.피부의 밝음과 투명도 특성 \"색소침착:%s,멜라닌:%s\" 3.피부의 외부 자극 반응도 특성 \"붉은기:%s,포피린:%s,경피수분손실:%s\"\n"
                    , measureInfo.getPoreString()
                    , measureInfo.getWrinkleString()
                    , measureInfo.getFuturewrinklesString()
                    , measureInfo.getElasticityString()
                    , measureInfo.getPigmemtationString()
                    , measureInfo.getMelaninString()
                    , measureInfo.getRednessString()
                    , measureInfo.getPorphyrinString()
                    , measureInfo.getTransdermalString());

            if(measureInfo.getSpecialtipMemo() != null && !measureInfo.getSpecialtipMemo().isBlank())
                result += String.format("참고사항(상담원메모):%s", measureInfo.getSpecialtipMemo());
            index++;
        }

        return result;
    }

    private String geneInfoToString(List<GeneInfo> geneInfos) {
        String result = new String();

        String prevCategoryName = "";
        for(GeneInfo geneInfo:geneInfos) {

//            log.debug(prevCategoryName + ":" + geneInfo.getCategoryName());

            if(!prevCategoryName.equals(geneInfo.getCategoryName())) {
                result += (result.isBlank() ? "[" : "\n[") + geneInfo.getCategoryName() + "]\n";
            }
//            result += geneInfo.getItemName() + ":" + geneInfo.getGradeName() + "-" + geneInfo.getDescription() + "\n";
            result += geneInfo.getItemName() + ":" + geneInfo.getGradeName() + ",";
            prevCategoryName = geneInfo.getCategoryName();
        }

        return result;
    }

    @Operation(summary = "chatupdated시간", description = "chatupdated시간")
    @PostMapping("/chattime/{member_id}")
    public ResponseEntity<String> chatUpdated(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable(name = "member_id") @Parameter(name = "member_id", required = true) Long memberId
    ) {
        try {
            memberService.registChatTime(memberId);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("failure");
        }
    }
}
