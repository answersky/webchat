package cn.liu.webChat.service.impl;

import cn.liu.webChat.domain.UserInfo;
import cn.liu.webChat.mybatis_dao.IUserInfoDao;
import cn.liu.webChat.service.IUserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * created by liufeng
 * 2019/7/29
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements IUserInfoService {
    @Resource
    private IUserInfoDao userInfoDao;

    @Override
    public UserInfo findUserByUsername(String username) {
        return userInfoDao.findUserByUsername(username);
    }
}
