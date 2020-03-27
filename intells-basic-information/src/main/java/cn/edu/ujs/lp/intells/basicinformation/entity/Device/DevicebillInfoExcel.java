package cn.edu.ujs.lp.intells.basicinformation.entity.Device;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 设备明细Excel数据类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevicebillInfoExcel extends BaseRowModel {

    /**
     * 资产编码
     */
    @ExcelProperty(index = 0, value = {"设备明细","资产编码"})
    private String assetCode;

    /**
     * 资产名称
     */
    @ExcelProperty(index = 1, value = {"设备明细","资产名称"})
    private String  assetName;

    /**
     * 型号规格
     */
    @ExcelProperty(index = 2, value = {"设备明细","型号规格"})
    private String modelSpec;

    /**
     * 设备运维类别
     */
    @ExcelProperty(index = 3, value = {"设备明细","运维类别"})
    private String deviceCategoryName;

    /**
     * 资产状态（在用/停用）
     */
    @ExcelProperty(index = 4, value = {"设备明细","资产状态"})
    private String isUsingName;

    /**
     * 使用状态（数据字典:正常，闲置，报废，停用，库存，待维修）
     */
    @ExcelProperty(index = 5, value = {"设备明细","使用状态"})
    private String useStatusName;

    /**
     * 启用日期yyyyMMdd
     */
    @ExcelProperty(index = 6, value = {"设备明细","使用日期"})
    private String enableDate;

    /**
     * 使用期限（月）
     */
    @ExcelProperty(index = 7, value = {"设备明细","使用期限(月)"})
    private int serviceLife;

    /**
     * 维保到期yyyyMMdd
     */
    @ExcelProperty(index = 8, value = {"设备明细","维保到期"})
    private String repairExpire;

    /**
     * 管理员名称
     */
    @ExcelProperty(index = 9, value = {"设备明细","管理员"})
    private String administratorsName;

    /**
     * 任务归属(外委班组名称)
     */
    @ExcelProperty(index = 10, value = {"设备明细","任务归属"})
    private String taskOwnerName;

    /**
     * 区域名称
     */
    @ExcelProperty(index = 11, value = {"设备明细","存放区域"})
    private String gridName;

    /**
     * 存放位置描述
     */
    @ExcelProperty(index = 12, value = {"设备明细","存放位置"})
    private String dposition;

    /**
     * 使用配置（电机型号，额定功率，容量）
     */
    @ExcelProperty(index = 13, value = {"设备明细","使用配置"})
    private String  configuration;

    /**
     * 品牌
     */
    @ExcelProperty(index = 14, value = {"设备明细","品牌"})
    private String brand;

    /**
     * 资产用途（医疗/非医疗）
     */
    @ExcelProperty(index = 15, value = {"设备明细","资产用途"})
    private String isMedicaluseName;

    /**
     * 计量单位
     */
    @ExcelProperty(index = 16, value = {"设备明细","计量单位"})
    private String measurementUnit;

    /**
     * 设备原值(元)
     */
    @ExcelProperty(index = 17, value = {"设备明细","设备原值(元)"})
    private double equipmentValue;

    /**
     * 资产来源（数据字典:购入、租赁，捐赠）
     */
    @ExcelProperty(index = 18, value = {"设备明细","资产来源"})
    private String assetSourceName;

    /**
     * 是否进口
     */
    @ExcelProperty(index = 19, value = {"设备明细","是否进口"})
    private String isImportedName;

    /**
     * 供应商
     */
    @ExcelProperty(index = 20, value = {"设备明细","供应商"})
    private String supplier;

    /**
     * 供应商联系人
     */
    @ExcelProperty(index = 21, value = {"设备明细","供应商联系人"})
    private String supplierContract;

    /**
     * 供应商电话
     */
    @ExcelProperty(index = 22, value = {"设备明细","供应商电话"})
    private String supplierTel;

    /**
     * 生成厂家
     */
    @ExcelProperty(index = 23, value = {"设备明细","生成厂家"})
    private String manufacturer;

    /**
     * 产地
     */
    @ExcelProperty(index = 24, value = {"设备明细","产地"})
    private String productionPlace;

    /**
     * 售后电话
     */
    @ExcelProperty(index = 25, value = {"设备明细","售后电话"})
    private String aftersaleTel;

    /**
     * 售后工程师
     */
    @ExcelProperty(index = 26, value = {"设备明细","售后工程师"})
    private String aftersaleEngineer;
}
