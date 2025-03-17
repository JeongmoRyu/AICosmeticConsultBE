package ai.maum.mcl.skins.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.util.List;

public class ObjectMapperUtil {

    private static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return objectMapper;
    }

    // 객체를 JSON 문자열로 변환하는 정적 메서드
    public static String writeValueAsString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            // 예외 처리: 실패시 적절한 처리나 로깅을 수행
            e.printStackTrace();
            return null; // 혹은 적절한 예외를 던질 수 있습니다.
        }
    }

    // JSON 문자열을 Java 객체로 변환하는 정적 메서드
    public static <T> T readValue(String content, Class<T> valueType) {
        try {
            return OBJECT_MAPPER.readValue(content, valueType);
        } catch (JsonProcessingException e) {
            // 예외 처리: 실패 시 적절한 처리나 로깅을 수행
            e.printStackTrace();
            return null; // 혹은 적절한 예외를 던질 수 있습니다.
        }
    }

    public static <T> List<T> readValueToList(String content, Class<T> valueType) {
        try {
            TypeReference<List<T>> typeRef = new TypeReference<List<T>>() {};
            return OBJECT_MAPPER.readValue(content, typeRef);
        } catch (JsonProcessingException e) {
            // 예외 처리: 실패 시 적절한 처리나 로깅을 수행
            e.printStackTrace();
            return null; // 혹은 적절한 예외를 던질 수 있습니다.
        }
    }
}
