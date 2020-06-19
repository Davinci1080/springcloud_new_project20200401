package com.itcast.service;

import com.itcast.entity.ProjectVO;

public interface ProjectService {

    void saveProject(ProjectVO projectVO, String memberId);
}
