package com.websocket.demo.service;

import com.websocket.demo.dto.DemoInfo;
import com.websocket.demo.repository.DemoInfoRepository;
import javassist.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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

    //=================================使用Redis缓存例子===========================
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

//=================================使用Ehcache缓存例子===========================
    //这里的单引号不能少，否则会报错，被识别是一个对象;
    public static final String CACHE_KEY = "'demoInfo'";
    /**
     * value属性表示使用哪个缓存策略，缓存策略在ehcache.xml
     */
    public static final String DEMO_CACHE_NAME = "demo";

    /**
     * 保存数据.
     * @param demoInfo
     */
    @CacheEvict(value=DEMO_CACHE_NAME,key=CACHE_KEY)
    public DemoInfo save(DemoInfo demoInfo){
        return  demoInfoRepository.save(demoInfo);
    }

    /**
     * 查询数据.
     * @param id
     * @return
     */
    @org.springframework.cache.annotation.Cacheable(value=DEMO_CACHE_NAME,key="'demoInfo_'+#id")
    public DemoInfo findById2(Long id){
        System.err.println("没有走缓存！"+id);
        return demoInfoRepository.findOne(id);
    }

    /**
     * http://www.mincoder.com/article/2096.shtml:
     *
     * 修改数据.
     *
     * 在支持Spring Cache的环境下，对于使用@Cacheable标注的方法，Spring在每次执行前都会检查Cache中是否存在相同key的缓存元素，如果存在就不再执行该方法，而是直接从缓存中获取结果进行返回，否则才会执行并将返回结果存入指定的缓存中。@CachePut也可以声明一个方法支持缓存功能。与@Cacheable不同的是使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。

     @CachePut也可以标注在类上和方法上。使用@CachePut时我们可以指定的属性跟@Cacheable是一样的。
      *
      * @param updated
     * @return
     *
     * @throws NotFoundException
     */
    @CachePut(value = DEMO_CACHE_NAME,key = "'demoInfo_'+#updated.getId()")
    //@CacheEvict(value = DEMO_CACHE_NAME,key = "'demoInfo_'+#updated.getId()")//这是清除缓存.
    public DemoInfo update(DemoInfo updated) throws NotFoundException{
        DemoInfo demoInfo = demoInfoRepository.findOne(updated.getId());
        if(demoInfo == null){
            throw new NotFoundException("No find");
        }
        demoInfo.setName(updated.getName());
        demoInfo.setPassword(updated.getPassword());
        return demoInfo;
    }

    /**
     * 删除数据.
     * @param id
     */
    @CacheEvict(value = DEMO_CACHE_NAME,key = "'demoInfo_'+#id")//这是清除缓存.
    public void delete(Long id){
        demoInfoRepository.delete(id);
    }
}
