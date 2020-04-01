package com.itcast.controller;

import com.itcast.entity.ResultEntity;
import com.itcast.util.CrowdConstant;
import com.itcast.util.CrowdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class RedisOperationController {

    /*
    redisTemplate.opsForValue();//操作字符串
    redisTemplate.opsForHash();//操作hash
    redisTemplate.opsForList();//操作list
    redisTemplate.opsForSet();//操作set
    redisTemplate.opsForZSet();//操作有序set
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 将字符串类型的键值对保存到Redis时调用的远程方法
     * @param normalKey
     * @param normalValue
     * @param timeoutMinute	超时时间（单位：分钟）
     * 		-1表示无过期时间
     * 		正数表示过期时间分钟数
     * 		0和null值不接受
     * @return
     */
    @RequestMapping("/save/normal/string/key/value")
    ResultEntity<String> saveNormalStringKeyValue(
            @RequestParam("normalKey") String normalKey,
            @RequestParam("normalValue") String normalValue,
            @RequestParam("timeoutMinute") Integer timeoutMinute){
        //获取字符串的操作器对象
        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        //对传入的数据进行验证
        if (!CrowdUtils.strEffectiveCheck(normalKey) || !CrowdUtils.strEffectiveCheck(normalValue)){
            return ResultEntity.failed(CrowdConstant.MESSAGE_REDIS_KEY_OR_VALUE_INVALID);
        }
        // 判断timeoutMinute值
        if (timeoutMinute == null || timeoutMinute == 0){
            return ResultEntity.failed(CrowdConstant.MESSAGE_REDIS_KEY_TIME_OUT_INVALID);
        }

        //判断timout值是否不设置过期时间
        if (timeoutMinute == -1){
            //不设置过期时间的方式保存
            try {
                stringStringValueOperations.set(normalKey,normalValue);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultEntity.failed(e.getMessage());
            }
            return ResultEntity.successNoData();
        }
        //执行保存操作要设置过期时间
        try {
            stringStringValueOperations.set(normalKey,normalValue,timeoutMinute, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
        return ResultEntity.successNoData();
    };
    /**
     * 根据key查询对应value时调用的远程方法
     * @param normalKey
     * @return
     */
    @RequestMapping("/retrieve/string/value/by/string/key")
    ResultEntity<String> retrieveStringValueByStringKey(@RequestParam("normalKey") String normalKey){
        if (!CrowdUtils.strEffectiveCheck(normalKey)){
            return ResultEntity.failed(CrowdConstant.MESSAGE_REDIS_KEY_OR_VALUE_INVALID);
        }
        try {
            System.out.println(normalKey);
            String value = redisTemplate.opsForValue().get(normalKey);
            System.out.println(value);
            return ResultEntity.successWithData(value);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    };

    /**
     * 根据key删除对应value时调用的远程方法
     * @param key
     * @return
     */
    @RequestMapping("/redis/remove/by/key")
    ResultEntity<String> removeByKey(@RequestParam("key") String key){
        if (!CrowdUtils.strEffectiveCheck(key)){
            return ResultEntity.failed(CrowdConstant.MESSAGE_REDIS_KEY_OR_VALUE_INVALID);
        }
        try {
            redisTemplate.delete(key);
            return ResultEntity.successNoData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    };

}
