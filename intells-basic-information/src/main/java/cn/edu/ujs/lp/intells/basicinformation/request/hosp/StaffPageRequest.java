package cn.edu.ujs.lp.intells.basicinformation.request.hosp;

import cn.edu.ujs.lp.intells.common.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 职工分页查询
 *
 * @author Meredith
 * @data 2019-10-08
 */
@Data
public class StaffPageRequest extends PageRequest {

    /**
     * 医院id
     */
    @ApiModelProperty(value = "医院ID")
    private String hospId;

    /**
     * 职工姓名
     */
    @ApiModelProperty(value = "职工名称,支持模糊查询")
    private String staffName;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "职工手机电话")
    private String staffMobile;

    /**
     * 职工办公电话
     */
    @ApiModelProperty(value = "职工办公电话")
    private String staffTel;

    /**
     * 所属科室部门
     */
    @ApiModelProperty(value = "科室ID")
    private String deptId;
}
