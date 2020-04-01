package com.itcast.test;

import com.itcast.util.CrowdUtils;
import org.junit.Test;

public class StringTest {

    @Test
    public void testSendCode(){
        String appcode = "a80bf60561424f908da06d5665d79d98";
        String randomCode = CrowdUtils.randomCode(4);
        String phoneNum = "13155423832";
        CrowdUtils.sendShortMessage(appcode,randomCode,phoneNum);
    }
}
