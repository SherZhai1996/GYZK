package cn.edu.ujs.lp.intells.controller.patrolinspection;


import cn.edu.ujs.lp.intells.patrolinspection.entity.SecuritypatrolPerson;
import cn.edu.ujs.lp.intells.patrolinspection.request.SecuritypatrolPersonRequest;
import cn.edu.ujs.lp.intells.patrolinspection.service.SecuritypatrolPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Api(description = "保安巡检人员表")
@CrossOrigin
@RestController
@RequestMapping(value = "/securitypatrol-person")
public class SecuritypatrolPersonController {
    @Autowired
    SecuritypatrolPersonService securitypatrolPersonService;

    @ApiOperation(value = "新增保安巡检人员" ,notes="新增保安巡检人员")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid SecuritypatrolPerson request){
        return securitypatrolPersonService.add(request);
    }


    @ApiOperation(value = "删除保安巡检人员", notes = "删除保安巡检人员")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid SecuritypatrolPerson request){
        return securitypatrolPersonService.delete(request.getId());
    }

    @ApiOperation(value = "修改保安巡检人员", notes = "修改保安巡检人员")
    @PostMapping(value = "update")
    public Object update(@RequestBody @Valid SecuritypatrolPerson request){
        return securitypatrolPersonService.update(request);
    }

    @ApiOperation(value = "分页查询保安巡检人员", notes = "分页查询保安巡检人员")
    @PostMapping(value = "findByIsleader")
    Map<String,Object> findByPage(@RequestBody SecuritypatrolPersonRequest request){
        return securitypatrolPersonService.findByIsleader(request);
    }


    @ApiOperation(value = "查找保安巡检人员" ,notes="查找保安巡检人员")
    @PostMapping(value = "/findPerson")
    public Map<String, Object> tableList(){
        Map tableList = securitypatrolPersonService.listTable();
        return tableList;
    }


    @ApiOperation(value = "更改保安巡检人员状态",notes = "更改保安巡检人员状态")
    @PostMapping(value = "updateStatus")
    public Object updateStatus (@RequestBody @Valid SecuritypatrolPerson request){
        return securitypatrolPersonService.updateStatus(request.getId(),request.getIsactive());
    }
}
