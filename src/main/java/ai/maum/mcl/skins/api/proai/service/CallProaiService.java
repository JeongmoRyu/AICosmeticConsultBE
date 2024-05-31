package ai.maum.mcl.skins.api.proai.service;

import ai.maum.mcl.skins.api.common.BaseResponse;
import ai.maum.mcl.skins.api.proai.model.ChatroomDetail;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CallProaiService {
    @Value("https://i-dev-mcl-proai-api.apddev.com")
    private String proaiUrl;
    @Value("skins")
//    @Value("pro-ai")
    private String proaiVendorId;
    @Value("eeb17afa-eb9e-4d3a-be7a-03d0b7bd496f")
//    @Value("f0179c5d-056e-4c4b-9f83-1e6a0780bfb4")
    private String proaiApiKey;

    private final RestTemplate restTemplate;

    public CallProaiService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<ChatroomDetail> callProaiChatGet(String url) {
        String apiUrl = proaiUrl + url;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Api-Key", proaiApiKey);
        headers.set("Vendor-Id", proaiVendorId);



        HttpEntity<String> entity = new HttpEntity<>(headers);
        log.debug(String.valueOf(entity));
        log.info("SKINS CALL START:" + proaiApiKey + ":" + proaiVendorId + ":" + apiUrl);
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    entity,
                    String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String responseBody = responseEntity.getBody();
                log.info("responsebody: {}", responseBody);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode data = objectMapper.readTree(responseBody).path("data");
                log.info("data: {}", data);
                List<ChatroomDetail> chatroomDetails = new ArrayList<>();
                if (data.isArray()) {
                    log.info("Array data :", data);
                    chatroomDetails = objectMapper.readValue(
                            data.traverse(),
                            new TypeReference<List<ChatroomDetail>>() {});
                } else {
                    log.info("Not Array data :", data);
                    ChatroomDetail singleDetail = objectMapper.convertValue(
                            data,
                            ChatroomDetail.class
                    );
                    chatroomDetails = new ArrayList<>();
                    chatroomDetails.add(singleDetail);
                }
                return chatroomDetails;

            } else {
                throw new RuntimeException("Failed to fetch data: " + responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Error translate during API call: {}", e.getMessage());
            throw new RuntimeException("Error translate during API call", e);
        }
    }

    public String callProaiGet(String url) {
        String apiUrl = proaiUrl + url;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Api-Key", proaiApiKey);
        headers.set("Vendor-Id", proaiVendorId);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                String.class);

        String strBody = responseEntity.getBody();
        log.info("strBody : {}", strBody);
        return strBody;
    }


    public String callProaiPost(String url, String request) {

        String apiUrl = proAiUrl + url + "/?message=" + request;
        log.info("apiUrl: {}", apiUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Api-Key", proAiApiKey);
        headers.set("Vendor-Id", proAiVendorId);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                String.class);

        String strBody = responseEntity.getBody();
        log.info("strBody: {}", strBody);
        return strBody;
    }


    // @Operation(summary = "test", description = "test")
    // @GetMapping({"/proai/test"})
    // public String getChatDetail (
    //         @RequestParam(value="url") String url
    // ) {
    //     return callProaiService.callProaiGet(url);
    // }

}
