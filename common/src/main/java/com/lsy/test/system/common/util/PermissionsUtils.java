package com.lsy.test.system.common.util;

import com.lsy.test.system.common.config.PermissionsConfigDataAbstract;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/***
 * 权限添加
 * @author admin
 */
public class PermissionsUtils extends PermissionsConfigDataAbstract {

    /***
     * 獲取用戶ID
     * @param userId
     * @return
     */
    public static List<String> obtainPermission(Long userId) {
        CheckUtils.checkIllegal(ObjectUtils.isEmpty(userId), "用戶ID不能为空");
        if (PERMISSIONS_MAP.containsKey(userId)) {
            return PERMISSIONS_MAP.get(userId);
        }
        return Lists.newArrayList();
    }

    /***
     * 设置用戶ID
     * @param userId
     * @return
     */
    public static void putPermission(Long userId, List<String> permissions) {
        CheckUtils.checkIllegal(ObjectUtils.isEmpty(userId), "用戶ID不能为空");
        if (!CollectionUtils.isEmpty(permissions)) {
            PERMISSIONS_MAP.put(userId, permissions);
        }
    }

}
