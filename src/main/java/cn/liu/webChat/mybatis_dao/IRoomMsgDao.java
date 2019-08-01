package cn.liu.webChat.mybatis_dao;

import cn.liu.webChat.domain.RoomMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * created by liufeng
 * 2019/7/25
 */
public interface IRoomMsgDao {
    void saveMsg(RoomMsg roomMsg);

    List<Map<String, Object>> initMsg(@Param("roomId") Integer roomId, @Param("index") int page);

    List<Map<String, Object>> realTimeMsg(@Param("roomId") Integer roomId, @Param("userId") Integer userId, @Param("lastTimeStr") Long lastTimeStr);
}
