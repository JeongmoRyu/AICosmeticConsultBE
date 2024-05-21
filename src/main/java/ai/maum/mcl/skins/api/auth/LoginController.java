package ai.maum.mcl.skins.api.auth;

import ai.maum.mcl.skins.api.manager.service.ManagerLoginService;
import ai.maum.mcl.skins.api.manager.service.ManagerService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ai.maum.mcl.skins.api.manager.model.Manager;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.security.Key;


@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name="로그인인증", description="로그인")

public class LoginController {

    @Value("${service.jwt.key}")
    private String jwtKey;

    private final ManagerLoginService managerLoginService;
    private final ManagerService managerService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/api/auth/login")
    public Map<String, String> login(
            @RequestBody @Parameter(name = "사용자(매니저)정보", required = true) Manager manager
    ) {
        log.debug("login controller!!!");

        UserDetails userDetails = managerLoginService.loadUserByUsername(manager.getManagerId());

        if(userDetails != null && passwordEncoder.matches(manager.getSkinsPwd(), userDetails.getPassword())) {
            Key key = Keys.hmacShaKeyFor(jwtKey.getBytes());
            Map<String,String> claims = new HashMap<String,String>();
            claims.put("manager_id", manager.getManagerId());
            claims.put("id", String.valueOf(userDetails.getUsername()));
            claims.put("name", manager.getName());
            String token = Jwts.builder()
//                    .setSubject(String.valueOf(userDetails.getUsername()))
//                    .setSubject(manager.getManagerId())
                    .setClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) //한시간
                    .signWith(key, SignatureAlgorithm.HS512)
//                    .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(jwtKey.getBytes()))
                    .compact();
            Map<String,String> response = new HashMap<String,String>();
            response.put("token", token);
            return response;
        }

        throw new RuntimeException("Invalid Login Credentials");
    }
    @PostMapping("/api/auth/regist")
    public Manager regist(
            @RequestBody @Parameter(name = "사용자(매니저)정보", required = true) Manager manager
    ) {



        managerService.registManager(manager);

        return manager;
    }
}
