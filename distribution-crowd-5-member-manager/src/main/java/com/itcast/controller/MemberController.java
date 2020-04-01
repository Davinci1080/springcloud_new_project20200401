package com.itcast.controller;

import com.itcast.api.RedisOperationRemoteService;
import com.itcast.entity.ResultEntity;
import com.itcast.util.CrowdConstant;
import com.itcast.util.CrowdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//这是consummer会去调provider
//前端工程会去调用manager到时候这里会变成provider所以返回的是json的片段
@RestController
public class MemberController {

    @Autowired
    private RedisOperationRemoteService redisOperationRemoteService;

    // Spring会根据@Value注解中的表达式读取yml或properties配置文件给成员变量设置对应的值
    @Value("${crowd.short.message.appCode}")
    private String appcode;
    @RequestMapping("/member/manager/send/code")
    //传入发短信的手机号
    public ResultEntity<String> sendCode(@RequestParam("phoneNum") String phoneNum){

        if (!CrowdUtils.strEffectiveCheck(phoneNum)){
            return ResultEntity.failed(CrowdConstant.MESSAGE_PHONE_NUM_INVALID);
        }
        
        //1.生成验证码
        int length = 4;
        String randomCode = CrowdUtils.randomCode(length);

        //2.保存到redis
        Integer timeoutMinute = 15;
        String normalKey = CrowdConstant.REDIS_RANDOM_CODE_PREFIX + phoneNum;
        ResultEntity<String> stringResultEntity = redisOperationRemoteService.saveNormalStringKeyValue(normalKey, randomCode, timeoutMinute);

        //3.验证是否正确
        if (ResultEntity.FAILED.equals(stringResultEntity.getResult())){
            return stringResultEntity;
        }

        //4.发送验证码到用户的手机
        //String appcode = "a80bf60561424f908da06d5665d79d98";
        System.out.println(appcode+0000);
        try {
            CrowdUtils.sendShortMessage(appcode,randomCode,phoneNum);
            return ResultEntity.successNoData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}
