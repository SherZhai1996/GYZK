package cn.edu.ujs.lp.intells.systemmanagement.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 医院管理信息保存对象
 */
@Data
public class HospMainSaveRequest {

    @ApiModelProperty(value = "医院ID,新增医院为null，否则为该医院ID值")
    private String id;

    /**
     * 医院名称
     */
    @ApiModelProperty(value = "医院名称，不能为空，不能重名", required = true)
    @NotBlank(message = "医院名称非空")
    private String hospName;

    /**
     * 省份ID
     */
    @ApiModelProperty(value = "省份编码，不能为空", required = true)
    @NotBlank(message = "省份编码非空")
    private String Pid;

    /**
     * 市
     */
    @ApiModelProperty(value = "市编码，不能为空", required = true)
    @NotBlank(message = "市编码非空")
    private String Cid;

    /**
     * 县（市）
     */
    @ApiModelProperty(value = "县市编码，不能为空", required = true)
    @NotBlank(message = "县市编码非空")
    private String CountyId;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "医院详细地址")
    private String hospDetailaddress;

    /**
     * 医院院长，直接填写姓名
     */
    @ApiModelProperty(value = "医院院长ID")
    private String hospPresidentID;

    /**
     * 法人代表，直接填写姓名
     */
    @ApiModelProperty(value = "医院法人代表姓名")
    private String hospLegalName;

    /**
     * 中心点(经度)
     */
    @ApiModelProperty(value = "医院中心点经度")
    private Double hospCenterX;

    /**
     * 中心点(纬度)
     */
    @ApiModelProperty(value = "医院中心点纬度")
    private Double hospCenterY;

    @ApiModelProperty(value = "医院半径（米）")
    private int hospRadius=100;

    /**
     * 医院功能列表
     */
    @ApiModelProperty(value = "医院功能名称列表")
    private List<String> hospFunctionNames;

    /**
     * 医院系统管理员基本信息，登录名缺省为手机号，密码缺省为12345678
     */
    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户手机")
    private String userMobile;
}
