package cn.liu.webChat.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public interface IRedisDao {

    /**
     * 根据key去redis查询产品id
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
     * 插入产品到redis
     *
     * @param msg
     * @return
     */
    public boolean insertToRedis(Map<String,String> msg);

}
