package ai.maum.mcl.skins.conf.security;

import ai.maum.mcl.skins.api.apiuser.service.ApiUserService;
import ai.maum.mcl.skins.meta.RegexMeta;
import ai.maum.mcl.skins.api.apiuser.model.ApiUser;
import ai.maum.mcl.skins.util.StringUtil;
import ai.maum.mcl.skins.meta.ConstantsMeta;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;


@Component
public class APIKeyAuthFilter extends OncePerRequestFilter {

    private ApiUserService apiUserService;
//    private MemberDetailService memberDetailService;

//    public APIKeyAuthFilter(ApiUserService apiUserService, MemberDetailService memberDetailService) {
    public APIKeyAuthFilter(ApiUserService apiUserService) {
        this.apiUserService = apiUserService;
//        this.memberDetailService = memberDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //미인증 URL 예외 처리
        String requestURI = request.getRequestURI();

//        if(StringUtil.matches(requestURI, RegexMeta.PUBLICAPI_PATH)
//            || StringUtil.matches(requestURI, RegexMeta.SWAGGER_PATHS)) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        if(StringUtil.matches(requestURI, RegexMeta.EXTAPI_PATH)) {
            String apiKey = request.getHeader(ConstantsMeta.HEADER_NAME_API_KEY);
            String vendorId = request.getHeader(ConstantsMeta.HEADER_NAME_VENDOR_ID);

            if(apiKey == null || vendorId == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("No API Key or No Vendor Id found in request headers");
                return;
            }

//            ApiUser apiUser = apiUserService.getApiUser(vendorId);

//        //api key validate check
//            if(!apiKey.equals(apiUser.getApiKey())) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.getWriter().write(vendorId + " api key is invalid");
//                return;
//            } else if (!"Y".equals(apiUser.getUseYn())) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.getWriter().write(vendorId + " is disabled");
//                return;
//            }

            UserDetails userDetails = apiUserService.loadUserByUsername(vendorId);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    userDetails.getPassword(),
                    userDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            //api validate check 이후 사용자 정보 Set
            /*
            UserDetails userDetails = memberDetailService.loadUserByUsername(memberId);

            if(userDetails == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(memberId + " not found");
                return;
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    userDetails.getPassword(),
                    userDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
             */
            filterChain.doFilter(request, response);

        } else if (StringUtil.matches(requestURI, RegexMeta.PUBLICAPI_PATH)
                || StringUtil.matches(requestURI, RegexMeta.SWAGGER_PATHS)
                || StringUtil.matches(requestURI, RegexMeta.SERVICEAPI_PATH)
                || StringUtil.matches(requestURI, RegexMeta.LOGIN_PATH)
        ) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("No API Key or No Vendor Id found in request headers");
            return;
        }
    }
}
