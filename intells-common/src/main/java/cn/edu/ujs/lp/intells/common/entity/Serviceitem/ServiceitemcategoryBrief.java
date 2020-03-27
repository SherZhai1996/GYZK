package cn.edu.ujs.lp.intells.common.entity.Serviceitem;

import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import cn.edu.ujs.lp.intells.common.service.ServiceitemcategoryServiceV2;
import cn.edu.ujs.lp.intells.common.utils.ExcelUtils;
import lombok.Data;

@Data
public class ServiceitemcategoryBrief {

    /**
     * 内部ID
     */
    private String id;

    private String serviceitemcategoryName; //分类名称',

    private String serviceitemcategoryCode;

    private String superiorId;

    private String superiorName;

    private String serviceTypeId;

    private String serviceTypeName;

    /**
     * 获取上级服务事项分类节点全名
     * @param serviceitemcategoryServiceV2
     * @return
     * @throws Exception
     */
    public String getSuperiorName(ServiceitemcategoryServiceV2 serviceitemcategoryServiceV2) throws Exception {
        String result = null;

        if ((!ExcelUtils.isNullofString(superiorId)) && (serviceitemcategoryServiceV2 != null))
            try {
                result = serviceitemcategoryServiceV2.GetserviceitemcategoryFullnamebyID(superiorId);
            } catch (Exception e) {
                throw new Exception("获取上级服务事项分类节点全名失败：" + e.getMessage());
            }

        superiorName = result;
        if (superiorName == null) superiorId = null;
        return result;
    }

    /**
     * 获取服务事项分类的服务类别（工单类型）
     * @param dataDictionaryService
     * @return
     * @throws Exception
     */
    public String getServiceTypeName(DataDictionaryService dataDictionaryService) throws Exception {
        String result = null;

        if ((!ExcelUtils.isNullofString(serviceTypeId)) && (dataDictionaryService != null))
            try {
                result = dataDictionaryService.getDataDictionary("db_tasksheet_type").getNamebyID(serviceTypeId);
            } catch (Exception e) {
                throw new Exception("获取服务事项分类的服务类别（工单类型）失败：" + e.getMessage());
            }

        serviceTypeName = result;
        if (serviceTypeName == null) serviceTypeId = null;
        return result;
    }
}

