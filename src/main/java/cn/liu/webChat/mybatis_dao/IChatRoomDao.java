package cn.liu.webChat.mybatis_dao;

import cn.liu.webChat.domain.ChatRoom;
import cn.liu.webChat.domain.RoomUser;
import org.apache.ibatis.annotations.Param;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

/**
 * created by liufeng
 * 2019/7/25
 */
public interface IChatRoomDao {
    void saveChatRoom(ChatRoom chatRoom);

    int countPersonOfChatRoom(Integer roomId);

    void saveRoomUser(RoomUser roomUser);

    List<RoomUser> findRoomUserByRoomId(Integer roomId);

    List<Map<String, Object>> findChatRooms(Integer userId);

    List<Integer> findRoomUserByRoomIdNoCurrent(@Param("roomId") Integer roomId, @Param("userId") Integer userId);
}
