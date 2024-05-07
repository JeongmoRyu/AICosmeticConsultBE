package ai.maum.mcl.skins.api.measure.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class MeasureInfo {
    @Schema(description = "설문고유번호", example = "9999")
    private Long surveyno;
    @Schema(description = "설문일자", example = "2024-01-01")
    private String surveydate;
    @Schema(description = "사용자Key", example = "9999")
    private Long userkey;
    @Schema(description = "피부점수", example = "99")
    private Integer skinScore;
    @Schema(description = "피부고민", example = "미래주름, 색소침착")
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
    @Schema(description = "솔루션결과", example = "솔루션 결과")
    private String solutionTypeResult;
    @Schema(description = "솔루션타입뷰티팁", example = "솔루션 순서에 따라 관리하세요")
    private String solutionTypeTip;
    @Schema(description = "민감도타입번호", example = "S1")
    private String sensitiveTypeNumber;
    @Schema(description = "민감도결과", example = "민감도 결과입니다.")
    private String sensitiveTypeResult;
    @Schema(description = "민감도타입뷰티팁", example = "솔루션 순서에 따라 관리하세요")
    private String sensitiveTypeTip;
    @Schema(description = "분석결과 메모", example = "메모할 사항은 블라 블라 ~~~~~~")
    private String specialtipMemo;
    @Schema(description = "피부고민-(Aging)모공", example = "81.3")
    private float pore;
    @Schema(description = "피부고민-(Aging)모공", example = "81.3")
    private float pore;
    @Schema(description = "피부고민-(Aging)주름", example = "70")
    private float wrinkle;
    @Schema(description = "피부고민-(Aging) 미래주름", example = "29.3")
    private float futurewrinkles;
    @Schema(description = "피부고민-(Tone) 탄력", example = "55.3")
    private float elasticity;
    @Schema(description = "피부고민-(Tone) 색소침착", example = "29.5")
    private float pigmentation;
    @Schema(description = "피부고민-(Tone) 멜라닌", example = "0")
    private float melanin;
    @Schema(description = "피부고민-(Sensitivity) 붉은기", example = "0")
    private float redness;
    @Schema(description = "피부고민-(Sensitivity) 포피린", example = "100")
    private float porphyrin;
    @Schema(description = "피부고민-(Sensitivity) 경피수분손실도", example = "47.9")
    private float transdermal;

    public String getPoreString() {
        return getResultString(pore);
    }
    public String getWrinkleString() {
        return getResultString(wrinkle);
    }
    public String getFuturewrinkleString() {
        return getResultString(futurewrinkles);
    }
    public String getElasticityString() {
        return getResultString(elasticity);
    }
    public String getPigmemtationString() {
        return getResultString(pigmentation);
    }
    public String getMelaninString() {
        return getResultString(melanin);
    }
    public String getRednessString() {
        return getResultString(redness);
    }
    public String getPorphyrinString() {
        return getResultString(porphyrin);
    }
    public String getTransdermalString() {
        return getResultString(transdermal);
    }

    private String getResultString(float score) {
        if(score >= 80.0) {
            return "매우좋음";
        } else if (score >= 60) {
            return "좋음";
        } else if (score >= 40) {
            return "보통";
        } else if (score >= 30) {
            return "관심필요";
        } else {
            return "집중관리";
        }
    }

}
