package com.distribution.management.system.common.result;


import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @author admin
 */
@Data
public class QueryList<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 查询集合
     */
    private List<T> list;

    /**
     * 数据总记录数。
     */
    private long total;


    public QueryList() {
    }

    public QueryList(List<T> list, long total) {
        this.list = list;
        this.total = total;
    }

    public QueryList(IPage<T> iPage) {
        this.list = iPage.getRecords();
        this.total = iPage.getTotal();
    }

}
