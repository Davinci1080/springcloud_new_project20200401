package com.itcast.test;

import org.springframework.beans.BeanUtils;

public class test3 {

    public static void main(String[] args) {
        test001 tt = new test001();
        test002 tt2 = new test002();
        tt.setName("nihao");
        BeanUtils.copyProperties(tt,tt2);
        System.out.println(tt2.getUname());
    }
}
