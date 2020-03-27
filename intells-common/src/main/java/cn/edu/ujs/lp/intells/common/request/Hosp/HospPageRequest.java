package cn.edu.ujs.lp.intells.common.request.Hosp;

import cn.edu.ujs.lp.intells.common.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 医院信息分页查询
 *
 * @author Meredith
 * @date 2019-09-17
 */
@Data
public class HospPageRequest extends PageRequest {

    /**
     * 医院名称
     */
    @ApiModelProperty(value = "医院名称，模糊查找")
    private String hospName;

    /**
     * 省
     */
    @ApiModelProperty(value = "省份ID、可以为空")
    private String pId;

    /**
     * 市
     */
    @ApiModelProperty(value = "市ID、可以为空")
    private String cId;
}
