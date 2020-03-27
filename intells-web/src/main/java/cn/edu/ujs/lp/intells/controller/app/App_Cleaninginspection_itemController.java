package cn.edu.ujs.lp.intells.controller.app;

import cn.edu.ujs.lp.intells.appservice.entity.Cleaninginspection_item;
import cn.edu.ujs.lp.intells.appservice.service.Cleaninginspection_itemService;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin
@RestController
@RequestMapping(value = "/Cleaninginspection_item")
public class App_Cleaninginspection_itemController {

    @Autowired
    private Cleaninginspection_itemService cleaninginspection_itemService;


    //通过模板id号查找项目
    @PostMapping(value = "/find_by_templateId")
    public JsonResponse find_by_templateId(@RequestBody @Valid Cleaninginspection_item request){
        return cleaninginspection_itemService.find_by_templateId(request.getTemplateId());
    }





}
