package com.gdax.consumer.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdax.consumer.feed.FeedConsumer;
import com.gdax.consumer.model.Subscribe;
import org.springframework.stereotype.Component;

import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/*
* */
@ClientEndpoint
@Component
public class WebSocketClient {

    private static final String WEBSOCKER_URL = "wss://ws-feed.gdax.com/";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final FeedConsumer feedConsumer;

    private Session userSession = null;

    private final Config config;
    public WebSocketClient(Config config, FeedConsumer feedConsumer) {

        this.config = config;
        this.feedConsumer = feedConsumer;

        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        container.setDefaultMaxBinaryMessageBufferSize(1024 * 1024);
        container.setDefaultMaxTextMessageBufferSize(1024 * 1024);
        try {
            userSession = container.connectToServer(this, new URI(WEBSOCKER_URL));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
        if (this.feedConsumer != null) {
            this.feedConsumer.onMessage(message);
        }
    }

    /*
    * For sending the subscribe message
    * */
    public void sendMessage(Subscribe msg) throws JsonProcessingException {
        String message = MAPPER.writeValueAsString(msg);
        this.userSession.getAsyncRemote().sendText(message);
    }

}
