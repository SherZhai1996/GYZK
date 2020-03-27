package cn.edu.ujs.lp.intells.common.entity.Device;

import cn.edu.ujs.lp.intells.common.entity.Common.TreeInterface;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import lombok.Data;

/**
 * 设备类别主要属性类
 */
@Data
public class DevicecategoryBrief implements TreeInterface {

    /**
     * 内部ID
     */
    private String id;

    /**
     * 分类编号，编号规则：SB+医院编号+上一级编号+nnn(本级编号)
     */
    private String categoryCode;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 上级分类id(外键引用),第一级外键为null
     */
    private String superiorId;
    private String superiorName;

    /**
     * 分类等级，从1开始，医院为1级
     */
    private int devicecategoryLevel;

    /**
     * 在用标志
     */
    private String isUsing;

    private String isUsingName;

    /**
     * 管理归属（外键关联，关联医院后勤相关职工）
     */
    private String manageAffiliation;

    private String manageAffiliationName;

    /**
     * 责任归属(外键关联，关联外委公司)
     */
    private String responAffiliation;

    private String responAffiliationName;

    /**
     * 分类全名称
     */
    private String categoryFullname;

    public String getNodeID()
    {
        return getId();
    }

    public String getNodeName()
    {
        return getCategoryName();
    }

    public int getNodeLevel()
    {
        return getDevicecategoryLevel();
    }

    public String getNodeParentID()
    {
        return getSuperiorId();
    }

    public String getNodeParentName()
    {
        return getSuperiorName();
    }

    /**
     * 获取使用标志名称
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
     * 获取物业公司名称
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
}
