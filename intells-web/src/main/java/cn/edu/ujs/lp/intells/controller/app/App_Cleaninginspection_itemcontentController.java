package cn.edu.ujs.lp.intells.controller.app;

import cn.edu.ujs.lp.intells.appservice.entity.Cleaninginspection_itemcontent;
import cn.edu.ujs.lp.intells.appservice.service.Cleaninginspection_itemcontentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin
@RestController
@RequestMapping(value = "/Cleaninginspection_itemcontent")
public class App_Cleaninginspection_itemcontentController {

    @Autowired
    private Cleaninginspection_itemcontentService cleaninginspection_itemcontentService;


//    //通过项目id查找项目内容
//    @PostMapping(value = "/find_byitemId")
//    public Object find_by_templateId(@RequestBody @Valid Cleaninginspection_itemcontent request){
//        return cleaninginspection_itemcontentService.find_by_itemId(request.getItemId());
//    }



}
