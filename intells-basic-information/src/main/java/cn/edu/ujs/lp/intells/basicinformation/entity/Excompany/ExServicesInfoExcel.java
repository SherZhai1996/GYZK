package cn.edu.ujs.lp.intells.basicinformation.entity.Excompany;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务班组Excel数据类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExServicesInfoExcel extends BaseRowModel {

    @ExcelProperty(index = 0, value = {"物业公司业务部门","公司名称"})
    private String excompanyName; //所属公司名称',

    @ExcelProperty(index = 1, value = {"物业公司业务部门","业务部门名称"})
    private String exserviceName; //业务部门名称',

    @ExcelProperty(index = 2, value = {"物业公司业务部门","业务类型"})
    private String serviceName; //服务类型ID',

    @ExcelProperty(index = 4, value = {"物业公司业务部门","业务主管姓名"})
    private String leaderName; //业务主管姓名',

    @ExcelProperty(index = 5, value = {"物业公司业务部门","业务主管电话"})
    private String leaderTel; //业务主管电话',
}
