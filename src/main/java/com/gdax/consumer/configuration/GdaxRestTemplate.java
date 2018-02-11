package com.gdax.consumer.configuration;

import com.gdax.consumer.model.DayStat;
import com.gdax.consumer.model.Product;
import com.gdax.consumer.model.Ticker;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Component
public class GdaxRestTemplate {

    private static final String CB_ACCESS_SIGN = "CB-ACCESS-SIGN";
    private static final String CB_ACCESS_TIMESTAMP = "CB-ACCESS-TIMESTAMP";
    private static final String CB_ACCESS_PASSPHRASE = "CB-ACCESS-PASSPHRASE";
    private static final String CB_ACCESS_KEY = "CB-ACCESS-KEY";

    private static String baseUrl = "https://api.gdax.com";
    private static String coinMarketCap = "https://api.coinmarketcap.com/v1/ticker/";

    private final RestTemplate restTemplate;
    private final Config config;

    public GdaxRestTemplate(RestTemplate restTemplate, Config config) {
        this.restTemplate = restTemplate;
        this.config = config;

    }


    public DayStat getDayStat(String productId) {

        Long timeStamp = System.currentTimeMillis();
        String requestPath = "/products/" +productId+ "/stats";

        HttpHeaders headers = new HttpHeaders();
        headers.add(CB_ACCESS_KEY, config.getApiKey());
        headers.add(CB_ACCESS_TIMESTAMP, timeStamp.toString());
        headers.add(CB_ACCESS_PASSPHRASE, config.getPhrase());
        headers.add(CB_ACCESS_SIGN, config.encode("GET", requestPath, "", timeStamp).get());
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(this.baseUrl + requestPath, HttpMethod.GET, httpEntity, DayStat.class).getBody();
    }

    public Product[] getProducts() {

        Long timeStamp = System.currentTimeMillis();
        String requestPath = "/products";
        HttpHeaders headers = new HttpHeaders();
        headers.add(CB_ACCESS_KEY, config.getApiKey());
        headers.add(CB_ACCESS_TIMESTAMP, timeStamp.toString());
        headers.add(CB_ACCESS_PASSPHRASE, config.getPhrase());
        headers.add(CB_ACCESS_SIGN, config.encode("GET", requestPath, "", timeStamp).get());
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(this.baseUrl + requestPath, HttpMethod.GET, httpEntity, Product[].class).getBody();
    }

    public List<Ticker> getInfo(List<String> ticketNames) {
        return ticketNames.parallelStream()
                          .map(ticker -> restTemplate.getForObject(coinMarketCap + ticker, Ticker[].class))
                          .flatMap(Arrays::stream)
                          .collect(Collectors.toList());
    }

}
