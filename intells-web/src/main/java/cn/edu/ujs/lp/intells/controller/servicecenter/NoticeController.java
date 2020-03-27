package cn.edu.ujs.lp.intells.controller.servicecenter;

import cn.edu.ujs.lp.intells.common.request.IdRequest;
import cn.edu.ujs.lp.intells.servicecenter.common.util.JsonResponse1;
import cn.edu.ujs.lp.intells.servicecenter.request.HospIdRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.Notice.NoticeCreateRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.Notice.NoticeQueryRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.Notice.NoticeUpdateRequest;
import cn.edu.ujs.lp.intells.servicecenter.service.NoticeService;
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
@Api(description = "通知公告接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @ApiOperation(value = "动态查询",notes = "动态查询")
    @PostMapping(value = "/queryByPage")
    public JsonResponse1 queryByPage(@RequestBody @Valid NoticeQueryRequest request) {
        return noticeService.queryByPage(request);
    }

    @ApiOperation(value = "初始化",notes = "初始化")
    @PostMapping(value = "/init")
    public JsonResponse1 init(HospIdRequest request) {
        return noticeService.init(request);
    }

    @ApiOperation(value = "删除记录",notes = "删除记录")
    @PostMapping(value = "/delete")
    public JsonResponse1 delete(@RequestBody @Valid IdRequest request) {
        noticeService.delete(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "更新记录",notes = "更新记录")
    @PostMapping(value = "/update")
    public JsonResponse1 update(@RequestBody @Valid NoticeUpdateRequest request) {
        noticeService.update(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "创建记录",notes = "创建记录")
    @PostMapping(value = "/create")
    public JsonResponse1 create(@RequestBody @Valid NoticeCreateRequest request) {
        noticeService.create(request);
        return JsonResponse1.success();
    }
}