package cn.liu.webChat.service.impl;

import cn.liu.webChat.domain.RoomMsg;
import cn.liu.webChat.domain.UserInfo;
import cn.liu.webChat.mybatis_dao.IChatRoomDao;
import cn.liu.webChat.service.IChatMessageService;
import cn.liu.webChat.service.IUserInfoService;
import com.alibaba.druid.support.json.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * created by liufeng
 * 2019/9/17
 */
@Service("chatMessageHandler")
public class ChatMessageHandler extends TextWebSocketHandler {

    @Resource
    private IUserInfoService userInfoService;
    @Resource
    private IChatMessageService chatMessageService;
    @Resource
    private IChatRoomDao chatRoomDao;

    private Logger logger = LoggerFactory.getLogger(ChatMessageHandler.class);

    /**
     * key is userId  value is WebSocketSession
     */
    private static Map<Integer, WebSocketSession> sessionMap = new LinkedHashMap<>();

    /**
     * 连接成功
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        String username = (String) attributes.get("username");
        Integer userId = (Integer) attributes.get("userId");
        logger.error(username + "连接上服务");
        sessionMap.put(userId, session);
        logger.error("当前存在的socket服务" + sessionMap);
    }

    /**
     * 消息处理
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        String username = (String) attributes.get("username");
        Integer userId = (Integer) attributes.get("userId");
        String msgInfo = message.getPayload();
        Map<String, Object> map = (Map<String, Object>) JSONUtils.parse(msgInfo);
        //type=0 聊天消息   type=1  通知对方有新的联系人加入
        Integer type = (Integer) map.get("type");
        if (type == 0) {
            Integer roomId = (Integer) map.get("roomId");
            String msg = (String) map.get("msg");
            //获取聊天室内所有的用户，给在线的用户发送消息
            logger.error(username + "发送了一条消息：" + msg);
            //获取昵称
            UserInfo userInfo = userInfoService.findUserByUsername(username);
            String nickName = userInfo.getNickname();
            Date time = new Date();
            //组装成消息推送给对方
            Map<String, Object> msgMap = new LinkedHashMap<>();
            msgMap.put("nickname", nickName);
            msgMap.put("create_time", time);
            msgMap.put("roomId", roomId);
            msgMap.put("message", msg);
            msgMap.put("type", type);
            List<Integer> roomUsers = chatRoomDao.findRoomUserByRoomIdNoCurrent(roomId, userId);
            for (Integer sessionKey : roomUsers) {
                if (sessionMap.containsKey(sessionKey)) {
                    //获取对方的websocket 通道
                    WebSocketSession webSocketSession = sessionMap.get(sessionKey);
                    //给在线的用户推送消息
                    if (webSocketSession.isOpen()) {
                        String info = JSONUtils.toJSONString(msgMap);
                        TextMessage textMessage = new TextMessage(info.getBytes("utf-8"));
                        webSocketSession.sendMessage(textMessage);
                    }

                }
            }

            //页面发送html消息转义
            if (msg.contains("&lt;") || msg.contains("&gt;")) {
                msg = msg.replaceAll("&lt;", "<");
                msg = msg.replaceAll("&gt;", ">");
            }

            //保存消息记录
            RoomMsg userMessage = new RoomMsg();
            userMessage.setRoom_id(roomId);
            userMessage.setUser_id(userId);
            userMessage.setTime_str(time.getTime());
            userMessage.setCreate_time(time);
            userMessage.setMsg(msg);
            chatMessageService.saveMessage(userMessage);
        } else {
            Integer roomId = (Integer) map.get("roomId");
            Integer friendId = (Integer) map.get("userId");
            Map<String, Object> msgMap = new LinkedHashMap<>();
            msgMap.put("roomId", roomId);
            msgMap.put("type", type);
            if (sessionMap.containsKey(friendId)) {
                //获取对方的websocket 通道
                WebSocketSession webSocketSession = sessionMap.get(friendId);
                //给在线的用户推送消息
                if (webSocketSession.isOpen()) {
                    String info = JSONUtils.toJSONString(msgMap);
                    TextMessage textMessage = new TextMessage(info.getBytes("utf-8"));
                    webSocketSession.sendMessage(textMessage);
                }

            }
        }


    }

    /**
     * 连接异常
     *
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        exitChat(session);
    }

    /**
     * 连接关闭
     *
     * @param session
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        exitChat(session);
    }

    /**
     * 退出聊天
     *
     * @param session
     */
    private void exitChat(WebSocketSession session) {
        Map<String, Object> attributes = session.getAttributes();
        Integer userId = (Integer) attributes.get("userId");
        String username = (String) attributes.get("username");
        //移除sessionMap
        sessionMap.remove(userId);
        logger.error(username + "退出聊天");
    }

    /*public void clearSessionByUserId(Integer userId,String username){
        sessionMap.remove(userId);
        logger.error(username + "退出聊天");
    }*/

}
