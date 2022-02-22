package com.distribution.management.system.common.util;

import com.distribution.management.system.common.constant.UserConstants;

/***
 * @author admin
 * 用户信息操作
 */
public class UserUtils {

    private final InheritableThreadLocal<Long> userIds = new InheritableThreadLocal<>();

    /***
     * 添加用户ID
     * @param userId
     */
    public void add(Long userId) {
        userIds.set(userId);
    }

    /***
     * 获取用户ID
     * @return
     */
    public Long getUserId() {
        return userIds.get();
    }

    /***
     * 获取用户ID
     * @return
     */
    public void clear() {
        userIds.remove();
    }

    /***
     * 是否超管
     * @return
     */
    public Boolean isAdmin() {
        return UserConstants.SUPER_ADMIN == userIds.get();
    }

    /***
     * 是否超管
     * @return
     */
    public Boolean isAdmin(Integer userId) {
        return UserConstants.SUPER_ADMIN == userId;
    }
}
