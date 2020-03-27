package cn.edu.ujs.lp.intells.basicinformation.service;

import cn.edu.ujs.lp.intells.basicinformation.dao.Excompany.ExteamGridRepository;
import cn.edu.ujs.lp.intells.basicinformation.dao.Excompany.ExteamMapper;
import cn.edu.ujs.lp.intells.basicinformation.dao.Excompany.ExteamRepository;
import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.*;
import cn.edu.ujs.lp.intells.basicinformation.request.Excompany.ExteamPageRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.Excompany.ExteamSaveRequest;
import cn.edu.ujs.lp.intells.common.dao.User.UserMapper;
import cn.edu.ujs.lp.intells.common.entity.Common.TreeInterface;
import cn.edu.ujs.lp.intells.common.entity.Hosp.ExteamBrief;
import cn.edu.ujs.lp.intells.common.entity.Hosp.ExteamGridBrief;
import cn.edu.ujs.lp.intells.common.entity.User.UserRole;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.service.*;
import cn.edu.ujs.lp.intells.common.utils.BeanUtils;
import cn.edu.ujs.lp.intells.common.utils.ExcelUtils;
import cn.edu.ujs.lp.intells.common.utils.OSUtils;
import cn.edu.ujs.lp.intells.common.utils.TreeUtil;
import com.alibaba.excel.metadata.BaseRowModel;
import com.github.pagehelper.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.FileStore;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ExteamService {
    @Autowired
    private ExteamMapper exteamMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ExteamRepository exteamRepository;

    @Autowired
    private ExcompanyService excompanyService;

    @Autowired
    private ExServicesService exServicesService;

    @Autowired
    private DBCommonService dbCommonService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SerialNumberService serialNumberService;

    @Autowired
    private ExstaffService exstaffService;

    @Autowired
    private ExteamGridRepository exteamGridRepository;

    @Autowired
    private DataDictionaryService dataDictionaryService;


    /**
     * 更新服务班组负责人信息
     * @param hospId
     * @return
     * @throws Exception
     */
    public boolean setExteamLeader(String hospId) throws Exception
    {
        if ((dbCommonService != null) && (exstaffService != null) && (userMapper != null)) {
            try {
                List<ExteamBrief> elst = dbCommonService.getExteams(hospId,null,null);

                if (elst != null) {

                    //写入服务班组负责人的信息，实现与人员表数据的关联
                    List<Exteam> list = new ArrayList<>();

                    for (ExteamBrief ecpb : elst) {
                        Exstaff exstaff = exstaffService.findByName(hospId, ecpb.getExcompanyId(), ecpb.getId(), ecpb.getExteamLeaderName());
                        if (exstaff != null) {
                            Exteam exteam = getByID(ecpb.getId());
                            exteam.setExteamLeaderMobile(exstaff.getExstaffMobile());
                            exteam.setExteamLeaderId(exstaff.getId());

                            list.add(exteam);
                        }
                    }

                    exteamRepository.saveAll(list);
                }

                elst = dbCommonService.getExteams(hospId,null,null);
                if (elst != null) {
                    //对服务班组人员信息设置角色
                    for (ExteamBrief ecpb : elst) {
                        if ((userService != null) && (ecpb.getServiceId() != null) && (ecpb.getExteamLeaderId() != null) && (ecpb.getExteamLeaderId().length()>0)) {
                            if (ecpb.getServiceId().compareTo("01")==0)
                                userService.SetUserRole(ecpb.getExteamLeaderId(),"017");
                            else if (ecpb.getServiceId().compareTo("02")==0)
                                userService.SetUserRole(ecpb.getExteamLeaderId(),"016");
                            else if (ecpb.getServiceId().compareTo("03")==0)
                                userService.SetUserRole(ecpb.getExteamLeaderId(),"018");
                            else if (ecpb.getServiceId().compareTo("04")==0)
                                userService.SetUserRole(ecpb.getExteamLeaderId(),"020");
                            else if (ecpb.getServiceId().compareTo("05")==0)
                                userService.SetUserRole(ecpb.getExteamLeaderId(),"019");
                        }
                    }
                }

                return true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("服务班组负责人信息更新失败:"+e.getMessage());
            }
        }

        return false;
    }

    /**
     * 导出Excel模板
     * @param response
     * @return
     * @throws Exception
     */
    public JsonResponse exportExcelTemplate(HttpServletResponse response) throws Exception{

        /** String templateName = "Exteamtemplate";

        try {
            ExcelUtils.exportTemplate(ExteamInfoExcel.class, response, templateName);
            return JsonResponse.success("服务班组导入模板导出成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("服务班组导入模板导出失败:"+e.getMessage());
        } */
        try
        {
            if (fileService != null) {
                fileService.downloadExcelFile(OSUtils.getExteamtemplatefile(),response);
                return JsonResponse.success("服务班组导入模板导出成功");
            }
            else return JsonResponse.success("服务班组导入模板导出失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("服务班组导入模板导出失败:"+e.getMessage());
        }
    }

    /**
     * 导出Excel数据
     * @param response
     * @return
     * @throws Exception
     */
    public JsonResponse exportExcelData(String hospId,HttpServletResponse response) throws Exception
    {
        String fileName = "ExteamData";

        try {
            if (dbCommonService != null) {
                List<ExteamBrief> elist = dbCommonService.getExteams(hospId, null, null);

                List<BaseRowModel> ls = new ArrayList<BaseRowModel>();
                for (ExteamBrief e : elist) {
                    ExteamInfoExcel ei = new ExteamInfoExcel();

                    //复制
                    ei.setExcompanyName(e.getExcompanyName());
                    ei.setExserviceName(e.getExservicesName());

                    if ((e.getSuperId() != null) && (e.getSuperId() != ""))
                        ei.setSuperName(e.getSuperName());
                    ei.setExteamName(e.getExteamName());
                    ei.setExteamLeaderName(e.getExteamLeaderName());
                    ei.setExteamLeaderMobile(e.getExteamLeaderMobile());

                    ls.add(ei);
                }

                ExcelUtils.exportData(ExteamInfoExcel.class, response, fileName, ls);
                return JsonResponse.success("服务班组数据导出成功");
            }
            else
                return JsonResponse.fail(1001,"从数据库中读取服务班组信息失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("服务班组数据导出失败："+e.getMessage());
        }
    }

    /**
     * 从Excel导入数据
     * @param file
     * @return
     * @throws Exception
     */
    public JsonResponse ImportExcelData(String hospId,MultipartFile file) throws Exception
    {
        int m_count = 0,m_fail = 0; //入库记录数量和未入库数量

        try {
            StringBuilder sb = new StringBuilder();

            //读入Excel文件数据
            List<ArrayList> data = (List<ArrayList>) ExcelUtils.readData(file.getInputStream(), ExteamInfoExcel.class);

            // arraylist转info.excel对象
            if ((data != null) && (data.size() > 0)) {
                for (ArrayList array : data) {
                    try {
                        //从Excel表提取一行数据
                        int fieldcount = 8;

                        if (array.size()==fieldcount) {
                            ExteamInfoExcel excel = new ExteamInfoExcel();
                            if ((array.size() >= 1) && (array.get(0) != null))
                                excel.setExcompanyName(array.get(0).toString().replace(" ", ""));
                            if ((array.size() >= 2) && (array.get(1) != null))
                                excel.setExserviceName(array.get(1).toString().replace(" ", ""));
                            if ((array.size() >= 3) && (array.get(2) != null))
                                excel.setSuperName(array.get(2).toString().replace(" ", ""));
                            if ((array.size() >= 4) && (array.get(3) != null))
                                excel.setExteamName(array.get(3).toString().replace(" ", ""));
                            if ((array.size() >= 5) && (array.get(4) != null))
                                excel.setExteamLeaderName(array.get(4).toString().replace(" ", ""));
                            if ((array.size() >= 6) && (array.get(5) != null))
                                excel.setExteamLeaderMobile(array.get(5).toString().replace(" ", ""));

                            //入库保存
                            if ((excel.getExteamName() != null) && (excel.getExteamName() != "")) {
                                save(hospId, excel);
                                m_count++;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_fail++;
                    }
                }

                if (m_count > 0)
                    return JsonResponse.success("服务班组导入成功", String.valueOf(m_count) + "," + String.valueOf(m_fail));
                else
                    return JsonResponse.fail(1009, "服务班组导入失败");
            }
            else
                return JsonResponse.fail(1009, "读入服务班组为null");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new  Exception("读入服务班组信息失败:"+e.getMessage());
        }
    }

    /**
     * 分页查找
     * @param request
     * @return
     */
    public PageResponse<ExteamBrief> page(String hospId,ExteamPageRequest request) throws Exception
    {
        try {
            request.setHospId(hospId);
            Page<ExteamBrief> page = exteamMapper.page(request, new RowBounds(request.getIndex(), request.getSize()));
            List<Long> lst = exteamMapper.Rcount(request);
            Long total = 0L;
            if ((lst!=null) && (lst.size()>0))
                total = lst.get(0);

            if ((page!=null)&&(page.size()>0))
            {
                for (ExteamBrief etb : page) {
                    if ((etb.getServiceId() != null) && (etb.getServiceId() != "")) {
                        etb.setServiceName(dataDictionaryService.getDataDictionary("db_extern_service_type").getNamebyID(etb.getServiceId()));
                    }

                    if ((etb.getSuperId() != null) && (etb.getSuperId() != ""))
                    {
                        etb.setSuperName(dbCommonService.getExteamFullnamebyId(etb.getSuperId()));
                    }

                    etb.getLeader(userService);
                }
            }

            return new PageResponse<ExteamBrief>(page, request, total);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("服务班组分页查询失败："+e.getMessage());
        }
    }

    /**
     * 依据ID获取一个完整的服务班组对象
     * @param id
     * @return
     * @throws Exception
     */
    public Exteam getByID(String id) throws  Exception
    {
        Exteam result=null;

        if (exteamMapper != null)
        {
            try
            {
                List<Exteam> lst = exteamMapper.getbyid(id);
                if ((lst != null) && (lst.size()>0)) {
                    result = lst.get(0);

                    if (result != null)
                    {
                        if ((result.getServiceId() != null) && (result.getServiceId() != "")) {
                            result.setServiceName(dataDictionaryService.getDataDictionary("db_extern_service_type").getNamebyID(result.getServiceId()));
                        }

                        if (result.getSuperId() != null)
                        {
                            result.setSuperName(dbCommonService.getExteamFullnamebyId(result.getSuperId()));
                        }

                        result.getGridPathbyId(dbCommonService);
                        result.getLeader(userService);
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据ID:["+id+"]获取服务班组对象失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 获取指定服务班组的服务类型
     * @param id
     * @return
     * @throws Exception
     */
    public String getServiceID(String id) throws Exception {
        String result = null;

        if (id != null){
            try
            {
                Exteam exteam = getByID(id);

                if (exteam != null) {
                    if ((exteam.getServiceId() != null) && (exteam.getServiceId().length()>0)){
                        result=exteam.getServiceId();
                    }
                }
            }
            catch (Exception e)
            {
                throw new Exception("获取服务班组的服务类型失败: "+e.getMessage());
            }
        }

        if ((result != null) && (result.length()<1)) result = null;
        return result;
    }
    /**
     * 删除指定id的服务班组对象
     * @param tCode
     * @return
     */
    public boolean delete(String tCode) throws  Exception
    {
        boolean result = false ;

        if (exteamMapper != null)
        {
            try
            {
                if (!ExcelUtils.isNullofString(tCode)) {
                    exteamMapper.delete_exteam_bycode(tCode);
                    //exteamMapper.delete(id);
                    result = true;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("删除服务班组对象["+tCode+"]异常:"+e.getMessage());
            }
        }

        return result ;
    }

    /**
     * 保存服务班组附件信息，即网格信息
     * @param hospId
     * @param exteam
     * @return
     * @throws Exception
     */
    private boolean saveattr(String hospId,Exteam exteam) throws Exception
    {
        boolean result = false ;

        if ((exteamMapper != null) && (exteamGridRepository != null)) {
            try {

                //删除原有相关信息
                exteamMapper.deleteAtt(exteam.getId());

                //保存责任网格区域
                if ((exteam.getGridIds() != null) && (exteam.getGridIds().size() > 0)) {
                    List<ExteamGrid> lts = new ArrayList<>();

                    for (ExteamGridBrief gid : exteam.getGridIds()) {
                        ExteamGrid sr = new ExteamGrid();
                        sr.setExteamId(exteam.getId());
                        if (gid.getGridId() != null) {
                            sr.setGridId(gid.getGridId());
                            sr.setGridCode(dbCommonService.getGridCodebyId(gid.getGridId()));
                            lts.add(sr);
                        }
                    }
                    exteamGridRepository.saveAll(lts);
                }

                result=true;
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("保存服务班组附加信息失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 保存服务班组信息，如果不存在则新建，否则更新
     * @param request
     * @return
     */
    public Exteam save(String hospId,ExteamSaveRequest request) throws Exception{

        Exteam exteam = null;
        String leaderId = null;
        String serviceId = null;

        if ((exteamRepository != null)&&(exteamMapper != null)&&(request != null)) {

            Excompany ec = null;
            ExServices es = null;

            //服务班组存在性校验
            try {
                if ((request.getExcompanyId() != null) && (request.getExcompanyId().trim() != ""))
                    ec = (excompanyService != null ? excompanyService.getByID(request.getExcompanyId()) : null);

                if ((request.getExservicesId() != null) && (request.getExservicesId().trim() != ""))
                    es = (exServicesService != null ? exServicesService.getByID(request.getExservicesId()) : null);

                if ((request.getId() != null) && (request.getId().trim() != "")) exteam = getByID(request.getId());

                if (exteam == null)
                    exteam = findExteamByExcompanyIDandExteamName(hospId, ec != null ? ec.getId() : null, request.getExteamName());

                if (exteam != null)
                {
                    leaderId = exteam.getExteamLeaderId();
                    serviceId = exteam.getServiceId();
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("服务班组存在性校验失败:" + e.getMessage());
            }

            if (exteam == null) {
                //构造新的服务班组对象
                exteam = Exteam
                        .builder()
                        .build();
            }

            try {
                BeanUtils.copyBeanIgnoreNull(exteam,request);

                if (ExcelUtils.isNullofString(request.getExteamLeaderId())) exteam.setExteamLeaderId(null);
                if (ExcelUtils.isNullofString(request.getExteamLeaderMobile())) exteam.setExteamLeaderMobile(null);
                if (ExcelUtils.isNullofString(request.getSuperId())) exteam.setSuperId(null);

                exteam.setHospID(hospId);

                if ((request.getGridFullnames() != null)&&(request.getGridFullnames().size()>0))
                    exteam.setGridIdsFromStrings(request.getGridFullnames(),dbCommonService);

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("复制服务班组对象失败:" + e.getMessage());
            }


            //保存服务班组对象
            try {

                exteam.setExcompanyId(request.getExcompanyId());
                exteam.setExservicesId(request.getExservicesId());
                exteam.setHospID(hospId);

                //获取服务类型
                if (es != null)  exteam.setServiceId(es.getServiceId());

                //保存服务班组
                Exteam exteam_s = save(hospId,exteam);
                exteam.setId(exteam_s.getId());

                // 设置主管角色
                if ((!ExcelUtils.isNullofString(exteam.getExteamLeaderId())) && (!ExcelUtils.isNullofString(exteam.getServiceId())) && (userService != null) ) {
                    if (((!ExcelUtils.isNullofString(leaderId)) && (leaderId.compareTo(exteam.getExteamLeaderId()) != 0)) || ((!ExcelUtils.isNullofString(serviceId)) && (serviceId.compareTo(exteam.getServiceId()) != 0))){
                        if ((!ExcelUtils.isNullofString(leaderId)) && (!ExcelUtils.isNullofString(serviceId)))
                        {
                            if (serviceId.compareTo("01") == 0) {
                                userService.DeleteUserRole(leaderId, "017");
                            } else if (serviceId.compareTo("02") == 0) {
                                userService.DeleteUserRole(leaderId, "016");
                            } else if (serviceId.compareTo("03") == 0) {
                                userService.DeleteUserRole(leaderId, "018");
                            } else if (serviceId.compareTo("04") == 0) {
                                userService.DeleteUserRole(leaderId, "020");
                            } else if (serviceId.compareTo("05") == 0) {
                                userService.DeleteUserRole(leaderId, "019");
                            }
                        }
                    }

                    if (exteam.getServiceId().compareTo("01") == 0) {
                        if (userService.getUserRoles(exteam.getExteamLeaderId(), "017") == null) userService.SetUserRole(exteam.getExteamLeaderId(), "017"); //日常维修主管
                    } else if (exteam.getServiceId().compareTo("02") == 0) {
                        if (userService.getUserRoles(exteam.getExteamLeaderId(), "016") == null) userService.SetUserRole(exteam.getExteamLeaderId(), "016"); //应急保洁主管
                    } else if (exteam.getServiceId().compareTo("03") == 0) {
                        if (userService.getUserRoles(exteam.getExteamLeaderId(), "018") == null) userService.SetUserRole(exteam.getExteamLeaderId(), "018"); //工勤搬运主管
                    } else if (exteam.getServiceId().compareTo("04") == 0) {
                        if (userService.getUserRoles(exteam.getExteamLeaderId(), "020") == null) userService.SetUserRole(exteam.getExteamLeaderId(), "020"); //保安巡逻主管
                    } else if (exteam.getServiceId().compareTo("05") == 0) {
                        if (userService.getUserRoles(exteam.getExteamLeaderId(), "019") == null) userService.SetUserRole(exteam.getExteamLeaderId(), "019"); //设备运维主管
                    }
                }

                if ((ExcelUtils.isNullofString(exteam.getExteamLeaderId())) || (ExcelUtils.isNullofString(exteam.getServiceId()))) {
                    if ((!ExcelUtils.isNullofString(leaderId)) && (!ExcelUtils.isNullofString(serviceId)) && (userService != null))
                    {
                        if (serviceId.compareTo("01") == 0) {
                            userService.DeleteUserRole(leaderId, "017");
                        } else if (serviceId.compareTo("02") == 0) {
                            userService.DeleteUserRole(leaderId, "016");
                        } else if (serviceId.compareTo("03") == 0) {
                            userService.DeleteUserRole(leaderId, "018");
                        } else if (serviceId.compareTo("04") == 0) {
                            userService.DeleteUserRole(leaderId, "020");
                        } else if (serviceId.compareTo("05") == 0) {
                            userService.DeleteUserRole(leaderId, "019");
                        }
                    }
                }

                //保存服务班组关联网格等属性
                saveattr(hospId,exteam);

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("保存服务班组对象失败:" + e.getMessage());
            }
        }

        return exteam;
    }

    /**
     * 依据Excel信息保存服务班组信息
     * @param request
     * @return
     * @throws Exception
     */
    private Exteam save(String hospId,ExteamInfoExcel request) throws Exception{

        Exteam exteam = null;
        String leaderId = null, serviceId = null;

        if (exteamRepository != null) {

            Excompany ec = null;
            ExServices es = null;

            //服务班组存在性校验
            try {
                if ((request.getExcompanyName() != null)&&(request.getExcompanyName() != ""))
                    ec = (excompanyService != null ? excompanyService.getByName(hospId,request.getExcompanyName()) : null);

                if ((request.getExserviceName() != null) && (request.getExserviceName() != ""))
                    es = (((ec != null) && (exServicesService != null)) ? exServicesService.getByName(hospId, ec.getId(), request.getExserviceName()) : null);

                exteam = findExteamByExcompanyIDandExteamName(hospId, ec != null ? ec.getId() : null, request.getExteamName());

                if (exteam != null) {
                    leaderId = exteam.getExteamLeaderId();
                    serviceId = exteam.getServiceId();
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("服务班组存在性校验失败:"+e.getMessage());
            }

            try {
                if (exteam == null) {
                    //构造新的服务班组对象
                    exteam = Exteam
                            .builder()
                            .build();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("创建服务班组对象失败："+e.getMessage());
            }

            if (exteam != null) {

                try {
                    BeanUtils.copyBeanIgnoreNull(exteam,request);

                    if (ec!=null)
                        exteam.setExcompanyId(ec.getId());

                    if (es!=null) {
                        exteam.setExservicesId(es.getId());
                        exteam.setServiceId(es.getServiceId());
                    }

                    if ((request.getSuperName() != null) && (request.getSuperName() != "")) {
                        exteam.setSuperId(dbCommonService.getExteamIdbyName(hospId, ec != null ? ec.getId() : null,request.getSuperName().replace(" ","")));
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("复制服务班组对象失败:"+e.getMessage());
                }

                //保存服务班组对象
                try {
                    Exteam exteam_s = save(hospId,exteam);
                    exteam.setId(exteam_s.getId());

                    // 设置主管角色
                    if ((!ExcelUtils.isNullofString(exteam.getExteamLeaderId())) && (!ExcelUtils.isNullofString(exteam.getServiceId())) && (userService != null) ) {
                        if (((!ExcelUtils.isNullofString(leaderId)) && (leaderId.compareTo(exteam.getExteamLeaderId()) != 0)) || ((!ExcelUtils.isNullofString(serviceId)) && (serviceId.compareTo(exteam.getServiceId()) != 0))){
                            if ((!ExcelUtils.isNullofString(leaderId)) && (!ExcelUtils.isNullofString(serviceId)))
                            {
                                if (serviceId.compareTo("01") == 0) {
                                    userService.DeleteUserRole(leaderId, "017");
                                } else if (serviceId.compareTo("02") == 0) {
                                    userService.DeleteUserRole(leaderId, "016");
                                } else if (serviceId.compareTo("03") == 0) {
                                    userService.DeleteUserRole(leaderId, "018");
                                } else if (serviceId.compareTo("04") == 0) {
                                    userService.DeleteUserRole(leaderId, "020");
                                } else if (serviceId.compareTo("05") == 0) {
                                    userService.DeleteUserRole(leaderId, "019");
                                }
                            }
                        }

                        if (exteam.getServiceId().compareTo("01") == 0) {
                            if (userService.getUserRoles(exteam.getExteamLeaderId(), "017") == null) userService.SetUserRole(exteam.getExteamLeaderId(), "017"); //日常维修主管
                        } else if (exteam.getServiceId().compareTo("02") == 0) {
                            if (userService.getUserRoles(exteam.getExteamLeaderId(), "016") == null) userService.SetUserRole(exteam.getExteamLeaderId(), "016"); //应急保洁主管
                        } else if (exteam.getServiceId().compareTo("03") == 0) {
                            if (userService.getUserRoles(exteam.getExteamLeaderId(), "018") == null) userService.SetUserRole(exteam.getExteamLeaderId(), "018"); //工勤搬运主管
                        } else if (exteam.getServiceId().compareTo("04") == 0) {
                            if (userService.getUserRoles(exteam.getExteamLeaderId(), "020") == null) userService.SetUserRole(exteam.getExteamLeaderId(), "020"); //保安巡逻主管
                        } else if (exteam.getServiceId().compareTo("05") == 0) {
                            if (userService.getUserRoles(exteam.getExteamLeaderId(), "019") == null) userService.SetUserRole(exteam.getExteamLeaderId(), "019"); //设备运维主管
                        }
                    }

                    if ((ExcelUtils.isNullofString(exteam.getExteamLeaderId())) || (ExcelUtils.isNullofString(exteam.getServiceId()))) {
                        if ((!ExcelUtils.isNullofString(leaderId)) && (!ExcelUtils.isNullofString(serviceId)) && (userService != null))
                        {
                            if (serviceId.compareTo("01") == 0) {
                                userService.DeleteUserRole(leaderId, "017");
                            } else if (serviceId.compareTo("02") == 0) {
                                userService.DeleteUserRole(leaderId, "016");
                            } else if (serviceId.compareTo("03") == 0) {
                                userService.DeleteUserRole(leaderId, "018");
                            } else if (serviceId.compareTo("04") == 0) {
                                userService.DeleteUserRole(leaderId, "020");
                            } else if (serviceId.compareTo("05") == 0) {
                                userService.DeleteUserRole(leaderId, "019");
                            }
                        }
                    }

                    saveattr(hospId,exteam);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("保存服务班组对象失败:" + e.getMessage());
                }
            }
        }

        return exteam;
    }

    /**
     * 保存服务班组对象
     * @param value
     * @return
     * @throws Exception
     */
    private Exteam save(String hospId,Exteam value) throws Exception
    {
        Exteam exteam = null;

        if ((exteamRepository != null) && (value != null))
        {
            //保存服务班组对象
            try {

                String superCode = null;
                if ((value.getSuperId() != null) && (value.getSuperId() !="")) {
                    Exteam st = getByID(value.getSuperId());
                    value.setExteamFullname(st.getExteamFullname() + "->" + value.getExteamName());
                    value.setExteamLevel(st.getExteamLevel()+1);
                    superCode = st.getExteamCode();
                }
                else {
                    value.setExteamFullname(value.getExteamName());
                    value.setExteamLevel(1);
                }

                //产生服务班组编码
                value.setExteamCode(serialNumberService.getETCode(hospId,superCode));
                value.setHospID(hospId);

                exteam = exteamRepository.save(value);

                value.setId(exteam.getId());

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("保存服务班组对象失败:" + e.getMessage());
            }
        }

        return value;
    }

    /**
     * 依据外委公司ID和服务班组名称获取服务班组对象
     * @param excompanyID
     * @param exteamName
     * @return
     * @throws Exception
     */
    private Exteam findExteamByExcompanyIDandExteamName(String hospId,String excompanyID, String exteamName) throws Exception
    {
        Exteam exteam = null;

        //服务班组存在性校验
        try {
            if (exteamMapper != null) {
                List<Exteam> ls = exteamMapper.findExteamByExcompanyIDandExteamName(hospId,excompanyID, exteamName);

                if ((ls!= null) && (ls.size()>0))
                    exteam = ls.get(0);

                if (exteam != null)
                {
                    if ((exteam.getServiceId() != null) && (exteam.getServiceId() != "")) {
                        exteam.setServiceName(dataDictionaryService.getDataDictionary("db_extern_service_type").getNamebyID(exteam.getServiceId()));
                    }

                    if (exteam.getSuperId() != null)
                    {
                        exteam.setSuperName(dbCommonService.getExteamFullnamebyId(exteam.getSuperId()));
                    }

                    exteam.getGridPathbyId(dbCommonService);
                    exteam.getLeader(userService);
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            throw  new Exception("校验服务班组存在性失败:"+e.getMessage());
        }

        return exteam;
    }

}
