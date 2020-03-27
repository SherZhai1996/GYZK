package cn.edu.ujs.lp.intells.controller.servicecenter;


import cn.edu.ujs.lp.intells.servicecenter.common.util.JsonResponse1;
import cn.edu.ujs.lp.intells.servicecenter.request.AllTaskSheet.AllTaskSheetQueryRequest;
import cn.edu.ujs.lp.intells.servicecenter.service.*;
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
@Api(description = "获取所有工单操作")
@RestController
@CrossOrigin
@RequestMapping(value = "/alltasksheet")
public class AllTaskSheetController {

    @Autowired
    private AllTaskSheetService allTaskSheetService;

    @ApiOperation(value = "新建投诉查询工单",notes = "新建投诉查询工单")
    @PostMapping(value = "/queryByPage")
    public JsonResponse1 queryByPage(@RequestBody @Valid AllTaskSheetQueryRequest request) {
        return allTaskSheetService.queryByPage(request);
    }

}