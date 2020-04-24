package com.itcast.service.api;

import com.itcast.entity.MemberPo;
import com.itcast.mapper.MemberPoMapper;
import com.itcast.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//开启事务只读不能做增删改的操作
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberPoMapper memberPoMapper;

    public int getLoginAcctCount(String loginAcct) {
        int count = memberPoMapper.selectCountByLoginAcct(loginAcct);
        System.out.println(count);
        return count;
    }

    @Transactional(readOnly = false)
    public void saveMemberPO(MemberPo memberPO) {
        memberPoMapper.insert(memberPO);
    }

    public MemberPo getMemberByLoginAcct(String loginAcct) {
        MemberPo memberPo = memberPoMapper.selectMemberByLoginAcct(loginAcct);
        return memberPo;
    }
}
