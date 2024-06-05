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
        this.featureList = parseFeatures(features);
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
            features = features.trim();
            if (features.startsWith("[") && features.endsWith("]")) {
                features = features.substring(1, features.length() - 1).trim(); // 제거할 '[' 및 ']'
                String[] featureArray = features.split("\\},\\{");
                for (String feature : featureArray) {
                    feature = feature.replaceAll("[{}\"]", "").trim();
                    String[] parts = feature.split(",");
                    int value = 0;
                    String description = "";
                    for (String part : parts) {
                        String[] keyValue = part.split(":");
                        if (keyValue.length == 2) {
                            String key = keyValue[0].trim();
                            String val = keyValue[1].trim();
                            if (key.equals("value")) {
                                try {
                                    value = Integer.parseInt(val);
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            } else if (key.equals("description")) {
                                description = val;
                            }
                        }
                    }
                    featureList.add(new Feature(value, description));
                }
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
        String json = "[{value: 0, description:바보야},{value: 1, description:천재야}]";
        ConsultDirect consultDirect = new ConsultDirect();
        List<Feature> featureList = consultDirect.parseFeatures(json);
        logFeatures(featureList);
    }
}
