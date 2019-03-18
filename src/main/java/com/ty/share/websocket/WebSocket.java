package com.ty.share.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 04637
 * @date 2018/12/24
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{username}")
public class WebSocket {

    /**
     * todo 1.为什么使用 ConcurrentHashMap
     *
     * @see WhyConcurrent#main(String[]) }
     */
    private static final Map<String, List<WebSocket>> clients = new ConcurrentHashMap<>();

    private Session session;

    private String username;

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) {
        this.username = username;
        this.session = session;
        List<WebSocket> windows = clients.get(username);
        if (windows != null && !windows.isEmpty()) {
            // todo 2.多窗口推送
            windows.add(this);
            log.debug("用户: [" + username + "] 打开新的窗口. 窗口数: " + windows.size());
        } else {
            List<WebSocket> userClients2 = new LinkedList<>();
            userClients2.add(this);
            clients.put(username, userClients2);
            log.debug("用户: [" + username + "] 已连接");
        }

    }

    @OnClose
    public void onClose() {
        List<WebSocket> windows = clients.get(username);
        if (windows != null && windows.size() > 1) {
            // todo 2.多窗口推送
            windows.removeIf(window -> window.session.getId().equals(session.getId()));
            log.debug("用户: [" + username + "] 关闭了一个窗口. 窗口数: " + windows.size());
        } else {
            clients.remove(username);
            log.debug("用户: [" + username + "] 已断开");
        }

    }

    @OnMessage
    public void onMessage(String message) {
        JSONObject jo = JSON.parseObject(message);
        String msg = jo.getString("content");
        String to = jo.getString("to");
        if (clients.containsKey(to)) {
            sendMessageTo(msg, jo.get("to").toString());
        }
    }

    @OnError
    public void onError(Throwable error) {
        log.error(error.getMessage());
    }

    private void sendMessageTo(String message, String to) {
        for (WebSocket window : clients.get(to)) {
            window.session.getAsyncRemote().sendText(message);
        }
    }

}
