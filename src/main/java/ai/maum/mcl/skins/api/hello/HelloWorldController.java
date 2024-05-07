package ai.maum.mcl.skins.api.hello;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name="test-hello-world")
public class HelloWorldController {
//    @Operation(summary = "테스트", description = "테스트용 api")
//    @GetMapping("/api/public/test")
    public String test() {
        String myEncryptionKey = System.getenv("JASYPT_ENCRYPTOR_PASSWORD");
        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword(myEncryptionKey);

        String param1 = "jdbc:postgresql://138.2.119.126:45432/maumchatplay?currentSchema=proai";
        String param2 = "maum.chatplay";
        String param3 = "chatplay123!";

//        String param1 = "jdbc:postgresql://genai-dev-common-pg-rds.ck8v2txozggz.ap-northeast-2.rds.amazonaws.com/genaidev?currentSchema=skins";
//        String param2 = "skinsown";
//        String param3 = "skinsown123!";

        log.debug("param1:" + textEncryptor.encrypt(param1));
        log.debug("param2:" + textEncryptor.encrypt(param2));
        log.debug("param3:" + textEncryptor.encrypt(param3));

        return "Hello MegaCity Lab!!";
    }

//    @Operation(summary = "테스트-apikey", description = "apikey 테스트용 api (일반)")
//    @GetMapping("/api/keytest")
    public ResponseEntity<String> apiKeyTestSecure(
    ) {

        return ResponseEntity.ok("Secure data accessed");
    }

//    @Operation(summary = "테스트-apikey", description = "apikey 테스트용 api (public-미인증")
//    @GetMapping("/api/public/keytest")
    public ResponseEntity<String> apiKeyTestPublic() {
        log.debug("apiKeyTestPublic");
        return ResponseEntity.ok("public data accessed");
    }

}

