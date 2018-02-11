package com.gdax.consumer.configuration;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Configuration
public class Config {

    @Value("${gdax.api.phrase}")
    private String phrase;

    @Value("${gdax.api.secret}")
    private String secret;

    @Value("${gdax.api.key}")
    private String apiKey;

    private static final String HMAC_SHA_256 = "HmacSHA256";

    public String getPhrase() {
        return phrase;
    }

    public String getSecret() {
        return secret;
    }

    public String getApiKey() {
        return apiKey;
    }
    public Optional<String> encode(String method, String requestPath, String body, Long timeStamp) {
        String what = timeStamp + method + requestPath + body;
        byte[] secret = java.util.Base64.getDecoder().decode(getSecret());
        SecretKeySpec key = new SecretKeySpec(secret, HMAC_SHA_256);
        Mac sha256_HMAC = null;
        try {
            sha256_HMAC = Mac.getInstance(HMAC_SHA_256);
            sha256_HMAC.init(key);
            return Optional.ofNullable(Base64.encodeBase64String(sha256_HMAC.doFinal(what.getBytes("UTF-8"))));
        } catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

}
