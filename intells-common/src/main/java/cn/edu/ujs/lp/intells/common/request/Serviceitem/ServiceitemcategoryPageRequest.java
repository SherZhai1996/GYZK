package cn.edu.ujs.lp.intells.common.request.Serviceitem;

import cn.edu.ujs.lp.intells.common.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ServiceitemcategoryPageRequest extends PageRequest {
    @ApiModelProperty(value = "服务事项类别名称，模糊查询，可以为null")
    private String serviceitemcategoryName;
}
