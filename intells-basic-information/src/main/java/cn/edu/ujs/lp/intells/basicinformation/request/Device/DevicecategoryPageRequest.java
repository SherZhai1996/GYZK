package cn.edu.ujs.lp.intells.basicinformation.request.Device;

import cn.edu.ujs.lp.intells.common.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 设备类别分页查找参数类
 */
@Data
public class DevicecategoryPageRequest extends PageRequest {

    /**
     * 医院ID
     */
    @ApiModelProperty(value = "医院ID", required = true)
    private String hospId;

    /**
     * 外委公司ID
     */
    @ApiModelProperty(value = "设备类别名，支持模糊查找", required = true)
    private String categoryName;
}
