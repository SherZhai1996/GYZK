package cn.edu.ujs.lp.intells.common.entity.User;

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
public class UserInfoExcel extends BaseRowModel {

    @ExcelProperty(index = 0,value = {"系统用户","姓名"})
    private String userName;

    @ExcelProperty(index = 1,value = {"系统用户","性别"})
    private String userSexName;

    @ExcelProperty(index = 2,value = {"系统用户","手机"})
    private String userMobile;
}
