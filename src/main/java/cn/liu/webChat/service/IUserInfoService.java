package cn.liu.webChat.service;

import cn.liu.webChat.domain.UserInfo;

import java.util.List;

/**
 * created by liufeng
 * 2019/7/29
 */
public interface IUserInfoService {
    UserInfo findUserByUsername(String username);

    List<UserInfo> findUserByNickname(String nickname);
}
