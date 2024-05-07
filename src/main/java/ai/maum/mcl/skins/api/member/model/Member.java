package ai.maum.mcl.skins.api.member.model;

import ai.maum.mcl.skins.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private String id;
    private String name;
    private String sex;
    private Integer age;
    private String birthCd;
    private String birthday;

//    id, name, birthday, birth_cd, sex


    public Member(String id, String name, String birthday, String birthCd, String sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.birthCd = birthCd;
        this.birthday = birthday;
    }

    public Member(String id, String name, String sex, Integer age) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public Integer getAge() {
        return DateUtil.calculateAge(this.birthday, "L".equals(this.birthCd)?true:false);
    }

}
