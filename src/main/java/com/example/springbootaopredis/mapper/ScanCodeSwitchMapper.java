package com.example.springbootaopredis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @title ScanCodeSwitchMapper
 * @Author zhongcg
 * @Description 消费者扫码开关
 * @Date 2023/5/9 16:28
 **/
@Mapper
public interface ScanCodeSwitchMapper extends BaseMapper<ScanCodeSwitchDO> {

    /**
     * 根据组织编码查询是否开启
     * @param orgCode
     * @return
     */
    @Select("select is_open from scan_code_switch where org_code = #{orgCode}")
    Integer selectOrgSwitch(String orgCode);

    /**
     * 开启或关闭扫码开关
     * @param isOpen
     * @param orgCode
     * @return
     */
    @Update("UPDATE scan_code_switch SET is_open = #{isOpen} WHERE org_code = #{orgCode} OR parent_org_code = #{orgCode}")
    Integer updateOrgSwitch(String isOpen, String orgCode);
}
