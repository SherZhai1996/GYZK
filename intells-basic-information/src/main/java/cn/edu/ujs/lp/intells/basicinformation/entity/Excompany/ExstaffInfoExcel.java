package cn.edu.ujs.lp.intells.basicinformation.entity.Excompany;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 外委职工Excel表格式数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExstaffInfoExcel extends BaseRowModel {

    @ExcelProperty(index = 0, value = {"物业职工","姓名"})
    private String exstaffName; //` varchar(20) NOT NULL COMMENT '姓名',

    @ExcelProperty(index = 1, value = {"物业职工","性别"})
    private String exstaffSex; //` varchar(32) DEFAULT NULL COMMENT '性别(数据字典)',

    @ExcelProperty(index = 2, value = {"物业职工","出生日期"})
    private String exstaffBirthday; //` datetime DEFAULT NULL COMMENT '出生日期',

    @ExcelProperty(index = 3, value = {"物业职工","手机号码"})
    private String exstaffMobile; //` varchar(15) NOT NULL COMMENT '手机号',

    @ExcelProperty(index = 4, value = {"物业职工","职工工号"})
    private String exstaffJobno; //` varchar(20) DEFAULT NULL COMMENT '职工工号',

    @ExcelProperty(index = 5, value = {"物业职工","物业公司"})
    private String excompanyName; //` varchar(32) NOT NULL COMMENT '所属公司（外键关联）',

    @ExcelProperty(index = 6, value = {"物业职工","服务班组"})
    private String exteamName; //` varchar(32) NOT NULL COMMENT '所属班组（外键关联）',

    @ExcelProperty(index = 7, value = {"物业职工","聘用日期"})
    private String exstaffEmploystartdate; //` datetime DEFAULT NULL COMMENT '聘用日期',

    @ExcelProperty(index = 8, value = {"物业职工","终止日期"})
    private String exstaffEmployenddate; //` datetime DEFAULT NULL COMMENT '终止日期',

    @ExcelProperty(index = 9, value = {"物业职工","紧急联系人姓名"})
    private String exstaffEmercontactname; //` varchar(20) DEFAULT  NULL COMMENT '紧急联系人姓名',

    @ExcelProperty(index = 10, value = {"物业职工","紧急联系电话"})
    private String exstaffEmercontacttel; //` varchar(15) DEFAULT  NULL COMMENT '紧急联系电话',
}
