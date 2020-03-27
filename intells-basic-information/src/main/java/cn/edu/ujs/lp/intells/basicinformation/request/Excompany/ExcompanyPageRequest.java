package cn.edu.ujs.lp.intells.basicinformation.request.Excompany;

import cn.edu.ujs.lp.intells.common.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 外委公司分页查询参数类
 */
@Data
public class ExcompanyPageRequest extends PageRequest {

    private String hospId;

    /**
     * 外委公司名称
     */
    @ApiModelProperty(value = "外委公司名称，进行模糊查询", required = true)
    private String excompanyName;

}
