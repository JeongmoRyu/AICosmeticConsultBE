package ai.maum.mcl.skins.api.member;

import ai.maum.mcl.skins.api.common.BaseResponse;
import ai.maum.mcl.skins.api.consult.model.ConsultInfo;
import ai.maum.mcl.skins.api.consult.service.ConsultService;
import ai.maum.mcl.skins.api.gene.model.GeneInfo;
import ai.maum.mcl.skins.api.gene.service.GeneService;
import ai.maum.mcl.skins.api.manager.model.Manager;
import ai.maum.mcl.skins.api.measure.model.MeasureInfo;
import ai.maum.mcl.skins.api.measure.service.MeasureService;
import ai.maum.mcl.skins.api.member.model.Member;
import ai.maum.mcl.skins.api.member.model.MemberResult;
import ai.maum.mcl.skins.api.member.model.MemberList;
import ai.maum.mcl.skins.api.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name="사용자(고객)", description="사용자관리API")
@RequestMapping("/api/member")
public class MemberController {

    private final ConsultService consultService;
    private final MeasureService measureService;
    private final GeneService geneService;
    private final MemberService memberService;

    @Operation(summary = "고객정보조회", description = "고객정보조회(only 사용자정보)")
    @GetMapping("/info/{member_id}")
    public BaseResponse<Member> getMemberInfo(
//            @AuthenticationPrincipal UserDetails user,
            @AuthenticationPrincipal Manager user,
            @PathVariable(name = "member_id", required = false) @Parameter(name = "member_id", required = true) Long memberId
    ) {

//        log.debug("manger_id:" + user.getUsername());
        log.debug("manger_id:" + user.getManagerId());


        Member member = memberService.getMemberById(memberId);

        log.debug("user_key:" + member.getId());
        log.debug("name:" + member.getName());
        log.debug("sex:" + member.getSex());
        log.debug("age:" + member.getAge());

        return BaseResponse.success(member);
    }

    @Operation(summary = "고객정보조회(전체)", description = "고객정보조회(상담결과/유전자검사결과/측정결과통합)")
    @GetMapping("/result")
    public BaseResponse<MemberResult> getMemberResult(
//            @AuthenticationPrincipal MemberDetail member
            @PathVariable(name = "member_id", required = false) @Parameter(name = "member_id", required = true) Long memberId
    ) {
        Member member = memberService.getMemberById(memberId);

        log.debug("user_key:" + member.getId());
        log.debug("name:" + member.getName());
        log.debug("sex:" + member.getSex());
        log.debug("age:" + member.getAge());

        MemberResult memberResult = new MemberResult();
//        Long userKey = Long.valueOf(member.getUsername());
//
//        if(userKey == null || userKey < 1L)
//            return BaseResponse.failure(null, "사용자 Key 오류");

//        Member memberObj = getMemberInfoFromMemberDetail(member);
        List<ConsultInfo> consultInfoList = consultService.getConsultInfoByUserKey(memberId);
        List<MeasureInfo> measureInfoList = measureService.getMeasureInfoByUserKey(memberId);
        List<GeneInfo> geneInfoList = geneService.getGeneInfoByUserKey(memberId);

        String memberInfo = memberInfoToString(member);
        String measureInfo = measureInfoToString(measureInfoList);
        String geneInfo = geneInfoToString(geneInfoList);
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
            result += String.format("T존:%s\nU존:%s\n", measureInfo.getTZoneResult(), measureInfo.getUZoneResult())
                    + String.format("피부등급:%s, 분석결과:\"%s\", 스킨케어팁:\"%s\"\n", measureInfo.getSolutionTypeNumber(), measureInfo.getSolutionTypeResult(), measureInfo.getSolutionTypeTip())
                    + String.format("민감등급:%s, 분석결과:\"%s\", 스킨케어팁:\"%s\"\n", measureInfo.getSensitiveTypeNumber(), measureInfo.getSensitiveTypeResult(), measureInfo.getSensitiveTypeTip())
                    + String.format("피부고민 - 1.피부의 노화관련 특성 \"모공:%s,주름:%s,미래주름:%s,탄력:%s\" 2.피부의 밝음과 투명도 특성 \"색소침착:%s,멜라닌:%s\" 3.피부의 외부 자극 반응도 특성 \"붉은기:%s,포피린:%s,경피수분손실:%s\""
                            , measureInfo.getPoreString()
                            , measureInfo.getWrinkleString()
                            , measureInfo.getFuturewrinklesString()
                            , measureInfo.getElasticityString()
                            , measureInfo.getPigmemtationString()
                            , measureInfo.getMelaninString()
                            , measureInfo.getRednessString()
                            , measureInfo.getPorphyrinString()
                            , measureInfo.getTransdermalString());
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
//
//    @Operation(summary = "고객List", description = "고객 목록 조회")
//    @GetMapping("/list")
//    public BaseResponse<List<Member>> getMemberList(
////            @AuthenticationPrincipal MemberDetail member
//    ) {
//        List<Member> memberList = new ArrayList<Member>();
//        return BaseResponse.success(memberList);
//    }


    @Operation(summary = "고객리스트(전체,이름, 나이, 채팅, 순서)", description = "고객리스트(전체,이름, 나이, 채팅, 순서) param")
    @GetMapping("/list")
    public BaseResponse<List<MemberList>> getMemberSearch(
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false) String order,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age
    ) {
        List<MemberList> allMembers = memberService.loadUsersList(orderBy, order, name, age);
        List<MemberList> memberListList = new ArrayList<>();

        for (MemberList member : allMembers) {
            MemberList memberList = new MemberList(
                    member.getId(),
                    member.getName(),
                    member.getSex(),
                    member.getAge(),
                    member.getConcern1(),
                    member.getConcern2(),
                    member.getConsultCount(),
                    member.getBirthday(),
                    member.getPhone(),
                    member.getBirthCd(),
                    member.getExtractedYear(),
                    member.getSite()
            );
            memberListList.add(memberList);
        }
        return BaseResponse.success(memberListList);
    }


}
