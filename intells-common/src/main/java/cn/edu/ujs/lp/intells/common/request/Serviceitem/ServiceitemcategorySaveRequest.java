package cn.edu.ujs.lp.intells.common.request.Serviceitem;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ServiceitemcategorySaveRequest {
    @ApiModelProperty(value = "服务事项类别ID,新增服务事项类别为null，否则为该服务事项类别ID值")
    private String id;

    @ApiModelProperty(value = "服务事项类别名称，不能为空", required = true)
    @NotBlank(message = "服务事项类别名称非空")
    private String serviceitemcategoryName;

    @ApiModelProperty(value = "服务事项类别上级类别ID")
    private String superiorId;

    @ApiModelProperty(value = "服务事项类别所属的服务类别（工单类别），不能为空")
    private String serviceTypeId;
}
