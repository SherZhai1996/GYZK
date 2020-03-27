package cn.edu.ujs.lp.intells.basicinformation.entity.Device;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseHospEntity;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_devicebill")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Devicebill extends BaseHospEntity {

    //作为主要属性考虑
    /**
     * 设备运维编号，编号规则：设备类别代码+nnnn(设备编号)
     */
    @Column(name = "device_code",length = 20)
    private String  deviceCode;

    /**
     * 资产编码
     */
    @Column(name = "assetcode",length = 50)
    private String assetCode;

    /**
     * 资产名称
     */
    @Column(name = "assetname",length = 100)
    private String  assetName;

    /**
     * 型号规格
     */
    @Column(name = "modelspec",length = 50)
    private String modelSpec;

    /**
     * 设备运维类别ID
     */
    @Column(name = "devicecategory",length = 32)
    private String deviceCategory;

    @Transient
    private String deviceCategoryName;

    /**
     * 资产状态（是否在用）
     */
    @Column(name = "isusing")
    private String isUsing;

    @Transient
    private String isUsingName;


    /**
     * 使用状态（数据字典:正常，闲置，报废，停用，库存，待维修）
     */
    @Column(name = "use_status",length = 32)
    private String useStatus;

    @Transient
    private String useStatusName;

    /**
     * 启用日期
     */
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "enabledate")
    private Date enableDate;

    /**
     * 使用期限（月）
     */
    @Column(name = "service_life")
    private int serviceLife;

    /**
     * 维保到期
     */
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "repair_expire")
    private Date repairExpire;

    /**
     * 管理员（外键关联，医院职工，科室先过滤）
     */
    @Column(name = "administrators",length = 32)
    private String administrators;

    @Transient
    private String administratorsName;

    /**
     * 任务归属(外键引用，外委班组)
     */
    @Column(name = "task_owner",length = 32)
    private String taskOwner;

    @Transient
    private String taskOwnerName;

    /**
     * 区域ID
     */
    @Column(name = "grid_id",length = 32)
    private String gridId;

    @Transient
    private String gridName;

    /**
     * 存放位置描述
     */
    @Column(name = "dposition",length = 100)
    private String dposition;

    /**
     * 使用配置（电机型号，额定功率，容量）
     */
    @Column(name = "configuration",length = 200)
    private String  configuration;

    /**
     * 品牌
     */
    @Column(name = "brand",length = 50)
    private String brand;

    /**
     * 资产用途（是否医疗用途）
     */
    @Column(name = "ismedicaluse")
    private String isMedicaluse;

    @Transient
    private String isMedicaluseName;

    /**
     * 计量单位
     */
    @Column(name = "measurementunit",length = 10)
    private String measurementUnit;

    /**
     * 设备原值(元)
     */
    @Column(name = "equipment_value")
    private double equipmentValue;

    /**
     * 资产来源（数据字典:购入、租赁，捐赠）
     */
    @Column(name = "assetsource",length = 32)
    private String assetSource;

    @Transient
    private String assetSourceName;

    /**
     * 是否进口
     */
    @Column(name = "isimported")
    private String isImported;

    @Transient
    private String isImportedName;

    /**
     * 供应商
     */
    @Column(name = "supplier",length = 100)
    private String supplier;

    /**
     * 供应商联系人
     */
    @Column(name = "suppliercontract",length = 50)
    private String supplierContract;

    /**
     * 供应商电话
     */
    @Column(name = "suppliertel",length = 15)
    private String supplierTel;

    /**
     * 生成厂家
     */
    @Column(name = "manufacturer",length = 100)
    private String manufacturer;

    /**
     * 产地
     */
    @Column(name = "productionplace",length = 100)
    private String productionPlace;

    /**
     * 售后电话
     */
    @Column(name = "aftersaletel",length = 15)
    private String aftersaleTel;

    /**
     * 售后工程师
     */
    @Column(name = "aftersaleengineer",length = 50)
    private String aftersaleEngineer;

    //作为次要属性考虑
    /**
     * 出厂日期
     */
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "dateproduction")
    private Date dateProduction;

    /**
     * 出厂编码
     */
    @Column(name = "factorycode",length = 50)
    private String  factoryCode;

    /**
     * 是否附属设备
     */
    @Column(name = "isaccessory")
    private String isAccessory;

    @Transient
    private String isAccessoryName;

    /**
     * 附属设备（数据字典）
     */
    @Column(name = "accessoryclass",length = 32)
    private String accessoryClass;

    @Transient
    private String accessoryClassName;

    /**
     *是否特种设备
     */
    @Column(name = "isspecial")
    private String isSpecial;

    @Transient
    private String isSpecialName;

    /**
     *特种设备（数据字典）
     */
    @Column(name = "specialclass",length = 32)
    private String specialClass;

    @Transient
    private String specialClassName;

    /**
     * 是否应急设备
     */
    @Column(name = "isemergency")
    private String isEmergency;

    @Transient
    private String isEmergencyName;

    /**
     *应急设备（数据字典）
     */
    @Column(name = "emergencyclass",length = 32)
    private String emergencyClass;

    @Transient
    private String emergencyClassName;

    /**
     * 设备图片文件名
     */
    @Column(name = "devicepic",length = 400,updatable = false)
    private String devicePic;

    /**
     *所在网格节点的网格树
     */
    @Transient
    private List<List<String>> gridPathIds;

    /**
     * 获取上级网络的全路径ID列表
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public List<List<String>>  getGridPathbyId(DBCommonService dbCommonService) throws Exception
    {
        List<List<String>> lst = null;

        if ((dbCommonService != null) && (gridId != null) && (gridId.length() > 0)) {
            try {
                lst = new ArrayList<>();
                List<String> its = dbCommonService.getGridPathbyId(gridId);
                lst.add(its);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据网格ID获取网格全路径ID列表失败:"+e.getMessage());
            }
        }

        gridPathIds = lst;
        return lst;
    }

    /**
     * 设备管关联科室列表，即使用设备科室列表
     */
    //@Transient
    //private List<DeviceusedeptBrief> deviceusedepts;

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
        if ((gridName == null) || (gridName == "")) gridId = null;
        return result;
    }

    /**
     * 依据网格区域全名获取ID
     * @param name
     * @param dbCommonService
     * @throws Exception
     */
    public void setGridIdFromName(String name,DBCommonService dbCommonService) throws Exception
    {
        if ((name != null) && (name != ""))
            try {
                gridId = dbCommonService.getGridIdbyFullname(getHospID(),name);
            }
            catch (Exception e)
            {
                throw new Exception("依据网格区域全名获取ID失败："+e.getMessage());
            }
    }

    /**
     * 获取设备类别名称
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String getDeviceCategoryName(DBCommonService dbCommonService) throws Exception {
        String result = null;

        if ((deviceCategory != null) && (deviceCategory != ""))
            try {
                result = dbCommonService.getDevicecategoryFullnamebyId(deviceCategory);
            } catch (Exception e) {
                throw new Exception("获取设备类别全名失败：" + e.getMessage());
            }

        deviceCategoryName = result;
        if ((deviceCategoryName == null) || (deviceCategoryName == "")) deviceCategory = null;
        return result;
    }

    /**
     * 依据设备类别全名获取设备类别ID
     * @param name
     * @param dbCommonService
     * @throws Exception
     */
    public void setDeviceCategoryFromName(String name,DBCommonService dbCommonService) throws Exception
    {
        if ((name != null) && (name != ""))
            try {
                deviceCategory = dbCommonService.getDevicecategoryIdbyFullname(getHospID(),name);
            }
            catch (Exception e)
            {
                throw new Exception("依据设备类别全名获取设备类别ID失败："+e.getMessage());
            }
    }
    /**
     * 获取任务班组名称
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String getTaskOwnerName(DBCommonService dbCommonService) throws Exception
    {
        String result = null;

        if ((taskOwner != null) && (taskOwner != ""))
            try {
                result = dbCommonService.getExteamFullnamebyId(taskOwner);
            }
            catch (Exception e)
            {
                throw new Exception("获取物业服务班组全名失败："+e.getMessage());
            }

        taskOwnerName=result;
        if ((taskOwnerName == null) || (taskOwnerName == "")) taskOwner = null;
        return result;
    }

    /**
     * 依据物业公司ID和服务班组全名获取物业服务班组ID
     * @param excompanyID
     * @param name
     * @param dbCommonService
     * @throws Exception
     */
    public void setTaskOwnerFromName(String excompanyID,String name,DBCommonService dbCommonService) throws Exception
    {
        if ((name != null) && (name != ""))
            try {
                taskOwner = dbCommonService.getExteamIdbyFullName(getHospID(),name);
            }
            catch (Exception e)
            {
                throw new Exception("依据物业公司ID和服务班组全名获取物业服务班组ID失败："+e.getMessage());
            }
    }
    /**
     * 获取管理人员名称
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String getAdministratorsName(DBCommonService dbCommonService) throws Exception
    {
        String result = null;

        if ((administrators != null) && (administrators != ""))
            try {
                result = dbCommonService.getStaffFullnamebyId(administrators);
            }
            catch (Exception e)
            {
                throw new Exception("获取医院职工全名失败："+e.getMessage());
            }

        administratorsName=result;
        if ((administratorsName == null) || (administratorsName == "")) administrators = null;
        return result;
    }


    /**
     * 依据医院职工全名获取ID
     * @param name
     * @param dbCommonService
     * @throws Exception
     */
    public void setAdministratorsFromName(String name,DBCommonService dbCommonService) throws Exception
    {
        String result = null;

        if ((name != null) && (name != ""))
            try {
                administrators = dbCommonService.getStaffIdbyFullname(getHospID(),name);
            }
            catch (Exception e)
            {
                throw new Exception("依据医院职工全名获取ID失败："+e.getMessage());
            }
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
        if ((useStatusName == null) || (useStatusName == "")) useStatus = null;
        return result;
    }

    public void setUseStatusFromName(String name,DataDictionaryService dataDictionaryService) throws Exception
    {
        String result = null;

        if ((name != null) && (name != ""))
            try {
                useStatus = dataDictionaryService.getDataDictionary("db_use_status_type").getIDbyName(name);
            }
            catch (Exception e)
            {
                throw new Exception("依据使用状态名称获取ID失败："+e.getMessage());
            }
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
        if ((assetSourceName == null) || (assetSourceName == "")) assetSource = null;
        return result;
    }

    /**
     * 获取资产来源ID
     * @param name
     * @param dataDictionaryService
     * @throws Exception
     */
    public void setAssetSourceFromName(String name,DataDictionaryService dataDictionaryService) throws Exception
    {
        if ((name != null) && (name != ""))
            try {
                assetSource = dataDictionaryService.getDataDictionary("db_assetsource_type").getIDbyName(name);
            }
            catch (Exception e)
            {
                throw new Exception("获取资产来源ID失败："+e.getMessage());
            }
    }

    /**
     * 获取是否正在使用名称
     * @return
     */
    public String getIsUsingName() {
        String result = null;

        if ((isUsing != null) && (isUsing.compareTo("1") == 0))
           result = "在用";
        else {
            result = "停用";
            isUsing = "0";
        }

        isUsingName = result;
        return result;
    }

    /**
     * 设置使用状态
     * @param name
     */
    public void setIsUsingFromName(String name) {
        String result = null;

        if ((name != null) && (name.replace(" ","").compareTo("在用") == 0))
            isUsing = "1";
        else
            isUsing = "0";
    }

    /**
     * 获取是否医疗设备名称
     * @return
     */
    public String getIsMedicaluseName() {
        String result = null;

        if ((isMedicaluse != null) && (isMedicaluse.compareTo("1") == 0))
            result = "医疗";
        else {
            result = "非医疗";
            isMedicaluse = "0";
        }

        isMedicaluseName = result;
        return result;
    }


    public void setIsMedicaluseFromName(String name) {
        if ((name != null) && (name.replace(" ","").compareTo("医疗") == 0))
            isMedicaluse = "1";
        else
            isMedicaluse = "0";
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
            isImported = "0";
        }

        isImportedName = result;
        return result;
    }

    public void setIsImportedFromName(String name) {
        if ((name != null) && (name.replace(" ","").compareTo("进口")==0))
            isImported = "1";
        else
            isImported = "0";
    }

    /**
     * 获取是否附属设备标志
     * @return
     */
    public String getIsAccessoryName() {
        String result = null;

        if ((isAccessory != null) && (isAccessory.compareTo("1") == 0))
            result = "附属设备";
        else {
            result = "非附属设备";
            isAccessory = "0";
        }

        isAccessoryName = result;
        return result;
    }

    /**
     * 获取附属设备类别
     * @param dataDictionaryService
     * @return
     * @throws Exception
     */
    public String getAccessoryClassName(DataDictionaryService dataDictionaryService) throws Exception {
        String result = null;

        if ((accessoryClass != null) && (accessoryClass != ""))
            try {
                result = dataDictionaryService.getDataDictionary("db_accessoryclass_type").getNamebyID(accessoryClass);
            } catch (Exception e) {
                throw new Exception("获取附属设备名称失败：" + e.getMessage());
            }

        accessoryClassName = result;
        if (accessoryClassName == null) accessoryClass = null;
        return result;
    }

    /**
     * 获取是否特种设备描述
     * @return
     */
    public String getIsSpecialName() {
        String result = null;

        if ((isSpecial != null) && (isSpecial.compareTo("1") == 0))
            result = "特种设备";
        else
        {
            result = "非特种设备";
            isSpecial = "0";
        }

        isSpecialName = result;
        return result;
    }

    /**
     * 获取特种设备类别
     * @param dataDictionaryService
     * @return
     * @throws Exception
     */
    public String getSpecialClassName(DataDictionaryService dataDictionaryService) throws Exception {
        String result = null;

        if ((specialClass != null) && (specialClass != ""))
            try {
                result = dataDictionaryService.getDataDictionary("db_specialclass_type").getNamebyID(specialClass);
            } catch (Exception e) {
                throw new Exception("获取特种设备名称失败：" + e.getMessage());
            }

        specialClassName = result;
        if (specialClassName == null) specialClass = null;
        return result;
    }

    /**
     * 获取是否是应急设备的描述
     * @return
     */
    public String getIsEmergencyName() {
        String result = null;

        if ((isEmergency != null) && (isEmergency.compareTo("1") == 0))
            result = "应急设备";
        else {
            result = "非应急设备";
            isEmergency = "0";
        }

        isEmergencyName = result;
        return result;
    }

    /**
     * 获取应急设备名称
     * @param dataDictionaryService
     * @return
     * @throws Exception
     */
    public String getEmergencyClassName(DataDictionaryService dataDictionaryService) throws Exception {
        String result = null;

        if ((emergencyClass != null) && (emergencyClass != ""))
            try {
                result = dataDictionaryService.getDataDictionary("db_emergencyclass_type").getNamebyID(emergencyClass);
            } catch (Exception e) {
                throw new Exception("获取应急设备名称失败：" + e.getMessage());
            }

        emergencyClassName = result;
        if (emergencyClassName == null) emergencyClass = null;
        return result;
    }

}
