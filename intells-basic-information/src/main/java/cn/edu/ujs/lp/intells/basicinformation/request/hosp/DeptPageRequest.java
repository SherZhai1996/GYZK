package cn.edu.ujs.lp.intells.basicinformation.request.hosp;

import cn.edu.ujs.lp.intells.common.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组织机构分页查询
 *
 * @author Meredith
 * @data 2019-10-06
 */
@Data
public class DeptPageRequest extends PageRequest {

    /**
     * 医院编码
     */
    @ApiModelProperty(value = "医院ID，不能为空", required = true)
    private String hospId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "科室名称", required = true)
    private String deptName;
}
