package cn.edu.ujs.lp.intells.basicinformation.entity.Hosp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 网格数据Excel导入导出模版
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GridInfoExcel extends BaseRowModel {
    /**
     * 网格名称
     */
    @ExcelProperty(index = 0, value = {"医院网格区域数据","网格名称"})
    private String gridName;

    /**
     * 上级网格名称
     */
    @ExcelProperty(index = 1, value = {"医院网格区域数据","上级网格名称"})
    private String superGridName;

    /**
     * 网格用途
     */
    @ExcelProperty(index = 2, value = {"医院网格区域数据","网格用途"})
    private String gridPlaceclassName;

    /**
     * 网格所属科室
     */
    @ExcelProperty(index = 3, value = {"医院网格区域数据","所属科室"})
    private String deptName;

    /**
     * 网格面积
     */
    @ExcelProperty(index = 4, value = {"医院网格区域数据","网格面积"})
    private double gridArea;

    /**
     * 网格负责人
     */
    @ExcelProperty(index = 5, value = {"医院网格区域数据","网格负责人"})
    private String gridLeaderName;

    /**
     * 电话，多部电话则分号分隔
     */
    @ExcelProperty(index = 6, value = {"医院网格区域数据","电话"})
    private String gridTels;

    /**
     * 服务班组，多个服务班组则分号分隔，仅供输出使用
     */
    @ExcelProperty(index = 7, value = {"医院网格区域数据","服务班组"})
    private String gridteams;
}
