package cn.edu.ujs.lp.intells.basicinformation.request.Excompany;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * 物业职工保存参数类，用于新增或更新外委职工记录
 */
@Data
public class ExstaffSaveRequest {

    @ApiModelProperty(value = "物业职工ID,新增物业职工为null，否则为该物业职工ID值")
    private String id;

    @ApiModelProperty(value = "物业职工姓名", required = true)
    @NotBlank(message = "姓名非空")
    private String exstaffName; //` varchar(20) NOT NULL COMMENT '姓名',

    @ApiModelProperty(value = "性别(数据字典)，数据字典选择")
    private String exstaffSex; //` varchar(32) DEFAULT NULL COMMENT '性别(数据字典)',

    @ApiModelProperty(value = "物业职工手机号码", required = true)
    @NotBlank(message = "手机号码非空")
    private String exstaffMobile; //` varchar(15) NOT NULL COMMENT '手机号',

    @ApiModelProperty(value = "职工工号")
    private String exstaffJobno; //` varchar(20) DEFAULT NULL COMMENT '职工工号',

    @ApiModelProperty(value = "岗位状态(数据字典)")
    private String exstaffJobstate; //` varchar(32) DEFAULT NULL COMMENT '岗位状态(数据字典, db_job_state)',

    @ApiModelProperty(value = "激活状态(\"1\"--激活, \"0\"--未激活)")
    private String exstaffActivestate; //` tinyint(1) DEFAULT 0 COMMENT '激活状态(1--激活, 0--未激活)',

    @ApiModelProperty(value = "所属公司（外键关联）", required = true)
    @NotBlank(message = "公司非空")
    private String excompanyId; //` varchar(32) NOT NULL COMMENT '所属公司（外键关联）',

    @ApiModelProperty(value = "所属班组（外键关联）", required = true)
    private String exteamId; //` varchar(32) NOT NULL COMMENT '所属班组（外键关联）',

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "聘用日期")
    private Date exstaffEmploystartdate; //` datetime DEFAULT NULL COMMENT '聘用日期',

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "聘用终止日期")
    private Date exstaffEmployenddate; //` datetime DEFAULT NULL COMMENT '终止日期',

    @ApiModelProperty(value = "技术工种(数据字典)")
    private String exstaffTechnicalwork; //` varchar(32) DEFAULT  NULL COMMENT '技术工种(数据字典)',

    @ApiModelProperty(value = "专用技能(文字描述)")
    private String exstaffExpertise; //` varchar(400) DEFAULT  NULL COMMENT '专用技能，文字描述',

    @ApiModelProperty(value = "直属领导(姓名)")
    private String exstaffLeadname; //` varchar(50) DEFAULT  NULL COMMENT '直属领导',

    @ApiModelProperty(value = "物业职工身高")
    private double exstaffStature; //` double(7,1) DEFAULT  NULL COMMENT '身高',

    @ApiModelProperty(value = "体重")
    private double exstaffWeight; //` double(7,1) DEFAULT  NULL COMMENT '体重',

    @ApiModelProperty(value = "鞋号")
    private double exstaffShoesize; //` double(7,1) DEFAULT  NULL COMMENT '鞋号',

    @ApiModelProperty(value = "紧急联系人姓名")
    private String exstaffEmercontactname; //` varchar(20) DEFAULT  NULL COMMENT '紧急联系人姓名',

    @ApiModelProperty(value = "紧急联系电话")
    private String exstaffEmercontacttel; //` varchar(15) DEFAULT  NULL COMMENT '紧急联系电话',

    @ApiModelProperty(value = "与紧急联系人关系")
    private String exstaffEmercontactrelationship; //` varchar(100) DEFAULT  NULL COMMENT '紧急联系人关系',

    @ApiModelProperty(value = "登录用户名（缺省时为手机号，自动填入）")
    private String exstaffLoginname; //` varchar(20)  NOT NULL COMMENT '登录用户名,缺省时为手机号，自动填入',

    @ApiModelProperty(value = "用户昵称")
    private String exstaffLoginnickname; //` varchar(20)  DEFAULT NULL COMMENT '用户昵称',

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "出生日期")
    private Date exstaffBirthday; //` datetime DEFAULT NULL COMMENT '出生日期',

    @ApiModelProperty(value = "办公电话")
    private String exstaffTel; //` varchar(15) DEFAULT NULL COMMENT '办公电话',

    @ApiModelProperty(value = "电子邮箱")
    private String exstaffEmail; //` varchar(200) DEFAULT  NULL COMMENT '电子邮箱',

    @ApiModelProperty(value = "民族(数据字典)")
    private String exstaffNation; //` varchar(32) DEFAULT  NULL COMMENT '民族(数据字典)',

    @ApiModelProperty(value = "籍贯")
    private String exstaffNativeplace; //` varchar(100) DEFAULT  NULL COMMENT '籍贯',

    @ApiModelProperty(value = "婚姻状况(数据字典)")
    private String exstaffMarriagestate; //` varchar(32) DEFAULT  NULL COMMENT '婚姻状况(数据字典)',

    @ApiModelProperty(value = "身份证号码")
    private String exstaffIdentifyid; //` varchar(20) DEFAULT  NULL COMMENT '身份证号码',

    @ApiModelProperty(value = "学历(数据字典)")
    private String exstaffDegree; //` varchar(32) DEFAULT NULL COMMENT '学历(数据字典)',

    @ApiModelProperty(value = "微信号")
    private String exstaffWeixinid;

    @ApiModelProperty(value = "QQ号")
    private String exstaffQqid; //` varchar(20) DEFAULT  NULL COMMENT 'qqid',

    @ApiModelProperty(value = "联系地址")
    private String exstaffAddress; //` varchar(255) DEFAULT  NULL COMMENT '联系地址',

    @ApiModelProperty(value = "技术证书ID列表")
    private List<String> exstaffskillcertificateIds;

    @ApiModelProperty(value = "角色名称[一种角色名]，可以为Null")
    private List<String> roleNames;

    @ApiModelProperty(value = "责任网格全名列表，可以为Null")
    private List<String> gridFullnames;
}
