package com.lsy.test.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsy.test.user.entity.Auth;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;

/**
 * 权限表(dms_Auth)数据Mapper
 *
 * @author kancy
 * @description 由 Mybatisplus Code Generator 创建
 * @since 2021-11-08 11:11:12
 */

public interface AuthMapper extends BaseMapper<Auth> {

    /**
     * mybatis大数据量流式查询
     *
     * @param sql
     * @param handler
     */
    @Select("${sql}")
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1000)
    @ResultType(Auth.class)
    void handlerDealData(@Param("sql") String sql, ResultHandler<Auth> handler);
}
