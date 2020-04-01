package com.itcast.service.api;

import com.itcast.mapper.MemberPoMapper;
import com.itcast.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//开启事务只读
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberPoMapper memberPoMapper;
}
