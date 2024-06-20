package ai.maum.mcl.skins.api.consult.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class ConsultDirect {
    private Long id;
    private Timestamp consultDate;
    private String userkey;
    private String consultData;
    private String concern1;
    private String concern2;
    private String product;
    private String significant;
    private String etc;
    private String consultNumber;
    private String manager;
    private List<ConsultFeature> features;
}


//    private static final Logger logger = LoggerFactory.getLogger(ConsultDirect.class);
//    private String id;
//    @JsonProperty("consult_date")
//    private Timestamp consult_date;
//    @JsonProperty("user_key")
//    private Long userKey;
//    @JsonProperty("consult_data")
//    private String consult_data;
//    private String concern1;
//    private String concern2;
//    private String product;
//    private String significant;
//    private String etc;
//    @JsonProperty("consult_number")
//    private Long consultNumber;
//    private String manager;
//
//    @JsonIgnore
//    private String features;
//    @JsonProperty("feature_list")
//    private List<Feature> featureList;
//    public ConsultDirect() {
//    }
//
//    public ConsultDirect(String id, Timestamp consult_date, Long userKey, String consult_data, String concern1, String concern2, String product, String significant, String etc, String features, Long consultNumber, String manager) {
//        this.id = id;
//        this.consult_date = consult_date;
//        this.userKey = userKey;
//        this.consult_data = consult_data;
//        this.concern1 = concern1;
//        this.concern2 = concern2;
//        this.product = product;
//        this.significant = significant;
//        this.etc = etc;
//        this.features = features;
//        this.featureList = parseFeatures(features);
//        this.consultNumber = consultNumber;
//        this.manager = manager;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getManager() {
//        return manager;
//    }
//
//    public void setManager(String manager) {
//        this.manager = manager;
//    }
//    @JsonProperty("consult_date")
//    public Timestamp getConsultDate() {
//        return consult_date;
//    }
//    @JsonProperty("consult_date")
//    public void setConsultDate(Timestamp consultDate) {
//        this.consult_date = consultDate;
//    }
//    @JsonProperty("user_key")
//    public Long getUserKey() {
//        return userKey;
//    }
//    @JsonProperty("user_key")
//    public void setUserKey(Long userKey) {
//        this.userKey = userKey;
//    }
//    @JsonProperty("consult_data")
//    public String getConsultData() {
//        return consult_data;
//    }
//    @JsonProperty("consult_data")
//    public void setConsultData(String consultData) {
//        this.consult_data = consultData;
//    }
//
//    public String getConcern1() {
//        return concern1;
//    }
//
//    public void setConcern1(String concern1) {
//        this.concern1 = concern1;
//    }
//
//    public String getConcern2() {
//        return concern2;
//    }
//
//    public void setConcern2(String concern2) {
//        this.concern2 = concern2;
//    }
//
//    public String getProduct() {
//        return product;
//    }
//
//    public void setProduct(String product) {
//        this.product = product;
//    }
//
//    public String getSignificant() {
//        return significant;
//    }
//
//    public void setSignificant(String significant) {
//        this.significant = significant;
//    }
//
//    public String getEtc() {
//        return etc;
//    }
//
//    public void setEtc(String etc) {
//        this.etc = etc;
//    }
//
//    @JsonProperty("consult_number")
//    public Long getconsultNumber() {
//        return consultNumber;
//    }
//    @JsonProperty("consult_number")
//    public void setconsultNumber(Long consultNumber) {
//        this.consultNumber = consultNumber;
//    }
//    @JsonProperty("features")
//    public String getFeatures() {
//        return features;
//    }
//    @JsonProperty("features")
//    public void setFeatures(String features) {
//        this.features = features;
//        this.featureList = parseFeatures(features);
//    }
//
//    @JsonProperty("feature_list")
//    public List<Feature> getFeatureList() {
//        return featureList;
//    }
//
//    @JsonProperty("feature_list")
//    public void setFeatureList(List<Feature> featureList) {
//        this.featureList = featureList;
//        this.features = serializeFeatures(featureList);
//
//    }
//
//
//    private List<Feature> parseFeatures(String features) {
//        List<Feature> featureList = new ArrayList<>();
//        if (features != null && !features.isEmpty()) {
//            features = features.trim();
//            if (features.startsWith("[") && features.endsWith("]")) {
//                features = features.substring(1, features.length() - 1).trim();
//                String[] featureArray = features.split("\\},\\{");
//                for (String feature : featureArray) {
//                    feature = feature.replaceAll("[{}\"]", "").trim();
//                    String[] parts = feature.split(",");
//                    int value = 0;
//                    String label = "";
//                    String description = "";
//                    for (String part : parts) {
//                        String[] keyValue = part.split(":");
//                        if (keyValue.length == 2) {
//                            String key = keyValue[0].trim();
//                            String val = keyValue[1].trim();
//                            if (key.equals("value")) {
//                                try {
//                                    value = Integer.parseInt(val);
//                                } catch (NumberFormatException e) {
//                                    e.printStackTrace();
//                                }
//                            } else if (key.equals("label")) {
//                                label = val;
//                            } else if (key.equals("description")) {
//                                description = val;
//                            }
//                        }
//                    }
//                    featureList.add(new Feature(value, label, description));
//                }
//            }
//        }
//        return featureList;
//    }
//
////    public String serializeFeatures(List<Feature> featureList) {
////        logger.info("featureList: {}",featureList.toString());
////        ObjectMapper objectMapper = new ObjectMapper();
////        try {
////            return objectMapper.writeValueAsString(featureList);
////        } catch (Exception e) {
////            logger.error("Error serializing features", e);
////            return "";
////        }
////    }
//
//    public String serializeFeatures(List<Feature> featureList) {
//        if (featureList == null || featureList.isEmpty()) {
//            return "";
//        }
//        StringBuilder sb = new StringBuilder();
//        sb.append("[");
//        for (int i = 0; i < featureList.size(); i++) {
//            Feature feature = featureList.get(i);
//            sb.append("{\"value\":").append(feature.getValue())
//                    .append(",\"label\":\"").append(feature.getLabel())
//                    .append(",\"description\":\"").append(feature.getDescription()).append("\"}");
//            if (i < featureList.size() - 1) {
//                sb.append(",");
//            }
//        }
//        sb.append("]");
//        logger.info("sb: {}", sb.toString());
//        return sb.toString();
//    }
//
//
//    public static class Feature {
//        private int value;
//        private String label;
//        private String description;
//
//        public Feature(int value, String label, String description) {
//            this.value = value;
//            this.label = label;
//            this.description = description;
//        }
//
//        public int getValue() {
//            return value;
//        }
//
//        public void setValue(int value) {
//            this.value = value;
//        }
//
//        public String getLabel() {
//            return label;
//        }

//        public void setLabel(String label) {
//            this.label = label;
//        }
//
//        public String getDescription() {
//            return description;
//        }
//
//        public void setDescription(String description) {
//            this.description = description;
//        }
//    }

//}
