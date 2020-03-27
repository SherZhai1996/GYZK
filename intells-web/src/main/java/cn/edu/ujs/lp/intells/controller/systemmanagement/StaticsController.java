package cn.edu.ujs.lp.intells.controller.systemmanagement;

import cn.edu.ujs.lp.intells.systemmanagement.request.StaticsRequest;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.systemmanagement.service.StaticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Api(description = "统计测试")
@RestController
@CrossOrigin
@RequestMapping(value = "/statics")
public class StaticsController {

    @Autowired
    private StaticsService staticsService;

    @ApiOperation(value = "统计医院数据",notes = "统计医院数据")
    @PostMapping(value = "/count")
    public JsonResponse countFinish(@RequestBody @Valid StaticsRequest request){
        try {
            return staticsService.statistical(request);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResponse.fail(1009,"统计医院数据失败");
        }
    }  
}
