package com.lsy.test.user.controller;

public class CasController {
    public static volatile  int i=0;
    public static void main(String[] args) {
      /*for (int i=0;i<10000000;i++){
          m();
          n();
      }*/
        String id="31050180460000000857";
        System.out.println(id.length());
    }
    public static synchronized  void  m(){

    }
    public static void n(){
        i=1;
    }
}
