package com.itcast.mapper;

import com.itcast.entity.ProjectPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProjectPoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectPo record);

    int insertSelective(ProjectPo record);

    ProjectPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectPo record);

    int updateByPrimaryKey(ProjectPo record);

    void addProjectAndType(@Param("projectId") Integer projectPoId, @Param("typeIdList") List<Integer> typeIdList);
}