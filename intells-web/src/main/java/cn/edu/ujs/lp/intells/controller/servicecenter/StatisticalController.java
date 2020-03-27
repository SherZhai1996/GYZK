package cn.edu.ujs.lp.intells.controller.servicecenter;

import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.servicecenter.common.util.JsonResponse1;
import cn.edu.ujs.lp.intells.servicecenter.request.StatisticalRequest;
import cn.edu.ujs.lp.intells.servicecenter.service.StatisticalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 工单统计
 *
 * @author troy
 * @date 2019-10-05
 */
@Api(description = "工单统计接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/statistical")
public class StatisticalController {

    @Autowired
    private StatisticalService statisticalService;

    @ApiOperation(value = "工单统计",notes = "工单统计")
    @PostMapping(value = "/statistical")
    public JsonResponse1 statistical(@RequestBody @Valid StatisticalRequest request) {
        return statisticalService.statistical(request);
    }
}