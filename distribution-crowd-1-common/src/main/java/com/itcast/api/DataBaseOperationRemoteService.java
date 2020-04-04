package com.itcast.api;

import com.itcast.entity.MemberPo;
import com.itcast.entity.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "database-provider")
public interface DataBaseOperationRemoteService {
    @RequestMapping("/retrieve/loign/acct/count")
    //登录的数量
    ResultEntity<Integer> retrieveLoignAcctCount(@RequestParam("loginAcct") String loginAcct);

    //这个是在database-provider中保存Member
    //注意一定要写@RequestBody注解如果不写这个注解数据就喘不过来
    @RequestMapping("/save/member/remote")
    ResultEntity<String> saveMemberRemote(@RequestBody MemberPo memberPO);
}
