package com.distribution.management.system.common.config;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/***
 * @author admin
 * 权限缓存
 */

public abstract class PermissionsConfigDataAbstract {

    /***
     * 用戶ID与用户权限关联关系
     */
    protected final static Map<Long, List<String>> PERMISSIONS_MAP = Maps.newConcurrentMap();

}
