package com.joey.boot.system.entity.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Joey
 */
@Getter
@Setter
public abstract class BaseDO {
    /**
     * 逻辑删除
     */
    @JsonIgnore
    protected Boolean deleted;
    /**
     * 创建时间
     */
    @JsonIgnore
    protected Date createTime;
    /**
     * 修改时间
     */
    @JsonIgnore
    protected Date updateTime;
}
