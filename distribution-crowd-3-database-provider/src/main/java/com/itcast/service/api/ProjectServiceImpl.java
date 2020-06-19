package com.itcast.service.api;

import com.itcast.entity.*;
import com.itcast.mapper.*;
import com.itcast.service.ProjectService;
import com.itcast.util.CrowdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    public void saveProject(ProjectVO projectVO, String memberId) {
        //1保存ProjectPo
        ProjectPo projectPo = new ProjectPo();
        //将vo拷贝进po
        BeanUtils.copyProperties(projectVO,projectPo);
        //实行自增
        projectPOMapper.insert(projectPo);
        // 2.获取保存ProjectPO后得到的自增主键
        // 在ProjectPOMapper.xml文件中insert方法对应的标签中设置useGeneratedKeys="true" keyProperty="id"
        //注意这里得到的是其自增的主键id我们要得到它的id
        Integer projectPoId = projectPo.getId();
        List<Integer> typeIdList = projectVO.getTypeIdList();
        if (CrowdUtils.collectionEffectiveCheck(typeIdList)) {
            //这里是一个多对一表的关联
            projectPOMapper.addProjectAndType(projectPoId,typeIdList);
        }
        // 4.保存tagIdList
        List<Integer> tagIdList = projectVO.getTagIdList();
        if(CrowdUtils.collectionEffectiveCheck(tagIdList)) {
            tagPOMapper.insertRelationshipBatch(projectPoId, tagIdList);
        }

        //5保存详情图片detailPicturePathList
        //当确定保证取出的list集合不为null或者大于0时
        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
        if (CrowdUtils.collectionEffectiveCheck(detailPicturePathList)){
            //初始化LIst集合将详情图片的地址存入创建的list集合中
            ArrayList<ProjectItemPicPo> projectItemPicPOList = new ArrayList<ProjectItemPicPo>();
            //循环遍历detailPicturePathListlist集合
            for (String detailPath: detailPicturePathList
                 ) {
                //将循环遍历出来的通过list的add方法插入创建的集合中其中分别是主键id我们设置为null
                ProjectItemPicPo projectItemPicPo = new ProjectItemPicPo(null,projectPoId,detailPath);
                //将创作的对象设置进创建的空集合去
                projectItemPicPOList.add(projectItemPicPo);
           }
            //调用insert方法进行增加
            projectItemPicPOMapper.addprojectItemPicPOList(projectItemPicPOList);
        }
// 6.保存MemberLaunchInfoPO
        MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();

        if(memberLauchInfoVO != null) {

            MemberLaunchInfoPo memberLaunchInfoPO = new MemberLaunchInfoPo();
            BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPO);

            memberLaunchInfoPO.setMemberid(Integer.parseInt(memberId));

            memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);
        }

        // 7.根据ReturnVO的List保存ReturnPO
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();

        if(CrowdUtils.collectionEffectiveCheck(returnVOList)) {

            List<ReturnPo> returnPOList = new ArrayList<ReturnPo>();

            for (ReturnVO returnVO : returnVOList) {

                ReturnPo returnPO = new ReturnPo();

                BeanUtils.copyProperties(returnVO, returnPO);

                returnPO.setProjectid(projectPoId);

                returnPOList.add(returnPO);
            }

            returnPOMapper.insertBatch(returnPOList);

        }

        // 8.保存MemberConfirmInfoPO
        MemberConfirmInfoVo memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();

        if(memberConfirmInfoVO != null) {
            MemberConfirmInfoPo memberConfirmInfoPO = new MemberConfirmInfoPo(null, Integer.parseInt(memberId), memberConfirmInfoVO.getPaynum(), memberConfirmInfoVO.getCardnum());
            memberConfirmInfoPOMapper.insert(memberConfirmInfoPO);

        }

    }
}
