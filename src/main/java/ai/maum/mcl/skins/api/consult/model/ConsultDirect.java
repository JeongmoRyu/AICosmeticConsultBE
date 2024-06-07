import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ConsultDirect {
    private static final Logger logger = LoggerFactory.getLogger(ConsultDirect.class);
    private String id;
    private Timestamp consultDate;
    private Long userKey;
    private String consultData;
    private String concern1;
    private String concern2;
    private String product;
    private String significant;
    private String etc;
    private Long consultNumber;

    @JsonIgnore
    private String features;
    
    @JsonProperty("featureList")
    private List<Feature> featureList;

    public ConsultDirect() {
    }

    public ConsultDirect(String id, Timestamp consultDate, Long userKey, String consultData, String concern1, String concern2, String product, String significant, String etc, String features, Long consultNumber) {
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
        this.featureList = parseFeatures(features);
        this.consultNumber = consultNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getConsultDate() {
        return consultDate;
    }

    public void setConsultDate(Timestamp consultDate) {
        this.consultDate = consultDate;
    }

    public Long getUserKey() {
        return userKey;
    }

    public void setUserKey(Long userKey) {
        this.userKey = userKey;
    }

    public String getConsultData() {
        return consultData;
    }

    public void setConsultData(String consultData) {
        this.consultData = consultData;
    }

    public String getConcern1() {
        return concern1;
    }

    public void setConcern1(String concern1) {
        this.concern1 = concern1;
    }

    public String getConcern2() {
        return concern2;
    }

    public void setConcern2(String concern2) {
        this.concern2 = concern2;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSignificant() {
        return significant;
    }

    public void setSignificant(String significant) {
        this.significant = significant;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public Long getConsultNumber() {
        return consultNumber;
    }

    public void setConsultNumber(Long consultNumber) {
        this.consultNumber = consultNumber;
    }

    @JsonIgnore
    public String getFeatures() {
        return features;
    }

    @JsonIgnore
    public void setFeatures(String features) {
        this.features = features;
        this.featureList = parseFeatures(features);
    }

    @JsonProperty("featureList")
    public List<Feature> getFeatureList() {
        return featureList;
    }

    @JsonProperty("featureList")
    public void setFeatureList(List<Feature> featureList) {
        this.featureList = featureList;
        this.features = serializeFeatures(featureList);
    }

    private List<Feature> parseFeatures(String features) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(features, new TypeReference<List<Feature>>() {});
        } catch (Exception e) {
            logger.error("Error parsing features", e);
            return new ArrayList<>();
        }
    }

    public String serializeFeatures(List<Feature> featureList) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(featureList);
        } catch (Exception e) {
            logger.error("Error serializing features", e);
            return "";
        }
    }

    public static class Feature {
        private int value;
        private String description;

        public Feature() {
        }

        public Feature(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
