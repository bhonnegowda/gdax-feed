package com.gdax.consumer.controller;

import com.gdax.consumer.configuration.GdaxRestTemplate;
import com.gdax.consumer.model.DayStat;
import com.gdax.consumer.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GdaxController {


    private final GdaxRestTemplate gdaxRestTemplate;

    public GdaxController(GdaxRestTemplate gdaxRestTemplate) {
        this.gdaxRestTemplate = gdaxRestTemplate;
    }

    @GetMapping("/products")
    public Product[] getSupportedProducts() {
        return gdaxRestTemplate.getProducts();
    }

    @GetMapping("/products/{productId}/stat")
    public DayStat getStat(@PathVariable String productId) {
        return gdaxRestTemplate.getDayStat(productId);
    }
}
