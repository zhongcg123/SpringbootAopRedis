package com.example.springbootaopredis.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.springbootaopredis.dto.ScanCodeSwitch;
import com.example.springbootaopredis.dto.ScanCodeSwitchParam;
import com.example.springbootaopredis.dto.SwitchScanCode;
import com.example.springbootaopredis.exception.CommonExcept;
import com.example.springbootaopredis.mapper.ScanCodeSwitchDO;
import com.example.springbootaopredis.mapper.ScanCodeSwitchMapper;
import com.example.springbootaopredis.util.DataChangeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @title ScanCodeSwitchServiceImpl
 * @Author zhongcg
 * @Description 消费者扫描开关服务
 * @Date 2023/5/19 16:17
 **/
@Component("ScanCodeSwitchServiceImpl")
@Slf4j
public class ScanCodeSwitchServiceImpl implements ScanCodeSwitchService {

    @Autowired
    private ScanCodeSwitchMapper scanCodeSwitchMapper;

    /**
     * 根据企业编码查询消费者扫码开关
     * @param param
     * @return
     */
    @Override
    public List<ScanCodeSwitch> queryScanCodeSwitch(ScanCodeSwitchParam param) {
        LambdaQueryWrapper<ScanCodeSwitchDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!StringUtils.isEmpty(param.getOrgCode()), ScanCodeSwitchDO::getOrgCode, param.getOrgCode());
        wrapper.isNull(ScanCodeSwitchDO::getParentOrgCode);
        wrapper.eq(ScanCodeSwitchDO::getDelStatus, 0);
        List<ScanCodeSwitchDO> scanCodeSwitchDOList = scanCodeSwitchMapper.selectList(wrapper);

        if (CollectionUtils.isEmpty(scanCodeSwitchDOList)) {
            throw new CommonExcept("暂无产品信息展示控制配置权限!");
        }

        return DataChangeUtil.changeList(scanCodeSwitchDOList, ScanCodeSwitch.class);
    }

    /**
     * 开启或关闭消费者扫码开关
     * @param param
     * @return
     */
    @Override
    public String switchScanCode(SwitchScanCode param) {
        LambdaQueryWrapper<ScanCodeSwitchDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ScanCodeSwitchDO::getOrgCode, param.getOrgCode());
        wrapper.eq(ScanCodeSwitchDO::getDelStatus, 0);
        List<ScanCodeSwitchDO> scanCodeSwitchDOList = scanCodeSwitchMapper.selectList(wrapper);

        if (CollectionUtils.isEmpty(scanCodeSwitchDOList)) {
            throw new CommonExcept("开启或关闭消费者扫码开关失败，组织编码[" + param.getOrgCode() + "]不存在");
        }

        int i = scanCodeSwitchMapper.updateOrgSwitch(param.getIsOpen(), param.getOrgCode());
        if (i > 0) {
            return "开启或关闭消费者扫码开关成功！";
        }else {
            return "开启或关闭消费者扫码开关失败！";
        }
    }

    @Override
    public String saveScanCodeSwitch(SwitchScanCode param) {
        ScanCodeSwitchDO scanCodeSwitchDO = new ScanCodeSwitchDO();
        scanCodeSwitchDO.setOrgCode(param.getOrgCode());
        scanCodeSwitchDO.setOrgName(param.getOrgName());
        scanCodeSwitchDO.setIsOpen(param.getIsOpen());
        scanCodeSwitchDO.setDelStatus(0);
        scanCodeSwitchDO.setCreator("zhangsna");
        scanCodeSwitchDO.setCreatedTime(new Date());
        scanCodeSwitchMapper.insert(scanCodeSwitchDO);

        return "成功";
    }
}
