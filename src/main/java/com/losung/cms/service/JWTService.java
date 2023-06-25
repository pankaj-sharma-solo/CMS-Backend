package com.losung.cms.service;

import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface JWTService {
    String createToken(String clientId, String apiKey) throws NoSuchAlgorithmException, InvalidKeySpecException;

    DecodedJWT validateToken(String authToken) throws InvalidKeySpecException, NoSuchAlgorithmException;
}
