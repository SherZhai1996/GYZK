package cn.edu.ujs.lp.intells.basicinformation.request.hosp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 职工新增
 *
 * @author Meredith
 * @data 2019-10-08
 */
@Data
public class StaffSaveRequest {

    @ApiModelProperty(value = "职工ID,新增用户为null，否则为该用户ID值")
    private String id;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "职工姓名，不能为空", required = true)
    @NotBlank(message = "职工姓名非空")
    private String staffName;

    /**
     * 性别
     */
    @ApiModelProperty(value = "职工性别", required = true)
    private String staffSex;

    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "职工身份证号", required = true)
    private String staffIdentification;

    /**
     * 职工号码
     */
    @ApiModelProperty(value = "职工工号", required = true)
    private String staffCode;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "职工手机号码，不能为空", required = true)
    @NotBlank(message = "职工手机号码非空")
    private String staffMobile;

    /**
     * 所属科室部门id
     */
    @ApiModelProperty(value = "科室ID，不能为空", required = true)
    @NotBlank(message = "科室ID非空")
    private String deptId;

    /**
     * 激活状态
     */
    @ApiModelProperty(value = "职工激活状态", required = true)
    private String staffActivestatus;

    /**
     * 是否有权使用医助(1--有权, 0--无权)
     */
    @ApiModelProperty(value = "职工是否有权使用微信医助手", required = true)
    private String isuseMedicalhelp;

    /**
     * 登录用户名,缺省时为手机号，自动填入
     */
    @ApiModelProperty(value = "职工登录名", required = true)
    private String staffLoginName;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "职工昵称", required = true)
    private String staffLoginNickname;

    /**
     * 办公电话
     */
    @ApiModelProperty(value = "职工办公电话", required = true)
    private String staffTel;

    /**
     * 出生日期
     */
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "职工出生日期", required = true)
    private String staffBirthday;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "职工有效", required = true)
    private String staffEmail;

    /**
     * 行政职务
     */
    @ApiModelProperty(value = "职工行政职务", required = true)
    private String staffAdminisPosition;

    /**
     * 技术职务
     */
    @ApiModelProperty(value = "职工技术职称", required = true)
    private String staffTechnicalPosition;

    /**
     * 教学职务
     */
    @ApiModelProperty(value = "职工教学职务", required = true)
    private String staffEducationPosition;

    /**
     * 导师类型
     */
    @ApiModelProperty(value = "职工导师类型", required = true)
    private String staffTutorType;

    /**
     * 学历
     */
    @ApiModelProperty(value = "职工学历", required = true)
    private String staffDegree;

    /**
     * 微信号
     */
    @ApiModelProperty(value = "职工微信号", required = true)
    private String staffWeixin;

    /**
     * QQ号
     */
    @ApiModelProperty(value = "职工QQ号", required = true)
    private String staffQq;

    @ApiModelProperty(value = "职工角色名【一种角色】，可以为null(默认为医院注册职工)", required = true)
    private List<String> roleNames;
    //private List<StaffRoleBrief> roleIds;

    @ApiModelProperty(value = "职工责任网格区域全名列表，可以为null", required = true)
    private List<String> gridFullnames;
    //private List<StaffGridBrief> gridIds;
}
