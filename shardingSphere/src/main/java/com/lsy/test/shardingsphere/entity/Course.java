package com.lsy.test.shardingsphere.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Course {

    private String cid;
    private String cname;
    private Long userid;
    private String cstatus;

}
