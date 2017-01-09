package cn.liu.webChat.service.impl;

import cn.liu.webChat.service.IRedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xcy
 * on 2016/12/15.
 */
@Service
public class RedisServiceImpl implements IRedisService{

    @Resource
    RedisTemplate redisTemplate;

    @Override
    public List<Object> findFromRedis(List<String> keys) {
        List<Object> list = new ArrayList<>();
        for (String key: keys
             ) {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            Object result = operations.get(key);
            list.add(result);
        }
        return list;
    }

    @Override
    public Object findFromRedis(String key) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        Object result = operations.get(key);
        return result;
    }

    @Override
    public boolean insertMapValue(Map<String,Object> map) {
        for (String key: map.keySet()) {
            boolean flag = redisTemplate.hasKey(key);
            if (flag) {
                return false;
            }

            try {
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                operations.set(key,map.get(key));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return false;
    }


}
