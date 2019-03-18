package com.ty.share.websocket;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 消息控制器
 * @author: 04637
 * @date: 2019/3/15
 **/
@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {

    private WebSocket webSocket;

    @RequestMapping("/sendMsg.do")
    public void sendMsg(String username, String message){
        log.debug(username+": "+message);
        JSONObject jo = new JSONObject();
        jo.put("to", username);
        jo.put("content", message);
        webSocket.onMessage(jo.toJSONString());
    }

    @Autowired
    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }
}
