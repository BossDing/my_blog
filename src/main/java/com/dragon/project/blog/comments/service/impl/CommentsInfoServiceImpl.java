package com.dragon.project.blog.comments.service.impl;

import com.dragon.project.blog.comments.domain.CommentsInfo;
import com.dragon.project.blog.comments.domain.CommentsReply;
import com.dragon.project.blog.comments.mapper.CommentsInfoMapper;
import com.dragon.project.blog.comments.mapper.CommentsReplyMapper;
import com.dragon.project.blog.comments.service.CommentsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2019/8/17 11:23
 * @description：新建评论服务层接口实现
 * @modified By：
 * @version: 1.0.0
 */
@Service
public class CommentsInfoServiceImpl implements CommentsInfoService {

    @Autowired
    CommentsInfoMapper commentsInfoMapper;

    @Autowired
    CommentsReplyMapper commentsReplyMapper;

    @Override
    public int insertCommentInfo(CommentsInfo commentsInfo) {
        return commentsInfoMapper.insertCommentInfo(commentsInfo);
    }

    @Override
    public List<CommentsInfo> selectCommentsInfoByOwnerId(Integer ownerId) {
        List<CommentsInfo> commentsInfoList = commentsInfoMapper.selectCommentsInfoByOwnerId(ownerId);
        for (int i = 0; i < commentsInfoList.size(); i++) {
            List<CommentsReply> commentsReplyList = commentsReplyMapper.selectCommentsReplyByCommentId(commentsInfoList.get(i).getId());
            commentsInfoList.get(i).setCommentsReplyList(commentsReplyList);
        }
        return commentsInfoList;
    }

    @Override
    public int updateCommentsInfo(CommentsInfo commentsInfo) {
        return commentsInfoMapper.updateCommentsInfo(commentsInfo);
    }

    @Override
    public CommentsInfo selectCommentsInfoById(Integer id) {
        return commentsInfoMapper.selectCommentsInfoById(id);
    }
}
