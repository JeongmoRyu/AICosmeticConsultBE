package ai.maum.mcl.skins.api.member.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class MemberSearch {

    private Long id;
    private String name;
    private String birthyear;
    private String sex;
    private Integer age;
    private String concern1;
    private String concern2;
    private Integer visitNum;
    private String phone;

    public MemberSearch(Long id, String name, String sex, Integer age, String concern1, String concern2, Integer visitNum, String birthyear, String phone) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.concern1 = concern1;
        this.concern2 = concern2;
        this.visitNum = visitNum;
        this.birthyear = birthyear;
        this.phone = phone;

    }

    public MemberSearch() {
    }
}
