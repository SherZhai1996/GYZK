package cn.edu.ujs.lp.intells.systemmanagement.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 蓝牙信标保存参数类
 */
@Data
public class IbeaconSaveRequest {

    @ApiModelProperty(value = "信标ID,新增信标为null，否则为该信标ID值")
    private String id;

    @ApiModelProperty(value = "信标名称，不能为空", required = true)
    @NotBlank(message = "用户ID非空")
    private String ibeaconName;

    @ApiModelProperty(value = "信标UUID", required = true)
    private String ibeaconUUID;

    @ApiModelProperty(value = "信标信号强度值", required = true)
    private String ibeaconRSSI;

    @ApiModelProperty(value = "信标所在网格", required = true)
    private String gridId;

    @ApiModelProperty(value = "信标是否启用[\"0\"-停用，\"1\"-启用]", required = true)
    private String isUsing;
}
