package com.reven.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reven.core.AbstractService;
import com.reven.entity.Dispatch;
import com.reven.mapper.DispatchMapper;
import com.reven.service.IDispatchService;


/**
 * Created by CodeGenerator on 2019/11/13.
 */
@Service
@Transactional
public class DispatchServiceImpl extends AbstractService<Dispatch> implements IDispatchService {
    @Resource
    private DispatchMapper dispatchMapper;

}
