package com.itcast.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVo {
    private String loginacct;

    private String userpswd;

    private String phoneNum;

    private String randomCode;
}
