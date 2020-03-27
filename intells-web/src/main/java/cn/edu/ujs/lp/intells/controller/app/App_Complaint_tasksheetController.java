package cn.edu.ujs.lp.intells.controller.app;



import cn.edu.ujs.lp.intells.appservice.entity.Complaint_tasksheet;

import cn.edu.ujs.lp.intells.appservice.service.Complaint_tasksheetService;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 添加投诉工单接口
 *
 * @author sher
 * @date 2019-10-02
 */


@CrossOrigin
@RestController
@RequestMapping(value = "/Complaint_tasksheet")
public class App_Complaint_tasksheetController {

    @Autowired
    private Complaint_tasksheetService complaint_tasksheetService;


    //生成记录
    @PostMapping(value = "/addRecord")
    public JsonResponse addRecord(@RequestBody Complaint_tasksheet complaint_tasksheet) {

        return complaint_tasksheetService.addRecord(complaint_tasksheet);
    }





}
