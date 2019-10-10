package cn.liu.webChat.service;

import cn.liu.webChat.domain.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * created by liufeng
 * 2019/7/29
 */
public interface IUserInfoService {
    UserInfo findUserByUsername(String username);

    List<UserInfo> findUserByNickname(String nickname);

    void saveUser(UserInfo user);

    List<UserInfo> friends(Integer userId);

    void updateInfo(UserInfo userInfo);

    List<Map<String, Object>> findRoomMember(Integer roomId);
}
