package ai.maum.mcl.skins.api.consult.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ConsultIndirect {
    @Schema(description = "userkey",example = "11111")
    private String userkey;
    @Schema(description = "사용자 이름", example = "가나다")
    private String name;
    @Schema(description = "상담시작",name = "consult_time",example = "2024-01-01 00:00:00")
    @JsonProperty("consult_time")
    private Timestamp consultTime;
    @Schema(description = "상담요약정보",name = "consult_data", example = "요약된 내용-----")
    @JsonProperty("consult_data")
    private String consultData;
    @Schema(description = "상담사", example = "가나다")
    private String manager;
    @Schema(description = "타입",name = "consult_type",example = "타입은--")
    @JsonProperty("consult_type")
    private String consultType;
    @Schema(description = "특이사항", example = "특이사항 어쩌구저쩌구")
    private String significant;
}
