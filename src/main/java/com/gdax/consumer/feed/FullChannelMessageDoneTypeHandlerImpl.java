package com.gdax.consumer.feed;

import com.gdax.consumer.model.Order;
import org.springframework.stereotype.Service;

@Service
public class FullChannelMessageDoneTypeHandlerImpl implements FullChannelTypeMessageHandler{
    @Override
    public void onMessage(Order message) {

    }
}
