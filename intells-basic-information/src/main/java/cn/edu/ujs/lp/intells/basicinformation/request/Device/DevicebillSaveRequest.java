package cn.edu.ujs.lp.intells.basicinformation.request.Device;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 设备保存参数类
 */
@Data
public class DevicebillSaveRequest {

    @ApiModelProperty(value = "设备ID,新增设备为null，否则为该设备ID值")
    private String id;

    /**
     * 资产编码
     */
    @ApiModelProperty(value = "资产编码", required = true)
    private String assetCode;

    /**
     * 资产名称
     */
    @ApiModelProperty(value = "资产名称", required = true)
    private String  assetName;

    /**
     * 型号规格
     */
    @ApiModelProperty(value = "型号规格", required = true)
    private String modelSpec;

    /**
     * 设备运维类别ID
     */
    @ApiModelProperty(value = "设备运维类别ID", required = true)
    private String deviceCategory;

    /**
     * 资产状态（是否在用）
     */
    @ApiModelProperty(value = "资产状态（是否在用）", required = true)
    private String isUsing;

    /**
     * 使用状态（数据字典:正常，闲置，报废，停用，库存，待维修）
     */
    @ApiModelProperty(value = "使用状态（数据字典:正常，闲置，报废，停用，库存，待维修）", required = true)
    private String useStatus;

    /**
     * 启用日期
     */
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "启用日期", required = true)
    private Date enableDate;

    /**
     * 使用期限（月）
     */
    @ApiModelProperty(value = "使用期限（月）", required = true)
    private int serviceLife;

    /**
     * 维保到期
     */
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "维保到期", required = true)
    private Date repairExpire;

    /**
     * 管理员（外键关联，医院职工，科室先过滤）
     */
    @ApiModelProperty(value = "管理员（外键关联，医院职工ID)", required = true)
    private String administrators;

    /**
     * 任务归属(外键引用，外委班组)
     */
    @ApiModelProperty(value = "任务归属(外键引用，物业服务班组ID)", required = true)
    private String taskOwner;

    /**
     * 区域ID
     */
    @ApiModelProperty(value = "区域ID", required = true)
    private String gridId;

    /**
     * 存放位置描述
     */
    @ApiModelProperty(value = "存放位置描述", required = true)
    private String dposition;

    /**
     * 使用配置（电机型号，额定功率，容量）
     */
    @ApiModelProperty(value = "使用配置描述（电机型号，额定功率，容量）", required = true)
    private String  configuration;

    /**
     * 品牌
     */
    @ApiModelProperty(value = "品牌", required = true)
    private String brand;

    /**
     * 资产用途（是否医疗用途）
     */
    @ApiModelProperty(value = "资产用途（是否医疗用途）", required = true)
    private String isMedicaluse;

    /**
     * 计量单位
     */
    @ApiModelProperty(value = "计量单位", required = true)
    private String measurementUnit;

    /**
     * 设备原值(元)
     */
    @ApiModelProperty(value = "设备原值(元)", required = true)
    private double equipmentValue;

    /**
     * 资产来源（数据字典:购入、租赁，捐赠）
     */
    @ApiModelProperty(value = "资产来源（数据字典:购入、租赁，捐赠）", required = true)
    private String assetSource;

    /**
     * 是否进口
     */
    @ApiModelProperty(value = "是否进口", required = true)
    private String isImported;

    /**
     * 供应商
     */
    @ApiModelProperty(value = "供应商", required = true)
    private String supplier;

    /**
     * 供应商联系人
     */
    @ApiModelProperty(value = "供应商联系人", required = true)
    private String supplierContract;

    /**
     * 供应商电话
     */
    @ApiModelProperty(value = "供应商电话", required = true)
    private String supplierTel;

    /**
     * 生成厂家
     */
    @ApiModelProperty(value = "生成厂家", required = true)
    private String manufacturer;

    /**
     * 产地
     */
    @ApiModelProperty(value = "产地", required = true)
    private String productionPlace;

    /**
     * 售后电话
     */
    @ApiModelProperty(value = "售后电话", required = true)
    private String aftersaleTel;

    /**
     * 售后工程师
     */
    @ApiModelProperty(value = "售后工程师", required = true)
    private String aftersaleEngineer;

    /**
     * 出厂日期
     */
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value = "出厂日期", required = true)
    private Date dateProduction;

    /**
     * 出厂编码
     */
    @ApiModelProperty(value = "出厂编码", required = true)
    private String  factoryCode;

    /**
     * 是否附属设备
     */
    @ApiModelProperty(value = "是否附属设备", required = true)
    private String isAccessory;

    /**
     * 附属设备（数据字典）
     */
    @ApiModelProperty(value = "附属设备（数据字典）", required = true)
    private String accessoryClass;

    /**
     *是否特种设备
     */
    @ApiModelProperty(value = "是否特种设备", required = true)
    private String isSpecial;

    /**
     *特种设备（数据字典）
     */
    @ApiModelProperty(value = "特种设备（数据字典）", required = true)
    private String specialClass;

    /**
     * 是否应急设备
     */
    @ApiModelProperty(value = "是否应急设备", required = true)
    private String isEmergency;

    /**
     *应急设备（数据字典）
     */
    @ApiModelProperty(value = "应急设备（数据字典）", required = true)
    private String emergencyClass;

    /**
     * 网格所在楼栋ID(网格ID)
     */
    @ApiModelProperty(value = "网格所在楼栋ID", required = true)
    private String remark;

    /**
     * 设备管关联科室列表，即使用设备科室列表
     */
    //@ApiModelProperty(value = "设备管关联科室列表，即使用设备科室列表", required = true)
    //private List<DeviceusedeptBrief> deviceusedepts;

}
