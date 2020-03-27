package cn.edu.ujs.lp.intells.basicinformation.request.Device;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DevicePDFRequest {

    /**
     * 需要输出二维码标签的设备明细对象列表
     */
    @ApiModelProperty(value = "需要输出二维码标签的设备明细对象ID列表", required = true)
    private List<String> devicebillIdList;
}
