package com.itcast.mapper;

import com.itcast.entity.ReturnPo;

public interface ReturnPoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReturnPo record);

    int insertSelective(ReturnPo record);

    ReturnPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReturnPo record);

    int updateByPrimaryKey(ReturnPo record);
}