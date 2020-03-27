package cn.edu.ujs.lp.intells.common.request.User;

import cn.edu.ujs.lp.intells.common.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 用户分页查询
 *
 * @author Meredith
 * @date 2019-09-17
 */
@Data
public class UserPageRequest extends PageRequest {

    /**
     * 医院ID
     */
    @ApiModelProperty(value = "医院ID，可以为null")
    private String hospId;

    @ApiModelProperty(value = "用户名称，模糊查询，可以为null")
    private String userName;

    @ApiModelProperty(value = "用户手机，可以为null")
    private String userMobile;


}
