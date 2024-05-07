package ai.maum.mcl.skins.mybatis.vo;

import ai.maum.mcl.skins.util.DateUtil;
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
public class MemberDetailVO implements UserDetails {
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean enabled;

    private String name;
    private String birthday;
    private String birthCd;
    private String sex;
    private Integer age;

    public MemberDetailVO(String id, String name, String birthday, String birthCd, String sex) {
        this.username = id;
        this.name = name;
        this.birthday = birthday;
        this.birthCd = birthCd;
        this.sex = sex;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> authorities = new ArrayList<String>();
        authorities.add("ROLE_USER");
        this.authorities = authorities
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return this.authorities;
    }

    public Integer getAge() {
        return DateUtil.calculateAge(this.birthday, "L".equals(this.birthCd)?true:false);
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
}
