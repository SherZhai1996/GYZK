package cn.edu.ujs.lp.intells.basicinformation.service;

import cn.edu.ujs.lp.intells.basicinformation.dao.Excompany.*;
import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.*;
import cn.edu.ujs.lp.intells.basicinformation.request.Excompany.ExstaffPageRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.Excompany.ExstaffSaveRequest;
import cn.edu.ujs.lp.intells.common.dao.User.UserGridRepository;
import cn.edu.ujs.lp.intells.common.dao.User.UserMapper;
import cn.edu.ujs.lp.intells.common.dao.User.UserRoleRepository;
import cn.edu.ujs.lp.intells.common.entity.Hosp.ExstaffBrief;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import cn.edu.ujs.lp.intells.common.service.FileService;
import cn.edu.ujs.lp.intells.common.service.UserService;
import cn.edu.ujs.lp.intells.common.utils.BeanUtils;
import cn.edu.ujs.lp.intells.common.utils.ExcelUtils;
import cn.edu.ujs.lp.intells.common.utils.MD5Util;
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

/**
 * 外委职工服务类
 */
@Service
public class ExstaffService {
    @Autowired
    private ExstaffMapper exstaffMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ExstaffRepository exstaffRepository;

    @Autowired
    private DataDictionaryService dataDictionaryService;

    @Autowired
    private DBCommonService dbCommonService;

    @Autowired
    private UserRoleRepository exstaffroleRepository;

    @Autowired
    private UserGridRepository exstaffGridRepository;

    @Autowired
    private ExstaffskillcertificateRepository exstaffskillcertificateRepository;

    @Autowired
    private ExstaffrestaurantcertificateRepository exstaffrestaurantcertificateRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private ExcompanyService excompanyService;

    @Autowired
    private ExServicesService exServicesService;

    @Autowired
    private ExteamService exteamService;


