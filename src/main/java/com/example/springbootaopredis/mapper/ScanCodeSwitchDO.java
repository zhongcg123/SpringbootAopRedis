package com.example.springbootaopredis.mapper;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@TableName("`scan_code_switch`")
@NoArgsConstructor
public class ScanCodeSwitchDO {

    @TableId(type = IdType.AUTO, value = "id")
    private String id;

    @TableField("org_code")
    private String orgCode;

    @TableField("org_name")
    private String orgName;

    @TableField("is_open")
    private String isOpen;

    @TableField("del_status")
    private Integer delStatus;

    private String creator;

    private Date createdTime;

    private Date modifiedTime;

    private String modifier;

    @TableField("parent_org_code")
    private String parentOrgCode;

}
