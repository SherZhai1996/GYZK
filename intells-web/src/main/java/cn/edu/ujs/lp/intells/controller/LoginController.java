package cn.edu.ujs.lp.intells.controller;


import cn.edu.ujs.lp.intells.servicecenter.entity.Login;
import cn.edu.ujs.lp.intells.servicecenter.request.LoginRequest;
import cn.edu.ujs.lp.intells.servicecenter.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(description = "登入接口")
@CrossOrigin
@RestController
@RequestMapping(value = "/LoginController")
public class LoginController {
    @Autowired
    LoginService loginService;

    @PostMapping(value = "/loginRequest")
    @ApiOperation(value = "登入校验" ,  notes="登入校验")
    public Map<String,Object> loginRequest(@RequestBody LoginRequest request){
        return loginService.loginRequest(request);
    }
}
