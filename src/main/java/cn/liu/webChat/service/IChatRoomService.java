package cn.liu.webChat.service;

import cn.liu.webChat.domain.ChatRoom;

import java.util.List;

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
    Integer createSingleChatRoom(Integer adminId, Integer userId);

    /**
     * 创建聊天室(等同于建群)
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

    List<ChatRoom> findChatRoom(Integer userId);

    ChatRoom findChatRoomById(Integer roomId);

    Integer checkRoom(Integer adminId, Integer userId);

    /**
     * 检查是否在线
     *
     * @param userId
     * @return
     */
    boolean checkRoomOnline(Integer userId);

    List<Integer> findRoomUserByRoomIdNoCurrent(Integer roomId, Integer userId);
}
