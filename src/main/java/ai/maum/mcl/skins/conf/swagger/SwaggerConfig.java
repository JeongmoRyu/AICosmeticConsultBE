package ai.maum.mcl.skins.conf.swagger;

import ai.maum.mcl.skins.meta.ConstantsMeta;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;
import java.util.List;

@Configuration
public class SwaggerConfig {
    @Value("${service.swagger.title}")
    private String title;
    @Value("${service.swagger.desc}")
    private String desc;
    @Value("${service.server.url}")
    private String serverUrl;
    @Bean
    public OpenAPI skinsSwagger() {
        return new OpenAPI()
                .info(new Info().title("SKINS API").version("1.0.0").description("SKINS API"))
                .servers(List.of(new Server().url(serverUrl)))
                .addSecurityItem(new SecurityRequirement()
                        .addList("api_key").addList("vendor_id").addList("member_id"))
                .components(new Components()
                        .addSecuritySchemes("api_key", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name(ConstantsMeta.HEADER_NAME_API_KEY))
                        .addSecuritySchemes("vendor_id", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name(ConstantsMeta.HEADER_NAME_VENDOR_ID))
                        .addSecuritySchemes("member_id", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name(ConstantsMeta.HEADER_NAME_MEMBER_ID)));
    }
}
