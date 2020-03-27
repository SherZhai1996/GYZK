package cn.edu.ujs.lp.intells.basicinformation.request.Excompany;

import cn.edu.ujs.lp.intells.common.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 外委职工分页查询参数类
 */
@Data
public class ExstaffPageRequest  extends PageRequest {
    /**
     * 医院ID
     */
    private String hospId;

    /**
     * 外委公司ID
     */
    @ApiModelProperty(value = "外委公司ID，可以为null", required = true)
    private String excompanyId;

    /**
     * 服务班组ID
     */
    @ApiModelProperty(value = "服务班组ID，可以为null", required = true)
    private String exteamId;

    /**
     * 员工姓名
     */
    @ApiModelProperty(value = "员工姓名，支持模糊查询，可以为null", required = true)
    private String exstaffName;

    /**
     * 员工手机号码
     */
    @ApiModelProperty(value = "员工手机号码，要求完整的电话号码，不做模糊查询，可以为null", required = true)
    private String exstaffMobile;
}
