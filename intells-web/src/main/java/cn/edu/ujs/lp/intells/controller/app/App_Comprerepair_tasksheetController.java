package cn.edu.ujs.lp.intells.controller.app;




import cn.edu.ujs.lp.intells.appservice.entity.Comprerepair_tasksheet_statetrack;
import cn.edu.ujs.lp.intells.appservice.entity.*;

import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.service.FileService;
import cn.edu.ujs.lp.intells.common.service.SerialNumberService;
import cn.edu.ujs.lp.intells.appservice.dao.*;
import cn.edu.ujs.lp.intells.appservice.service.Comprerepair_tasksheet_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 添加工单，动态查询接口
 *
 * @author sher
 * @date 2019-10-02
 */


@CrossOrigin
@RestController
@RequestMapping(value = "/Comprerepair_tasksheet")
public class App_Comprerepair_tasksheetController {

    @Autowired
    private Comprerepair_tasksheet_Service comprerepair_tasksheet_Service;
    @Autowired
    private Comprerepair_tasksheet_AppsearchRepsitory comprerepair_tasksheet_AppsearchRepsitory;
    @Autowired
    private Cleaning_tasksheet_addRepsitory cleaning_tasksheet_addRepsitory;
    @Autowired
    private SerialNumberService serialNumberService;
    @Autowired
    HttpServletRequest header;
    @Autowired
    private FileService fileService;



    //生成维修工单
    @PostMapping(value = "/addRecord")
    public JsonResponse addRecord(@RequestBody Comprerepair_tasksheet_add comprerepair_tasksheet_add) {

        return comprerepair_tasksheet_Service.addRecord(comprerepair_tasksheet_add);
    }

    //添加新的工单状态记录
    @PostMapping(value = "/add_statetrack_Record")
    public JsonResponse add_statetrack_Record(@RequestBody Comprerepair_tasksheet_statetrack comprerepair_tasksheet_statetrack) {

        return comprerepair_tasksheet_Service.add_statetrack_Record(comprerepair_tasksheet_statetrack);
    }

    //派工
    @PostMapping(value = "/undertake")
    public JsonResponse undertake(@RequestBody Comprerepair_tasksheet_undertake comprerepair_tasksheet_undertake) {

        return comprerepair_tasksheet_Service.undertake(comprerepair_tasksheet_undertake);
    }

    //拒绝派工
    @PostMapping(value = "/delete_undertake")
    public JsonResponse delete_undertake(@RequestBody search search) {

        return comprerepair_tasksheet_Service.delete_undertake(search.getId(), search.getPicture(), search.getWav());
    }

    //添加服务事项
    @PostMapping(value = "/add_serviceitem_Record")
    public JsonResponse add_serviceitem_Record(@RequestBody Comprerepair_tasksheet_serviceitem comprerepair_tasksheet_serviceitem) {

        return comprerepair_tasksheet_Service.add_serviceitem_Record(comprerepair_tasksheet_serviceitem);
    }

    //提交工单
    @PostMapping(value = "/add_finish_Record")
    public JsonResponse add_finish_Record(@RequestBody Comprerepair_tasksheet_finish comprerepair_tasksheet_finish) {

        return comprerepair_tasksheet_Service.add_finish_Record(comprerepair_tasksheet_finish);
    }



    @PostMapping(value = "/find")
    public List<Comprerepair_tasksheet_Appsearch> find(@RequestBody @Valid Comprerepair_tasksheet_Appsearchindex request) {


       return  comprerepair_tasksheet_AppsearchRepsitory.find1(request.getUndertakePerson(),request.getStartTime(),request.getEndTime());
    }

    @PostMapping(value = "/getBXsheetCode")
    public String  getBXsheetCode ( ) throws Exception {
        String result = null;

        String m_ll = cleaning_tasksheet_addRepsitory.serialNumber(header.getHeader("hosp-ID"));

        if ((m_ll != null))
        {
            result = m_ll.replace("Y", "");
            result = result.replace("y", "");
        }
        return  serialNumberService.getBXsheetCode( result) ;

    }



    @PostMapping(value = "/getSXsheetCode")
    public String  getSXsheetCode( ) throws Exception {
        String result = null;

        String m_ll = cleaning_tasksheet_addRepsitory.serialNumber(header.getHeader("hosp-ID"));

        if ((m_ll != null))
        {
            result = m_ll.replace("Y", "");
            result = result.replace("y", "");
        }
        return  serialNumberService.getSXsheetCode( result) ;

    }

