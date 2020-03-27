package cn.edu.ujs.lp.intells.controller.app;

import cn.edu.ujs.lp.intells.appservice.dao.MaterialconsumerecordRepsitory;
import cn.edu.ujs.lp.intells.appservice.entity.Materialconsumerecord;
import cn.edu.ujs.lp.intells.appservice.service.MaterialconsumerecordService;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 耗材明细接口
 *
 * @author sher
 * @date 2019-10-02
 */


@CrossOrigin
@RestController
@RequestMapping(value = "/Materialconsumerecord")
public class App_MaterialconsumerecordController {

    @Autowired
    private MaterialconsumerecordService materialconsumerecordService;


    //生成耗材使用记录
    @PostMapping(value = "/addRecord")
    public JsonResponse addRecord(@RequestBody Materialconsumerecord materialconsumerecord) {

        return materialconsumerecordService.addRecord(materialconsumerecord);
    }



}
