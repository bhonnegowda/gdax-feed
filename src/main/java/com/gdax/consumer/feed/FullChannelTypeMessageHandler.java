package com.gdax.consumer.feed;

import com.gdax.consumer.model.Order;

public interface FullChannelTypeMessageHandler {
    void onMessage(Order message);
}
