package cn.edu.ujs.lp.intells.common.entity.Serviceitem;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceitemcategoryInfoExcel extends BaseRowModel {

    /**
     * 类别名称
     */
    @ExcelProperty(index = 0, value = {"服务事项类别","类别名称"})
    private String serviceitemcategoryName; //分类名称',

    /**
     * 上级事项类别
     */
    @ExcelProperty(index = 1, value = {"服务事项类别","上级事项类别"})
    private String superiorName;

    /**
     * 服务类别
     */
    @ExcelProperty(index = 2, value = {"服务事项类别","服务类别"})
    private String serviceTypeName;

}
