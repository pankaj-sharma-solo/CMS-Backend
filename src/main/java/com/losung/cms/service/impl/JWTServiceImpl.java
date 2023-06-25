package com.losung.cms.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.losung.cms.entity.ApiKeys;
import com.losung.cms.exception.BusinessException;
import com.losung.cms.repository.ApiKeysRepository;
import com.losung.cms.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class JWTServiceImpl implements JWTService {

    @Value("${cms.private.key}")
    private String cmsPrivateKey;

    @Value("${cms.public.key}")
    private String cmsPublicKey;

    @Autowired
    private ApiKeysRepository apiKeysRepository;

    @Override
    public String createToken(String clientId, String apiKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        ApiKeys apiKeysByClientId = apiKeysRepository.getApiKeysByClientId(clientId);
        if(apiKeysByClientId == null || !apiKeysByClientId.getApiKey().equalsIgnoreCase(apiKey)) {
            throw BusinessException.builder()
                    .statusCode(HttpStatus.NOT_ACCEPTABLE)
                    .message("Invalid client id or api key.")
                    .build();
        }
        RSAPrivateKey cmsPrivateKey = getCMSPrivateKey();
        Algorithm algorithm = Algorithm.RSA256(cmsPrivateKey);
        return generateJWTToken(Map.of("client-id", clientId, "iss", "CMS"), algorithm);
    }

    @Override
    public DecodedJWT validateToken(String authToken) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Algorithm algorithm = Algorithm.RSA256(getCMSPublicKey());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(authToken);
        return decodedJWT;
    }

    private String generateJWTToken(Map<String, String> payload, Algorithm algo) {
        JWTCreator.Builder builder = JWT.create()
                .withExpiresAt(Date.from(Instant.now().plusSeconds(86400)))
                .withIssuedAt(Date.from(Instant.now()));
        for(String key: payload.keySet()){
            builder.withClaim(key, payload.get(key));
        }
        return builder.sign(algo);
    }

    private RSAPublicKey getCMSPublicKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] decode = Base64.getMimeDecoder().decode(cmsPublicKey.trim());

        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decode);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
    }

    private RSAPrivateKey getCMSPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decode = Base64.getMimeDecoder().decode(cmsPrivateKey.trim());

        EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(decode);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(encodedKeySpec);
        return privateKey;
    }
}
