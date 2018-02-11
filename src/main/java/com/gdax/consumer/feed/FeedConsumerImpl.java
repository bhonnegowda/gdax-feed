package com.gdax.consumer.feed;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdax.consumer.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FeedConsumerImpl implements FeedConsumer {

    private static final Logger log = LoggerFactory.getLogger(FeedConsumerImpl.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void onMessage(String message) {
        try {
            Order order = MAPPER.readValue(message, Order.class);
            switch (order.getType().toLowerCase()) {
            case "snapshot" :
                log.info(message);
                break;
            case "l2update" :
                log.info(message);
                break;
            default:
                log.info("unhandled");
                break;
            }
        } catch (IOException e) {
            log.error("Error Occurred while consuming", e);
        }
    }
}
