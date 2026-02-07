package com.example.droneclashserver.handler;

import com.example.droneclashserver.model.GameAction;
import com.example.droneclashserver.model.GameState;
import com.example.droneclashserver.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class GameHandler extends TextWebSocketHandler {

    private final GameService gameService;
    private final ObjectMapper objectMapper;
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public GameHandler(GameService gameService, ObjectMapper objectMapper) {
        this.gameService = gameService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("New connection established: " + session.getId());
        sendGameState(session, gameService.getGameState());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            GameAction action = objectMapper.readValue(message.getPayload(), GameAction.class);
            GameState updatedState = gameService.processAction(action);
            broadcast(updatedState);
        } catch (IOException e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Connection closed: " + session.getId());
    }

    private void sendGameState(WebSocketSession session, GameState gameState) throws IOException {
        String gameStateJson = objectMapper.writeValueAsString(gameState);
        session.sendMessage(new TextMessage(gameStateJson));
    }

    private void broadcast(GameState gameState) {
        String gameStateJson;
        try {
            gameStateJson = objectMapper.writeValueAsString(gameState);
        } catch (IOException e) {
            System.err.println("Error serializing game state: " + e.getMessage());
            return;
        }

        for (WebSocketSession session : sessions) {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(gameStateJson));
                }
            } catch (IOException e) {
                System.err.println("Error sending message to session " + session.getId() + ": " + e.getMessage());
            }
        }
    }
}
