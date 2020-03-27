package cn.edu.ujs.lp.intells.common.entity.Serviceitem;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicebillInfoExcel extends BaseRowModel {

    /**
     * 服务事项名称
     */
    @ExcelProperty(index = 0, value = {"服务事项明细","服务事项名称"})
    private String serviceitemName;

    /**
     * 类别
     */
    @ExcelProperty(index = 1, value = {"服务事项明细","服务事项类别"})
    private String categoryName;

    /**
     * 服务类别（工单类别）
     */
    @ExcelProperty(index = 2, value = {"服务事项明细","服务类别"})
    private String serviceTypeName;
}
