import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

    @JsonIgnore
    private String features; // JSON 입력 시 사용될 필드
    private List<Feature> featureList; // 변환 후 사용될 필드

    // 기본 생성자
    public ConsultDirect() {
    }

    // 파라미터가 있는 생성자
    public ConsultDirect(String id, Timestamp consultDate, Long userKey, String consultData, String concern1, String concern2, String product, String significant, String etc, String features) {
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
    }

    // Getter 및 Setter 메서드
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

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
        this.featureList = parseFeatures(features); // String 값이 변경될 때 List로 변환
    }

    public List<Feature> getFeatureList() {
        return featureList;
    }

    public void setFeatureList(List<Feature> featureList) {
        this.featureList = featureList;
    }

    // features 필드를 List<Feature>로 변환하는 메서드
    private List<Feature> parseFeatures(String features) {
        List<Feature> featureList = new ArrayList<>();
        if (features != null && !features.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                // JSON 배열 문자열을 List<Feature>로 변환
                featureList = mapper.readValue(features, mapper.getTypeFactory().constructCollectionType(List.class, Feature.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return featureList;
    }

    public static class Feature {
        private int value;
        private String description;

        // 기본 생성자
        public Feature() {
        }

        // 파라미터가 있는 생성자
        public Feature(int value, String description) {
            this.value = value;
            this.description = description;
        }

        // Getter 및 Setter 메서드
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
