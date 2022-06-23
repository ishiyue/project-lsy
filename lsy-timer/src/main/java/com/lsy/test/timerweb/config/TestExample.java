package com.lsy.test.timerweb.config;

import java.util.*;

public class TestExample {
    public static void main(String[] args) {
        List<String> list=new ArrayList<>();
        List<String> stringList=new LinkedList<>();
        list.add("q");
        list.add("a");
        list.iterator();
        System.out.printf(list.toString());
        Collections.synchronizedCollection(list);
    }
}
