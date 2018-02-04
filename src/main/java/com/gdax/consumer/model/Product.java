package com.gdax.consumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    @JsonProperty("id")
    private String id;

    @JsonProperty("base_currency")
    private String baseCurrency;

    @JsonProperty("quote_currency")
    private String quoteCurrency;

    @JsonProperty("base_min_size")
    private String baseMinSize;

    @JsonProperty("base_max_size")
    private String baseMaxSize;

    @JsonProperty("quote_increment")
    private String quoteIncrement;

    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public void setQuoteCurrency(String quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    public String getBaseMinSize() {
        return baseMinSize;
    }

    public void setBaseMinSize(String baseMinSize) {
        this.baseMinSize = baseMinSize;
    }

    public String getBaseMaxSize() {
        return baseMaxSize;
    }

    public void setBaseMaxSize(String baseMaxSize) {
        this.baseMaxSize = baseMaxSize;
    }

    public String getQuoteIncrement() {
        return quoteIncrement;
    }

    public void setQuoteIncrement(String quoteIncrement) {
        this.quoteIncrement = quoteIncrement;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Product{");
        sb.append("id='").append(id).append('\'');
        sb.append(", baseCurrency='").append(baseCurrency).append('\'');
        sb.append(", quoteCurrency='").append(quoteCurrency).append('\'');
        sb.append(", baseMinSize='").append(baseMinSize).append('\'');
        sb.append(", baseMaxSize='").append(baseMaxSize).append('\'');
        sb.append(", quoteIncrement='").append(quoteIncrement).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
