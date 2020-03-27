package cn.edu.ujs.lp.intells.basicinformation.request.hosp;

import cn.edu.ujs.lp.intells.common.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 网格对象分页查询参数类
 */
@Data
public class GridPageRequest  extends PageRequest {

    private String hospId;

    @ApiModelProperty(value = "网格全名称，可以为null，模糊查找")
    private String gridFullname;

    @ApiModelProperty(value = "科室部门ID，可以为null")
    private String deptId;

    @ApiModelProperty(value = "上级网格ID，可以为null")
    private String superiorGridid;
}