    @PostMapping(value = "/getBYsheetCode")
    public String  getBYsheetCode( ) throws Exception {
        String result = null;

        String m_ll = cleaning_tasksheet_addRepsitory.serialNumber(header.getHeader("hosp-ID"));

        if ((m_ll != null))
        {
            result = m_ll.replace("Y", "");
            result = result.replace("y", "");
        }
        return  serialNumberService.getBYsheetCode( result) ;

    }


    @PostMapping(value = "/getWXsheetCode")
    public String  getWXsheetCode( ) throws Exception {
        String result = null;

        String m_ll = cleaning_tasksheet_addRepsitory.serialNumber(header.getHeader("hosp-ID"));

        if ((m_ll != null))
        {
            result = m_ll.replace("Y", "");
            result = result.replace("y", "");
        }
        return  serialNumberService.getWXsheetCode( result) ;

    }




    @PostMapping(value = "/getBJsheetCode")
    public String getBJsheetCode( ) throws Exception {
        String result = null;

        String m_ll = cleaning_tasksheet_addRepsitory.serialNumber(header.getHeader("hosp-ID"));

        if ((m_ll != null))
        {
            result = m_ll.replace("Y", "");
            result = result.replace("y", "");
        }
        return  serialNumberService.getBJsheetCode( result) ;

    }

    @PostMapping(value = "/getTSsheetCode")
    public String getTSsheetCode( ) throws Exception {
        String result = null;

        String m_ll = cleaning_tasksheet_addRepsitory.serialNumber(header.getHeader("hosp-ID"));

        if ((m_ll != null))
        {
            result = m_ll.replace("Y", "");
            result = result.replace("y", "");
        }

        return  serialNumberService.getTSsheetCode( result) ;

    }

    @PostMapping(value = "/getZWsheetCode")
    public String getZWsheetCode( ) throws Exception {

        String result = null;

        String m_ll = cleaning_tasksheet_addRepsitory.serialNumber(header.getHeader("hosp-ID"));

        if ((m_ll != null))
        {
            result = m_ll.replace("Y", "");
            result = result.replace("y", "");
        }

        return  serialNumberService.getZWsheetCode( result) ;

    }


    @PostMapping(value = "/getYSsheetCode")
    public String getYSsheetCode( ) throws Exception {

        String result = null;

        String m_ll = cleaning_tasksheet_addRepsitory.serialNumber(header.getHeader("hosp-ID"));

        if ((m_ll != null))
        {
            result = m_ll.replace("Y", "");
            result = result.replace("y", "");
        }

        return  serialNumberService.getYSsheetCode( result) ;

    }



    //查询主管
    @PostMapping(value = "/find_By_role")
    public Object find_By_role() {


        return  comprerepair_tasksheet_Service.findByrole();
    }



    //小组长派工，完工
    @PostMapping(value = "/find_by_state")
    public Object find_by_state(@RequestBody @Valid search request) {


        return  comprerepair_tasksheet_Service.find_by_state(request.getPerson(),request.getStartTime(),request.getEndTime(),request.getSheetstate());
    }

    //员工完工
    @PostMapping(value = "/find_by_state_exstaff")
    public Object find_by_state_exstaff(@RequestBody @Valid search request) {


        return  comprerepair_tasksheet_Service.find_by_state_exstaff(request.getPerson(),request.getStartTime(),request.getEndTime(),request.getSheetstate());
    }


    //工单申报人追踪评价（）
    @PostMapping(value = "/assess")
    public Object assess(@RequestBody @Valid search request) {


        return  comprerepair_tasksheet_Service.assess(request.getPerson(),request.getStartTime(),request.getEndTime());
    }

    //工单申报人追踪评价（）
    @PostMapping(value = "/all_tasksheet")
    public Object all_tasksheet(@RequestBody @Valid search request) {


        return  comprerepair_tasksheet_Service.all_tasksheet(request.getPerson(),request.getStartTime(),request.getEndTime());
    }

    //项目经理查询
    @PostMapping(value = "/find_by_Department_Manager")
    public JsonResponse find_by_Department_Manager(@RequestBody @Valid search request) {


        return  comprerepair_tasksheet_Service.find_by_Department_Manager(request.getStartTime(),request.getEndTime());
    }


    @PostMapping(value = "/upload2")
    public JsonResponse upload2(@RequestParam("file")  List<MultipartFile> uploadfiles ) throws Exception {

        String hospcode,typename,location;
        typename="sheet";
        hospcode=header.getHeader("hosp-ID");

        return fileService.filesUpload(hospcode,typename,uploadfiles);

    }

}
