package com.websocket.demo.service;

import com.websocket.demo.dto.DemoInfo;
import com.websocket.demo.repository.DemoInfoRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Cacheable;

@Service
public class DemoInfoService {

    @Resource
    private DemoInfoRepository demoInfoRepository;
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * value：缓存位置名称，不能为空，如果使用EHCache，就是ehcache.xml中声明的cache的name
     * key：缓存的key，默认为空，既表示使用方法的参数类型及参数值作为key，支持SpEL
     * condition：触发条件，只有满足条件的情况才会加入缓存，默认为空，既表示全部都加入缓存，支持SpEL（Spring Expression Language）
     * @param id
     * @return
     */
    @org.springframework.cache.annotation.Cacheable(value = "demoInfo",key = "#id+'findById'")
    public DemoInfo findById(Long id){
        System.out.println("DemoInfoServiceImpl.findById()=========从数据库中进行获取的....id="+id);
        return demoInfoRepository.findOne(id);
    }

    /**
     * value：缓存位置名称，不能为空，同上
     * key：缓存的key，默认为空，同上
     * condition：触发条件，只有满足条件的情况才会清除缓存，默认为空，支持SpEL
     * allEntries：true表示清除value中的全部缓存，默认为false
     * @param id
     */
    @CacheEvict(value = "demoInfo",key = "#id+'findById'")
    public void deletedFromCache(Long id){
        System.out.println("DemoInfoServiceImpl.delete().从缓存中删除.");
    }
}
