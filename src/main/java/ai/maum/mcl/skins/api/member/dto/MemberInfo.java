package ai.maum.mcl.skins.api.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfo {
    private String id;
    private String name;
    private String sex;
    private Integer age;

    public MemberInfo(String id, String name, String sex, Integer age) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
    }
}
