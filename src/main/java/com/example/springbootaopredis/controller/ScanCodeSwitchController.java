package com.example.springbootaopredis.controller;

import com.example.springbootaopredis.dto.ScanCodeSwitch;
import com.example.springbootaopredis.dto.ScanCodeSwitchParam;
import com.example.springbootaopredis.dto.SwitchScanCode;
import com.example.springbootaopredis.service.ScanCodeSwitchService;
import com.example.springbootaopredis.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @title ScanCodeSwitchController
 * @Author zhongcg
 * @Description 消费者扫描开关服务
 * @Date 2023/5/19 16:14
 **/
@RestController
@RequestMapping(path = "/scanCodeSwitch")
public class ScanCodeSwitchController {

    @Autowired
    private ScanCodeSwitchService scanCodeSwitchService;

    /**
     * 根据企业编码查询消费者扫码开关
     * @param param
     * @return
     */
    @PostMapping(path = "/scanCodeSwitch/queryScanCodeSwitch/v1")
    public Result<List<ScanCodeSwitch>> queryScanCodeSwitch(ScanCodeSwitchParam param) {
        return Result.success(scanCodeSwitchService.queryScanCodeSwitch(param));
    }

    /**
     * 开启或关闭消费者扫码开关
     * @param param
     * @return
     */
    @PostMapping(path = "/scanCodeSwitch/switchScanCode/v1")
    public Result<String> switchScanCode(SwitchScanCode param) {
        return Result.success(scanCodeSwitchService.switchScanCode(param));
    }
}
