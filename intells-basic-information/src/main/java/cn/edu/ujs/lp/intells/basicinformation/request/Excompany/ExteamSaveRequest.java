package cn.edu.ujs.lp.intells.basicinformation.request.Excompany;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 服务班组编辑保存数据类，要求同一家公司班组名称不能相同
 */
@Data
public class ExteamSaveRequest {

    @ApiModelProperty(value = "服务班组ID,新增服务班组为null，否则为该服务班组ID值")
    private String id;

    /**
     * 班组名称
     */
    @ApiModelProperty(value = "班组名称，不能为空", required = true)
    @NotBlank(message = "班组名称非空")
    private String exteamName; //班组名称

    /**
     * 业务部门ID
     */
    @ApiModelProperty(value = "业务部门ID(选择)", required = true)
    private String exservicesId; //` varchar(32) NOT NULL COMMENT '业务部门(外键关联，关联外委公司的业务部门)',

    /**
     * 公司ID
     */
    @ApiModelProperty(value = "外委公司ID(选择)", required = true)
    private String excompanyId; //` varchar(32) NOT NULL COMMENT '所属公司（外键关联），实现业务部门关联时自动提取',

    /**
     * 班组长
     */
    @ApiModelProperty(value = "班组长姓名")
    private String exteamLeaderName; //` varchar(20) DEFAULT NULL COMMENT '班组负责人',

    /**
     * 班组长电话
     */
    @ApiModelProperty(value = "班组长电话")
    private String exteamLeaderMobile; //` varchar(15) DEFAULT NULL COMMENT '负责人电话',

    /**
     * 班组电话
     */
    @ApiModelProperty(value = "班组电话")
    private String exteamTel; //` varchar(15) DEFAULT NULL COMMENT '班组电话，不填，默认为班组长电话',

    /**
     * 班组长ID
     */
    @ApiModelProperty(value = "班组电话，班组负责人ID，在选择外键关联时负责人姓名和电话可以员工表中自动提取")
    private String exteamLeaderId; //` varchar(32) DEFAULT NULL COMMENT '班组负责人ID（外键引用），在选择外键关联时负责人姓名和电话可以员工表中自动提取',

    /**
     * 上一级班组ID
     */
    @ApiModelProperty(value = "父节点ID（外键关联），第一层节点父节点为Null")
    private String superId; //` varchar(32) DEFAULT NULL COMMENT '父节点ID（外键关联），第一层节点父节点为Null',

    @ApiModelProperty(value = "责任网格全名列表，可以为Null")
    private List<String> gridFullnames;
    //private List<ExteamGridBrief> gridIds;

}
