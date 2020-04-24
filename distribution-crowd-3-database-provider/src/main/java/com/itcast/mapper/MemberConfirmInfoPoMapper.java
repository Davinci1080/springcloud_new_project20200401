package com.itcast.mapper;

import com.itcast.entity.MemberConfirmInfoPo;

public interface MemberConfirmInfoPoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberConfirmInfoPo record);

    int insertSelective(MemberConfirmInfoPo record);

    MemberConfirmInfoPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberConfirmInfoPo record);

    int updateByPrimaryKey(MemberConfirmInfoPo record);
}