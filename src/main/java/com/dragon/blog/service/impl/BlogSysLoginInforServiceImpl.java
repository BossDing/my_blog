package com.dragon.blog.service.impl;

import com.dragon.annotation.BaseService;
import com.dragon.base.BaseServiceImpl;
import com.dragon.blog.mapper.BlogSysLoginInforMapper;
import com.dragon.blog.model.BlogSysLoginInfor;
import com.dragon.blog.model.BlogSysLoginInforExample;
import com.dragon.blog.service.BlogSysLoginInforService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* BlogSysLoginInforService实现
* Created by Dragon.Wen on 2019/5/19.
*/
@Service
@Transactional
@BaseService
public class BlogSysLoginInforServiceImpl extends BaseServiceImpl<BlogSysLoginInforMapper, BlogSysLoginInfor, BlogSysLoginInforExample> implements BlogSysLoginInforService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogSysLoginInforServiceImpl.class);

    @Autowired
    BlogSysLoginInforMapper blogSysLoginInforMapper;

}