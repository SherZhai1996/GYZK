package cn.edu.ujs.lp.intells.common.request.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 外委职工位置跟踪保存参数类
 */
@Data
public class UserSaveRequest {
    @ApiModelProperty(value = "系统用户ID,新增系统用户为null，否则为该系统用户ID值")
    private String id;

    @ApiModelProperty(value = "用户名称，不能为空", required = true)
    @NotBlank(message = "用户名称非空")
    private String userName;

    @ApiModelProperty(value = "用户性别，不能为空", required = true)
    @NotBlank(message = "用户性别非空")
    private String userSex;

    @ApiModelProperty(value = "用户手机，不能为空", required = true)
    @NotBlank(message = "用户手机非空")
    private String userMobile;

    @ApiModelProperty(value = "用户登录名")
    private String userLoginname;

    @ApiModelProperty(value = "可访问医院名称列表，可以为null（默认可访问所有医院）")
    private List<String> accesshospNames;

    @ApiModelProperty(value = "用户角色【一种角色】，可以为null（默认为系统用户）")
    private List<String> roleNames;

}
