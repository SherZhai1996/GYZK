package cn.edu.ujs.lp.intells.controller.app;

import cn.edu.ujs.lp.intells.appservice.entity.Cleaning_tasksheet_serviceitem;
import cn.edu.ujs.lp.intells.appservice.entity.Cleaning_tasksheet_undertake;
import cn.edu.ujs.lp.intells.appservice.entity.Cleaning_tasksheet_finish;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.service.CallerService;
import cn.edu.ujs.lp.intells.appservice.dao.Cleaning_tasksheet_statetrackRepsitory;
import cn.edu.ujs.lp.intells.appservice.entity.Cleaning_tasksheet_statetrack;
import cn.edu.ujs.lp.intells.appservice.dao.Cleaning_tasksheet_AppsearchRepsitory;
import cn.edu.ujs.lp.intells.appservice.dao.Cleaning_tasksheet_addRepsitory;
import cn.edu.ujs.lp.intells.appservice.entity.Cleaning_tasksheet_Appsearch;
import cn.edu.ujs.lp.intells.appservice.entity.Cleaning_tasksheet_Appsearchindex;
import cn.edu.ujs.lp.intells.appservice.entity.Cleaning_tasksheet_add;
import cn.edu.ujs.lp.intells.appservice.entity.search;
import cn.edu.ujs.lp.intells.appservice.service.Cleaning_tasksheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 保洁工单添加工单，动态查询接口
 *
 * @author sher
 * @date 2019-10-02
 */


@CrossOrigin
@RestController
@RequestMapping(value = "/Cleaning_tasksheet")
public class App_Cleaning_tasksheetController {

    @Autowired
    private Cleaning_tasksheetService cleaning_tasksheet_Service;

    @Autowired
    private CallerService callerService;

    //添加新的工单记录
    @PostMapping(value = "/addRecord")
    public JsonResponse addRecord(@RequestBody Cleaning_tasksheet_add cleaning_tasksheet_add) {

        return cleaning_tasksheet_Service.addRecord(cleaning_tasksheet_add);
    }


    //添加新的工单状态记录
    @PostMapping(value = "/add_statetrack_Record")
    public JsonResponse add_statetrack_Record(@RequestBody Cleaning_tasksheet_statetrack cleaning_tasksheet_statetrack) {

        return cleaning_tasksheet_Service.add_statetrack_Record(cleaning_tasksheet_statetrack);
    }

    //派工
    @PostMapping(value = "/undertake")
    public JsonResponse undertake(@RequestBody Cleaning_tasksheet_undertake cleaning_tasksheet_undertake) {

        return cleaning_tasksheet_Service.undertake(cleaning_tasksheet_undertake);
    }

    //拒绝派工
    @PostMapping(value = "/delete_undertake")
    public JsonResponse delete_undertake(@RequestBody search search) {

        return cleaning_tasksheet_Service.delete_undertake(search.getId(), search.getPicture(), search.getWav());
    }

    //添加服务事项
    @PostMapping(value = "/add_serviceitem_Record")
    public JsonResponse add_serviceitem_Record(@RequestBody Cleaning_tasksheet_serviceitem cleaning_tasksheet_serviceitem) {

        return cleaning_tasksheet_Service.add_serviceitem_Record(cleaning_tasksheet_serviceitem);
    }

    //提交工单
    @PostMapping(value = "/add_finish_Record")
    public JsonResponse add_finish_Record(@RequestBody Cleaning_tasksheet_finish cleaning_tasksheet_finish) {

        return cleaning_tasksheet_Service.add_finish_Record(cleaning_tasksheet_finish);
    }


    //通过区域id查负责人id
    @PostMapping(value = "/find_acceptPerson")
    public String set_acceptPerson2(@RequestBody Cleaning_tasksheet_statetrack request) {

        return callerService.getexteam_leaderID_bygridid(request.getId());
    }





    //小组长派工，完工
    @PostMapping(value = "/find_by_state")
    public Object find_by_state(@RequestBody @Valid search request) {


        return  cleaning_tasksheet_Service.find_by_state(request.getPerson(),request.getStartTime(),request.getEndTime(),request.getSheetstate());
    }

    //班组员工查询
    @PostMapping(value = "/find_by_exstaff")
    public Object find_by_exstaff(@RequestBody @Valid search request) {


        return  cleaning_tasksheet_Service.find_by_exstaff(request.getPerson(),request.getStartTime(),request.getEndTime(),request.getSheetstate());
    }


    //工单申报人追踪评价（）
    @PostMapping(value = "/assess")
    public Object assess(@RequestBody @Valid search request) {


        return  cleaning_tasksheet_Service.assess(request.getPerson(),request.getStartTime(),request.getEndTime());
    }
    //工单申报人全部单子
    @PostMapping(value = "/all_tasksheet")
    public Object all_tasksheet(@RequestBody @Valid search request) {


        return  cleaning_tasksheet_Service.all_tasksheet(request.getPerson(),request.getStartTime(),request.getEndTime());
    }


    //项目经理查询
    @PostMapping(value = "/find_by_Department_Manager")
    public Object find_by_Department_Manager(@RequestBody @Valid search request) {


        return  cleaning_tasksheet_Service.find_by_Department_Manager(request.getStartTime(),request.getEndTime());
    }

}
