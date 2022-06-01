package com.test.lsy.user.api;

import com.lsy.test.system.common.config.Summary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author admin
 */
@FeignClient(name = Summary.USER_CENTER, path = Summary.USER_PATH)
public interface UserServiceClient {

    /**
     * 获取用户菜单列表
     *
     * @param
     * @return
     */
    @GetMapping("/query/auths")
    void addUser();

}
