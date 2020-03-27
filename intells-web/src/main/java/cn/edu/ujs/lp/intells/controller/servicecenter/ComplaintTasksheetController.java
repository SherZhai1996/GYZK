package cn.edu.ujs.lp.intells.controller.servicecenter;

import cn.edu.ujs.lp.intells.common.request.IdRequest;
import cn.edu.ujs.lp.intells.servicecenter.common.util.JsonResponse1;
import cn.edu.ujs.lp.intells.servicecenter.request.ComplaintTasksheet.ComplaintTaskSheetCreateRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.ComplaintTasksheet.ComplaintTaskSheetQueryRequest;
import cn.edu.ujs.lp.intells.servicecenter.service.ComplaintTasksheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 工单投诉接口
 *
 * @author troy
 * @date 2019-10-05
 */
@Api(description = "工单投诉接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/complaint")
public class ComplaintTasksheetController {

    @Autowired
    private ComplaintTasksheetService complaintTasksheetService;

    @ApiOperation(value = "创建",notes = "创建")
    @PostMapping(value = "/create")
    public JsonResponse1 create(@RequestBody @Valid ComplaintTaskSheetCreateRequest request)throws Exception {
        complaintTasksheetService.create(request);
        return JsonResponse1.success("创建成功",null);
    }

    @ApiOperation(value = "删除",notes = "删除")
    @PostMapping(value = "/delete")
    public JsonResponse1 delete(@RequestBody @Valid IdRequest request) {
        complaintTasksheetService.delete(request);
        return JsonResponse1.success("删除成功");
    }

    @ApiOperation(value = "查询",notes = "查询")
    @PostMapping(value = "/queryByPage")
    public JsonResponse1 queryByPage(@RequestBody @Valid ComplaintTaskSheetQueryRequest request) {
        return complaintTasksheetService.queryByPage(request);
    }

}
