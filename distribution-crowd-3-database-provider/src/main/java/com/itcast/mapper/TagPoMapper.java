package com.itcast.mapper;

import com.itcast.entity.TagPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagPoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TagPo record);

    int insertSelective(TagPo record);

    TagPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TagPo record);

    int updateByPrimaryKey(TagPo record);

    void insertRelationshipBatch(@Param("projectId") Integer projectId,@Param("tagIdList") List<Integer> tagIdList);
}