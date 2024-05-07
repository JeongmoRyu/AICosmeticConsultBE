package ai.maum.mcl.skins.api.member.model;

import ai.maum.mcl.skins.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberList {

  @Schema(description = "ID", example = "1")
    private Long id;

    @Schema(description = "Name", example = "가나다")
    private String name;

    @Schema(description = "Sex", example = "F")
    private String sex;

    @Schema(description = "Age", example = "24")
    private Integer age;

    @Schema(description = "Birthday", example = "20000101")
    private String birthday;

    @Schema(description = "First Concern", example = "피부고민1")
    private String concern1;

    @Schema(description = "Second Concern", example = "피부고민2")
    private String concern2;

    @Schema(description = "Consult Count", name = "consult_count", example = "0")
    @JsonProperty("consult_count")
    private Long consultCount;

    @Schema(description = "Phone Number", example = "01012345678")
    private String phone;

    @Schema(description = "Birth Code", example = "L")
    private String birthCd;

    @Schema(description = "Extracted Year", name = "extracted_year", example = "2000")
    @JsonProperty("extracted_year")
    private String extractedYear;

    @Schema(description = "Site", example = "부산")
    private String site;

    public MemberList(Long id, String name, String sex, Integer age, String concern1, String concern2, Long consultCount, String birthday, String phone, String birthCd, String extractedYear, String site) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.concern1 = concern1;
        this.concern2 = concern2;
        this.consultCount = consultCount;
        this.birthday = birthday;
        this.phone = phone;
        this.birthCd = birthCd;
        this.extractedYear = extractedYear;
        this.site = site;
    }

    public MemberList() {
    }

    public Integer getAge() {
        boolean isLunar = "L".equals(this.birthCd);
        return DateUtil.calculateAge(this.birthday, isLunar);
    }

    @JsonProperty("extracted_year")
    public String getExtractedYear() {
        return (birthday != null && birthday.length() >= 4) ? birthday.substring(0, 4) : null;
    }

    @JsonProperty("consult_count")
    public Long getConsultCount() {
        return consultCount;
    }

}
