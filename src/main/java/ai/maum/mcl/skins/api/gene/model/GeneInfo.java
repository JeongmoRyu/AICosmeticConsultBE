package ai.maum.mcl.skins.api.gene.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneInfo {
    @Schema(description = "key", example = "11111")
    private String surveyDate;
    private String surveyId;
    private String categoryCd;
    private String categoryName;
    private String itemCd;
    private String itemName;
    private String grade;
    private String gradeName;
    private String description;

}
