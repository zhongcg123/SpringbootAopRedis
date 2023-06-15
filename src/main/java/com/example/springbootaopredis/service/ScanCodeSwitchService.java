package com.example.springbootaopredis.service;


import com.example.springbootaopredis.dto.ScanCodeSwitch;
import com.example.springbootaopredis.dto.ScanCodeSwitchParam;
import com.example.springbootaopredis.dto.SwitchScanCode;

import java.util.List;

/**
 * @title ScanCodeSwitchService
 * @Author zhongcg
 * @Description 消费者扫描开关服务
 * @Date 2023/5/19 16:15
 **/
public interface ScanCodeSwitchService {

    List<ScanCodeSwitch> queryScanCodeSwitch(ScanCodeSwitchParam param);

    String switchScanCode(SwitchScanCode param);
}
