package cn.edu.ujs.lp.intells.controller;


import cn.edu.ujs.lp.intells.phoneservice.service.WebSocketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 测试用接口，接收外部消息，调用websocket，主动向前端发送通知
 *
 * @author troy
 * @date 2019-09-22
 */
@RestController
@Api(description = "websocket")
@CrossOrigin
public class MessageController {

    @Autowired
    WebSocketService webSocketService;

    @PostMapping(value = "/message")
    @ApiOperation(value = "来电话弹窗" ,  notes="来电话弹窗")
    public void message(@RequestParam String content) {
        System.out.println(String.format("接收外部消息：%s", content));
        // 自动发送给前端
        webSocketService.sendMessage("phone:"+content);
    }



}
