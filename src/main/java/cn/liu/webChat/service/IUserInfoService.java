package cn.liu.webChat.service;

import cn.liu.webChat.domain.UserInfo;

/**
 * created by liufeng
 * 2019/7/29
 */
public interface IUserInfoService {
    UserInfo findUserByUsername(String username);
}
