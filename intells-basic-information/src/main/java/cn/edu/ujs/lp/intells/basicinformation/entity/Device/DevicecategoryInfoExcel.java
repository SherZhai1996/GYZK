package cn.edu.ujs.lp.intells.basicinformation.entity.Device;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备类别Excel导入导出类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevicecategoryInfoExcel extends BaseRowModel {

    /**
     * 分类名称
     */
    @ExcelProperty(index = 0, value = {"设备运维类别","分类名称"})
    private String categoryName;

    /**
     * 上级分类
     */
    @ExcelProperty(index = 1, value = {"设备运维类别","上级分类"})
    private String superiorName;

    /**
     * 在用标志(在用/停用)
     */
    @ExcelProperty(index = 2, value = {"设备运维类别","设备状态"})
    private String isUsing;

    /**
     * 管理归属(医院职工)[科室->姓名]
     */
    @ExcelProperty(index = 3, value = {"设备运维类别","管理归属"})
    private String manageAffiliationname;

    /**
     * 责任归属(外委公司)[物业公司名称]
     */
    @ExcelProperty(index = 4, value = {"设备运维类别","责任归属"})
    private String responAffiliationname;

}