    /**
     * 导出Excel模板
     * @param response
     * @return
     * @throws Exception
     */
    public JsonResponse exportExcelTemplate(HttpServletResponse response) throws Exception{

        /**String templateName = "Exstafftemplate";

        try {
            ExcelUtils.exportTemplate(ExstaffInfoExcel.class, response, templateName);
            return JsonResponse.success("外委职工导入模板导出成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("外委职工导入模板导出失败:"+e.getMessage());
        }*/
        try
        {
            if (fileService != null) {
                fileService.downloadExcelFile(OSUtils.getExstafftemplatefile(),response);
                return JsonResponse.success("物业职工导入模板导出成功");
            }
            else return JsonResponse.success("物业职工导入模板导出失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("物业职工导入模板导出失败:"+e.getMessage());
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
        String fileName = "ExstaffData";

        try {
            if (dbCommonService != null) {
                List<ExstaffBrief> elist = dbCommonService.getExstaffs(hospId, null, null);

                List<BaseRowModel> ls = new ArrayList<BaseRowModel>();
                for (ExstaffBrief e : elist) {
                    ExstaffInfoExcel ei = new ExstaffInfoExcel();

                    //复制
                    BeanUtils.copyBeanIgnoreNull(ei, e);

                    //特殊字段的处理，即由ID转换为名称，日期转换为字符串
                    if (e.getExstaffBirthday() != null)
                        ei.setExstaffBirthday(ExcelUtils.DateToString(e.getExstaffBirthday(), "yyyyMMdd"));

                    if (e.getExstaffEmploystartdate() != null)
                        ei.setExstaffEmploystartdate(ExcelUtils.DateToString(e.getExstaffEmploystartdate(), "yyyyMMdd"));

                    if (e.getExstaffEmployenddate() != null)
                        ei.setExstaffEmployenddate(ExcelUtils.DateToString(e.getExstaffEmployenddate(), "yyyyMMdd"));

                    if(e.getExcompanyId() != null)
                        ei.setExcompanyName(dbCommonService != null ? dbCommonService.getExcompanyNamebyId(e.getExcompanyId()) : null);

                    if (e.getExteamId()!=null)
                        ei.setExteamName(dbCommonService != null ? dbCommonService.getExteamFullnamebyId(e.getExteamId()) : null);

                    if (e.getExstaffSex() != null)
                        ei.setExstaffSex(dataDictionaryService != null ? dataDictionaryService.getDataDictionary("db_sex").getNamebyID(e.getExstaffSex()) : null);

                    ls.add(ei);
                }

                ExcelUtils.exportData(ExstaffInfoExcel.class, response, fileName, ls);
                return JsonResponse.success("外委职工数据导出成功");
            }
            else return JsonResponse.fail(1001,"从数据库中读取外围员工信息失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("外委职工数据导出失败："+e.getMessage());
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
            List<ArrayList> data = (List<ArrayList>) ExcelUtils.readData(file.getInputStream(), ExstaffInfoExcel.class);

            // arraylist转info.excel对象
            if ((data != null) && (data.size() > 0)) {
                for (ArrayList array:data) {
                    try {
                        //从Excel表提取一行数据
                        ExstaffInfoExcel excel = new ExstaffInfoExcel();
                        if ((array.size()>=1)&&(array.get(0) != null)) excel.setExstaffName(array.get(0).toString().replace(" ",""));
                        if ((array.size()>=2)&&(array.get(1) != null)) excel.setExstaffSex(array.get(1).toString().replace(" ",""));

                        if ((array.size()>=3)&&(array.get(2) != null)) excel.setExstaffBirthday(array.get(2).toString().replace(" ",""));
                        if ((array.size()>=4)&&(array.get(3) != null)) excel.setExstaffMobile(array.get(3).toString().replace(" ",""));
                        if ((array.size()>=5)&&(array.get(4) != null)) excel.setExstaffJobno(array.get(4).toString().replace(" ",""));
                        if ((array.size()>=6)&&(array.get(5) != null)) excel.setExcompanyName(array.get(5).toString().replace(" ",""));
                        if ((array.size()>=7)&&(array.get(6) != null)) excel.setExteamName(array.get(6).toString().replace(" ",""));
                        if ((array.size()>=8)&&(array.get(7) != null)) excel.setExstaffEmploystartdate(array.get(7).toString().replace(" ",""));
                        if ((array.size()>=9)&&(array.get(8) != null)) excel.setExstaffEmployenddate(array.get(8).toString().replace(" ",""));
                        if ((array.size()>=10)&&(array.get(9) != null)) excel.setExstaffEmercontactname(array.get(9).toString().replace(" ",""));
                        if ((array.size()>=11)&&(array.get(10) != null)) excel.setExstaffEmercontacttel(array.get(10).toString().replace(" ",""));


                        //入库保存
                        if ((excel.getExstaffName() != null) && (excel.getExstaffName() != "")) {
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

                if (m_count>0) {

                    //更新服务班组组长信息
                    try
                    {
                        exteamService.setExteamLeader(hospId);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        throw new Exception("更新服务班组组长信息失败:"+e.getMessage());
                    }

                    //更新业务主管信息
                    try
                    {
                        exServicesService.setExServicesLeader(hospId);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        throw new Exception("更新业务主管信息失败:"+e.getMessage());
                    }

                    //更新外委公司领导人信息
                    try
                    {
                        excompanyService.setExcompanyLeader(hospId);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        throw new Exception("更新外委公司负责人信息失败:"+e.getMessage());
                    }

                    return JsonResponse.success("外委职工导入成功", String.valueOf(m_count) + "," + String.valueOf(m_fail));
                }else
                    return JsonResponse.fail(1009, "外委职工导入失败");
            }
            else
                return JsonResponse.fail(1009, "读入外委职工为null");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new  Exception("读入外委职工信息失败:"+e.getMessage());
        }
    }


    /**
     上传外委职工照片文件
     */
    public JsonResponse uploadExstaff_picture(String hospId,String exstaffid,MultipartFile picturefile) throws Exception
    {
        JsonResponse rt = JsonResponse.fail(1006, "外委职工照片保存失败");

        try {
            if ((fileService != null) && (dbCommonService != null) && (exstaffMapper != null)) {
                String hospCode = dbCommonService.getHospCodebyId(hospId);

                List<Exstaff> ls = exstaffMapper.getbyid(exstaffid);
                if ((ls != null) && (ls.size()>0))
                {
                    Exstaff exstaff = ls.get(0);

                    //上传网格图片
                    rt = fileService.fileUpload(hospCode, "basic", picturefile);

                    //上传成功则修改数据库记录内容
                    if (rt.getCode() == 0) {
                        //删除原有照片
                        if ((exstaff.getExstaffPic() != null)&&(exstaff.getExstaffPic() != ""))
                            fileService.delFile(exstaff.getExstaffPic());

                        //保存网格图片文件名到数据库记录
                        exstaffRepository.UpdateExstaff_pic(exstaff.getId(), rt.getData().toString());
                        rt = JsonResponse.success("外委职工照片保存成功",rt.getData().toString());
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("网格照片上传失败："+e.getMessage());
        }

        return rt;
    }

    /**
     * 删除外委职工照片
     * @return
     */
    public JsonResponse clearExstaff_picture(String exstaffid) throws Exception
    {
        JsonResponse  rt = JsonResponse.fail(2001, "删除外委职工照片失败");

        try {
            if ((exstaffRepository != null) && (exstaffMapper != null)) {
                List<Exstaff> ls = exstaffMapper.getbyid(exstaffid);
                if ((ls != null) && (ls.size() > 0)) {
                    Exstaff exstaff = ls.get(0);

                    if (fileService != null) {
                        //删除图像文件
                        if (fileService.delFile(exstaff.getExstaffPic()).getCode() == 0) {
                            exstaffRepository.clearExstaff_pic(exstaff.getId());

                            rt = JsonResponse.success("删除外委职工照片成功");
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("删除外委职工照片失败:"+e.getMessage());
        }

        return rt;
    }


    /**
     上传外委职工技术证书照片文件
     */
    public JsonResponse uploadExstaff_skillcertificate_pic(String hospId,String exstaffId,String skillcertificateId,MultipartFile picturefile) throws Exception
    {
        JsonResponse rt = JsonResponse.fail(1006, "外委职工技术证书照片保存失败");

        try {
            if ((fileService != null) && (dbCommonService != null) && (exstaffMapper != null) && (exstaffskillcertificateRepository != null)) {
                String hospCode = dbCommonService.getHospCodebyId(hospId);

                List<Exstaffskillcertificate> ls = exstaffMapper.getExstaffskillcertificate(exstaffId,skillcertificateId);

                Exstaffskillcertificate exstaffskillcertificate = null;

                if ((ls != null) && (ls.size() > 0)) exstaffskillcertificate = ls.get(0);

                //不存在则新增
                if (exstaffskillcertificate == null) {
                    exstaffskillcertificate = new Exstaffskillcertificate();
                    exstaffskillcertificate.setExstaffId(exstaffId);
                    exstaffskillcertificate.setSkillcertificateId(skillcertificateId);
                    exstaffskillcertificate = exstaffskillcertificateRepository.save(exstaffskillcertificate);
                }

                if (exstaffskillcertificate != null) {
                    //上传图片
                    rt = fileService.fileUpload(hospCode, "basic", picturefile);

                    //上传成功则修改数据库记录内容并删除原有照片
                    if (rt.getCode() == 0) {
                        //删除原有照片
                        if ((exstaffskillcertificate.getSkillcertificatePic() != null)&&(exstaffskillcertificate.getSkillcertificatePic() != ""))
                            fileService.delFile(exstaffskillcertificate.getSkillcertificatePic());

                        //保存图片文件名到数据库记录
                        exstaffMapper.UpdateExstaff_skillcertificate_pic(exstaffId,skillcertificateId, rt.getData().toString());
                        rt = JsonResponse.success("外委职工技术证书照片保存成功",rt.getData().toString());
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("外委职工技术证书照片上传失败："+e.getMessage());
        }

        return rt;
    }

    /**
     * 删除外委职工技术证书照片
     * @return
     */
    public JsonResponse clearExstaff_skillcertificate_pic(String exstaffId,String skillcertificateId) throws Exception
    {
        JsonResponse  rt = JsonResponse.fail(2001, "删除外委职工技术证书照片失败");

        try {
            if ((exstaffskillcertificateRepository != null) && (exstaffMapper != null) && (fileService != null)) {
                List<Exstaffskillcertificate> ls = exstaffMapper.getExstaffskillcertificate(exstaffId,skillcertificateId);

                Exstaffskillcertificate exstaffskillcertificate = null;

                if ((ls != null) && (ls.size() > 0))
                    exstaffskillcertificate = ls.get(0);

                if (exstaffskillcertificate != null) {
                    //删除图像文件
                    if (fileService.delFile(exstaffskillcertificate.getSkillcertificatePic()).getCode() == 0) {
                       exstaffMapper.clearExstaff_skillcertificate_pic(exstaffId,skillcertificateId);
                       rt = JsonResponse.success("删除外委职工技术证书照片成功");
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("删除外委职工技术证书照片失败:"+e.getMessage());
        }

        return rt;
    }


    /**
     * 清除指定ID的外委员工所有技术证书照片文件
     * @param exstaffId
     * @return
     * @throws Exception
     */
    public JsonResponse clearExstaff_Allskillcertificate_pic(String exstaffId) throws Exception
    {
        JsonResponse  rt = JsonResponse.fail(2001, "删除外委职工所有技术证书照片失败");

        try {
            if ((exstaffRepository != null) && (exstaffMapper != null)) {
                Exstaff exstaff = getByID(exstaffId);

                if (exstaff != null) {
                    List<String> skillids = exstaff.getExstaffskillcertificateIds();

                    if (skillids != null) {
                        for (String skillid:skillids) {
                            List<Exstaffskillcertificate> ls = exstaffMapper.getExstaffskillcertificate(exstaffId, skillid);
                            if ((ls != null) && (ls.size() > 0)) {
                                Exstaffskillcertificate exstaffskillcertificate = ls.get(0);

                                if (fileService != null) {
                                    //删除图像文件
                                    if (fileService.delFile(exstaffskillcertificate.getSkillcertificatePic()).getCode() == 0) {
                                        exstaffMapper.clearExstaff_skillcertificate_pic(exstaffId, skillid);
                                    }
                                }
                            }
                        }

                        rt = JsonResponse.success("清除外委职工所有技术证书照片成功");
                    }
                    else rt = JsonResponse.success("该外委职工没有技术证书");
                }
                else rt = JsonResponse.success("该外委职工不存在");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("删除外委职工技术证书照片失败:"+e.getMessage());
        }

        return rt;
    }

    /**
     * 分页查找
     * @param request
     * @return
     */
    public PageResponse<ExstaffBrief> page(String hospId,ExstaffPageRequest request) throws Exception
    {
        try {
            request.setHospId(hospId);
            Page<ExstaffBrief> page = exstaffMapper.page(request, new RowBounds(request.getIndex(), request.getSize()));
            List<Long> lst = exstaffMapper.Rcount(request);
            Long total = 0L;
            if ((lst!=null) && (lst.size()>0)) total = lst.get(0);

            if ((page!=null)&&(page.size()>0))
            {
                for (ExstaffBrief esb:page)
                {
                    if (userService != null) esb.setRoleIds(userService.getUserRoles(esb.getId(),null));
                    esb.getExstaffActivestateName();
                    esb.getExstaffJobstateName(dataDictionaryService);
                }
            }

            return new PageResponse<ExstaffBrief>(page, request, total);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("外委职工分页查询失败："+e.getMessage());
        }
    }

    /**
     * 依据ID获取一个完整的外委职工对象
     * @param id
     * @return
     * @throws Exception
     */
    public Exstaff getByID(String id) throws  Exception
    {
        Exstaff result=null;

        if (exstaffMapper != null) {
            try {
                List<Exstaff> lst = exstaffMapper.getbyid(id);
                if ((lst != null) && (lst.size() > 0))
                    result = lst.get(0);

                if (result != null) {
                    result.getExcompanyName(dbCommonService);
                    result.getExstaffActivestateName();
                    result.getExstaffJobstateName(dataDictionaryService);
                    result.getExteamName(dbCommonService);
                    result.getExstaffskillcertificateIdsFromDB();
                    result.getExstaffskillcertificatePicsFromDB();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("依据ID:[" + id + "]获取外委职工对象失败:" + e.getMessage());
            }
        }

        return result;
    }

    /**
     * 删除指定id的外委职工对象
     * @param id
     * @return
     */
    public boolean delete(String id) throws  Exception
    {
        boolean result = false ;

        if (exstaffMapper != null)
        {
            try
            {
                exstaffMapper.delete(id);
                result = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("删除外委职工对象["+id+"]异常:"+e.getMessage());
            }
        }

        return result ;
    }

    /**
     * 保存外委员工附加信息，即网格信息和角色信息
     * @param hospId
     * @param exstaff
     * @return
     * @throws Exception
     */
    private boolean saveattr(String hospId,Exstaff exstaff) throws Exception
    {
        boolean result = false ;

        if ((exstaffMapper != null) && (exstaffRepository != null)&&(userMapper != null) && (exstaffskillcertificateRepository != null) && (exstaff != null)) {
            try {
                //删除原有相关信息(网格与角色)
                // exstaffMapper.deleteAtt(exstaff.getId());

                /**
                 * 删除不存在的技术证书记录
                 */
                Exstaff exstaff_old = getByID(exstaff.getId());
                List<String> skillids0 = exstaff_old.getExstaffskillcertificateIds();
                List<String> skillids = exstaff.getExstaffskillcertificateIds();

                if (skillids0 != null)
                {
                    for (String skillid0:skillids0)
                    {
                        if (skillids.indexOf(skillid0)<0)
                        {
                            exstaffMapper.deleteExstaff_skillcertificate(exstaff.getId(),skillid0);
                        }
                    }
                }

                result=true;
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("保存物业职工附加信息失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 保存外委职工信息，包括新增和更新
     * @param hospId
     * @param request
     * @return
     * @throws Exception
     */
    public Exstaff save(String hospId, ExstaffSaveRequest request) throws Exception
    {
        Exstaff exstaff = null;
        boolean isExists = false ;
        String exteamid = null;

        if ((exstaffRepository != null)&&(exstaffMapper != null)&&(request != null)) {

            try {

                if ((request.getId() != null) && (request.getId().trim() != "")) exstaff = getByID(request.getId());

                if (exstaff == null) {
                    //存在性校验
                    exstaff = isExist(hospId, request);
                }

                //复制对象
                if (exstaff == null) {
                    //构造新的网格对象
                    exstaff = Exstaff
                            .builder()
                            .build();
                }
                else {
                    isExists = true;

                    if (exstaff.getExteamId() != null)
                        exteamid = exstaff.getExteamId();
                    else
                        exteamid = null;
                }

                if (exstaff != null) {
                    try {
                        String loginname = exstaff.getExstaffLoginname();

                        //复制对象
                        BeanUtils.copyBeanIgnoreNull(exstaff, request);
                        if ((request.getExteamId() == null) || ((request.getExteamId() != null) && (request.getExteamId().length()<1)))
                            exstaff.setExteamId(null);

                        exstaff.setExstaffskillcertificateIds(request.getExstaffskillcertificateIds());

                        //登陆名修改，复位密码
                        if ((loginname != null) && (exstaff.getExstaffLoginname() != null) && (loginname.compareTo(exstaff.getExstaffLoginname()) != 0))
                        {
                            exstaff.setExstaffLoginpassword(null);
                        }

                        exstaff.setHospID(hospId);

                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new Exception("复制外委职工对象失败:" + e.getMessage());
                    }

                    //保存网格对象
                    exstaff.setHospID(hospId);
                    Exstaff exstaff_s = save(exstaff);
                    exstaff.setId(exstaff_s.getId());

                    //设置角色
                    if ((!ExcelUtils.isNullofString(exstaff.getExteamId())) && (userService != null) ) {
                        if (((!ExcelUtils.isNullofString(exteamid)) && (exteamid.compareTo(exstaff.getExteamId()) != 0))||(ExcelUtils.isNullofString(exteamid))) {
                            //删除原角色
                            userService.DeleteUserExstaffRole(exstaff.getId());
                        }

                        //设置角色
                        String serviceId = exteamService.getServiceID(exstaff.getExteamId());
                        if (serviceId != null) {
                            if (serviceId.compareTo("01") == 0) {
                                if (userService.getUserRoles(exstaff.getId(), "024") == null)
                                    userService.SetUserRole(exstaff.getId(), "024"); //日常维修主管
                            } else if (serviceId.compareTo("02") == 0) {
                                if (userService.getUserRoles(exstaff.getId(), "022") == null)
                                    userService.SetUserRole(exstaff.getId(), "022"); //应急保洁主管
                            } else if (serviceId.compareTo("03") == 0) {
                                if (userService.getUserRoles(exstaff.getId(), "023") == null)
                                    userService.SetUserRole(exstaff.getId(), "023"); //工勤搬运主管
                            } else if (serviceId.compareTo("04") == 0) {
                                if (userService.getUserRoles(exstaff.getId(), "021") == null)
                                    userService.SetUserRole(exstaff.getId(), "021"); //保安巡逻主管
                            } else if (serviceId.compareTo("05") == 0) {
                                if (userService.getUserRoles(exstaff.getId(), "025") == null)
                                    userService.SetUserRole(exstaff.getId(), "025"); //设备运维主管
                            }
                        }
                    }

                    if (ExcelUtils.isNullofString(exstaff.getExteamId())) {
                        //删除原角色
                        userService.DeleteUserExstaffRole(exstaff.getId());
                    }

                    //保存附加信息，即网格和角色信息
                    saveattr(hospId,exstaff);

                    //校验用户的角色
                    userMapper.VerifyUserRoles(exstaff.getId());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("保存外委职工对象失败:"+e.getMessage());
            }
        }

        return exstaff;
    }

    /**
     * 查找外委职工
     * @param hospId
     * @param excompanyId
     * @param name
     * @return
     * @throws Exception
     */
    public Exstaff findByName(String hospId,String excompanyId,String exteamId,String name) throws Exception
    {
            Exstaff exstaff = null;

            if (exstaffMapper != null){

                //外委职工存在性校验
                try {
                    List<Exstaff> lst = exstaffMapper.findExstaffByExcompanyIDExteamIDandName(hospId, excompanyId,exteamId,name);

                    if ((lst != null) && (lst.size() > 0)) exstaff = lst.get(0);

                    if (exstaff != null) {
                        exstaff.getExcompanyName(dbCommonService);
                        exstaff.getExstaffActivestateName();
                        exstaff.getExstaffJobstateName(dataDictionaryService);
                        exstaff.getExteamName(dbCommonService);
                        exstaff.getExstaffskillcertificateIdsFromDB();
                        exstaff.getExstaffskillcertificatePicsFromDB();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("查找外委职工失败:" + e.getMessage());
                }
            }

            return exstaff;
    }

    /**
     * 依据Excel信息保存外委职工信息
     * @param request
     * @return
     * @throws Exception
     */
    private Exstaff save(String hospId,ExstaffInfoExcel request) throws Exception
    {

       Exstaff exstaff = null;

        if ((exstaffRepository != null)&&(dbCommonService != null)) {

            //构建新对象
            try {
                //外委职工对象存在性校验
                exstaff = isExist(hospId,request);

                //构造新对象
                if (exstaff == null) {
                    //构造新的网格对象
                    exstaff = Exstaff
                            .builder()
                            .build();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("判断存在性和构建新外委职工对象失败:"+e.getMessage());
            }

            //复制对象
            try {
                //复制对象
                BeanUtils.copyBeanIgnoreNull(exstaff, request);

                exstaff.setHospID(hospId);

                //特殊字段处理
                if ((request.getExstaffSex() != null)&&(request.getExstaffSex() != ""))
                exstaff.setExstaffSex(dataDictionaryService != null?dataDictionaryService.getDataDictionary("db_sex").getIDbyName(request.getExstaffSex()):null);

                if ((request.getExcompanyName() != null)&&(request.getExcompanyName() != ""))
                exstaff.setExcompanyId(dbCommonService.getExcompanyIdbyName(hospId,request.getExcompanyName().trim()));

                if ((request.getExteamName() != null)&&(request.getExteamName() != "")){
                    String excompanyId = dbCommonService.getExcompanyIdbyName(hospId,request.getExcompanyName());
                    String exteam_id = dbCommonService.getExteamIdbyName(hospId,excompanyId,request.getExteamName().replace(" ","").trim());

                    exstaff.setExteamId(exteam_id);

                }

                if ((request.getExstaffBirthday() != null)&&(request.getExstaffBirthday() != ""))
                {
                    exstaff.setExstaffBirthday(ExcelUtils.StringToDate(request.getExstaffBirthday(),"yyyyMMdd"));
                }

                if ((request.getExstaffEmploystartdate() != null)&&(request.getExstaffEmploystartdate() != ""))
                {
                    exstaff.setExstaffEmploystartdate(ExcelUtils.StringToDate(request.getExstaffEmploystartdate(),"yyyyMMdd"));
                }

                if ((request.getExstaffEmployenddate() != null)&&(request.getExstaffEmployenddate() != ""))
                {
                    exstaff.setExstaffEmployenddate(ExcelUtils.StringToDate(request.getExstaffEmployenddate(),"yyyyMMdd"));
                }

                exstaff.setHospID(hospId);

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("复制物业员工对象失败:" + e.getMessage());
            }

            //保存网格对象
            try {

                if (exstaff != null) {
                    exstaff.setHospID(hospId);
                    exstaff.setExstaffLoginpassword(null); //设置为默认密码
                    Exstaff exstaff_s = save(exstaff);
                    exstaff.setId(exstaff_s.getId());

                    //保存附加信息，即网格和角色信息
                    saveattr(hospId,exstaff);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("保存外委职工失败:"+e.getMessage());
            }
        }

        return exstaff;
    }

    /**
     * 保存对象的存在性校验, 同一公司不允许存在重名员工
     * @param request
     * @return
     * @throws Exception
     */
    private Exstaff isExist(String hospId,ExstaffSaveRequest request)  throws  Exception
    {
        Exstaff exstaff = null;

        if ((exstaffRepository != null) && (exstaffMapper != null) &&(request != null)){

            //外委职工存在性校验
            try {
                if ((request.getId() != null) && (request.getId().trim() != "")) exstaff = getByID(request.getId());

                if (exstaff == null) {
                    // List<Exstaff> lst = exstaffMapper.findExstaffByExcompanyIDExteamIDandName(hospId, request.getExcompanyId(),request.getExteamId(),request.getExstaffName());
                    List<Exstaff> lst = exstaffMapper.findExstaffByExcompanyIDExteamIDandName(hospId, request.getExcompanyId(), null, request.getExstaffName().replace(" ", ""));
                    if ((lst != null) && (lst.size() > 0)) exstaff = lst.get(0);
                }

                if (exstaff != null) {
                    exstaff.getExcompanyName(dbCommonService);
                    exstaff.getExstaffActivestateName();
                    exstaff.getExstaffJobstateName(dataDictionaryService);
                    exstaff.getExteamName(dbCommonService);
                    exstaff.getExstaffskillcertificateIdsFromDB();
                    exstaff.getExstaffskillcertificatePicsFromDB();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("外委存在性校验失败:" + e.getMessage());
            }
        }

        return exstaff;
    }

    /**
     * 保存对象的存在性校验, 同一公司不允许存在重名员工
     * @param request
     * @return
     * @throws Exception
     */
    private Exstaff isExist(String hospId,ExstaffInfoExcel request)  throws  Exception
    {
        Exstaff exstaff = null;

        if ((exstaffRepository != null) && (exstaffMapper != null) && (dbCommonService != null) &&(request != null)) {

            //依据外委公司、服务班组判断外委职工存在性
            try {
                String excompanyId = dbCommonService.getExcompanyIdbyName(hospId,request.getExcompanyName());
                // String exteamId = dbCommonService.getExteamIdbyFullName(hospId,excompanyId,request.getExteamName());
                // List<Exstaff> lst = exstaffMapper.findExstaffByExcompanyIDExteamIDandName(hospId,excompanyId,exteamId,request.getExstaffName());
                List<Exstaff> lst = exstaffMapper.findExstaffByExcompanyIDExteamIDandName(hospId,excompanyId,null,request.getExstaffName().replace(" ",""));

                if ((lst != null) && (lst.size()>0))
                    exstaff = lst.get(0);

                if (exstaff != null) {
                    exstaff.getExcompanyName(dbCommonService);
                    exstaff.getExstaffActivestateName();
                    exstaff.getExstaffJobstateName(dataDictionaryService);
                    exstaff.getExteamName(dbCommonService);
                    exstaff.getExstaffskillcertificateIdsFromDB();
                    exstaff.getExstaffskillcertificatePicsFromDB();
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("外委职工存在性校验失败:" + e.getMessage());
            }
        }

        return exstaff;
    }

    /**
     * 保存外围职工对象
     * @param value
     * @return
     * @throws Exception
     */
    private Exstaff save(Exstaff value) throws Exception
    {
        Exstaff exstaff = null;

        if ((exstaffRepository != null) && (value != null))
        {
            //保存外围公司员工对象 ,remark中存放带公司名全路径名称，缺省密码为12345678
            try {
                if ((value.getExstaffLoginname()==null) || (value.getExstaffLoginname().trim() ==""))
                    value.setExstaffLoginname(value.getExstaffMobile());
                if ((value.getExstaffLoginnickname()==null) || (value.getExstaffLoginnickname().trim() ==""))
                    value.setExstaffLoginnickname(value.getExstaffLoginname());
                if ((value.getExstaffLoginpassword()==null) || (value.getExstaffLoginpassword().trim()==""))
                    value.setExstaffLoginpassword(MD5Util.md5(value.getExstaffLoginname(),"12345678"));

                if ((value.getExteamId() != null)&&(value.getExteamId() != ""))
                {
                    value.setRemark(dbCommonService.getExteamFullnamebyId(value.getExteamId())+"->"+value.getExstaffName());
                }else
                    value.setRemark(value.getExstaffName());

                if ((value.getExcompanyId() != null)&&(value.getExcompanyId() != ""))
                {
                    value.setRemark(dbCommonService.getExcompanyNamebyId(value.getExcompanyId())+"->"+value.getRemark());
                }

                value.setUserType("3");
                exstaff = exstaffRepository.save(value);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("保存外委职工对象失败:" + e.getMessage());
            }
        }

        return exstaff;
    }

}
