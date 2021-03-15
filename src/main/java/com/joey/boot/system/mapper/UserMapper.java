package com.joey.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joey.boot.system.entity.dao.UserDO;
import org.springframework.stereotype.Repository;

/**
 * @author Joey
 */
@Repository
public interface UserMapper extends BaseMapper<UserDO> {
}
