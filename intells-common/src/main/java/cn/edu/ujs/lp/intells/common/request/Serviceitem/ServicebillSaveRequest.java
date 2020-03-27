package cn.edu.ujs.lp.intells.common.request.Serviceitem;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ServicebillSaveRequest {

    @ApiModelProperty(value = "服务事项ID,新增服务事项为null，否则为该服务事项ID值")
    private String id;

    @ApiModelProperty(value = "服务事项名称，不能为空", required = true)
    @NotBlank(message = "服务事项名称非空")
    private String serviceitemName;

    @ApiModelProperty(value = "服务事项类别ID")
    private String categoryId;

    @ApiModelProperty(value = "服务事项所属的服务类别（工单类别），不能为空")
    private String serviceTypeId;
}
