package cn.edu.ujs.lp.intells.basicinformation.service;

import cn.edu.ujs.lp.intells.basicinformation.dao.Excompany.ExServicesMapper;
import cn.edu.ujs.lp.intells.basicinformation.dao.Excompany.ExServicesRepository;
import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.ExServices;
import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.Exstaff;
import cn.edu.ujs.lp.intells.common.dao.User.UserMapper;
import cn.edu.ujs.lp.intells.common.entity.Hosp.ExServicesBrief;
import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.ExServicesInfoExcel;
import cn.edu.ujs.lp.intells.basicinformation.request.Excompany.ExServicesPageRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.Excompany.ExServicesSaveRequest;
import cn.edu.ujs.lp.intells.common.entity.Hosp.ExteamBrief;
import cn.edu.ujs.lp.intells.common.entity.User.UserRole;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import cn.edu.ujs.lp.intells.common.service.FileService;
import cn.edu.ujs.lp.intells.common.service.UserService;
import cn.edu.ujs.lp.intells.common.utils.BeanUtils;
import cn.edu.ujs.lp.intells.common.utils.ExcelUtils;
import cn.edu.ujs.lp.intells.common.utils.OSUtils;
import com.alibaba.excel.metadata.BaseRowModel;
import com.github.pagehelper.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExServicesService {

    @Autowired
    private ExServicesMapper exServicesMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ExServicesRepository exServicesRepository;


    @Autowired
    private DataDictionaryService dataDictionaryService;

    @Autowired
    private DBCommonService dbCommonService;

    @Autowired
    private ExstaffService exstaffService;


    /**
     * 更新业务部门主管信息
     * @param hospId
     * @return
     * @throws Exception
     */
    public boolean setExServicesLeader(String hospId) throws Exception
    {
        if ((dbCommonService != null) && (exstaffService != null)) {
            try {
                List<ExServicesBrief> elst = dbCommonService.getExservices(hospId,null);

                //建立业务部门负责人员与人员库的联系
                if (elst != null) {
                    List<ExServices> list = new ArrayList<>();

                    for (ExServicesBrief ecpb : elst) {
                        if ((ecpb.getLeaderName() != null) && (ecpb.getLeaderName().length() > 0) && (ecpb.getLeaderID() == null)) {
                            Exstaff exstaff = exstaffService.findByName(hospId, ecpb.getExcompanyId(), null, ecpb.getLeaderName());
                            if (exstaff != null) {
                                ExServices exServices = getByID(ecpb.getId());
                                exServices.setLeaderTel(exstaff.getExstaffMobile());
                                exServices.setLeaderID(exstaff.getId());

                                list.add(exServices);
                            }
                        }
                    }

                    exServicesRepository.saveAll(list);

                    //对业务部门负责人员信息设置角色
                    elst = dbCommonService.getExservices(hospId,null);
                    for (ExServicesBrief ecpb : elst) {
                        // 设置主管角色
                        if ((ecpb.getLeaderID() != null) && (userService != null) && (ecpb.getServiceId() != null)) {
                            if (ecpb.getServiceId().compareTo("01") == 0)
                                userService.SetUserRole(ecpb.getLeaderID(), "012"); //日常维修主管
                            else if (ecpb.getServiceId().compareTo("02") == 0)
                                userService.SetUserRole(ecpb.getLeaderID(), "011"); //应急保洁主管
                            else if (ecpb.getServiceId().compareTo("03") == 0)
                                userService.SetUserRole(ecpb.getLeaderID(), "013"); //工勤搬运主管
                            else if (ecpb.getServiceId().compareTo("04") == 0)
                                userService.SetUserRole(ecpb.getLeaderID(), "015"); //保安巡逻主管
                            else if (ecpb.getServiceId().compareTo("05") == 0)
                                userService.SetUserRole(ecpb.getLeaderID(), "014"); //设备运维主管
                        }
                    }
                }

                return true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("业务部门负责人信息更新失败:"+e.getMessage());
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

        /** String templateName = "ExcompanyServicestemplate";

        try {
            ExcelUtils.exportTemplate(ExServicesInfoExcel.class, response, templateName);
            return JsonResponse.success("业务部门导入模板导出成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("业务部门导入模板导出失败:"+e.getMessage());
        }*/
        try
        {
            if (fileService != null) {
                fileService.downloadExcelFile(OSUtils.getExservicetemplatefile(),response);
                return JsonResponse.success("业务部门导入模板导出成功");
            }
            else return JsonResponse.success("业务部门导入模板导出失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("业务部门导入模板导出失败:"+e.getMessage());
        }
    }

    /**
     * 导出业务部门数据
     * @param response
     * @return
     * @throws Exception
     */
    public JsonResponse exportExcelData(String hospId,HttpServletResponse response) throws Exception
    {
        String fileName = "ExcompanyServicesData";

        try {
            if (dbCommonService != null) {
                List<ExServicesBrief> elist = dbCommonService.getExservices(hospId, null);

                List<BaseRowModel> ls = new ArrayList<BaseRowModel>();
                for (ExServicesBrief e : elist) {
                    ExServicesInfoExcel ei = new ExServicesInfoExcel();
                    //BeanUtils.copyBeanIgnoreNull(ei,e);
                    //复制
                    ei.setExcompanyName(dbCommonService != null ? dbCommonService.getExcompanyNamebyId(e.getExcompanyId()) : null);
                    ei.setExserviceName(e.getExserviceName());
                    ei.setServiceName(dataDictionaryService != null ? dataDictionaryService.getDataDictionary("db_extern_service_type").getNamebyID(e.getServiceId()) : null);
                    ei.setLeaderName(e.getLeaderName());
                    ei.setLeaderTel(e.getLeaderTel());

                    ls.add(ei);
                }

                ExcelUtils.exportData(ExServicesInfoExcel.class, response, fileName, ls);
                return JsonResponse.success("业务部门数据导出成功");
            }
            else
                return JsonResponse.fail(1001,"从数据库中读取业务部门信息失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("业务部门数据导出失败："+e.getMessage());
        }
    }

    /**
     * 从Excel导入业务部门数据
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
            List<ArrayList> data = (List<ArrayList>) ExcelUtils.readData(file.getInputStream(), ExServicesInfoExcel.class);

            // arraylist转info.excel对象
            if ((data != null) && (data.size() > 0)) {
                for (ArrayList array:data) {
                    try {
                        //从Excel表提取一行数据
                        ExServicesInfoExcel excel = new ExServicesInfoExcel();
                        if ((array.size()>=1)&&(array.get(0) != null)) excel.setExcompanyName(array.get(0).toString().trim());
                        if ((array.size()>=2)&&(array.get(1) != null)) excel.setExserviceName(array.get(1).toString().trim());
                        if ((array.size()>=3)&&(array.get(2) != null)) excel.setServiceName( array.get(2).toString().trim());
                        if ((array.size()>=4)&&(array.get(3) != null)) excel.setLeaderName(array.get(3).toString().trim());
                        if ((array.size()>=5)&&(array.get(4) != null)) excel.setLeaderTel( array.get(4).toString().trim());

                        //入库保存
                        if ((excel.getExserviceName() != null) && (excel.getExserviceName() != "")) {
                            save(hospId, excel);
                            m_count++;
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        m_fail++;
                    }
                }

                if (m_count>0)
                    //return  JsonResponse.success("外委公司导入成功");
                    return  JsonResponse.success("业务部门导入成功",String.valueOf(m_count)+","+String.valueOf(m_fail));
                else
                    return JsonResponse.fail(1009, "业务部门导入失败");
            }
            else
                return JsonResponse.fail(1009, "读入业务部门为null");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new  Exception("读入业务部门信息失败:"+e.getMessage());
        }
    }

    /**
     * 分页查找
     * @param request
     * @return
     */
    public PageResponse<ExServicesBrief> page(String hospId,ExServicesPageRequest request) throws Exception
    {
        try {
            request.setHospId(hospId.trim());
            Page<ExServicesBrief> page = exServicesMapper.page(request, new RowBounds(request.getIndex(), request.getSize()));
            List<Long> lst = exServicesMapper.Rcount(request);
            Long total = 0L;
            if ((lst!=null) && (lst.size()>0))
                total = lst.get(0);

            if ((page!=null)&&(page.size()>0))
            {
                for (ExServicesBrief es:page)
                {
                    if ((es.getServiceId() != null) && (es.getServiceId() != "")) {
                        es.setServiceName(dataDictionaryService.getDataDictionary("db_extern_service_type").getNamebyID(es.getServiceId()));
                    }

                    es.getLeader(userService);
                }
            }

            return new PageResponse<ExServicesBrief>(page, request, total);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("业务部门分页查询失败："+e.getMessage());
        }
    }

    /**
     * 依据ID获取一个完整的外委公司对象
     * @param id
     * @return
     * @throws Exception
     */
    public ExServices getByID(String id) throws  Exception
    {
        ExServices result=null;

        if (exServicesMapper != null)
        {
            try
            {
                List<ExServices> lst = exServicesMapper.getbyid(id.trim());
                if ((lst != null) && (lst.size()>0)) {
                    result = lst.get(0);
                    if (result != null) {
                        result.getLeader(userService);

                        if ((result.getServiceId() != null) && (result.getServiceId() != "")) {
                            result.setServiceName(dataDictionaryService.getDataDictionary("db_extern_service_type").getNamebyID(result.getServiceId()));
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据ID:["+id+"]获取业务部门对象失败!");
            }
        }

        return result;
    }

    /**
     * 依据公司ID和业务部门名称获取业务部门对象
     * @param excompanyId
     * @param name
     * @return
     * @throws Exception
     */
    public ExServices getByName(String hospId,String excompanyId, String name) throws Exception
    {
        ExServices result = null;

        try
        {
            result = findExcompanyServicesByExcompanyIDandServiceName(hospId,excompanyId,name);

            if (result != null) {
                result.getLeader(userService);

                if ((result.getServiceId() != null) && (result.getServiceId() != "")) {
                    result.setServiceName(dataDictionaryService.getDataDictionary("db_extern_service_type").getNamebyID(result.getServiceId()));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("依据公司ID和业务部门名称获取业务部门对象失败："+e.getMessage());
        }

        return result;
    }

    /**
     * 删除指定id的业务部门
     * @param id
     * @return
     */
    public boolean delete(String id) throws  Exception
    {
        boolean result = false ;

        if (exServicesMapper != null)
        {
            try
            {
                exServicesMapper.delete(id.trim());
                result = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("删除业务部门对象["+id+"]异常!");
            }
        }

        return result ;
    }

    /**
     * 保存业务部门信息，如果不存在则新建，否则更新
     * @param request
     * @return
     */
    public ExServices save(String hospId,ExServicesSaveRequest request) throws Exception{

        ExServices exServices = null;
        String leaderId = null, serviceId = null;

        if ((exServicesRepository != null) && (request != null)) {

            //业务部门对象存在性校验
            try {
                if ((request.getId() != null) && (request.getId().trim() != ""))
                    exServices = getByID(request.getId());

                if (exServices == null)
                    exServices = findExcompanyServicesByExcompanyIDandServiceName(hospId,request.getExcompanyId(), request.getExserviceName());

                if (exServices != null) {
                    exServices.getLeader(userService);

                    if ((exServices.getServiceId() != null) && (exServices.getServiceId() != "")) {
                        exServices.setServiceName(dataDictionaryService.getDataDictionary("db_extern_service_type").getNamebyID(exServices.getServiceId()));
                    }
                }

                if (exServices != null) {
                    leaderId = exServices.getLeaderID();
                    serviceId = exServices.getServiceId();
                }
            }catch (Exception e)
            {
                e.printStackTrace();
                throw  new Exception(e.getMessage());
            }

            if (exServices == null) {
                //构造新的外委公司对象
                exServices = ExServices
                        .builder()
                        .build();
            }

            //保存外委公司对象
            if (exServices != null) {
                try {
                    BeanUtils.copyBeanIgnoreNull(exServices, request);

                    if (ExcelUtils.isNullofString(request.getLeaderID())) exServices.setLeaderID(null);
                    if (ExcelUtils.isNullofString(request.getLeaderTel())) exServices.setLeaderTel(null);

                    exServices.setHospID(hospId);
                    ExServices exServices_s = exServicesRepository.save(exServices);
                    exServices.setId(exServices_s.getId());

                    // 设置主管角色
                    if ((!ExcelUtils.isNullofString(exServices.getLeaderID())) && (!ExcelUtils.isNullofString(exServices.getServiceId()))&& (userService != null) ) {
                       if (((!ExcelUtils.isNullofString(leaderId)) && (leaderId.compareTo(exServices.getLeaderID()) != 0)) || ((!ExcelUtils.isNullofString(serviceId)) && (serviceId.compareTo(exServices.getServiceId()) != 0))){
                           if ((!ExcelUtils.isNullofString(leaderId)) && (!ExcelUtils.isNullofString(serviceId)))
                           {
                               if (serviceId.compareTo("01") == 0) {
                                   userService.DeleteUserRole(leaderId, "012");
                               } else if (serviceId.compareTo("02") == 0) {
                                   userService.DeleteUserRole(leaderId, "011");
                               } else if (serviceId.compareTo("03") == 0) {
                                   userService.DeleteUserRole(leaderId, "013");
                               } else if (serviceId.compareTo("04") == 0) {
                                   userService.DeleteUserRole(leaderId, "015");
                               } else if (serviceId.compareTo("05") == 0) {
                                   userService.DeleteUserRole(leaderId, "014");
                               }
                           }
                       }

                        if (exServices.getServiceId().compareTo("01") == 0) {
                            if (userService.getUserRoles(exServices.getLeaderID(), "012") == null) userService.SetUserRole(exServices.getLeaderID(), "012"); //日常维修主管
                        } else if (exServices.getServiceId().compareTo("02") == 0) {
                            if (userService.getUserRoles(exServices.getLeaderID(), "011") == null) userService.SetUserRole(exServices.getLeaderID(), "011"); //应急保洁主管
                        } else if (exServices.getServiceId().compareTo("03") == 0) {
                            if (userService.getUserRoles(exServices.getLeaderID(), "013") == null) userService.SetUserRole(exServices.getLeaderID(), "013"); //工勤搬运主管
                        } else if (exServices.getServiceId().compareTo("04") == 0) {
                            if (userService.getUserRoles(exServices.getLeaderID(), "015") == null) userService.SetUserRole(exServices.getLeaderID(), "015"); //保安巡逻主管
                        } else if (exServices.getServiceId().compareTo("05") == 0) {
                            if (userService.getUserRoles(exServices.getLeaderID(), "014") == null) userService.SetUserRole(exServices.getLeaderID(), "014"); //设备运维主管
                        }
                    }

                    if ((ExcelUtils.isNullofString(exServices.getLeaderID())) || (ExcelUtils.isNullofString(exServices.getServiceId()))) {
                        if ((!ExcelUtils.isNullofString(leaderId)) && (!ExcelUtils.isNullofString(serviceId)) && (userService != null))
                        {
                            if (serviceId.compareTo("01") == 0) {
                                userService.DeleteUserRole(leaderId, "012");
                            } else if (serviceId.compareTo("02") == 0) {
                                userService.DeleteUserRole(leaderId, "011");
                            } else if (serviceId.compareTo("03") == 0) {
                                userService.DeleteUserRole(leaderId, "013");
                            } else if (serviceId.compareTo("04") == 0) {
                                userService.DeleteUserRole(leaderId, "015");
                            } else if (serviceId.compareTo("05") == 0) {
                                userService.DeleteUserRole(leaderId, "014");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("保存业务部门对象失败！");
                }
            }
        }

        return exServices;
    }

    /**
     * 依据Excel信息保存外委公司信息
     * @param request
     * @return
     * @throws Exception
     */
    private ExServices save(String hospId,ExServicesInfoExcel request) throws Exception{

        ExServices exServices = null;
        String leaderId = null, serviceId = null;

        if (exServicesRepository != null) {

            //业务部门存在性校验
            try {
                exServices = findExcompanyServicesByExcompanyIDandServiceName(hospId,dbCommonService.getExcompanyIdbyName(hospId,request.getExcompanyName()),request.getExserviceName());

                if (exServices != null) {
                    exServices.getLeader(userService);

                    if ((exServices.getServiceId() != null) && (exServices.getServiceId() != "")) {
                        exServices.setServiceName(dataDictionaryService.getDataDictionary("db_extern_service_type").getNamebyID(exServices.getServiceId()));
                    }
                }

                if (exServices != null) {
                    leaderId = exServices.getLeaderID();
                    serviceId = exServices.getServiceId();
                }
            }catch (Exception e)
            {
                e.printStackTrace();
                throw  new Exception(e.getMessage());
            }

            if (exServices == null) {
                //构造新的外委公司对象
                exServices = ExServices
                        .builder()
                        .exserviceName(request.getExserviceName())
                        .excompanyId(dbCommonService!=null?dbCommonService.getExcompanyIdbyName(hospId,request.getExcompanyName()):null)
                        .serviceId(dataDictionaryService!=null?dataDictionaryService.getDataDictionary("db_extern_service_type").getIDbyName(request.getServiceName()):null)
                        .leaderName(request.getLeaderName())
                        .leaderTel(request.getLeaderTel())
                        .build();

                //补充由业主主管姓名获取其ID的功能

            } else {
                try {
                    //依据Excel数据修改业务部门对象
                    exServices.setExserviceName(request.getExserviceName());
                    String excompanyid = null;
                    if ((request.getExcompanyName() != null) && (request.getExcompanyName().length() > 0)) {
                        if (dbCommonService != null)
                            excompanyid = dbCommonService.getExcompanyIdbyName(hospId,request.getExcompanyName().trim());
                    }
                    exServices.setExcompanyId(excompanyid);
                    exServices.setServiceId(dataDictionaryService!=null?dataDictionaryService.getDataDictionary("db_extern_service_type").getIDbyName(request.getServiceName()):null);
                    exServices.setLeaderName(request.getLeaderName());
                    exServices.setLeaderTel(request.getLeaderTel());

                    String exstaffid = null;
                    if ((request.getLeaderName() != null) && (request.getLeaderName().length()>0) && (excompanyid != null))
                    {
                        if (dbCommonService != null) {
                         exstaffid = dbCommonService.getExstaffIdbyName(hospId,excompanyid,null,request.getLeaderName().trim());
                        }
                    }
                    exServices.setLeaderID(exstaffid);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    throw  new Exception("复制业务主管对象失败！");
                }
            }

            //保存外委公司对象
            try {
                exServices.setHospID(hospId);
                ExServices exServices_s = exServicesRepository.save(exServices);
                exServices.setId(exServices_s.getId());

                // 设置主管角色
                if ((!ExcelUtils.isNullofString(exServices.getLeaderID())) && (!ExcelUtils.isNullofString(exServices.getServiceId()))&& (userService != null) ) {
                    if (((!ExcelUtils.isNullofString(leaderId)) && (leaderId.compareTo(exServices.getLeaderID()) != 0)) || ((!ExcelUtils.isNullofString(serviceId)) && (serviceId.compareTo(exServices.getServiceId()) != 0))){
                        if ((!ExcelUtils.isNullofString(leaderId)) && (!ExcelUtils.isNullofString(serviceId)))
                        {
                            if (serviceId.compareTo("01") == 0) {
                                userService.DeleteUserRole(leaderId, "012");
                            } else if (serviceId.compareTo("02") == 0) {
                                userService.DeleteUserRole(leaderId, "011");
                            } else if (serviceId.compareTo("03") == 0) {
                                userService.DeleteUserRole(leaderId, "013");
                            } else if (serviceId.compareTo("04") == 0) {
                                userService.DeleteUserRole(leaderId, "015");
                            } else if (serviceId.compareTo("05") == 0) {
                                userService.DeleteUserRole(leaderId, "014");
                            }
                        }
                    }

                    if (exServices.getServiceId().compareTo("01") == 0) {
                        if (userService.getUserRoles(exServices.getLeaderID(), "012") == null) userService.SetUserRole(exServices.getLeaderID(), "012"); //日常维修主管
                    } else if (exServices.getServiceId().compareTo("02") == 0) {
                        if (userService.getUserRoles(exServices.getLeaderID(), "011") == null) userService.SetUserRole(exServices.getLeaderID(), "011"); //应急保洁主管
                    } else if (exServices.getServiceId().compareTo("03") == 0) {
                        if (userService.getUserRoles(exServices.getLeaderID(), "013") == null) userService.SetUserRole(exServices.getLeaderID(), "013"); //工勤搬运主管
                    } else if (exServices.getServiceId().compareTo("04") == 0) {
                        if (userService.getUserRoles(exServices.getLeaderID(), "015") == null) userService.SetUserRole(exServices.getLeaderID(), "015"); //保安巡逻主管
                    } else if (exServices.getServiceId().compareTo("05") == 0) {
                        if (userService.getUserRoles(exServices.getLeaderID(), "014") == null) userService.SetUserRole(exServices.getLeaderID(), "014"); //设备运维主管
                    }
                }

                if ((ExcelUtils.isNullofString(exServices.getLeaderID())) || (ExcelUtils.isNullofString(exServices.getServiceId()))) {
                    if ((!ExcelUtils.isNullofString(leaderId)) && (!ExcelUtils.isNullofString(serviceId)) && (userService != null))
                    {
                        if (serviceId.compareTo("01") == 0) {
                            userService.DeleteUserRole(leaderId, "012");
                        } else if (serviceId.compareTo("02") == 0) {
                            userService.DeleteUserRole(leaderId, "011");
                        } else if (serviceId.compareTo("03") == 0) {
                            userService.DeleteUserRole(leaderId, "013");
                        } else if (serviceId.compareTo("04") == 0) {
                            userService.DeleteUserRole(leaderId, "015");
                        } else if (serviceId.compareTo("05") == 0) {
                            userService.DeleteUserRole(leaderId, "014");
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("保存业务部门对象失败:"+e.getMessage());
            }
        }

        return exServices;
    }

    /**
     * 依据外委公司ID和业务部门名称获取业务部门对象
     * @param excompanyID
     * @param exserviceName
     * @return
     * @throws Exception
     */
    private ExServices findExcompanyServicesByExcompanyIDandServiceName(String hospId,String excompanyID, String exserviceName) throws Exception
    {
        ExServices exServices = null;

        //业务部门存在性校验
        try {
            if (exServicesMapper != null) {
                List<ExServices> ls = exServicesMapper.findExcompanyServicesByExcompanyIDandServiceName(hospId.trim(),excompanyID.trim(), exserviceName.trim());

                if ((ls!= null) && (ls.size()>0))
                    exServices = ls.get(0);

                if (exServices != null) {
                    exServices.getLeader(userService);

                    if ((exServices.getServiceId() != null) && (exServices.getServiceId() != "")) {
                        exServices.setServiceName(dataDictionaryService.getDataDictionary("db_extern_service_type").getNamebyID(exServices.getServiceId()));
                    }
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            throw  new Exception("校验业务部门存在性失败:"+e.getMessage());
        }

        return exServices;
    }

}
