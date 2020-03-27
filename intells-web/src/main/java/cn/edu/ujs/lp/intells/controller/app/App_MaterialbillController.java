package cn.edu.ujs.lp.intells.controller.app;

import cn.edu.ujs.lp.intells.appservice.dao.MaterialbillRepsitory;
import cn.edu.ujs.lp.intells.appservice.entity.Materialbill;
import cn.edu.ujs.lp.intells.appservice.entity.search;
import cn.edu.ujs.lp.intells.appservice.service.MaterialbillService;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 耗材明细接口
 *
 * @author sher
 * @date 2019-10-02
 */


@CrossOrigin
@RestController
@RequestMapping(value = "/Materialbill")
public class App_MaterialbillController {

    @Autowired
    private MaterialbillService materialbillService;


    //冗余 废弃
    @PostMapping(value = "/addRecord")
    public JsonResponse addRecord(@RequestBody Materialbill materialbill) {

        return materialbillService.addRecord(materialbill);
    }


    //更新库存
    @PostMapping(value = "/update_material_stock")
    public JsonResponse update(@RequestBody @Valid Materialbill request){
        return materialbillService.update_material_stock(request);
    }

    //根据id查询耗材详细信息
    @PostMapping(value = "/find")
    public JsonResponse find(@RequestBody search request) {

        return materialbillService.find(request.getId());
    }

}
