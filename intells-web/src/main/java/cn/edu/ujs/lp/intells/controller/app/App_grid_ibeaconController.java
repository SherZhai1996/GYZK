package cn.edu.ujs.lp.intells.controller.app;

import cn.edu.ujs.lp.intells.appservice.dao.Grid_ibeaconRepsitory;
import cn.edu.ujs.lp.intells.appservice.entity.Grid_ibeacon;
import cn.edu.ujs.lp.intells.appservice.service.Grid_ibeaconService;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 职工登录，认证Controller
 *
 * @author sher
 * @date 2019-09-30
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/grid_ibeacon")
public class App_grid_ibeaconController {

    @Autowired
    private Grid_ibeaconService grid_ibeaconService;

    //根据gridid查询蓝牙信标
    @PostMapping(value = "/find_by_gridId")
    public JsonResponse find_by_id(@RequestBody @Valid Grid_ibeacon request){
        return grid_ibeaconService.find_by_gridId(request.getGridId());
    }



}
