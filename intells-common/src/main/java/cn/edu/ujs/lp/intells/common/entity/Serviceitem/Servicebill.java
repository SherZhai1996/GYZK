package cn.edu.ujs.lp.intells.common.entity.Serviceitem;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseHospEntity;
import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import cn.edu.ujs.lp.intells.common.utils.ExcelUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tb_servicebill")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Servicebill  extends BaseHospEntity {
    @Column(name = "serviceitem_name",length = 50)
    private String serviceitemName; //` varchar(50) NOT NULL COMMENT '服务事项名称',

    @Column(name = "category_id",length = 32)
    private String categoryId; //` varchar(32) DEFAULT NULL COMMENT '分类id(外键引用)',

    @Transient
    private String categoryName;

    @Column(name = "service_type_id",length = 32)
    private String serviceTypeId; //` varchar(32) DEFAULT NULL COMMENT '服务类别id(数据字典，工单类别)，自动从类别中继承',

    @Transient
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
