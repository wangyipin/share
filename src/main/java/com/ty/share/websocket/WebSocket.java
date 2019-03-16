package com.ty.share.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @author 04637
 * @date 2018/12/24
 */
@Component
@ServerEndpoint("/websocket/{username}")
public class WebSocket {
    private static int onlineCount = 0;

    /**
     * 为什么使用 ConcurrentHashMap {@link com.ty.share.threadpool.ThreadPool}
     */
    private static final Map<String, WebSocket> clients = new ConcurrentHashMap<>();

    private static final Logger logger = LogManager.getLogger();

    private Session session;

    private String username;

    // TODO: 2019/3/16 补充多窗口推送
    private int windowCount;

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) {
        this.username = username;
        this.session = session;
        WebSocket client = clients.get(username);
        if (client != null) {
            client.windowCount++;
            logger.debug("用户: [" + username + "] 打开新的窗口. 窗口数: " + client.windowCount);
        } else {
            addOnlineCount();
            this.windowCount++;
            clients.put(username, this);
            logger.debug("用户: [" + username + "] 已连接");
        }

    }

    @OnClose
    public void onClose() {
        WebSocket client = clients.get(username);
        if (client.windowCount > 1) {
            client.windowCount--;
            logger.debug("用户: [" + username + "] 关闭了一个窗口. 窗口数: " + client.windowCount);
        } else {
            clients.remove(username);
            logger.debug("用户: [" + username + "] 已断开");
            subOnlineCount();
        }

    }

    @OnMessage
    public void onMessage(String message) {
        JSONObject jo = JSON.parseObject(message);
        String mes = (String) jo.get("content");

        if (!jo.get("to").equals("All")) {
            sendMessageTo(mes, jo.get("to").toString());
        } else {
            sendMessageAll(mes);
        }

    }

    @OnError
    public void onError(Throwable error) {
        logger.error(error.getMessage());
    }

    private void sendMessageTo(String message, String to) {
        WebSocket ws = clients.get(to);
        if(ws != null){
            ws.session.getAsyncRemote().sendText(message);
        }
    }

    private void sendMessageAll(String message) {
        for (WebSocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }

    public static synchronized Map<String, WebSocket> getClients() {
        return clients;
    }
}
