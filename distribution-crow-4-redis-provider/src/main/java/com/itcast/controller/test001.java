package com.itcast.controller;

import com.itcast.util.CrowdConstant;
import com.itcast.util.CrowdUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class test001 {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void rest(){
        String value = (String) redisTemplate.opsForValue().get("bui");
        System.out.println(value);
    }

    public static void main(String[] args) {
        /*if (CrowdUtils.strEffectiveCheck("8989")){
            System.out.println("failed");
        } else{
            System.out.println("success");
        }*/

        System.out.println("测试分支的可用性");

    }
}
