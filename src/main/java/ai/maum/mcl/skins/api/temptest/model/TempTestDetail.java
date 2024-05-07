package ai.maum.mcl.skins.api.temptest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TempTestDetail {
    private String tempTestInfo;

    public TempTestDetail(String tempTestInfo) {
        this.tempTestInfo = tempTestInfo;
    }
    public TempTestDetail() {

    }
}
