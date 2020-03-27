package cn.edu.ujs.lp.intells.basicinformation.request.Excompany;

import cn.edu.ujs.lp.intells.common.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ExServicesPageRequest extends PageRequest {

    @ApiModelProperty(value = "医院ID", required = true)
    private String hospId;

    /**
     * 外委公司ID
     */
    @ApiModelProperty(value = "外委公司ID，可以为null", required = true)
    private String excompanyId;

    @ApiModelProperty(value = "业务部门名称，支持模糊查询，可以为null", required = true)
    private String exserviceName;
}
