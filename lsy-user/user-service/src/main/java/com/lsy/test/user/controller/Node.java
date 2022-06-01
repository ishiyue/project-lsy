package com.lsy.test.user.controller;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Node {
    private int id;
    private int pid;
    private String name;
    private List<Node> sub = new ArrayList<>();
    public Node(int id, int pid) {
        this.id = id;
        this.pid = pid;
    }

    public Node(int id, int pid, String name) {
        this(id, pid);
        this.name = name;
    }

}
