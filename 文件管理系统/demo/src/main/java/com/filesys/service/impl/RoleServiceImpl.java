package com.filesys.service.impl;

import com.filesys.entity.Role;
import com.filesys.mapper.RoleMapper;
import com.filesys.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author meiqi
 * @since 2024-10-25
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
