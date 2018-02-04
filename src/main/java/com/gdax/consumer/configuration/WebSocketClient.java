package com.gdax.consumer.configuration;

import com.gdax.consumer.feed.FeedConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/*
* */
@ClientEndpoint
public class WebSocketClient {

    private static final String WEBSOCKER_URL = "wss://ws-feed-public.sandbox.gdax.com/";

  //  private final FeedConsumer feedConsumer;
    MessageHandler messageHandler = null;

    private Session userSession = null;

    public WebSocketClient() {


        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            userSession = container.connectToServer(this, new URI(WEBSOCKER_URL));
        } catch (DeploymentException | IOException | URISyntaxException e) {
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
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    /*
    * For sending the subscribe message
    * */
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    public static interface MessageHandler {
        public void handleMessage(String message);
    }

}
