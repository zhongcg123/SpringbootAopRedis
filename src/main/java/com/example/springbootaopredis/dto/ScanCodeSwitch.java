package com.example.springbootaopredis.dto;

import lombok.Data;

/**
 * @title ScanCodeSwitchParam
 * @Author zhongcg
 * @Description TODO
 * @Date 2023/5/19 16:11
 **/
@Data
public class ScanCodeSwitch {

    //@Field(name = "企业编码")
    private String orgCode;

    //@Field(name = "企业名称")
    private String orgName;

    //@Field(name = "是否开启 0开启 1关闭")
    private String isOpen;
}
