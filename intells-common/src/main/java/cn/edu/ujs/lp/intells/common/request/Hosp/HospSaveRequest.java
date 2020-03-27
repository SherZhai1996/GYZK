package cn.edu.ujs.lp.intells.common.request.Hosp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * 医院具体信息保存
 *
 * @author Meredith
 * @date 2019-09-18
 */
@Data
public class HospSaveRequest {

    /**
     * 医院名称
     */
    @ApiModelProperty(value = "医院名称，不能为空", required = true)
    @NotBlank(message = "医院名称非空")
    private String hospName;

    /**
     * 登记证号
     */
    @ApiModelProperty(value = "医院登记证号")
    private String hospCertificateNumber;

    /**
     * 创立时间
     */
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "创立时间，yyyy-MM-dd")
    private Date hospBuildDate;

    /**
     * 编制人数
     */
    @ApiModelProperty(value = "编制人数")
    private Integer hospAuthorizedStrength;

    /**
     * 建筑面积(m²)
     */
    @ApiModelProperty(value = "建筑面积(m²)")
    private Double hospBuildArea;

    /**
     * 门急诊面积(m²)
     */
    @ApiModelProperty(value = "门急诊面积(m²)")
    private Double hospEmergencyArea;

    /**
     * 医院床位数
     */
    @ApiModelProperty(value = "医院床位数")
    private Integer hospBedAmount;

    /**
     * 日门诊量
     */
    @ApiModelProperty(value = "日门诊量")
    private Integer hospOutpatientVolume;

    /**
     * 医院详细地址
     */
    @ApiModelProperty(value = "医院详细地址")
    private String hospDetailaddress;

    /**
     * 设计车位数
     */
    @ApiModelProperty(value = "设计车位数")
    private Integer hospParkingAmount;

    /**
     * 驻院民警数
     */
    @ApiModelProperty(value = "驻院民警数")
    private Integer hospPoliceAmount;

    /**
     * 保安数量
     */
    @ApiModelProperty(value = "保安数量")
    private Integer hospSecurityAmount;

    /**
     * 保安公司名称
     */
    @ApiModelProperty(value = "保安公司ID，从外委公司中选择，否则为空")
    private String hospSecurityCompanyId;

    /**
     * 医院院长，直接填写姓名
     */
    @ApiModelProperty(value = "医院院长ID")
    private String hospPresidentID;

    /**
     * 主要代表人，直接填写姓名
     */
    @ApiModelProperty(value = "主要代表人")
    private String hospRepresentative;

    /**
     * 法人代表，直接填写姓名
     */
    @ApiModelProperty(value = "法人代表")
    private String hospLegalName;

    /**
     * 主要领导团队，直接填写姓名
     */
    @ApiModelProperty(value = "主要领导团队")
    private String hospLeadershipTeam;

    /**
     * 总务处长姓名，直接填写姓名
     */
    @ApiModelProperty(value = "总务处长姓名")
    private String hospAffairChief;

    /**
     * 总务处长手机号码
     */
    @ApiModelProperty(value = "总务处长手机号码")
    private String hospAffairChiefMobile;

    /**
     *总务处长办公电话
     */
    @ApiModelProperty(value = "总务处长办公电话")
    private String hospAffairChiefTel;

    /**
     * 总务处长邮箱
     */
    @ApiModelProperty(value = "总务处长邮箱")
    private String hospAffairChiefEmail;

    /**
     * 保卫处长姓名，直接填写姓名
     */
    @ApiModelProperty(value = "保卫处长姓名")
    private String hospSecurityChief;

    /**
     * 保卫处长手机号码
     */
    @ApiModelProperty(value = "保卫处长手机号码")
    private String hospSecurityChiefMobile;

    /**
     * 医院简介
     */
    @ApiModelProperty(value = "医院简介")
    private String hospIntroduction;

    /**
     * 一站式服务电话
     */
    @ApiModelProperty(value = "一站式服务电话")
    private String hospServeCenterTel;

    private List<String> hospPresidentList;

    private List<String> hospServicepersons;

}

