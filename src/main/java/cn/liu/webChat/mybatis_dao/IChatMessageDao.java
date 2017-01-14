package cn.liu.webChat.mybatis_dao;

import cn.liu.webChat.domain.UserMessage;

import java.util.List;

/**
 * Created by liuf on 2017/1/14.
 */
public interface IChatMessageDao {
    /**
     * 保存消息
     *
     * @param userMessage
     */
    void saveMessage(UserMessage userMessage);

    /**
     * 初始化消息
     *
     * @return
     */
    List<UserMessage> findInitMessage();

    /**
     * 接收消息
     *
     * @param timeStr
     * @return
     */
    List<UserMessage> reciveMessage(Long timeStr);
}
