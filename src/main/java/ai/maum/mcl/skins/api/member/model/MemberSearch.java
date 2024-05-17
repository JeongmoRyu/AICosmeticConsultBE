package ai.maum.mcl.skins.api.member.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSearch {
    private String memberInfo;
    private String consultInfo;

    public MemberSearch(String memberInfo, String consultInfo) {
        this.memberInfo = memberInfo;
        this.consultInfo = consultInfo;
    }

    public MemberSearch() {
    }
}
