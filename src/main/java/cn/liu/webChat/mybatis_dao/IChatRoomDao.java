package cn.liu.webChat.mybatis_dao;

import cn.liu.webChat.domain.ChatRoom;
import cn.liu.webChat.domain.RoomUser;

import java.util.List;

/**
 * created by liufeng
 * 2019/7/25
 */
public interface IChatRoomDao {
    void saveChatRoom(ChatRoom chatRoom);

    int countPersonOfChatRoom(Integer roomId);

    void saveRoomUser(RoomUser roomUser);

    List<RoomUser> findRoomUserByRoomId(Integer roomId);
}
