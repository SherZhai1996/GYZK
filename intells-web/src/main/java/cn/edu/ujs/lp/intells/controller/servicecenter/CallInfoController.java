package cn.edu.ujs.lp.intells.controller.servicecenter;

import cn.edu.ujs.lp.intells.common.request.IdRequest;
import cn.edu.ujs.lp.intells.servicecenter.common.util.JsonResponse1;
import cn.edu.ujs.lp.intells.servicecenter.request.CallInfo.CallInfoRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.HospIdRequest;
import cn.edu.ujs.lp.intells.servicecenter.service.CallInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 工单保洁服务接口
 *
 * @author troy
 * @date 2019-10-05
 */
@Api(description = "通话记录接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/callinfo")
public class CallInfoController {

    @Autowired
    private CallInfoService callInfoService;

    @ApiOperation(value = "动态查询",notes = "动态查询")
    @PostMapping(value = "/queryByPage")
    public JsonResponse1 queryByPage(@RequestBody @Valid CallInfoRequest request) {
        return callInfoService.queryByPage(request);
    }

    @ApiOperation(value = "初始化",notes = "初始化")
    @PostMapping(value = "/init")
    public JsonResponse1 init(HospIdRequest request) {
        return callInfoService.init(request);
    }

    @ApiOperation(value = "删除记录",notes = "删除记录")
    @PostMapping(value = "/delete")
    public JsonResponse1 delete(@RequestBody @Valid IdRequest request) {
         callInfoService.delete(request);
         return JsonResponse1.success("删除成功");
    }
}