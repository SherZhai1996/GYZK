package cn.edu.ujs.lp.intells.common.entity.Device;

import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 设备明细主要属性类
 */
@Data
public class DevicebillBrief {

    String id;

    /**
     * 设备运维编号，编号规则：设备类别代码+nnnn(设备编号)
     */
    private String  deviceCode;

    /**
     * 资产编码
     */
    private String assetCode;

    /**
     * 资产名称
     */
    private String  assetName;

    /**
     * 型号规格
     */
    private String modelSpec;

    /**
     * 设备运维类别ID
     */
    private String deviceCategory;

    private String deviceCategoryName;

    /**
     * 资产状态（是否在用）
     */
    private String isUsing;

    private String isUsingName;

    /**
     * 使用状态（数据字典:正常，闲置，报废，停用，库存，待维修）
     */
    private String useStatus;

    private String useStatusName;

    /**
     * 启用日期
     */
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date enableDate;

    /**
     * 使用期限（月）
     */
    private int serviceLife;

    /**
     * 维保到期
     */
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date repairExpire;

    /**
     * 管理员（外键关联，医院职工，科室先过滤）
     */
    private String administrators;

    private String administratorsName;

    /**
     * 任务归属(外键引用，外委班组)
     */
    private String taskOwner;

    private String taskOwnerName;

    /**
     * 区域ID
     */
    private String gridId;

    private String gridName;

    /**
     * 存放位置描述
     */
    private String dposition;

    /**
     * 使用配置（电机型号，额定功率，容量）
     */
    private String  configuration;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 资产用途（是否医疗用途）
     */
    private String isMedicaluse;

    private String isMedicaluseName;

    /**
     * 计量单位
     */
    private String measurementUnit;

    /**
     * 设备原值(元)
     */
    private double equipmentValue;

    /**
     * 资产来源（数据字典:购入、租赁，捐赠）
     */
    private String assetSource;

    private String assetSourceName;

    /**
     * 是否进口
     */
    private String isImported;

    private String isImportedName;

    /**
     * 供应商
     */
    private String supplier;

    /**
     * 供应商联系人
     */
    private String supplierContract;

    /**
     * 供应商电话
     */
    private String supplierTel;

    /**
     * 生成厂家
     */
    private String manufacturer;

    /**
     * 产地
     */
    private String productionPlace;

    /**
     * 售后电话
     */
    private String aftersaleTel;

    /**
     * 售后工程师
     */
    private String aftersaleEngineer;

    /**
     * 获取网格区域全名
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String getGridName(DBCommonService dbCommonService) throws Exception {
        String result = null;

        if ((gridId != null) && (gridId != ""))
            try {
                result = dbCommonService.getGridFullnamebyId(gridId);
            } catch (Exception e) {
                throw new Exception("获取网格区域全名失败：" + e.getMessage());
            }

        gridName = result;
        if (gridName == null) gridId = null;
        return result;
    }

    public String getDeviceCategoryName(DBCommonService dbCommonService) throws Exception {
        String result = null;

        if ((deviceCategory != null) && (deviceCategory != ""))
            try {
                result = dbCommonService.getDevicecategoryFullnamebyId(deviceCategory);
            } catch (Exception e) {
                throw new Exception("获取设备类别全名失败：" + e.getMessage());
            }

        deviceCategoryName = result;
        if (deviceCategoryName == null) deviceCategory = null;
        return result;
    }

    /**
     * 获取任务班组名称
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String getTaskOwnerName(DBCommonService dbCommonService) throws Exception {
        String result = null;

        if ((taskOwner != null) && (taskOwner != ""))
            try {
                result = dbCommonService.getExteamFullnamebyId(taskOwner);
            } catch (Exception e) {
                throw new Exception("获取物业服务班组全名失败：" + e.getMessage());
            }

        taskOwnerName = result;
        if (taskOwnerName == null) taskOwner = null;
        return result;
    }

    /**
     * 获取管理人员名称
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String getAdministratorsName(DBCommonService dbCommonService) throws Exception {
        String result = null;

        if ((administrators != null) && (administrators != ""))
            try {
                result = dbCommonService.getStaffFullnamebyId(administrators);
            } catch (Exception e) {
                throw new Exception("获取医院职工全名失败：" + e.getMessage());
            }

        administratorsName = result;
        if (administratorsName == null) administrators = null;
        return result;
    }

    /**
     * 获取设备使用状态名称
     * @param dataDictionaryService
     * @return
     * @throws Exception
     */
    public String getUseStatusName(DataDictionaryService dataDictionaryService) throws Exception {
        String result = null;

        if ((useStatus != null) && (useStatus != ""))
            try {
                result = dataDictionaryService.getDataDictionary("db_use_status_type").getNamebyID(useStatus);
            } catch (Exception e) {
                throw new Exception("获取设备使用状态名称失败：" + e.getMessage());
            }

        useStatusName = result;
        if (useStatusName == null) useStatus = null;
        return result;
    }

    /**
     * 获取资产来源名称
     * @param dataDictionaryService
     * @return
     * @throws Exception
     */
    public String getAssetSourceName(DataDictionaryService dataDictionaryService) throws Exception {
        String result = null;

        if ((assetSource != null) && (assetSource != ""))
            try {
                result = dataDictionaryService.getDataDictionary("db_assetsource_type").getNamebyID(assetSource);
            } catch (Exception e) {
                throw new Exception("获取资产来源名称失败：" + e.getMessage());
            }

        assetSourceName = result;
        if (assetSourceName == null) assetSource = null;
        return result;
    }

    /**
     * 获取是否正在使用名称
     * @return
     */
    public String getIsUsingName() {
        String result = null;

        if ((isUsing != null) && (isUsing.compareTo("1") == 0))
            result = "在用";
        else
        {
            result = "停用";
            isUsing = "0";
        }

        isUsingName = result;
        return result;
    }

    /**
     * 获取是否医疗设备名称
     * @return
     */
    public String getIsMedicaluseName() {
        String result = null;

        if ((isMedicaluse != null) && (isMedicaluse.compareTo("1") == 0))
            result = "医疗";
        else
        {
            result = "非医疗";
            isMedicaluse = null;
        }

        isMedicaluseName = result;
        return result;
    }

    /**
     * 获取是否进口描述
     * @return
     */
    public String getIsImportedName() {
        String result = null;

        if ((isImported != null) && (isImported.compareTo("1") == 0))
            result = "进口";
        else
        {
            result = "非进口";
            isImported = null;
        }

        isImportedName = result;
        return result;
    }
}
