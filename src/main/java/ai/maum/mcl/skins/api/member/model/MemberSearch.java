package ai.maum.mcl.skins.api.member.model;

import ai.maum.mcl.skins.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberList {

    private Long id;
    private String name;
    private String sex;
    private Integer age;
    private String birthday;
    private String concern1;
    private String concern2;
    private Long consult_count;
    private String phone;
    private String birthCd;
    private String extracted_year;
    private String site;


    public MemberList(Long id, String name, String sex, Integer age, String concern1, String concern2, Long consult_count, String birthday, String phone, String birthCd, String extracted_year, String site) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.concern1 = concern1;
        this.concern2 = concern2;
        this.consult_count = consult_count;
        this.birthday = birthday;
        this.phone = phone;
        this.birthCd = birthCd;
        this.extracted_year = extracted_year;
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
        return consult_count;
    }
}
