package cn.edu.ujs.lp.intells.common.request.User;

import lombok.Data;

/**
 * 设置密码请求参数类
 */
@Data
public class UserSetPWRequest {
    private String username;
    private String oldpassword;
    private String newpassword;
}
