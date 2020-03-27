package cn.edu.ujs.lp.intells.systemmanagement.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 外委职工位置跟踪保存参数类
 */
@Data
public class UserPosSaveRequest {

    @ApiModelProperty(value = "用户ID，不能为空", required = true)
    @NotBlank(message = "用户ID非空")
    private String userId;

    @ApiModelProperty(value = "网格ID，不能为空", required = true)
    @NotBlank(message = "网格ID非空")
    private String gridId;

    @ApiModelProperty(value = "该网格是否存在信标，存在则赋值\"1\"，否则为null或\"0\"")
    private String isexistibeacon=null;
}
