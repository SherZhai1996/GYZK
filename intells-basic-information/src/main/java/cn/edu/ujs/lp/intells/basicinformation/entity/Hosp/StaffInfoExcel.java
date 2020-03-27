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
public class StaffInfoExcel  extends BaseRowModel {

    @ExcelProperty(index = 0,value = {"医院职工","姓名"})
    private String staffName;

    @ExcelProperty(index = 1,value = {"医院职工","性别"})
    private String staffSex;

    @ExcelProperty(index = 2,value = {"医院职工","手机号码"})
    private String staffMobile;

    @ExcelProperty(index = 3,value = {"医院职工","出生年月日"})
    private String staffBirthday;

    @ExcelProperty(index = 4,value = {"医院职工","所属科室/部门"})
    private String deptName;

    @ExcelProperty(index = 5,value = {"医院职工","行政职务"})
    private String staffAdminisPosition;

    @ExcelProperty(index = 6,value = {"医院职工","技术职务"})
    private String staffTechnicalPosition;

     @ExcelProperty(index = 7,value = {"医院职工","学历"})
    private String staffDegree;
}
