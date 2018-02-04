package com.gdax.consumer.feed;

@FunctionalInterface
public interface FeedConsumer {
    void onMessage(String message);
}
