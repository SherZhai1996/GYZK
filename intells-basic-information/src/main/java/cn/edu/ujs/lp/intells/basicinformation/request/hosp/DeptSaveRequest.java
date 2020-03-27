package cn.edu.ujs.lp.intells.basicinformation.request.hosp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 组织机构新增请求
 *
 * @author Meredith
 * @data 2019-10-06
 */
@Data
public class DeptSaveRequest {

    @ApiModelProperty(value = "科室ID,新增科室为null，否则为该科室ID值")
    private String id;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "科室名称,非空",required = true)
    @NotBlank(message = "部门名称非空")
    private String deptName;

    /**
     * 部门编码
     */
    @ApiModelProperty(value = "科室组织机构编码")
    private String deptCode;

    /**
     * 部门等级
     */
    @ApiModelProperty(value = "科室部门等级")
    private int deptLevel;

    /**
     * 上级部门id
     */
    @ApiModelProperty(value = "科室父节点ID")
    private String superiorId;

    /**
     * 部门电话
     */
    @ApiModelProperty(value = "科室电话")
    private String deptTel;

    /**
     * 部门负责人名称
     */
    @ApiModelProperty(value = "科室负责人姓名")
    private String deptLeaderName;

    /**
     * 部门负责人电话
     */
    @ApiModelProperty(value = "科室负责人电话")
    private String deptLeaderTel;

    /**
     * 部门负责人ID
     */
    @ApiModelProperty(value = "科室负责人ID")
    private String deptLeaderId;

}
