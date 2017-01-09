package cn.liu.webChat.dao.impl;

import cn.liu.webChat.dao.IRedisDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by ruichang on 2016/12/15
 */
@Repository
public class IRedisDaoImpl implements IRedisDao {
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public List<Object> findFromRedis(List<String> keys) {
        List<Object> productIds = new ArrayList();
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        for (String key : keys) {
            Object result = operations.get(key);
            productIds.add(result);
        }
        return productIds;
    }

    @Override
    public Object findFromRedis(String key) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        Object result = operations.get(key);
        return result;
    }

    @Override
    public boolean insertToRedis(Map<String, String> msg) {
        String roomId=msg.get("roomId");
        Map<String,String> map=new LinkedHashMap<>();
        String user=msg.get("user");
        String message=msg.get("message");
        map.put(user,message);
        boolean flag = redisTemplate.hasKey(roomId);
        if(flag){
            //更新
            Map<String,String> chatInfo= (Map<String, String>) findFromRedis(roomId);
            chatInfo.put(user,message);
            try {
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                operations.set(roomId, chatInfo);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                operations.set(roomId, map);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
