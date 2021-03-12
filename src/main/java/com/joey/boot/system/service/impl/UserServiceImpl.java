package com.joey.boot.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joey.boot.system.entity.dao.UserDO;
import com.joey.boot.system.mapper.UserMapper;
import com.joey.boot.system.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Joey
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
}
