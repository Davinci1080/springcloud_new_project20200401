package com.itcast.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTypeVo {
    private Integer id;
    private Integer projectid;
    private Integer typeid;
}
