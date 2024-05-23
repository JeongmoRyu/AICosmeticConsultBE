package ai.maum.mcl.skins.api.consult.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ConsultIndirect {
//    @Schema(description = "key", example = "11111")
//    private Long id;
    @Schema(description = "사용자 이름", example = "가나다")
    private String name;
    @Schema(description = "상담일시", example = "2024-01-01 00:00:00")
    private Timestamp consultDate;
    @Schema(description = "피부고민1", example = "기미잡티")
    private String concern1;
    @Schema(description = "피부고민2", example = "여드름트러블")
    private String concern2;
    @Schema(description = "제품정보", example = "라네즈제품")
    private String product;
    @Schema(description = "상담사", example = "가나다")
    private String manager;
    @Schema(description = "지역", example = "서울")
    private String region;
    @Schema(description = "caseType", example = "타입은--")
    private String caseType;
    @Schema(description = "상담내용", example = "상담내용 이고객은 어쩌구 저쩌구")
    private String consultInfo;
    @Schema(description = "특이사항", example = "특이사항 어쩌구저쩌구")
    private String significant;
}
