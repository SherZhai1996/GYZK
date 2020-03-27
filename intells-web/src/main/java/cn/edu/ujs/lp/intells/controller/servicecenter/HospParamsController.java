package cn.edu.ujs.lp.intells.controller.servicecenter;

import cn.edu.ujs.lp.intells.servicecenter.common.util.JsonResponse1;
import cn.edu.ujs.lp.intells.servicecenter.request.HospIdRequest;
import cn.edu.ujs.lp.intells.servicecenter.service.HospParamsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 医院参数接口
 *
 * @author troy
 * @date 2019-10-05
 */
@Api(description = "医院参数接口(手动派工还是自动派工)")
@RestController
@CrossOrigin
@RequestMapping(value = "/hospparams")
public class HospParamsController {

    @Autowired
    private HospParamsService hospParamsService;

    @ApiOperation(value = "通过医院编号找手自动派工",notes = "通过医院编号找手自动派工")
    @PostMapping(value = "/hospIdToHospParams")
    public JsonResponse1 hospIdToHospParams(@RequestBody @Valid HospIdRequest request) {
        return hospParamsService.hospIdToHospParams(request);
    }
}