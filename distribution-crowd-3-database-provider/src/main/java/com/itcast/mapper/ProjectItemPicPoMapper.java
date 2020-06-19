package com.itcast.mapper;

import com.itcast.entity.ProjectItemPicPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectItemPicPoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectItemPicPo record);

    int insertSelective(ProjectItemPicPo record);

    ProjectItemPicPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectItemPicPo record);

    int updateByPrimaryKey(ProjectItemPicPo record);

    void addprojectItemPicPOList(@Param("projectItemPicPOList") List<ProjectItemPicPo> projectItemPicPOList);
}