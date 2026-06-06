package br.com.msacademico.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor authorizationRequestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                HttpServletRequest request = attrs.getRequest();
                String auth = request.getHeader("Authorization");
                if (auth != null)
                    requestTemplate.header("Authorization", auth);
                String role = request.getHeader("X-User-Role");
                if (role != null)
                    requestTemplate.header("X-User-Role", role);
                String username = request.getHeader("X-Auth-Username");
                if (username != null)
                    requestTemplate.header("X-Auth-Username", username);
                String userId = request.getHeader("X-User-Id");
                if (userId != null)
                    requestTemplate.header("X-User-Id", userId);
            }
        };
    }
}
