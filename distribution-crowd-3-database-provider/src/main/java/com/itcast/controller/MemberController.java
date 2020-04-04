package com.itcast.controller;

import com.itcast.entity.MemberPo;
import com.itcast.entity.ResultEntity;
import com.itcast.service.MemberService;
import com.itcast.util.CrowdConstant;
import com.itcast.util.CrowdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//必然返回json数据所以使用RestController
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/retrieve/loign/acct/count")
        //登录的数量
    public ResultEntity<Integer> retrieveLoignAcctCount(@RequestParam("loginAcct") String loginAcct){
        if (!CrowdUtils.strEffectiveCheck(loginAcct)){
            return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        try {
            int count = memberService.getLoginAcctCount(loginAcct);
            return ResultEntity.successWithData(count);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    };

    @RequestMapping("/save/member/remote")
   public ResultEntity<String> saveMemberRemote(@RequestBody MemberPo memberPO){
       try {
           //执行保存操作
           memberService.saveMemberPO(memberPO);
       } catch (Exception e) {
           e.printStackTrace();
           return ResultEntity.failed(e.getMessage());
       }
       return ResultEntity.successNoData();
   };
}
