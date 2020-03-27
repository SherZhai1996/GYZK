package cn.edu.ujs.lp.intells.common.entity.Serviceitem;

import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import cn.edu.ujs.lp.intells.common.utils.ExcelUtils;
import lombok.Data;

@Data
public class ServicebillBrief {
    /**
     * 内部ID
     */
    private String id;

    private String serviceitemName;

    private String categoryId;

    private String categoryName;

    private String serviceTypeId;

    private String serviceTypeName;

    /**
     * 获取服务事项的服务类别（工单类型）
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
                throw new Exception("获取服务事项的服务类别（工单类型）失败：" + e.getMessage());
            }

        serviceTypeName = result;
        if (serviceTypeName == null) serviceTypeId = null;
        return result;
    }

    /**
     * 依据服务类别名称设置设置服务事项的服务类别ID
     * @param name
     * @param dataDictionaryService
     * @return
     * @throws Exception
     */
    public String setServiceType(String name, DataDictionaryService dataDictionaryService) throws Exception {
        String result = null;

        if ((!ExcelUtils.isNullofString(name)) && (dataDictionaryService != null))
            try {
                result = dataDictionaryService.getDataDictionary("db_tasksheet_type").getIDbyName(name);
            } catch (Exception e) {
                throw new Exception("依据服务类别名称设置设置服务事项的服务类别ID失败：" + e.getMessage());
            }

        serviceTypeId = result;
        if (serviceTypeId == null)
            serviceTypeName = null;
        else
            serviceTypeName=name;
        return result;
    }
}
