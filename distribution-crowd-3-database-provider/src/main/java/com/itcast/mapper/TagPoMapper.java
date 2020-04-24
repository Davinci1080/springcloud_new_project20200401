package com.itcast.mapper;

import com.itcast.entity.TagPo;

public interface TagPoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TagPo record);

    int insertSelective(TagPo record);

    TagPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TagPo record);

    int updateByPrimaryKey(TagPo record);
}