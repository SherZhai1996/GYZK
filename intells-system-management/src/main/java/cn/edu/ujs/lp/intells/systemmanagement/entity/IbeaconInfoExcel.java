package cn.edu.ujs.lp.intells.systemmanagement.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 蓝牙信标Excel导入导出类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IbeaconInfoExcel extends BaseRowModel {

    /**
     * 名称
     */
    @ExcelProperty(index = 0, value = {"蓝牙信标","名称"})
    private String ibeaconName;

    /**
     * UUID
     */
    @ExcelProperty(index = 1, value = {"蓝牙信标","UUID"})
    private String ibeaconUUID;

    /**
     * RSSI
     */
    @ExcelProperty(index = 2, value = {"蓝牙信标","RSSI"})
    private String ibeaconRSSI;

    /**
     * 所在网格全名
     */
    @ExcelProperty(index = 3, value = {"蓝牙信标","所在网格全名"})
    private String gridName;

    /**
     * 启用标志
     */
    @ExcelProperty(index = 4, value = {"蓝牙信标","在用标志(启用/停用)"})
    private String isUsingName;

}
