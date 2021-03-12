package com.joey.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joey.boot.system.entity.SecurityUser;
import com.joey.boot.system.entity.dao.UserDO;
import com.joey.boot.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Joey
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO query = new UserDO();
        query.setUsername(username);
        UserDO userDO = userService.getOne(new QueryWrapper(query));
        return new SecurityUser(userDO);
    }
}
