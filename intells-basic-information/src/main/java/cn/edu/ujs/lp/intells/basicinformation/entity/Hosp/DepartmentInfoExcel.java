package cn.edu.ujs.lp.intells.basicinformation.entity.Hosp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Excel导入导出模板
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentInfoExcel extends BaseRowModel {

    @ExcelProperty(index = 0,value = {"组织机构","科室名称"})
    private String deptName;

    @ExcelProperty(index = 1,value = {"组织机构","科室负责人"})
    private String deptLeaderName;

    @ExcelProperty(index = 2,value = {"组织机构","负责人电话"})
    private String deptLeaderTel;

    @ExcelProperty(index = 3,value = {"组织机构","组织机构编号"})
    private String deptCode;

    @ExcelProperty(index = 4,value = {"组织机构","上级科室(部门)"})
    private String superFullname;

}
