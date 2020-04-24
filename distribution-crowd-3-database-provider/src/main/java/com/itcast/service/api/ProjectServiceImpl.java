package com.itcast.service.api;

import com.itcast.entity.MemberPo;
import com.itcast.mapper.*;
import com.itcast.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private MemberConfirmInfoPoMapper memberConfirmInfoPOMapper;

    @Autowired
    private MemberLaunchInfoPoMapper memberLaunchInfoPOMapper;

    @Autowired
    private ProjectItemPicPoMapper projectItemPicPOMapper;

    @Autowired
    private ProjectPoMapper projectPOMapper;

    @Autowired
    private ReturnPoMapper returnPOMapper;

    @Autowired
    private TagPoMapper tagPOMapper;

    @Autowired
    private TypePoMapper typePOMapper;
    public int getLoginAcctCount(String loginAcct) {
        return 0;
    }

    public void saveMemberPO(MemberPo memberPO) {

    }

    public MemberPo getMemberByLoginAcct(String loginAcct) {
        return null;
    }
}
