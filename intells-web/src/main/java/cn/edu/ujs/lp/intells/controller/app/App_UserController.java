package cn.edu.ujs.lp.intells.controller.app;

import cn.edu.ujs.lp.intells.appservice.service.App_UserService;
import cn.edu.ujs.lp.intells.common.request.User.UserLoginRequest;
import cn.edu.ujs.lp.intells.phoneservice.service.WebSocketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 外委职工登录，认证，角色类型Controller
 *
 * @author sher
 * @date 2019-10-13
 */
@Api(description = "用户登录接口")
@CrossOrigin
@RestController
@RequestMapping(value = "/User")
public class App_UserController {

    @Autowired
    private App_UserService userService;

    @Autowired
    WebSocketService webSocketService;

    //汤佳雨测试用
    @ApiOperation(value = "用户登录接口",notes = "用户登录接口")
    @ResponseBody
    @PostMapping(value = "/login")
    public Object login(@RequestBody @Valid UserLoginRequest request){
        return userService.find_By_LoginName(request);
    }

    @PostMapping(value = "/new_task_message")
    @ApiOperation(value = "来电话弹窗" ,  notes="来电话弹窗")
    public void new_task_message() {
        // 自动发送给前端
        webSocketService.sendMessage("new_task:"+"1");
    }


}
