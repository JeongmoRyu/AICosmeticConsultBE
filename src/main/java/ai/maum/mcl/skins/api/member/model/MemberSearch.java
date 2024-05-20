package ai.maum.mcl.skins.api.member.model;

import ai.maum.mcl.skins.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSearch {

    private Long id;
    private String name;
    private String sex;
    private Integer age;
    private String birthday;
    private String concern1;
    private String concern2;
    private Long consultCount;
    private String phone;
    private String birthCd;
    private String ExtractedYear;


    public MemberSearch(Long id, String name, String sex, Integer age, String concern1, String concern2, Long consultCount,String birthday, String phone, String birthCd, String ExtractedYear) {
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
        this.ExtractedYear = ExtractedYear;

    }

    public MemberSearch() {
    }

    public Integer getAge() {
        boolean isLunar = "L".equals(this.birthCd);
        return DateUtil.calculateAge(this.birthday, isLunar);
    }
    public String getExtractedYear() {
        return (birthday != null && birthday.length() >= 4) ? birthday.substring(0, 4) : null;
    }

}
