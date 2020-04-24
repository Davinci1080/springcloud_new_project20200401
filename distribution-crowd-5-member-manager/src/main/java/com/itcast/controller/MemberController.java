package com.itcast.controller;

import com.itcast.api.DataBaseOperationRemoteService;
import com.itcast.api.RedisOperationRemoteService;
import com.itcast.entity.MemberPo;
import com.itcast.entity.MemberSignSuccessVo;
import com.itcast.entity.MemberVo;
import com.itcast.entity.ResultEntity;
import com.itcast.util.CrowdConstant;
import com.itcast.util.CrowdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



//这是consummer会去调provider
//前端工程会去调用manager到时候这里会变成provider所以返回的是json的片段
@RestController
public class MemberController {

    @Autowired
    private DataBaseOperationRemoteService dataBaseOperationRemoteService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RedisOperationRemoteService redisOperationRemoteService;

    // Spring会根据@Value注解中的表达式读取yml或properties配置文件给成员变量设置对应的值
    @Value("${crowd.short.message.appCode}")
    private String appcode;


    //退出操作
   @RequestMapping("/member/manager/logout")
    public ResultEntity<String> logout(@RequestParam("token") String token) {

        return redisOperationRemoteService.removeByKey(token);
    }
    @RequestMapping("/memberm/manager/login")
    public ResultEntity<MemberSignSuccessVo> login(@RequestParam("loginAcct") String loginAcct,@RequestParam("userPswf") String userPswd){
        //根据登录账户查询数据库获取MemberPO对象
        ResultEntity<MemberPo> memberPoResultEntity = dataBaseOperationRemoteService.retrieveMemberByLoginAcct(loginAcct);
        if (ResultEntity.FAILED.equals(memberPoResultEntity.getResult())){
            return ResultEntity.failed(memberPoResultEntity.getMessage());
        }

        //获取MemberPo的对象是否为空
        MemberPo memberPo = memberPoResultEntity.getData();
        if (memberPo == null){
            return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        //获取从数据库查询的到的密码
        String userpswdDatabase = memberPo.getUserpswd();

        //比较密码
        boolean matches = bCryptPasswordEncoder.matches(userPswd, userpswdDatabase);

        if (!matches){
            return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        
        //生成Token
        String token = CrowdUtils.generateToken();
        
        //从MemberPo对象获取memberId
        String memberId = memberPo.getId() + "";

        // 8.将token和memberId存入Redis
        ResultEntity<String> resultEntitySaveToken = redisOperationRemoteService.saveNormalStringKeyValue(token, memberId, 30);

        if(ResultEntity.FAILED.equals(resultEntitySaveToken.getResult())) {
            return ResultEntity.failed(resultEntitySaveToken.getMessage());
        }

        // 9.封装MemberSignSuccessVO对象
        MemberSignSuccessVo memberSignSuccessVO = new MemberSignSuccessVo();

        // 调用Spring提供的BeanUtils.copyProperties()工具类直接完成属性值注入
        BeanUtils.copyProperties(memberPo, memberSignSuccessVO);

        memberSignSuccessVO.setToken(token);

        // 10.返回结果
        return ResultEntity.successWithData(memberSignSuccessVO);
    }

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
        System.out.println(appcode);
        try {
            CrowdUtils.sendShortMessage(appcode,randomCode,phoneNum);
            return ResultEntity.successNoData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/member/manager/register")
    public ResultEntity<String> register(@RequestBody MemberVo memberVo){
        //1.获取检验码数据进行有效的检测
        String randomCode = memberVo.getRandomCode();
        if (!CrowdUtils.strEffectiveCheck(randomCode)){
            return ResultEntity.failed(CrowdConstant.MESSAGE_RANDOM_CODE_LENGTH_INVALID);
        }
        //验证码通过校验手机号码可以给前端来做
        String phoneNum = memberVo.getPhoneNum();
        if (!CrowdUtils.strEffectiveCheck(phoneNum)){
            return ResultEntity.failed(CrowdConstant.MESSAGE_PHONE_NUM_INVALID);
        }

        //拼接验证码的key
        String randomCodeKey =  CrowdConstant.REDIS_RANDOM_CODE_PREFIX + phoneNum;

        //调用远程的redis-provider方法获取对应的验证码值
        ResultEntity<String> stringResultEntity = redisOperationRemoteService.retrieveStringValueByStringKey(randomCodeKey);

        //判断情况如果取出的值是Failed就直接失败
        if (ResultEntity.FAILED.equals(stringResultEntity.getResult())){
            return stringResultEntity;
        }

        String randomCodeRedis = stringResultEntity.getData();

        //没有查询到值直接失败
        if (randomCodeRedis == null){
            return ResultEntity.failed(CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
        }

        //如果查询出来则进行比较
        if (!randomCode.equals(randomCodeRedis)){
            return ResultEntity.failed(CrowdConstant.MESSAGE_CODE_NOT_MATCH);
        }

        //一切都合格那么直接删除当前KEY对应的验证码
        ResultEntity<String> resultEntityRemove = redisOperationRemoteService.removeByKey(randomCodeKey);

        //如果删除失败
        if (ResultEntity.FAILED.equals(resultEntityRemove.getResult())){
            return resultEntityRemove;
        }

        //远程调用database-provider方法检查登录名是否被占用
        String loginacct = memberVo.getLoginacct();

        ResultEntity<Integer> integerResultEntity = dataBaseOperationRemoteService.retrieveLoignAcctCount(loginacct);

        Integer data = integerResultEntity.getData();

        //查看是否被占用如果被占用返回失败信息停止执行
        if (data > 0 ){
            return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
        }

        //如果一切顺利取出密码进行加密
        String userpswd = memberVo.getUserpswd();
        String encode = bCryptPasswordEncoder.encode(userpswd);
        memberVo.setUserpswd(encode);

        //调用远程方法进行保存操作
        MemberPo memberPo = new MemberPo();

        //调用spring提供的BeanUtils.copyProperties()工具类完成属性值得注入
        BeanUtils.copyProperties(memberVo,memberPo);
        return dataBaseOperationRemoteService.saveMemberRemote(memberPo);
    }
}
