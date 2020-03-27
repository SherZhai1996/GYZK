package cn.edu.ujs.lp.intells.basicinformation.entity.Device;


import cn.edu.ujs.lp.intells.common.entity.Common.BaseHospEntity;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 设备类别完整类
 */
@Entity
@Table(name = "tb_devicecategory")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Devicecategory extends BaseHospEntity {

    /**
     * 分类编号，编号规则：SB+医院编号+上一级编号+nnn(本级编号)
     */
    @Column(name = "category_code",length = 20)
    private String categoryCode;

    /**
     * 分类名称
     */
    @Column(name = "category_name",length = 50)
    private String categoryName;

    /**
     * 上级分类id(外键引用),第一级外键为null
     */
    @Column(name = "superior_id",length = 32)
    private String superiorId;

    @Transient
    private String superiorName;

    /**
     * 分类等级，从1开始，医院为1级
     */
    @Column(name = "devicecategory_level")
    private int devicecategoryLevel;

    /**
     * 排序号
     */
    @Column(name = "sortorder")
    private int sortOrder;

    /**
     * 在用标志
     */
    @Column(name = "isusing")
    private String isUsing;

    @Transient
    private String isUsingName;

    /**
     * 管理归属（外键关联，关联医院后勤相关职工）
     */
    @Column(name = "manage_affiliation",length = 32)
    private String manageAffiliation;

    @Transient
    private String manageAffiliationName;

    /**
     * 责任归属(外键关联，关联外委公司)
     */
    @Column(name = "respon_affiliation",length = 32)
    private String responAffiliation;

    @Transient
    private String responAffiliationName;

    /**
     * 分类全名称
     */
    @Column(name = "category_fullname",length = 200)
    private String categoryFullname;


    /**
     * 获取使用标志名称
     * @return
     */
    public String getIsUsingName() {
        String result = "停用";

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
     * 设置启用状态
     * @param name
     */
    public void setIsUsingFromgName(String name)
    {
        String result = null;

        if ((name != null) && (name.replace(" ","").compareTo("在用") == 0))
            isUsing = "1";
        else
            isUsing = "0";
    }


    /**
     * 获取设备类别全名
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String getSuperiorName(DBCommonService dbCommonService) throws Exception {
        String result = null;

        if ((superiorId != null) && (superiorId != ""))
            try {
                result = dbCommonService.getDevicecategoryFullnamebyId(superiorId);
            } catch (Exception e) {
                throw new Exception("获取设备类别全名失败：" + e.getMessage());
            }

        superiorName = result;
        if (superiorName == null) superiorId = null;
        return result;
    }

    /**
     * 获取管理归属（管理设备的医院职工）的全名
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String getManageAffiliationName(DBCommonService dbCommonService) throws Exception {
        String result = null;

        if ((manageAffiliation != null) && (manageAffiliation != ""))
            try {
                result = dbCommonService.getStaffFullnamebyId(manageAffiliation);
            } catch (Exception e) {
                throw new Exception("获取医院职工全名失败：" + e.getMessage());
            }

        manageAffiliationName = result;
        if (manageAffiliationName == null) manageAffiliation = null;
        return result;
    }


    /**
     * 设置管理归属
     * @param name
     * @param dbCommonService
     * @throws Exception
     */
    public void setManageAffiliationFromName(String name,DBCommonService dbCommonService) throws Exception
    {
        if ((name != null) && (name != ""))
            try {
                manageAffiliation = dbCommonService.getStaffIdbyFullname(getHospID(),name);
            }
            catch (Exception e)
            {
                throw new Exception("获取医院职工ID失败："+e.getMessage());
            }
    }


    /**
     * 获取责任物业公司名称
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String getResponAffiliationName(DBCommonService dbCommonService) throws Exception {
        String result = null;

        if ((responAffiliation != null) && (responAffiliation != ""))
            try {
                result = dbCommonService.getExcompanyNamebyId(responAffiliation);
            } catch (Exception e) {
                throw new Exception("获取物业公司名称失败：" + e.getMessage());
            }

        responAffiliationName = result;
        if (responAffiliationName == null) responAffiliation = null;
        return result;
    }

    /**
     * 设置责任物业公司
     * @param name
     * @param dbCommonService
     * @throws Exception
     */
    public void setResponAffiliationFromName(String name,DBCommonService dbCommonService) throws Exception
    {
        if ((name != null) && (name != ""))
            try {
                responAffiliation = dbCommonService.getExcompanyIdbyName(getHospID(),name);
            }
            catch (Exception e)
            {
                throw new Exception("获取物业公司ID失败："+e.getMessage());
            }
    }
}
