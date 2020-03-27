package cn.edu.ujs.lp.intells.common.utils;

import java.io.Serializable;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public final class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    /*
    判断是否存在指定的键值
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
    追加键值对
     */
    public boolean saveRedis(String typename,Object value)
    {
        try
        {
            redisTemplate.opsForValue().set(typename,value);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /*
    获取指定键值的对象
     */
    public Object getRedis(String typename){
        Object rt = null;

        try
        {
            if ((typename != null) && (redisTemplate != null) && (redisTemplate.opsForValue()!=null))
                rt = redisTemplate.opsForValue().get(typename);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            rt = null;
        }
        return rt;
    }

    /**
     * 批量删除key
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /*
    删除键值对
     */
    public boolean deleteRedis(String typename)
    {
        boolean rt = false ;

        try {
            redisTemplate.delete(typename);
            rt = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            rt = false ;
        }

        return rt;
    }

}
