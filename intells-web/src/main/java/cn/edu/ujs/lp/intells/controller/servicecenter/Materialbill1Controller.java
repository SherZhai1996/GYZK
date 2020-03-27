package cn.edu.ujs.lp.intells.controller.servicecenter;

import cn.edu.ujs.lp.intells.common.request.IdRequest;
import cn.edu.ujs.lp.intells.servicecenter.common.util.JsonResponse1;
import cn.edu.ujs.lp.intells.servicecenter.request.HospIdRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.Materialbill.MaterialbillCreateRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.Materialbill.MaterialbillQueryRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.Materialbill.MaterialbillUpdateRequest;
import cn.edu.ujs.lp.intells.servicecenter.service.Materialbill1Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Api(description = "耗材明细接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/materialbill")
public class Materialbill1Controller {
    @Autowired
    private Materialbill1Service materialbill1Servicel;

    @ApiOperation(value = "创建耗材明细",notes = "创建耗材明细")
    @PostMapping(value = "/create")
    public JsonResponse1 create(@RequestBody @Valid MaterialbillCreateRequest request) {
        materialbill1Servicel.create(request);
        return JsonResponse1.success("耗材明细创建成功");
    }

    @ApiOperation(value = "删除耗材明细",notes = "删除耗材明细")
    @PostMapping(value = "/delete")
    public JsonResponse1 delete(@RequestBody @Valid IdRequest request) {
        materialbill1Servicel.delete(request);
        return JsonResponse1.success("耗材明细删除成功");
    }

    @ApiOperation(value = "分页查询耗材明细",notes = "分页查询耗材明细")
    @PostMapping(value = "/queryByPage")
    public JsonResponse1 queryByPage(@RequestBody @Valid MaterialbillQueryRequest request) {
        return materialbill1Servicel.queryByPage(request);
    }

    @ApiOperation(value = "初始化耗材明细",notes = "初始化耗材明细")
    @PostMapping(value = "/init")
    public JsonResponse1 init(HospIdRequest request) {
        return materialbill1Servicel.init(request);
    }

    @ApiOperation(value = "更新耗材明细",notes = "更新耗材明细")
    @PostMapping(value = "/update")
    public JsonResponse1 update(@RequestBody @Valid MaterialbillUpdateRequest request) {
        materialbill1Servicel.update(request);
        return JsonResponse1.success("耗材明细更新成功");
    }
}
