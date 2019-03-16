package com.ty.share.websocket;

import com.alibaba.fastjson.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 消息控制器
 * @author: 04637
 * @date: 2019/3/15
 **/
@RestController
@RequestMapping("/message")
public class MessageController {

    private WebSocket webSocket;
    private static Logger logger = LogManager.getLogger();

    @RequestMapping("/sendMsg.do")
    public void sendMsg(String username, String message){
        logger.debug(username+": "+message);
        JSONObject jo = new JSONObject();
        jo.put("to", username);
        jo.put("content", message);
        System.out.println(webSocket);
        webSocket.onMessage(jo.toJSONString());
    }



    @Autowired
    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }
}
