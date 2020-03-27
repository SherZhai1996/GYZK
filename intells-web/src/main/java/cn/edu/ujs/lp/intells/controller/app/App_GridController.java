package cn.edu.ujs.lp.intells.controller.app;

import cn.edu.ujs.lp.intells.appservice.entity.Grid;
import cn.edu.ujs.lp.intells.appservice.service.Grid1Service;
import cn.edu.ujs.lp.intells.appservice.dao.GridRepsitory;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 职工登录，认证Controller
 *
 * @author sher
 * @date 2019-10-07
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/Grid")
public class App_GridController {

    @Autowired
    private Grid1Service grid1Service;

    @Autowired
    private GridRepsitory gridRepsitory;



    //通过部门id查询gridid
    @PostMapping(value = "/find_by_dept_id")
    public Object find_by_dept_id(@RequestBody @Valid Grid request){
        return gridRepsitory.find_by_dept_id(request.getId());
    }

    //冗余 废弃
    @PostMapping(value = "/findbyId")
    public JsonResponse findbyId(@RequestBody @Valid Grid request){
        return grid1Service.find_by_id(request.getId());
    }

    @PostMapping(value = "/find_by_gridLevel")
    public JsonResponse find_by_gridLevel(@RequestBody @Valid Grid request){
        return grid1Service.find_by_gridLevel(request.getGridLevel());
    }

    //保洁二维码
    @PostMapping(value = "/CleanInspection_grid")
    public JsonResponse CleanInspection_grid(){
        return grid1Service.CleanInspection_grid();
    }

    //保安二维码
    @PostMapping(value = "/SecurityPatrol_grid")
    public JsonResponse SecurityPatrol_grid(){
        return grid1Service.SecurityPatrol_grid();
    }

    //设备二维码
    @PostMapping(value = "/DeviceInspection_grid")
    public JsonResponse DeviceInspection_grid(){
        return grid1Service.DeviceInspection_grid();
    }


}
