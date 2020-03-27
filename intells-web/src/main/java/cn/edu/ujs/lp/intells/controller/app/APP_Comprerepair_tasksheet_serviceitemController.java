package cn.edu.ujs.lp.intells.controller.app;

import cn.edu.ujs.lp.intells.appservice.entity.Servicebill;
import cn.edu.ujs.lp.intells.appservice.entity.Serviceitemcategory;
import cn.edu.ujs.lp.intells.appservice.entity.Comprerepair_tasksheet_serviceitem;
import cn.edu.ujs.lp.intells.appservice.service.Serviceitemcategory_searchService;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 综合维修服务事项接口
 *
 * @author sher
 * @date 2019-10-02
 */


@CrossOrigin
@RestController
@RequestMapping(value = "/comprerepair_tasksheet_serviceitem")
public class APP_Comprerepair_tasksheet_serviceitemController {

    @Autowired
    private Serviceitemcategory_searchService serviceitemcategorySearchService;

   //生成工单
    @PostMapping(value = "/addRecord")
    public JsonResponse addRecord(@RequestBody Comprerepair_tasksheet_serviceitem comprerepair_tasksheet_serviceitem) {

        return serviceitemcategorySearchService.addRecord(comprerepair_tasksheet_serviceitem);
    }


    //一级服务事项
    @PostMapping(value = "/find1")
    public JsonResponse find1(@RequestBody @Valid Serviceitemcategory request) {

        return  serviceitemcategorySearchService.find1(request.getServiceTypeId());
    }

    //二级服务事项
    @PostMapping(value = "/find2")
    public JsonResponse find3(@RequestBody @Valid Serviceitemcategory request) {
        System.out.print(request.getSuperiorId());
        return serviceitemcategorySearchService.find2(request.getSuperiorId());
    }

    //三级服务事项
    @PostMapping(value = "/find3")
    public JsonResponse find3(@RequestBody @Valid Servicebill request) {

        return serviceitemcategorySearchService.find3(request.getCategory_id());
    }
}
