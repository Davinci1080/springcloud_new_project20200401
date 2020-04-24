package com.itcast.service;

import com.itcast.entity.MemberPo;

public interface ProjectService {

    int getLoginAcctCount(String loginAcct);

    void saveMemberPO(MemberPo memberPO);

    MemberPo getMemberByLoginAcct(String loginAcct);
}
