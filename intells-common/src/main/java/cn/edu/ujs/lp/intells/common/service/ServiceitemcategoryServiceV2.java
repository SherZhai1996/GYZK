package cn.edu.ujs.lp.intells.common.service;

import cn.edu.ujs.lp.intells.common.dao.Serviceitem.ServiceitemcategoryMapper;
import cn.edu.ujs.lp.intells.common.dao.Serviceitem.ServiceitemcategoryRepository;
import cn.edu.ujs.lp.intells.common.entity.Serviceitem.Serviceitemcategory;
import cn.edu.ujs.lp.intells.common.entity.Serviceitem.ServiceitemcategoryBrief;
import cn.edu.ujs.lp.intells.common.entity.Serviceitem.ServiceitemcategoryInfoExcel;
import cn.edu.ujs.lp.intells.common.entity.User.User;
import cn.edu.ujs.lp.intells.common.entity.User.UserBrief;
import cn.edu.ujs.lp.intells.common.entity.User.UserInfoExcel;
import cn.edu.ujs.lp.intells.common.entity.User.UserRoleBrief;
import cn.edu.ujs.lp.intells.common.request.Serviceitem.ServiceitemcategoryPageRequest;
import cn.edu.ujs.lp.intells.common.request.Serviceitem.ServiceitemcategorySaveRequest;
import cn.edu.ujs.lp.intells.common.request.User.UserPageRequest;
import cn.edu.ujs.lp.intells.common.request.User.UserSaveRequest;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.utils.BeanUtils;
import cn.edu.ujs.lp.intells.common.utils.ExcelUtils;
import cn.edu.ujs.lp.intells.common.utils.OSUtils;
import com.alibaba.excel.metadata.BaseRowModel;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceitemcategoryServiceV2 {
    @Autowired
    private ServiceitemcategoryMapper serviceitemcategoryMapper;

    @Autowired
    private ServiceitemcategoryRepository serviceitemcategoryRepository;

    @Autowired
    private DataDictionaryService dataDictionaryService;

    @Autowired
    private SerialNumberService serialNumberService;

    /**
     * 导出Excel模板
     * @param response
     * @return
     * @throws Exception
     */
    public JsonResponse exportExcelTemplate(HttpServletResponse response) throws Exception{

        String templateName = "Serviceitemcategorytemplate";

         try {
         ExcelUtils.exportTemplate(ServiceitemcategoryInfoExcel.class, response, templateName);
         return JsonResponse.success("系统用户导入模板导出成功");
         }
         catch (Exception e)
         {
         e.printStackTrace();
         throw new Exception("系统用户导入模板导出失败:"+e.getMessage());
         }

        /**try
        {
            if (fileService != null) {
                fileService.downloadExcelFile(OSUtils.getUsertemplate(),response);
                return JsonResponse.success("系统用户导入模板导出成功");
            }
            else return JsonResponse.success("系统用户导入模板导出失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("系统用户导入模板导出失败:"+e.getMessage());
        }*/
    }

    /**
     * 导出Excel数据
     * @param response
     * @return
     * @throws Exception
     */
    public JsonResponse exportExcelData(HttpServletResponse response) throws Exception
    {
        String fileName = "ServiceitemcategoryData";

        try {
            ServiceitemcategoryPageRequest serviceitemcategoryPageRequest = new ServiceitemcategoryPageRequest();
            serviceitemcategoryPageRequest.setServiceitemcategoryName(null);
            List<ServiceitemcategoryBrief> elist = list(serviceitemcategoryPageRequest);

            List<BaseRowModel> ls = new ArrayList<BaseRowModel>();

            for (ServiceitemcategoryBrief e : elist) {
                UserInfoExcel ei = new UserInfoExcel();

                //复制
                BeanUtils.copyBeanIgnoreNull(ei, e);

                ls.add(ei);
            }

            //输出Excel
            ExcelUtils.exportData(UserInfoExcel.class, response, fileName, ls);
            return JsonResponse.success("服务事项分类数据导出成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("服务事项分类数据导出失败："+e.getMessage());
        }
    }

    /**
     * 从Excel导入数据
     * @param file
     * @return
     * @throws Exception
     */
    public JsonResponse ImportExcelData(String hospId, MultipartFile file) throws Exception
    {
        int m_count = 0,m_fail = 0; //入库记录数量和未入库数量

        try {
            StringBuilder sb = new StringBuilder();

            //读入Excel文件数据
            List<ArrayList> data = (List<ArrayList>) ExcelUtils.readData(file.getInputStream(), ServiceitemcategoryInfoExcel.class);

            // arraylist转info.excel对象
            if ((data != null) && (data.size() > 0)) {
                for (ArrayList array : data) {
                    try {
                        //从Excel表提取一行数据
                        ServiceitemcategoryInfoExcel excel = new ServiceitemcategoryInfoExcel();

                        if ((array.size() >= 1) && (array.get(0) != null)) excel.setServiceitemcategoryName(array.get(0).toString().replace(" ",""));
                        if ((array.size() >= 2) && (array.get(1) != null)) excel.setSuperiorName(array.get(1).toString().replace(" ",""));
                        if ((array.size() >= 3) && (array.get(2) != null)) excel.setServiceTypeName(array.get(2).toString().replace(" ",""));

                        //入库保存
                        if (excel.getServiceitemcategoryName() != null) {
                            save(excel);
                            m_count++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_fail++;
                    }
                }

                if (m_count > 0) {
                    return JsonResponse.success("服务事项分类数据导入成功", String.valueOf(m_count) + "," + String.valueOf(m_fail));
                } else
                    return JsonResponse.fail(1009, "服务事项分类数据导入失败");
            } else
                return JsonResponse.fail(1009, "读入服务事项分类数据为null");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new  Exception("读入服务事项分类数据信息失败:"+e.getMessage());
        }
    }

    /**
     *
     * 依据上级ID和服务事项分类名称判断某个服务事项分类是否存在
     * @param superiorId
     * @param serviceitemcategoryName
     * @return
     * @throws Exception
     */
    public Serviceitemcategory isExistofServiceitemcategoryName(String superiorId, String serviceitemcategoryName)  throws  Exception {
        Serviceitemcategory serviceitemcategory = null;

        try {

            serviceitemcategory = findByName(superiorId,serviceitemcategoryName);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("依据医院ID和用户名判断某个用户是否存在失败:" + e.getMessage());
        }

        return serviceitemcategory;
    }


    /**
     * 依据服务事项类别ID获取服务事项全名
     * @param Id
     * @return
     * @throws Exception
     */
    public String GetserviceitemcategoryFullnamebyID(String Id) throws Exception
    {
        String result = null;

        if ((serviceitemcategoryMapper != null) && (!ExcelUtils.isNullofString(Id))) {
            try {
                List<String> lst = serviceitemcategoryMapper.GetserviceitemcategoryFullnamebyID(Id.replace(" ", ""));

                if ((lst != null) && (lst.size() > 0)) result = lst.get(0);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据服务事项类别ID获取服务事项全名失败："+e.getMessage());
            }
        }

        // if (result == null) throw new Exception("依据服务事项类别ID获取服务事项全名失败");

        return result;
    }

    /**
     * 依据服务事项类别ID获取服务事项名称
     * @param Id
     * @return
     * @throws Exception
     */
    public String GetserviceitemcategoryNamebyID(String Id) throws Exception {
        String result = null;

        if ((serviceitemcategoryMapper != null) && (!ExcelUtils.isNullofString(Id))) {
            try {
                List<String> lst = serviceitemcategoryMapper.GetserviceitemcategoryNamebyID(Id.replace(" ", ""));

                if ((lst != null) && (lst.size() > 0)) result = lst.get(0);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据服务事项类别ID获取服务事项名称失败："+e.getMessage());
            }
        }

        //if (result == null) throw new Exception("依据服务事项类别ID获取服务事项名称失败：");

        return result;
    }


    /**
     * 分页查找
     * @param request
     * @return
     */
    public PageResponse<ServiceitemcategoryBrief> page(ServiceitemcategoryPageRequest request) throws Exception
    {
        try {
            //request.setHospId(hospId);
            Page<ServiceitemcategoryBrief> page = serviceitemcategoryMapper.page(request, new RowBounds(request.getIndex(), request.getSize()));
            List<Long> lst = serviceitemcategoryMapper.Rcount(request);
            Long total = 0L;
            if ((lst!=null) && (lst.size()>0)) total = lst.get(0);

            if (page != null)
            {
                for (ServiceitemcategoryBrief sb:page)
                {
                    sb.getSuperiorName(this);
                    sb.getServiceTypeName(dataDictionaryService);
                }
            }
            return new PageResponse<>(page, request, total);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("系统用户分页查询失败："+e.getMessage());
        }
    }

    /**
     * 获取系统用户列表
     * @param request
     * @return
     * @throws Exception
     */
    public List<ServiceitemcategoryBrief> list(ServiceitemcategoryPageRequest request) throws Exception
    {
        List<ServiceitemcategoryBrief> result = null;

        try {
            //request.setHospId(hospId);
            result = serviceitemcategoryMapper.list(request);

            if (result != null)
            {
                for (ServiceitemcategoryBrief sb:result)
                {
                    sb.getSuperiorName(this);
                    sb.getServiceTypeName(dataDictionaryService);
                }
            }
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("获取系统用户列表失败："+e.getMessage());
        }
    }

    /**
     * 依据ID获取一个完整的系统用户对象
     * @param id
     * @return
     * @throws Exception
     */
    public Serviceitemcategory getByID(String id) throws  Exception
    {
        Serviceitemcategory result=null;

        if (serviceitemcategoryMapper != null)
        {
            try
            {
                List<Serviceitemcategory> lst = serviceitemcategoryMapper.getbyid(id);
                if ((lst != null) && (lst.size()>0))
                    result = lst.get(0);

                if (result != null)
                {
                    result.getSuperiorName(this);
                    result.getServiceTypeName(dataDictionaryService);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据ID:["+id+"]获取系统用户对象失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 删除指定ID的系统用户对象
     * @param serviceitemcategorycode
     * @return
     */
    public boolean delete(String serviceitemcategorycode) throws  Exception
    {
        boolean result = false ;

        if (serviceitemcategoryMapper != null)
        {
            try
            {
                serviceitemcategoryMapper.deletebyCode(serviceitemcategorycode);
                result = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("删除系统用户对象["+serviceitemcategorycode+"]异常:"+e.getMessage());
            }
        }

        return result ;
    }


    /**
     * 依据服务事项分类上级节点id和名称获取服务事项分类对象
     * @param superiorId
     * @param serviceitemcategoryName
     * @return
     * @throws Exception
     */
    public Serviceitemcategory findByName(String superiorId,String serviceitemcategoryName) throws  Exception {
        Serviceitemcategory serviceitemcategory = null;

        if ((serviceitemcategoryMapper != null)&&(!ExcelUtils.isNullofString(serviceitemcategoryName))) {

            try {

                List<Serviceitemcategory> ls = serviceitemcategoryMapper.findByName(superiorId,serviceitemcategoryName);
                if ((ls != null) && (ls.size() > 0)) serviceitemcategory = ls.get(0);

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("依据服务事项分类上级节点id和名称获取服务事项分类对象失败:" + e.getMessage());
            }
        }

        return serviceitemcategory;
    }

    /**
     * 依据服务事项分类全名获取服务事项分类对象
     * @param serviceitemcategoryFullname
     * @return
     * @throws Exception
     */
    public Serviceitemcategory findByFullname(String serviceitemcategoryFullname) throws  Exception {
        Serviceitemcategory serviceitemcategory = null;

        if ((serviceitemcategoryMapper != null)&&(!ExcelUtils.isNullofString(serviceitemcategoryFullname))) {

            try {

                List<Serviceitemcategory> ls = serviceitemcategoryMapper.findByFullname(serviceitemcategoryFullname);
                if ((ls != null) && (ls.size() > 0)) serviceitemcategory = ls.get(0);

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("依据服务事项分类全名获取服务事项分类对象失败:" + e.getMessage());
            }
        }

        return serviceitemcategory;
    }

    /**
     * 保存服务事项类别信息，如果不存在则新建，否则更新
     * @param request
     * @return
     */
    public Serviceitemcategory save(ServiceitemcategorySaveRequest request) throws Exception{

        Serviceitemcategory serviceitemcategory = null;
        boolean isExist = false ;

        if ((serviceitemcategoryMapper!= null) && (serviceitemcategoryRepository != null) && (request != null)) {

            if (request.getId() != null) serviceitemcategory = getByID(request.getId());

            if (serviceitemcategory == null) {
                //系统用户存在性校验
                serviceitemcategory = isExistofServiceitemcategoryName(request.getSuperiorId(), request.getServiceitemcategoryName());
            }

            if (serviceitemcategory == null) {
                //构造新的系统用户对象
                serviceitemcategory = Serviceitemcategory
                        .builder()
                        .build();
            } else isExist = true;

            try {
                BeanUtils.copyBeanIgnoreNull(serviceitemcategory,request);

                if (request.getSuperiorId() == null) serviceitemcategory.setSuperiorId(null);
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new Exception("复制服务事项类别对象失败:" + e.getMessage());
            }

            //保存系统用户对象
            try {
                Serviceitemcategory serviceitemcategory_s = save(serviceitemcategory,isExist);
                serviceitemcategory.setId(serviceitemcategory_s.getId());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("保存服务事项类别失败:"+e.getMessage());
            }
        }

        return serviceitemcategory;
    }

    /**
     * 保存系统用户
     * @param request
     * @return
     * @throws Exception
     */
    private Serviceitemcategory save(ServiceitemcategoryInfoExcel request) throws Exception{

        Serviceitemcategory serviceitemcategory = null;
        Serviceitemcategory st = null; //父节点
        boolean isExist = false ;

        if ((serviceitemcategoryMapper != null) && (serviceitemcategoryRepository != null)) {

            if (!ExcelUtils.isNullofString(request.getSuperiorName())) {
                st = findByFullname(request.getSuperiorName());

                if (st != null) {
                    serviceitemcategory = isExistofServiceitemcategoryName(st.getSuperiorId(),request.getServiceitemcategoryName());
                }
            }

            if (serviceitemcategory == null)
                serviceitemcategory = isExistofServiceitemcategoryName(null,request.getServiceitemcategoryName());

            try {
                if (serviceitemcategory == null) {
                    //构造新的系统用户对象
                    serviceitemcategory = Serviceitemcategory
                            .builder()
                            .build();
                } else isExist = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("创建服务事项分类对象失败："+e.getMessage());
            }

            if (serviceitemcategory != null) {

                try {
                    BeanUtils.copyBeanIgnoreNull(serviceitemcategory,request);

                    if (st != null)
                        serviceitemcategory.setSuperiorId(st.getSuperiorId());
                    else
                        serviceitemcategory.setSuperiorId(null);

                    //特殊字段的处理
                    if (!ExcelUtils.isNullofString(request.getServiceTypeName()))
                        serviceitemcategory.setServiceType(request.getServiceTypeName(),dataDictionaryService);

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("复制系统用户对象失败:"+e.getMessage());
                }

                //保存系统用户对象
                try {
                    Serviceitemcategory serviceitemcategory_s = save(serviceitemcategory,isExist);
                    serviceitemcategory.setId(serviceitemcategory_s.getId());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    throw new Exception("保存服务事项类别失败:"+e.getMessage());
                }
            }
        }

        return serviceitemcategory;
    }


    /**
     * 保存服务事项分类对象
     * @param value
     * @param isexist
     * @return
     * @throws Exception
     */
    private Serviceitemcategory save(Serviceitemcategory value,boolean isexist) throws Exception
    {
        Serviceitemcategory rt = null;

        try {
            if ((value != null) && (serviceitemcategoryRepository != null) && (serialNumberService != null))
            {
                //新建对象，产生编码
                if (isexist == false) {
                    String superCode = null;
                    if (!ExcelUtils.isNullofString(value.getSuperiorId())) {
                        Serviceitemcategory st = getByID(value.getSuperiorId());

                        if (st != null) {
                            value.setServiceitemcategoryFullname(st.getServiceitemcategoryFullname() + "->" + value.getServiceitemcategoryName());
                            value.setServiceitemcategoryLevel(st.getServiceitemcategoryLevel() + 1);
                            superCode = st.getServiceitemcategoryCode();
                        }
                    } else {
                        value.setServiceitemcategoryLevel(1);
                        value.setServiceitemcategoryFullname(value.getServiceitemcategoryName());
                    }

                    value.setServiceitemcategoryCode(serialNumberService.getSTCode(null, superCode));
                } else {
                    if (!ExcelUtils.isNullofString(value.getSuperiorId())) {
                        Serviceitemcategory st = getByID(value.getSuperiorId());

                        if (st != null) {
                            value.setServiceitemcategoryFullname(st.getServiceitemcategoryFullname() + "->" + value.getServiceitemcategoryName());
                        }
                    } else {
                        value.setServiceitemcategoryFullname(value.getServiceitemcategoryName());
                    }
                }

                rt = serviceitemcategoryRepository.save(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("保存服务事项分类对象失败:" + e.getMessage());
        }

        return rt;
    }
}
