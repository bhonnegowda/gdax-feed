package com.gdax.consumer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gdax.consumer.configuration.GdaxRestTemplate;
import com.gdax.consumer.configuration.WebSocketClient;
import com.gdax.consumer.model.DayStat;
import com.gdax.consumer.model.Product;
import com.gdax.consumer.model.Subscribe;
import com.gdax.consumer.model.Ticker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class GdaxController {

    private final GdaxRestTemplate gdaxRestTemplate;
    private final WebSocketClient webSocketClient;

    public GdaxController(GdaxRestTemplate gdaxRestTemplate, WebSocketClient webSocketClient) {
        this.gdaxRestTemplate = gdaxRestTemplate;
        this.webSocketClient = webSocketClient;
    }

    @GetMapping("/gdax/products")
    public Product[] getSupportedProducts() {
        return gdaxRestTemplate.getProducts();
    }

    @GetMapping("/gdax/products/{productId}/stat")
    public DayStat getStat(@PathVariable String productId) {
        return gdaxRestTemplate.getDayStat(productId);
    }

    @PostMapping("/websocket/start")
    public void start() {

        String[] products = {"ETH-USD", "BCH-USD", "BTC-USD", "LTC-USD"};
        Subscribe subscribe = new Subscribe(products);

        try {
            webSocketClient.sendMessage(subscribe);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/products")
    public List<Ticker> getProduct() {

        String[] products = {"", "district0x", "agrello-delta", "maecenas"};
        return gdaxRestTemplate.getInfo(Arrays.asList(products));
    }
}
