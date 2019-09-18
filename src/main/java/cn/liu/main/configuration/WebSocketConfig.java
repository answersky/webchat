package cn.liu.main.configuration;

import cn.liu.webChat.service.impl.ChatHandshakeInterceptor;
import cn.liu.webChat.service.impl.ChatMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * created by liufeng
 * 2019/9/17
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private ChatMessageHandler chatMessageHandler;

    /**
     * 第一个addHandler是对正常连接的配置，
     * 第二个是如果浏览器不支持websocket，使用socketjs模拟websocket的连接。
     *
     * @param registry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatMessageHandler, "/webSocketServer").addInterceptors(new ChatHandshakeInterceptor());
        registry.addHandler(chatMessageHandler, "/sockjs/webSocketServer").addInterceptors(new ChatHandshakeInterceptor()).withSockJS();
    }

}
