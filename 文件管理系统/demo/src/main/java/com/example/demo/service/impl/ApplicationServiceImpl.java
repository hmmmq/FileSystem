package com.example.demo.service.impl;

import com.example.demo.entity.Application;
import com.example.demo.mapper.ApplicationMapper;
import com.example.demo.service.IApplicationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author meiqi huang
 * @since 2024-11-29
 */
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements IApplicationService {

}
