package com.itcast.mapper;

import com.itcast.entity.ProjectPo;

public interface ProjectPoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectPo record);

    int insertSelective(ProjectPo record);

    ProjectPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectPo record);

    int updateByPrimaryKey(ProjectPo record);
}