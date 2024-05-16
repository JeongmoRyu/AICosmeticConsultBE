package ai.maum.mcl.skins.api.temptest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TempTestInfo {
    @Schema(description = "설문고유번호", example = "9999")
    private Long surveyno;
    @Schema(description = "설문일자", example = "2024-01-01")
    private String surveydate;
    @Schema(description = "사용자Key", example = "9999")
    private Long userkey;
    @Schema(description = "피부점수", example = "99")
    private Integer skinScore;
    @Schema(description = "피부고민", example = "미래주름,색소침착")
    private String skinGomin;
    @Schema(description = "복합성여부", example = "Y")
    private String iscomplexity;
    @Schema(description = "T존결과", example = "유수분 균형 중성")
    private String tZoneResult;
    @Schema(description = "T존위치", example = "5")
    private Integer tZonePositionNum;
    @Schema(description = "U존결과", example = "수분부족 유분과다 지성")
    private String uZoneResult;
    @Schema(description = "U존위치", example = "1")
    private Integer uZonePositionNum;
    @Schema(description = "Fizpatrick척도", example = "5")
    private Integer fizpatrickColorPoint;
    @Schema(description = "솔루션타입번호", example = "19")
    private String solutionTypeNumber;
    @Schema(description = "솔루션결과", example = "솔루션결과입니다.")
    private String solutionTypeResult;
    @Schema(description = "솔루션타입뷰티팁", example = "이러니 이러 이러하게 관리 하세요")
    private String solutionTypeTip;
    @Schema(description = "민감도타입번호", example = "S1")
    private String sensitiveTypeNumber;
    @Schema(description = "민감도결과", example = "민감도 결과 입니다.")
    private String sensitiveTypeResult;
    @Schema(description = "민감도타입뷰티팁", example = "저러니 저러 저러하게 관리 하세요")
    private String sensitiveTypeTip;
    @Schema(description = "분석결과 메모", example = "결과가 어쩌고 저쩌고")
    private String specialtipMemo;



}
