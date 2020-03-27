package cn.edu.ujs.lp.intells.controller.servicecenter;

import cn.edu.ujs.lp.intells.common.request.IdRequest;
import cn.edu.ujs.lp.intells.servicecenter.common.util.JsonResponse1;
import cn.edu.ujs.lp.intells.servicecenter.request.ServiceBill.ServiceBillCreateRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.ServiceBill.ServiceBillQueryRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.ServiceBill.ServiceBillUpdateRequest;
import cn.edu.ujs.lp.intells.servicecenter.service.ServiceBillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Api(description = "服务事项接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/servicebill")
public class ServiceBillController {
    @Autowired
    private ServiceBillService serviceBillService;

    @ApiOperation(value = "更新服务事项明细",notes = "更新服务事项明细")
    @PostMapping(value = "/update")
    public JsonResponse1 update(@RequestBody @Valid ServiceBillUpdateRequest request)throws  Exception {
        serviceBillService.update(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "创建服务事项明细",notes = "创建服务事项明细")
    @PostMapping(value = "/create")
    public JsonResponse1 create(@RequestBody @Valid ServiceBillCreateRequest request) {
        serviceBillService.create(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "删除服务事项明细",notes = "删除服务事项明细")
    @PostMapping(value = "/delete")
    public JsonResponse1 delete(@RequestBody @Valid IdRequest request) {
        serviceBillService.delete(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "分页查询服务事项明细",notes = "分页查询服务事项明细")
    @PostMapping(value = "/queryByPage")
    public JsonResponse1 queryByPage(@RequestBody @Valid ServiceBillQueryRequest request) {
        return serviceBillService.queryByPage(request);
    }
}
