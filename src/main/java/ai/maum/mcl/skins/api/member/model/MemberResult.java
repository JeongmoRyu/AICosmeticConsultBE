package ai.maum.mcl.skins.api.member.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResult {
    private String memberInfo;
    private String consultInfo;
    private String measureInfo;
    private String geneInfo;

    public MemberResult(String memberInfo, String consultInfo, String measureInfo, String geneInfo) {
        this.memberInfo = memberInfo;
        this.measureInfo = measureInfo;
        this.geneInfo = geneInfo;
        this.consultInfo = consultInfo;
    }

    public MemberResult() {
    }
}
