package cn.edu.ujs.lp.intells.controller.app;

import cn.edu.ujs.lp.intells.appservice.entity.APP_department;
import cn.edu.ujs.lp.intells.appservice.service.App_departmentService;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 部门Controller
 *
 * @author sher
 * @date 2019-10-07
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/department")
public class App_departmentController {

    @Autowired
    private App_departmentService app_departmentService;

    //显示所有部门
    @PostMapping(value = "/findAll")
    public JsonResponse findAll() {
        return app_departmentService.findAll();
    }

    //根据id显示部门
    @PostMapping(value = "/find_by_id")
    public JsonResponse find_by_id(@RequestBody @Valid APP_department request) {
        return app_departmentService.findbyid(request.getId());
    }
}
