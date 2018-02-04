package com.gdax.consumer.configuration;

import com.gdax.consumer.model.DayStat;
import com.gdax.consumer.model.Product;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Component
public class GdaxRestTemplate {

    private static final String CB_ACCESS_SIGN = "CB-ACCESS-SIGN";
    private static final String CB_ACCESS_TIMESTAMP = "CB-ACCESS-TIMESTAMP";
    private static final String CB_ACCESS_PASSPHRASE = "CB-ACCESS-PASSPHRASE";
    private static final String CB_ACCESS_KEY = "CB-ACCESS-KEY";
    private static final String HMAC_SHA_256 = "HmacSHA256";

    @Value("${gdax.api.phrase}")
    private String phrase;

    @Value("${gdax.api.secret}")
    private String secret;

    @Value("${gdax.api.key}")
    private String apiKey;

    private static String baseUrl = "https://api.gdax.com";

    private final RestTemplate restTemplate;

    public GdaxRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private Optional<String> encode(String method, String requestPath, String body, Long timeStamp) {
        String what = timeStamp + method + requestPath + body;
        byte[] secret = java.util.Base64.getDecoder().decode(this.secret);
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

    public DayStat getDayStat(String productId) {

        Long timeStamp = System.currentTimeMillis();
        String requestPath = "/products/" +productId+ "/stats";

        HttpHeaders headers = new HttpHeaders();
        headers.add(CB_ACCESS_KEY, apiKey);
        headers.add(CB_ACCESS_TIMESTAMP, timeStamp.toString());
        headers.add(CB_ACCESS_PASSPHRASE, phrase);
        headers.add(CB_ACCESS_SIGN, encode("GET", requestPath, "", timeStamp).get());
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(this.baseUrl + requestPath, HttpMethod.GET, httpEntity, DayStat.class).getBody();
    }

    public Product[] getProducts() {

        Long timeStamp = System.currentTimeMillis();
        String requestPath = "/products";
        HttpHeaders headers = new HttpHeaders();
        headers.add(CB_ACCESS_KEY, apiKey);
        headers.add(CB_ACCESS_TIMESTAMP, timeStamp.toString());
        headers.add(CB_ACCESS_PASSPHRASE, phrase);
        headers.add(CB_ACCESS_SIGN, encode("GET", requestPath, "", timeStamp).get());
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(this.baseUrl + requestPath, HttpMethod.GET, httpEntity, Product[].class).getBody();
    }

}
