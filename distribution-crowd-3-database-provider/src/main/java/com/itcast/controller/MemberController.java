package com.itcast.controller;

import com.itcast.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

//必然返回json数据所以使用RestController
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;
}
