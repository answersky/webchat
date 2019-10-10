package cn.liu.webChat.mybatis_dao;

import cn.liu.webChat.domain.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * created by liufeng
 * 2019/7/25
 */
public interface IUserInfoDao {
    void saveUserInfo(UserInfo userInfo);

    UserInfo findUserInfo(Integer id);

    UserInfo findUserByUsername(String username);

    List<UserInfo> findUserByNickname(@Param("nickname") String nickname);

    List<UserInfo> findFrineds(@Param("roomIds") List<Integer> roomIds, @Param("userId") Integer userId);

    void updateInfo(UserInfo userInfo);

    List<Map<String, Object>> findRoomMember(Integer roomId);
}
