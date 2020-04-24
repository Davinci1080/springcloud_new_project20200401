package com.itcast.mapper;

import com.itcast.entity.MemberLaunchInfoPo;

public interface MemberLaunchInfoPoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberLaunchInfoPo record);

    int insertSelective(MemberLaunchInfoPo record);

    MemberLaunchInfoPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberLaunchInfoPo record);

    int updateByPrimaryKey(MemberLaunchInfoPo record);
}