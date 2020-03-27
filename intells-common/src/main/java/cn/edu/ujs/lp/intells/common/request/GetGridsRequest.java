package cn.edu.ujs.lp.intells.common.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetGridsRequest {

    @ApiModelProperty(value = "科室ID，可以为null", required = true)
    private String deptId;

    @ApiModelProperty(value = "父节点ID，可以为null", required = true)
    private String superGridId;

    @ApiModelProperty(value = "网格名称，可以为null，支持模糊查找", required = true)
    private String gridName;
}
