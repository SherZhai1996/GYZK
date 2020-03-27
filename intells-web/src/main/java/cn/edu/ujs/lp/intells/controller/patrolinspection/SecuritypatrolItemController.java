package cn.edu.ujs.lp.intells.controller.patrolinspection;


import cn.edu.ujs.lp.intells.patrolinspection.entity.SecuritypatrolItem;
import cn.edu.ujs.lp.intells.patrolinspection.request.SecuritypatrolItemRequest;
import cn.edu.ujs.lp.intells.patrolinspection.service.SecuritypatrolItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Api(description = "保安巡检项目")
@CrossOrigin
@RestController
@RequestMapping(value = "/securitypatrol-item")
public class SecuritypatrolItemController {

    @Autowired
    SecuritypatrolItemService securitypatrolItemService;

    @ApiOperation(value = "新增保安巡检项目" ,notes="新增保安巡检项目")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid SecuritypatrolItem request){
        return securitypatrolItemService.add(request);
    }

    @ApiOperation(value = "删除保安巡检项目", notes = "删除保安巡检项目")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid SecuritypatrolItem request){
        return securitypatrolItemService.delete(request.getId());
    }

    @ApiOperation(value = "修改保安巡检项目", notes = "修改保安巡检项目")
    @PostMapping(value = "update")
    public Object update(@RequestBody @Valid SecuritypatrolItem request){
        return securitypatrolItemService.update(request);
    }

    @ApiOperation(value = "分页查询保安巡检项目", notes = "分页查询保安巡检项目")
    @PostMapping(value = "findByPage")
    Map<String,Object> findByPage(@RequestBody SecuritypatrolItemRequest request){
        return securitypatrolItemService.findByPage(request);
    }



    @ApiOperation(value = "根据模板id查询", notes = "条根据模板id查询")
    @PostMapping(value = "findByTemplateId")
    public Object findByTemplateId(@RequestBody SecuritypatrolItemRequest request){
        Map tableList = securitypatrolItemService.templateList(request);
        return tableList;
    }
}
