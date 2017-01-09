package cn.liu.webChat.service;

import cn.qegoo.model.oms.User;

import java.util.List;
import java.util.Map;

/**
 * Created by asus-p on 2016/12/15.
 */
public interface IRedisService {
    /**
     * 根据key去redis查询
     *
     * @param keys
     * @return
     */
    public List<Object> findFromRedis(List<String> keys);

    /**
     * 根据key查询产品id
     *
     * @param key
     * @return
     */
    public Object findFromRedis(String key);


    /**
     * 插入任意形式
     * @param map
     * @return
     */
    boolean insertMapValue(Map<String,Object> map);


}
