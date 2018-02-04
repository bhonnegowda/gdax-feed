package com.gdax.consumer.feed;

import com.gdax.consumer.model.Order;
import org.springframework.stereotype.Service;

@Service
public class FullChannelMessageOpenTypeHandlerImpl implements FullChannelTypeMessageHandler {
    @Override
    public void onMessage(Order message) {

    }
}
