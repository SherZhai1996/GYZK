package cn.edu.ujs.lp.intells.common.entity.Serviceitem;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseHospEntity;
import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import cn.edu.ujs.lp.intells.common.service.ServiceitemcategoryServiceV2;
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
@Table(name = "tb_serviceitemcategory")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Serviceitemcategory extends BaseHospEntity {

    @Column(name = "serviceitemcategory_name",length = 50)
    private String serviceitemcategoryName; //分类名称',

    @Column(name = "serviceitemcategory_code",length = 20)
    private String serviceitemcategoryCode; //'服务事项分类code，编码规则:ST(医院)nnnn(一级)DDDD(二级)LLLL(三级)QQQQ，2019.10.30增加',

    @Column(name = "superior_id",length = 32)
    private String superiorId; // varchar(32) DEFAULT NULL COMMENT '上级分类id(外键引用)',

    @Transient
    private String superiorName;

    @Column(name = "service_type_id",length = 32)
    private String serviceTypeId; //varchar(32) DEFAULT NULL COMMENT '服务类别id(数据字典,工单类别，规定该类服务事项用于说明服务类别)，下一级从上一级中继承',

    @Transient
    private String serviceTypeName;

    @Column(name = "serviceitemcategory_level")
    private int serviceitemcategoryLevel; //` int(1) DEFAULT NULL COMMENT '分类等级，从0开始，医院为0级',

    @Column(name = "serviceitemcategory_fullname",length = 200)
    private String serviceitemcategoryFullname; //` varchar(200) DEFAULT NULL COMMENT '分类全名称',

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

    /**
     * 依据服务类别名称设置设置服务事项分类的服务类别ID
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
                throw new Exception("依据服务类别名称设置设置服务事项分类的服务类别ID失败：" + e.getMessage());
            }

        serviceTypeId = result;
        if (serviceTypeId == null)
            serviceTypeName = null;
        else
            serviceTypeName=name;
        return result;
    }
}
