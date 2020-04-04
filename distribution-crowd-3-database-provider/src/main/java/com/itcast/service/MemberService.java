package com.itcast.service;

import com.itcast.entity.MemberPo;

public interface MemberService {
    int getLoginAcctCount(String loginAcct);

    void saveMemberPO(MemberPo memberPO);
}
