package ai.maum.mcl.skins.api.consult.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsultDirect {
    private String id;
    private Timestamp consultDate;
    private Long userKey;
    private String consultData;
    private String concern1;
    private String concern2;
    private String product;
    private String significant;
    private String etc;
    private List<Feature> features;


    public ConsultDirect(String id, Timestamp consultDate, Long userKey, String consultData, String concern1, String concern2, String product, String significant, String etc, List<Feature> features) {
        this.id = id;
        this.consultDate = consultDate;
        this.userKey = userKey;
        this.consultData = consultData;
        this.concern1 = concern1;
        this.concern2 = concern2;
        this.product = product;
        this.significant = significant;
        this.etc = etc;
        this.features = features;
    }
    public static class Feature {
        private int value;
        private String description;

        public Feature(int value, String description) {
            this.value = value;
            this.description = description;
        }
    }

}
