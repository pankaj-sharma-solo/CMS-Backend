package com.losung.cms.controller;

import com.losung.cms.exception.BusinessException;
import com.losung.cms.service.JWTService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/jwt")
@Tag(name = "JWT Controller")
public class JWTController {

    @Autowired
    private JWTService jwtService;

    @GetMapping("/create")
    @Operation(description = "Create Authorization token using client_id and api-key.")
    public ResponseEntity<String> createJWT(@RequestParam("client_id") String clientId, @RequestParam("api-key") String apiKey) throws Exception {
        return ResponseEntity.ok(jwtService.createToken(clientId, apiKey));
    }

    @GetMapping("/validate")
    public ResponseEntity validateJWT(@RequestParam("jwt") String token) throws Exception {
        return ResponseEntity.ok(jwtService.validateToken(token));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BusinessException> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(BusinessException.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BusinessException> handleException(BusinessException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e);
    }
}
