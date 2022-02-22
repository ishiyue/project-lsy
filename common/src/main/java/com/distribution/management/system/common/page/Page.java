package com.distribution.management.system.common.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 通用分页请求类
 *
 * @author liubin
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Page implements Serializable {

    private static final long serialVersionUID = -666114981664013892L;

    public static final int PAGE_NUM = 1;
    public static final int PAGE_SIZE = 10;

    /**
     * 当前页
     */
    @Builder.Default
    private int pageNum = PAGE_NUM;

    /**
     * 分页大小
     */
    @Builder.Default
    private int pageSize = PAGE_SIZE;

}