package com.lsy.test.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限表(dms_Auth)实体类
 *
 * @author kancy
 * @description 由 Mybatisplus Code Generator 创建
 * @since 2021-11-11 11:11:11
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("dms_Auth")
public class Auth extends Model<Auth> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 父id
     */
    private Integer pid;
    /**
     * 节点排序
     */
    private Integer seq;
    /**
     * 权限类型：1模块，2功能，3按钮
     */
    private Integer type;
    /**
     * 项目类型：1、inc,2、opr,3、云快缴mc，4、配电运维mc
     */
    private Integer projectType;
    /**
     * 是否启用
     */
    private Integer isOpen;
    /**
     * 路径
     */
    private String url;
    /**
     * 是否删除:0:正常,1删除
     */
    private Integer isDelete;
    /**
     * 创建者id
     */
    private Long createBy;
    /**
     * 更新者id
     */
    private Long updateBy;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 更新时间
     */
    private Date gmtUpdate;

}