package com.joey.boot.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joey.boot.system.entity.dao.UserDO;

/**
 * @author Joey
 */
public interface UserService extends IService<UserDO> {
    /**
     * 获取 user 分页
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    IPage<UserDO> getPage(Page<UserDO> page, Wrapper<UserDO> queryWrapper);
}
