package cn.edu.ujs.lp.intells.basicinformation.entity.Excompany;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务班组Excel表格式数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExteamInfoExcel extends BaseRowModel {

    @ExcelProperty(index = 0, value = {"服务班组","物业公司名称"})
    private String excompanyName; //` varchar(32) NOT NULL COMMENT '所属公司（外键关联），实现业务部门关联时自动提取',

    @ExcelProperty(index = 1, value = {"服务班组","业务部门名称"})
    private String exserviceName; //` varchar(32) NOT NULL COMMENT '业务部门(外键关联，关联外委公司的业务部门)',

    @ExcelProperty(index = 2, value = {"服务班组","上级班组名称"})
    private String superName; //` varchar(32) DEFAULT NULL COMMENT '父节点ID（外键关联），第一层节点父节点为Null',

    @ExcelProperty(index = 3, value = {"服务班组","班组名称"})
    private String exteamName; //` varchar(100) NOT NULL COMMENT '班组名称',

    @ExcelProperty(index = 4, value = {"服务班组","班组长姓名"})
    private String exteamLeaderName; //` varchar(20) DEFAULT NULL COMMENT '班组负责人',

    @ExcelProperty(index = 5, value = {"服务班组","班组长电话"})
    private String exteamLeaderMobile; //` varchar(15) DEFAULT NULL COMMENT '负责人电话',
}
