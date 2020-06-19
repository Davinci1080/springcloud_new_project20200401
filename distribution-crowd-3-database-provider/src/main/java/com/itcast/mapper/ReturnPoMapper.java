package com.itcast.mapper;

import com.itcast.entity.ReturnPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReturnPoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReturnPo record);

    int insertSelective(ReturnPo record);

    ReturnPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReturnPo record);

    int updateByPrimaryKey(ReturnPo record);

    //注意这里的批量添加如果没有使用@Param的话在sql里面的类型要是用list不能直接写returnPOList
    void insertBatch(@Param("returnPOList") List<ReturnPo> returnPOList);
}