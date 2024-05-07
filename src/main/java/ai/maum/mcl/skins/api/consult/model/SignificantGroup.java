package ai.maum.mcl.skins.api.consult.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class SignificantGroup {
    @Schema(description = "분류", example = "유전자")
    private String code;
    @Schema(description = "명칭", example = "스킨케어 추천")
    private String description;
    @Schema(description = "값", example = "0")
    private Integer value;
}
