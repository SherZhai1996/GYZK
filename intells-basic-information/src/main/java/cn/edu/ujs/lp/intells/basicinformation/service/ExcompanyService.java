package cn.edu.ujs.lp.intells.basicinformation.service;

import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.Exstaff;
import cn.edu.ujs.lp.intells.common.dao.User.UserMapper;
import cn.edu.ujs.lp.intells.common.entity.Hosp.ExServicesBrief;
import cn.edu.ujs.lp.intells.common.entity.Hosp.ExcompanyBrief;
import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.ExcompanyInfoExcel;
import cn.edu.ujs.lp.intells.basicinformation.request.Excompany.ExcompanyPageRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.Excompany.ExcompanySaveRequest;
import cn.edu.ujs.lp.intells.common.entity.User.UserRole;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.service.FileService;
import cn.edu.ujs.lp.intells.common.service.UserService;
import cn.edu.ujs.lp.intells.common.utils.BeanUtils;
import cn.edu.ujs.lp.intells.basicinformation.dao.Excompany.ExcompanyMapper;
import cn.edu.ujs.lp.intells.basicinformation.dao.Excompany.ExcompanyRepository;
import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.Excompany;
import cn.edu.ujs.lp.intells.common.utils.ExcelUtils;
import cn.edu.ujs.lp.intells.common.utils.OSUtils;
import com.alibaba.excel.metadata.BaseRowModel;
import com.github.pagehelper.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class ExcompanyService {
    @Autowired
    private DBCommonService dbCommonService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ExcompanyMapper excompanyMapper;

    @Autowired
    private ExcompanyRepository excompanyRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private ExstaffService exstaffService;

    /**
     * 更新外委公司负责人信息
     * @param hospId
     * @return
     * @throws Exception
     */
    public boolean setExcompanyLeader(String hospId) throws Exception
    {
        if ((dbCommonService != null) && (exstaffService != null)&&(userMapper != null)) {
            try {
                List<ExcompanyBrief> elst = dbCommonService.getExcompanys(hospId);

                /**
                 * 建立公司负责人与人员库的关联
                 */
                if (elst != null) {
                    List<Excompany> list = new ArrayList<>();

                    for (ExcompanyBrief ecpb : elst) {
                        if ((ecpb.getExcompanyLeadername() != null) && (ecpb.getExcompanyLeadername().length() > 0) && (ecpb.getExcompanyLeaderID() == null)) {
                            Exstaff exstaff = exstaffService.findByName(hospId, ecpb.getId(), null, ecpb.getExcompanyLeadername());
                            if (exstaff != null) {
                                Excompany excompany = getByID(ecpb.getId());
                                excompany.setExcompanyLeadertel(exstaff.getExstaffMobile());
                                excompany.setExcompanyLeaderID(exstaff.getId());
                                list.add(excompany);
                            }
                        }
                    }

                    excompanyRepository.saveAll(list);
                }

                //对物业公司负责人员信息设置角色
                elst = dbCommonService.getExcompanys(hospId);
                if (elst != null) {
                    for (ExcompanyBrief ecpb : elst) {
                        if ((ecpb!=null) && (ecpb.getExcompanyLeaderID() != null))
                        {
                            if (userService != null) userService.SetUserRole(ecpb.getExcompanyLeaderID(),"009");
                        }
                    }
                }

                return true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("外委公司负责人信息更新失败:"+e.getMessage());
            }
        }

        return false;
    }


    /**
     上传营业执照图片文件
     */
    public JsonResponse uploadCertificate(String hospId,String excompanyId,MultipartFile certificatefile) throws Exception
    {
        JsonResponse rt;

        try {
            Excompany excompany = getByID(excompanyId);
            String hospCode = dbCommonService.getHospCodebyId(hospId);

            if ((fileService != null) && (excompany != null)&&(hospCode != null)){

                rt = fileService.fileUpload(hospCode, "basic", certificatefile);

                if (rt.getCode() == 0) {
                    clearCertificate(hospId,excompanyId);

                    excompanyRepository.updateExcompanycertificate(excompanyId, rt.getData().toString());
                    rt = JsonResponse.success("营业执照图片保存成功");
                } else {
                    rt = JsonResponse.fail(1006, "营业执照图片保存失败");
                }
            } else
                rt = JsonResponse.fail(1006, "营业执照图片保存失败");

        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("营业执照图片保存失败:"+e.getMessage());
        }

        return rt;
    }


    /**
     * 删除营业执照图片
     * @return
     */
    public JsonResponse clearCertificate(String hospId,String excompanyId) throws Exception
    {
        JsonResponse rt;

        try {
            Excompany excompany = getByID(excompanyId);
            if ((excompanyRepository != null) && (excompany != null)){

                if ((fileService != null) && (excompany.getExcompanyCertificate() != null)) {
                    rt = fileService.delFile(excompany.getExcompanyCertificate());

                    if (rt.getCode()==0)
                        excompanyRepository.clearExcompanycertificate(excompanyId);
                }

                rt = JsonResponse.success("删除营业执照图片成功");
            } else
                rt = JsonResponse.fail(1001, "删除营业执照图片失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("删除营业执照图片失败:"+e.getMessage());
        }

        return rt;
    }

    /**
     * 导出Excel模板
     * @param response
     * @return
     * @throws Exception
     */
    public JsonResponse exportExcelTemplate(HttpServletResponse response) throws Exception{

        /**String templateName = "Excompanytemplate";

        try {
            ExcelUtils.exportTemplate(ExcompanyInfoExcel.class, response, templateName);
            return JsonResponse.success("外委公司导入模板导出成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("外委公司导入模板导出失败:"+e.getMessage());
        }*/

        try
        {
            if (fileService != null) {
                fileService.downloadExcelFile(OSUtils.getExcompanytemplatefile(),response);
                return JsonResponse.success("物业公司导入模板导出成功");
            }
            else return JsonResponse.success("物业公司导入模板导出失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("物业公司导入模板导出失败:"+e.getMessage());
        }
    }


    /**
     * 导出外委公司数据
     * @param response
     * @return
     * @throws Exception
     */
    public JsonResponse exportExcelData(String hospCode, HttpServletResponse response) throws Exception
    {
        String fileName = "ExcompanyData";

        try {
            if (dbCommonService != null) {
                List<ExcompanyBrief> elist = dbCommonService.getExcompanys(hospCode);

                List<BaseRowModel> ls = new ArrayList<BaseRowModel>();
                for (ExcompanyBrief e : elist) {
                    ExcompanyInfoExcel ei = new ExcompanyInfoExcel();
                    BeanUtils.copyBeanIgnoreNull(ei, e);

                    if (e.getExcompanyServicestartdate() != null)
                        ei.setExcompanyServicestartdate(ExcelUtils.DateToString(e.getExcompanyServicestartdate(), "yyyyMMdd"));

                    if (e.getExcompanyServiceenddate() != null)
                        ei.setExcompanyServiceenddate(ExcelUtils.DateToString(e.getExcompanyServiceenddate(), "yyyyMMdd"));

                    ls.add(ei);
                }

                ExcelUtils.exportData(ExcompanyInfoExcel.class, response, fileName, ls);
                return JsonResponse.success("外委公司数据导出成功");
            }
            else
                return JsonResponse.fail(1001,"从数据库中读取外委公司信息失败!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("外委公司数据导出失败："+e.getMessage());
        }
    }


    /**
     * 导入Excel数据文件
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
            List<ArrayList> data = (List<ArrayList>) ExcelUtils.readData(file.getInputStream(),ExcompanyInfoExcel.class);

            // arraylist转info.excel对象
            if ((data != null) && (data.size() > 0)) {
                for (ArrayList array:data) {
                    //if (array.size() != 8) continue;

                    try {
                        //从Excel表提取一行数据
                        ExcompanyInfoExcel excel = new ExcompanyInfoExcel();

                        if ((array.size()>=1)&&(array.get(0) != null)) excel.setExcompanyName(array.get(0).toString().replace(" ",""));
                        if ((array.size()>=2)&&(array.get(1) != null)) excel.setExcompanyLegal(array.get(1).toString().replace(" ",""));
                        if ((array.size()>=3)&&(array.get(2) != null)) excel.setExcompanyLeadername(array.get(2).toString().replace(" ",""));
                        if ((array.size()>=4)&&(array.get(3) != null)) excel.setExcompanyLeadertel(array.get(3).toString().replace(" ",""));
                        if ((array.size()>=5)&&(array.get(4) != null))
                            excel.setExcompanyServicestartdate(array.get(4).toString().replace(" ",""));
                        if ((array.size()>=6)&&(array.get(5) != null))
                            excel.setExcompanyServiceenddate(array.get(5).toString().replace(" ",""));
                        if ((array.size()>=7)&&(array.get(6) != null)) excel.setServicesRemark(array.get(6).toString().replace(" ",""));
                        if ((array.size()>=8)&&(array.get(7) != null)) excel.setExcompanyAddress(array.get(7).toString().replace(" ",""));

                        //入库保存
                        if ((excel.getExcompanyName() != null) && (excel.getExcompanyName() != "")) {
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
                    return  JsonResponse.success("外委公司导入成功",String.valueOf(m_count)+","+String.valueOf(m_fail));
                else
                    return JsonResponse.fail(1009, "外委公司导入失败");
            }
            else
                return JsonResponse.fail(1009, "读入外委公司为null");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new  Exception("读入外委公司信息失败:"+e.getMessage());
        }
    }



    /**
     * 分页查找
     * @param request
     * @return
     */
    public PageResponse<ExcompanyBrief> page(String hospId,ExcompanyPageRequest request) throws Exception
    {
        try {
            request.setHospId(hospId);
            Page<ExcompanyBrief> page = excompanyMapper.page(request,new RowBounds(request.getIndex(), request.getSize()));
            List<Long> lst = excompanyMapper.Rcount(request);
            Long total = 0L;
            if ((lst!=null) && (lst.size()>0))
                total = lst.get(0);

            if ((page!=null)&&(page.size()>0))
            {
                for (ExcompanyBrief ec:page)
                {
                    ec.getLeader(userService);
                }
            }

            return new PageResponse<ExcompanyBrief>(page, request, total);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("外委公司分页查询失败："+e.getMessage());
        }
    }

    /**
     * 依据ID获取一个完整的外委公司对象
     * @param id
     * @return
     * @throws Exception
     */
    public Excompany getByID(String id) throws  Exception
    {
        Excompany result=null;

        if (excompanyMapper != null)
        {
            try
            {
                List<Excompany> lst = excompanyMapper.getbyid(id);
                if ((lst != null) && (lst.size()>0)) {
                    result = lst.get(0);
                    if (result != null) {
                        result.getLeader(userService);
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("以及ID:["+id+"]获取外委公司对象失败!");
            }
        }

        return result;
    }


    /**
     * 依据名称获取外委公司对象
     * @param excompanyName
     * @return
     * @throws Exception
     */
    public Excompany getByName(String hospId,String excompanyName) throws  Exception
    {
        Excompany result = null;

        if (excompanyMapper != null)
        {
            try {
                List<Excompany> lst = excompanyMapper.getbyName(hospId.trim(),excompanyName.trim());

                if ((lst != null)&&(lst.size()>0))
                    result = lst.get(0);

                if (result != null) {
                    result.getLeader(userService);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据名称获取外委公司失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 删除指定id的外委公司
     * @param id
     * @return
     */
    public boolean delete(String id) throws  Exception
    {
        boolean result = false ;

        if (excompanyMapper != null)
        {
            try
            {
                excompanyMapper.delete(id);
                result = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("删除外委公司对象["+id+"]异常!");
            }
        }

        return result ;
    }

    /**
     * 保存公司信息，如果不存在则新建，否则更新
     * @param request
     * @return
     */
    public Excompany save(String hospId,ExcompanySaveRequest request) throws Exception{

        Excompany excompany = null;
        String leaderId = null;

        if ((excompanyRepository != null) &&(request != null)){

            //外委公司存在性校验
            try {
                if ((request.getId() != null) && (request.getId().trim() != "")) excompany = getByID(request.getId());

                if (excompany == null) excompany = getByName(hospId,request.getExcompanyName());

                if (excompany != null) {
                    excompany.getLeader(userService);
                }

                if (excompany != null) leaderId = excompany.getExcompanyLeaderID();

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("校验外委公司存在性失败:"+e.getMessage());
            }

            if (excompany == null) {
                //构造新的外委公司对象
                excompany = Excompany
                        .builder()
                        .build();
            }

            try {
                //修改外委公司对象
                if (excompany != null)
                {
                    BeanUtils.copyBeanIgnoreNull(excompany, request);

                    if (ExcelUtils.isNullofString(request.getExcompanyLeaderID())) excompany.setExcompanyLeaderID(null);
                    if (ExcelUtils.isNullofString(request.getExcompanyLeadertel())) excompany.setExcompanyLeadertel(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("复制外委公司对象失败:"+e.getMessage());
            }

            //保存外委公司对象
            try {
                if (excompany != null) {
                    excompany.setHospID(hospId);
                    Excompany excompany_s = excompanyRepository.save(excompany);
                    excompany.setId(excompany_s.getId());

                    // 设置项目负责人角色
                    if ((excompany.getExcompanyLeaderID() != null) && (excompany.getExcompanyLeaderID().length()>0))
                    {
                        if ((!ExcelUtils.isNullofString(leaderId)) && (leaderId.compareTo(excompany.getExcompanyLeaderID()) != 0)){
                            if (userService != null) userService.DeleteUserRole(leaderId,"009");
                        }
                        if (userService != null) {
                            if (userService.getUserRoles(excompany.getExcompanyLeaderID(), "009") == null)
                                userService.SetUserRole(excompany.getExcompanyLeaderID(), "009");
                        }
                    }

                    if (ExcelUtils.isNullofString(excompany.getExcompanyLeaderID())) {
                        if (userService != null) userService.DeleteUserRole(leaderId,"009");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("保存外委公司对象失败:"+e.getMessage());
            }
        }

        return excompany;
    }

    /**
     * 依据Excel信息保存外委公司信息
     * @param request
     * @return
     * @throws Exception
     */
    private Excompany save(String hospId,ExcompanyInfoExcel request) throws Exception{

        Excompany excompany = null;
        String leaderId = null;

        if (excompanyRepository != null) {

            //外委公司存在性校验
            try {
                excompany = getByName(hospId, request.getExcompanyName());

                if (excompany != null) {
                    excompany.getLeader(userService);
                }

                if (excompany != null) leaderId = excompany.getExcompanyLeaderID();
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("校验外委公司存在性失败:"+e.getMessage());
            }

            if (excompany == null) {
                //构造新的外委公司对象
                excompany = Excompany
                        .builder()
                        .build();
            }
            try {
                //修改外委公司对象
                BeanUtils.copyBeanIgnoreNull(excompany, request);
                excompany.setExcompanyServicestartdate(ExcelUtils.StringToDate(request.getExcompanyServicestartdate(),"yyyyMMdd"));
                excompany.setExcompanyServiceenddate(ExcelUtils.StringToDate(request.getExcompanyServiceenddate(),"yyyyMMdd"));
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("复制外委公司对象失败:"+e.getMessage());
            }


            //保存外委公司对象
            try {
                excompany.setHospID(hospId);
                Excompany excompany_s = excompanyRepository.save(excompany);
                excompany.setId(excompany_s.getId());

                // 设置项目负责人角色
                if ((excompany.getExcompanyLeaderID() != null) && (excompany.getExcompanyLeaderID().length()>0))
                {
                    if ((!ExcelUtils.isNullofString(leaderId)) && (leaderId.compareTo(excompany.getExcompanyLeaderID()) != 0)){
                        if (userService != null) userService.DeleteUserRole(leaderId,"009");
                    }
                    if (userService != null) {
                        if (userService.getUserRoles(excompany.getExcompanyLeaderID(), "009") == null)
                            userService.SetUserRole(excompany.getExcompanyLeaderID(), "009");
                    }
                }

                if (ExcelUtils.isNullofString(excompany.getExcompanyLeaderID())) {
                    if (userService != null) userService.DeleteUserRole(leaderId,"009");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("保存外委公司对象失败:"+e.getMessage());
            }
        }

        return excompany;
    }

}
