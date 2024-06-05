import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

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
    private String features;
    private List<Feature> featureList;

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
    // (기존 Getter/Setter 생략)

    // features 필드를 List<Feature>로 변환하는 메서드
    private List<Feature> parseFeatures(String features) {
        List<Feature> featureList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            featureList = objectMapper.readValue(features, new TypeReference<List<Feature>>() {});
        } catch (Exception e) {
            e.printStackTrace();
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

        @Override
        public String toString() {
            return "Feature{" +
                    "value=" + value +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    // 로깅을 위한 메서드
    private static void logFeatures(List<Feature> features) {
        for (Feature feature : features) {
            System.out.println("Feature - Value: " + feature.getValue() + ", Description: " + feature.getDescription());
        }
    }

    public static void main(String[] args) {
        String json = "[{\"value\": 0, \"description\":\"바보야\"},{\"value\": 1, \"description\":\"천재야\"}]";
        ConsultDirect consultDirect = new ConsultDirect();
        List<Feature> featureList = consultDirect.parseFeatures(json);
        logFeatures(featureList);
    }
}
