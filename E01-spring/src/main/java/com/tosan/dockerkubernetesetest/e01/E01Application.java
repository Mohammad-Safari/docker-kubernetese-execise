package com.tosan.dockerkubernetesetest.e01;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@SpringBootApplication
@RestController
@Slf4j
public class E01Application {

    public static void main(String[] args) {
        SpringApplication.run(E01Application.class, args);
    }

    @GetMapping(value = "hello")
    public String sayHello(@RequestParam(value = "name", required = false) String name) {
        return String.join(" ", "Hello", (name != null ? name : "Stranger"));
    }

    @GetMapping(value = "author")
    public String getAuthor() {
        return "MohammadSafari";
    }

}

@Component
@Slf4j
class MdcFilter extends OncePerRequestFilter {
    final String PROXY_FORWARD_HEADER = "X-FORWARDED-FOR";
    final String REQUEST_ID_HEADER = "X-HEADER-TOKEN";
    final String RESPONSE_ID_HEADER = REQUEST_ID_HEADER;
    final String CLIENT_IP_MDC_IDENTIFIER = "clientIP";
    final String REQUEST_ID_MDC_IDENTIFIER = "requestId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request != null) {
            final String token;
            String clientIp = "";
            // [reverse] proxy identifier of request ip origination
            clientIp = request.getHeader(PROXY_FORWARD_HEADER);
            if (clientIp == null || clientIp.isEmpty()) {
                clientIp = request.getRemoteAddr();
            }
            MDC.put(CLIENT_IP_MDC_IDENTIFIER, clientIp);
            if (!StringUtils.hasText(REQUEST_ID_HEADER) && StringUtils.hasText(request.getHeader(REQUEST_ID_HEADER))) {
                token = request.getHeader(REQUEST_ID_HEADER);
            } else {
                token = UUID.randomUUID().toString().toUpperCase().replace("-", "");
            }
            MDC.put(REQUEST_ID_MDC_IDENTIFIER, token);
            if (StringUtils.hasText(RESPONSE_ID_HEADER)) {
                response.addHeader(RESPONSE_ID_HEADER, token);
            }
            filterChain.doFilter(request, response);
            log.info(String.join(" ",
                    "requestServletPath:", request.getServletPath(),
                    "requestQueryString:", request.getQueryString(),
                    "responseCode:", Integer.toString(response.getStatus())));
            MDC.remove(REQUEST_ID_MDC_IDENTIFIER);
            MDC.remove(CLIENT_IP_MDC_IDENTIFIER);
        }
    }
}

