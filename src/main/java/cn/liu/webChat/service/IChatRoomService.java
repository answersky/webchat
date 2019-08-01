package cn.liu.webChat.service;

import cn.liu.webChat.domain.ChatRoom;

import java.util.List;
import java.util.Map;

/**
 * created by liufeng
 * 2019/7/25
 */
public interface IChatRoomService {
    /**
     * 创建聊天室(等同于加好友)
     *
     * @param adminId
     * @param userId
     */
    void createSingleChatRoom(Integer adminId, Integer userId);

    /**
     * 创建聊天室(等同于加好友)
     *
     * @param adminId
     * @param userId
     */
    void createGroupChatRoom(Integer roomId, Integer adminId, Integer userId);

    /**
     * 添加成员
     *
     * @param roomId
     * @param userId
     */
    void addMember(Integer roomId, Integer userId, String username, String is_admin);

    List<Map<String, Object>> findChatRoom(Integer userId);
}
