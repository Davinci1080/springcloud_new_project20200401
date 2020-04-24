package com.itcast.mapper;

import com.itcast.entity.TypePo;

public interface TypePoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TypePo record);

    int insertSelective(TypePo record);

    TypePo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TypePo record);

    int updateByPrimaryKey(TypePo record);
}