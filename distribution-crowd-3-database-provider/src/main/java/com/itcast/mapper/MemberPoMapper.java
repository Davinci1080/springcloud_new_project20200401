package com.itcast.mapper;

import com.itcast.entity.MemberPo;

public interface MemberPoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberPo record);

    int insertSelective(MemberPo record);

    MemberPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberPo record);

    int updateByPrimaryKey(MemberPo record);

    int selectCountByLoginAcct(String LoginAcct);

    MemberPo selectMemberByLoginAcct(String LoginAcct);
}