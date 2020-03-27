package cn.edu.ujs.lp.intells.systemmanagement.request;

import cn.edu.ujs.lp.intells.common.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 蓝牙信标位置分页查询
 *
 * @author Meredith
 * @date 2019-09-17
 */
@Data
public class IbeaconPageRequest extends PageRequest {

    /**
     * 医院ID
     */
    @ApiModelProperty(value = "医院ID，可以为null")
    private String hospId;

    @ApiModelProperty(value = "信标名称，可以为null")
    private String ibeaconName;


}
