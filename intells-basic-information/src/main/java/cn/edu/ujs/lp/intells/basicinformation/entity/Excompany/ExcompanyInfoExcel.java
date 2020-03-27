package cn.edu.ujs.lp.intells.basicinformation.entity.Excompany;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 外委公司Excel数据类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcompanyInfoExcel extends BaseRowModel {

    @ExcelProperty(index = 0, value = {"外委公司信息","公司名称"})
    private String excompanyName;

    @ExcelProperty(index = 1, value = {"外委公司信息","公司法人"})
    private String excompanyLegal;

    @ExcelProperty(index = 2, value = {"外委公司信息","公司负责人姓名"})
    private String excompanyLeadername;

    @ExcelProperty(index = 3, value = {"外委公司信息","公司负负责人电话"})
    private String excompanyLeadertel;

    @ExcelProperty(index = 4, value = {"外委公司信息","服务开始时间"})
    private String excompanyServicestartdate;

    @ExcelProperty(index = 5, value = {"外委公司信息","服务结束时间"})
    private String excompanyServiceenddate;

    @ExcelProperty(index = 6, value = {"外委公司信息","服务简介"})
    private String servicesRemark;

    @ExcelProperty(index = 7, value = {"外委公司信息","公司地址"})
    private String excompanyAddress;
}
