package ai.maum.mcl.skins.api.proai.service;

import ai.maum.mcl.skins.api.common.BaseResponse;
import ai.maum.mcl.skins.api.proai.model.ChatroomDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class CallProaiService {
    @Value("https://i-dev-mcl-proai-api.apddev.com")
    private String proaiUrl;
    @Value("skins")
    private String proaiVendorId;
    @Value("eeb17afa-eb9e-4d3a-be7a-03d0b7bd496f")
    private String proaiApiKey;

    private final RestTemplate restTemplate;

    public CallProaiService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public BaseResponse<List<ChatroomDetail>> callProaiChatGet(String url) {
        String apiUrl = proaiUrl + url;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Api-Key", proaiApiKey);
        headers.set("Vendor-Id", proaiVendorId);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<ChatroomDetail>> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ChatroomDetail>>() {});

         if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return BaseResponse.success(responseEntity.getBody());
        } else {
            throw new RuntimeException("Failed to fetch data: " + responseEntity.getStatusCode());
        }
    }

}
