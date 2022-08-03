package com.lsy.test.user.test.reference;

import com.lsy.test.user.test.M;

import java.io.IOException;

/**
 * 强引用
 */
public class T01_NormalReference {
    public static void main(String[] args) throws IOException {
        M m=new M();
        m=null;
        System.gc();
        System.in.read();
    }
}
