package ai.maum.mcl.skins.api.consult.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ConsultInfo {
    @Schema(description = "key", example = "11111")
    private String id;
    @Schema(description = "상담일시", example = "2024-01-01 00:00:00")
    private Timestamp consultDate;
    @Schema(description = "사용자Key", example = "9999")
    private String userKey;
    @Schema(description = "상담정보(Full)", example = "블랙헤드와 솜털이 많아 블라 블라~~~~")
    private String consultData;
    @Schema(description = "피부고민1", example = "기미잡티")
    private String concern1;
    @Schema(description = "피부고민2", example = "여드름트러블")
    private String concern2;
    @Schema(description = "추천제품", example = "에스트라 아토베리어 크림")
    private String product;
    @Schema(description = "특이사항", example = "특이사항은 블라 블라 ~~~~~~")
    private String significant;
    @Schema(description = "기타(메모)", example = "메모할 사항은 블라 블라 ~~~~~")
    private String etc;
}
