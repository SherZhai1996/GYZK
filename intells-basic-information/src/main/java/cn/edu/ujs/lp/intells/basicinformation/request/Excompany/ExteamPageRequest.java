package cn.edu.ujs.lp.intells.basicinformation.request.Excompany;

import cn.edu.ujs.lp.intells.common.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ExteamPageRequest extends PageRequest {
    /**
     * 医院ID
     */
    private String hospId;

    /**
     * 外委公司ID
     */
    @ApiModelProperty(value = "外委公司ID")
    private String excompanyId;

    /**
     * 服务班组名称
     */
    @ApiModelProperty(value = "服务班组名称,可以模糊查询过滤")
    private String exteamName;
}
