package com.joey.boot.system.entity.request;

import lombok.Data;

/**
 * @author Joey
 */
@Data
public class BasePageParams {
    protected int current;
    protected int size;
}
