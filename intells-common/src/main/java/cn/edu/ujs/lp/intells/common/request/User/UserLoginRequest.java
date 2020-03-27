package cn.edu.ujs.lp.intells.common.request.User;

import lombok.Data;

/**
 * 用户登陆参数类，用于接收用户登陆参数
 */
@Data
public class UserLoginRequest {
    private String username;
    private String password;
}
