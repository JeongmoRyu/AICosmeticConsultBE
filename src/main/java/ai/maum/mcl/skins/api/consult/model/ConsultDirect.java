import java.sql.Timestamp;

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

    // No-argument constructor
    public ConsultDirect() {
    }

    // Parameterized constructor
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
    }

    // Getter methods
    public String getId() {
        return id;
    }

    public Timestamp getConsultDate() {
        return consultDate;
    }

    public Long getUserKey() {
        return userKey;
    }

    public String getConsultData() {
        return consultData;
    }

    public String getConcern1() {
        return concern1;
    }

    public String getConcern2() {
        return concern2;
    }

    public String getProduct() {
        return product;
    }

    public String getSignificant() {
        return significant;
    }

    public String getEtc() {
        return etc;
    }

    public String getFeatures() {
        return features;
    }

    // Setter methods
    public void setId(String id) {
        this.id = id;
    }

    public void setConsultDate(Timestamp consultDate) {
        this.consultDate = consultDate;
    }

    public void setUserKey(Long userKey) {
        this.userKey = userKey;
    }

    public void setConsultData(String consultData) {
        this.consultData = consultData;
    }

    public void setConcern1(String concern1) {
        this.concern1 = concern1;
    }

    public void setConcern2(String concern2) {
        this.concern2 = concern2;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setSignificant(String significant) {
        this.significant = significant;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public void setFeatures(String features) {
        this.features = features;
    }
}
