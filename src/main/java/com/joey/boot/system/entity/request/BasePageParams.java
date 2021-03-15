package com.joey.boot.system.entity.request;

import lombok.Data;

/**
 * @author Joey
 */
@Data
public class BasePageParams {
    private int current;
    private int size;
}
