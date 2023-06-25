package com.losung.cms.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.losung.cms.exception.BusinessException;
import com.losung.cms.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
@Component
public class JwtValidationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    final AntPathMatcher antPathMatcher = new AntPathMatcher();

    final List<String> byPassUrl = List.of(
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/v3/api-docs/**",
            "/h2-console",
            "/h2-console/**",
            "/v1/jwt/**"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        authToken = authToken.replaceAll("Bearer ", "");
        if (StringUtils.isNotEmpty(authToken)) {
            try {
                log.info("inside authorization");
                jwtService.validateToken(authToken);
                log.info("after authorization");
                filterChain.doFilter(request,response);
                return;
            } catch (Exception e) {
                log.error("Error validation token", e);
                PrintWriter writer = response.getWriter();
                ObjectMapper mapper = new ObjectMapper();
                String message = mapper.writeValueAsString(BusinessException.builder()
                        .statusCode(HttpStatus.UNAUTHORIZED)
                        .message("Invalid Auth Token")
                        .build());
                writer.println(message);
            }
        } else {
            log.info("outside authorization");
            PrintWriter writer = response.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            String message = mapper.writeValueAsString(BusinessException.builder()
                    .statusCode(HttpStatus.UNAUTHORIZED)
                    .message("Invalid Auth Token")
                    .build());
            writer.println(message);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
       return byPassUrl.stream().anyMatch(uri -> antPathMatcher.match(uri, request.getServletPath()));
    }
}
