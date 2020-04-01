package com.itcast.api;

import com.itcast.entity.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//这就是springcloud微服务的核心了 远程调用接口使用Feign
//微服务的基础是consummer去调用provider
//其中consumer去调用的service就是这个接口使用@Autowired调用
@FeignClient(value="redis-provider")
public interface RedisOperationRemoteService {
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
    ResultEntity<String> saveNormalStringKeyValue(@RequestParam("normalKey") String normalKey, @RequestParam("normalValue") String normalValue, @RequestParam("timeoutMinute") Integer timeoutMinute);

    /**
     * 根据key查询对应value时调用的远程方法
     * @param normalKey
     * @return
     */
    @RequestMapping("/retrieve/string/value/by/string/key")
    ResultEntity<String> retrieveStringValueByStringKey(@RequestParam("normalKey") String normalKey);

    /**
     * 根据key删除对应value时调用的远程方法
     * @param key
     * @return
     */
    @RequestMapping("/redis/remove/by/key")
    ResultEntity<String> removeByKey(@RequestParam("key") String key);

}
