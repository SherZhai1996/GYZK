package cn.edu.ujs.lp.intells.basicinformation.request.Device;

import cn.edu.ujs.lp.intells.common.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DevicebillPageRequest extends PageRequest {
    private String hospId;

    /**
     * 设备类别ID
     */
    @ApiModelProperty(value = "设备类别ID", required = true)
    private String deviceCategory;

    /**
     * 资产名称
     */
    @ApiModelProperty(value = "资产名称，支持模糊查找", required = true)
    private String assetName;
}
