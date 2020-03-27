package cn.edu.ujs.lp.intells.common.service;

import cn.edu.ujs.lp.intells.common.dao.Serviceitem.ServicebillMapper;
import cn.edu.ujs.lp.intells.common.dao.Serviceitem.ServicebillRepository;
import cn.edu.ujs.lp.intells.common.entity.Serviceitem.*;
import cn.edu.ujs.lp.intells.common.entity.User.UserInfoExcel;
import cn.edu.ujs.lp.intells.common.request.Serviceitem.ServicebillPageRequest;
import cn.edu.ujs.lp.intells.common.request.Serviceitem.ServicebillSaveRequest;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.utils.BeanUtils;
import cn.edu.ujs.lp.intells.common.utils.ExcelUtils;
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
public class ServicebillServiceV2 {
    @Autowired
    private DataDictionaryService dataDictionaryService;

    @Autowired
    private ServiceitemcategoryServiceV2 serviceitemcategoryServiceV2;

    @Autowired
    private ServicebillMapper servicebillMapper;

    @Autowired
    private ServicebillRepository servicebillRepository;

    /**
     * 导出Excel模板
     * @param response
     * @return
     * @throws Exception
     */
    public JsonResponse exportExcelTemplate(HttpServletResponse response) throws Exception{

        String templateName = "Servicebilltemplate";

        try {
            ExcelUtils.exportTemplate(ServicebillInfoExcel.class, response, templateName);
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
        String fileName = "ServicebillData";

        try {
            ServicebillPageRequest servicebillPageRequest = new ServicebillPageRequest();
            servicebillPageRequest.setCategoryId(null);
            servicebillPageRequest.setServiceitemName(null);
            List<ServicebillBrief> elist = list(servicebillPageRequest);

            List<BaseRowModel> ls = new ArrayList<BaseRowModel>();

            for (ServicebillBrief e : elist) {
                UserInfoExcel ei = new UserInfoExcel();

                //复制
                BeanUtils.copyBeanIgnoreNull(ei, e);

                ls.add(ei);
            }

            //输出Excel
            ExcelUtils.exportData(UserInfoExcel.class, response, fileName, ls);
            return JsonResponse.success("服务事项明细数据导出成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("服务事项明细数据导出失败："+e.getMessage());
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
            List<ArrayList> data = (List<ArrayList>) ExcelUtils.readData(file.getInputStream(), ServicebillInfoExcel.class);

            // arraylist转info.excel对象
            if ((data != null) && (data.size() > 0)) {
                for (ArrayList array : data) {
                    try {
                        //从Excel表提取一行数据
                        ServicebillInfoExcel excel = new ServicebillInfoExcel();

                        if ((array.size() >= 1) && (array.get(0) != null)) excel.setServiceitemName(array.get(0).toString().replace(" ",""));
                        if ((array.size() >= 2) && (array.get(1) != null)) excel.setCategoryName(array.get(1).toString().replace(" ",""));
                        if ((array.size() >= 3) && (array.get(2) != null)) excel.setServiceTypeName(array.get(2).toString().replace(" ",""));

                        //入库保存
                        if (excel.getServiceitemName() != null) {
                            save(excel);
                            m_count++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_fail++;
                    }
                }

                if (m_count > 0) {
                    return JsonResponse.success("服务事项明细数据导入成功", String.valueOf(m_count) + "," + String.valueOf(m_fail));
                } else
                    return JsonResponse.fail(1009, "服务事项明细数据导入失败");
            } else
                return JsonResponse.fail(1009, "读入服务事项明细数据为null");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new  Exception("读入服务事项明细数据信息失败:"+e.getMessage());
        }
    }

    /**
     *
     * 依据服务事项分类ID和服务事项明细名称判断其是否存在
     * @param categoryId
     * @param servicebillName
     * @return
     * @throws Exception
     */
    public Servicebill isExistofServicebillName(String categoryId, String servicebillName)  throws  Exception {
        Servicebill servicebill = null;

        try {
            servicebill = findByName(categoryId,servicebillName);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("依据服务事项分类ID和服务事项明细名称判断其是否存在失败:" + e.getMessage());
        }

        return servicebill;
    }


    /**
     * 分页查找
     * @param request
     * @return
     */
    public PageResponse<ServicebillBrief> page(ServicebillPageRequest request) throws Exception
    {
        try {
            if ((servicebillMapper != null) && (dataDictionaryService != null)) {
                Page<ServicebillBrief> page = servicebillMapper.page(request, new RowBounds(request.getIndex(), request.getSize()));
                List<Long> lst = servicebillMapper.Rcount(request);
                Long total = 0L;
                if ((lst != null) && (lst.size() > 0)) total = lst.get(0);

                if (page != null) {
                    for (ServicebillBrief sb : page) {
                        sb.getServiceTypeName(dataDictionaryService);
                    }
                }
                return new PageResponse<>(page, request, total);
            }
            else
            {
                throw new Exception("服务事项明细分页查询失败：查询服务启动失败");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("服务事项明细分页查询失败："+e.getMessage());
        }
    }

    /**
     * 获取服务事项明细列表
     * @param request
     * @return
     * @throws Exception
     */
    public List<ServicebillBrief> list(ServicebillPageRequest request) throws Exception
    {
        List<ServicebillBrief> result = null;

        try {
            if ((servicebillMapper != null) && (dataDictionaryService != null)) {
                result = servicebillMapper.list(request);

                if (result != null) {
                    for (ServicebillBrief sb : result) {
                        sb.getServiceTypeName(dataDictionaryService);
                    }
                }
                return result;
            } else {
                throw new Exception("获取服务事项明细列表失败：查询服务启动失败");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("获取服务事项明细列表失败："+e.getMessage());
        }
    }

    /**
     * 依据ID获取一个完整的服务事项明细
     * @param id
     * @return
     * @throws Exception
     */
    public Servicebill getByID(String id) throws  Exception
    {
        Servicebill result=null;

        if (servicebillMapper != null)
        {
            try
            {
                List<Servicebill> lst = servicebillMapper.getbyid(id);
                if ((lst != null) && (lst.size()>0))
                    result = lst.get(0);

                if (result != null)
                {
                    result.getServiceTypeName(dataDictionaryService);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据ID:["+id+"]获取完整的服务事项明细对象失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 删除指定ID的系统用户对象
     * @param id
     * @return
     */
    public boolean delete(String id) throws  Exception
    {
        boolean result = false ;

        if (servicebillMapper != null)
        {
            try
            {
                servicebillMapper.delete(id);
                result = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("删除服务事项明细对象["+id+"]异常:"+e.getMessage());
            }
        }

        return result ;
    }


    /**
     * 依据服务事项分类id和名称获取服务事项明细对象
     * @param categoryId
     * @param serviceitemName
     * @return
     * @throws Exception
     */
    public Servicebill findByName(String categoryId, String serviceitemName) throws  Exception {
        Servicebill servicebill = null;

        if ((servicebillMapper != null)&&(!ExcelUtils.isNullofString(categoryId))&&(!ExcelUtils.isNullofString(serviceitemName))) {

            try {

                List<Servicebill> ls = servicebillMapper.findByName(categoryId,serviceitemName);
                if ((ls != null) && (ls.size() > 0)) servicebill = ls.get(0);

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("依据服务事项分类id和名称获取服务事项明细对象失败:" + e.getMessage());
            }
        }

        return servicebill;
    }


    /**
     * 保存服务事项类别信息，如果不存在则新建，否则更新
     * @param request
     * @return
     */
    public Servicebill save(ServicebillSaveRequest request) throws Exception{

        Servicebill servicebill = null;
        boolean isExist = false ;

        if ((servicebillMapper!= null) && (servicebillRepository != null) && (request != null)) {

            if (!ExcelUtils.isNullofString(request.getId())) servicebill = getByID(request.getId());

            if ((servicebill == null) && (!ExcelUtils.isNullofString(request.getCategoryId()))){
                //系统用户存在性校验
                servicebill = isExistofServicebillName(request.getCategoryId(), request.getServiceitemName());
            }

            if (servicebill == null) {
                //构造新的系统用户对象
                servicebill = Servicebill
                        .builder()
                        .build();
            } else isExist = true;

            try {
                BeanUtils.copyBeanIgnoreNull(servicebill,request);

                if (request.getCategoryId() == null) servicebill.setCategoryId(null);
                if (request.getServiceTypeId() == null) servicebill.setServiceTypeId(null);
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new Exception("复制服务事项明细对象失败:" + e.getMessage());
            }

            //保存服务事项明细
            try {
                Servicebill servicebill_s = save(servicebill,isExist);
                servicebill.setId(servicebill_s.getId());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("保存服务事项明细失败:"+e.getMessage());
            }
        }

        return servicebill;
    }

    /**
     * 保存系统用户
     * @param request
     * @return
     * @throws Exception
     */
    private Servicebill save(ServicebillInfoExcel request) throws Exception{

        Servicebill servicebill = null;
        Serviceitemcategory serviceitemcategory = null;
        boolean isExist = false ;

        if ((servicebillMapper != null) && (servicebillRepository != null) && (serviceitemcategoryServiceV2 != null)) {

            if (!ExcelUtils.isNullofString(request.getCategoryName())) {
                serviceitemcategory = serviceitemcategoryServiceV2.findByFullname(request.getCategoryName());

                if (serviceitemcategory != null) {
                    servicebill = isExistofServicebillName(serviceitemcategory.getId(),request.getServiceitemName());
                }
            }

            try {
                if (servicebill == null) {
                    //构造新的服务事项明细对象
                    servicebill = Servicebill
                            .builder()
                            .build();
                } else isExist = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("创建服务事项木明细对象失败："+e.getMessage());
            }

            if (servicebill != null) {

                try {
                    BeanUtils.copyBeanIgnoreNull(servicebill,request);

                    if (serviceitemcategory != null)
                        servicebill.setCategoryId(serviceitemcategory.getId());
                    else
                        servicebill.setCategoryId(null);

                    //特殊字段的处理
                    if (!ExcelUtils.isNullofString(request.getServiceTypeName()))
                        servicebill.setServiceType(request.getServiceTypeName(),dataDictionaryService);

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("复制服务事项明细对象失败:"+e.getMessage());
                }

                //保存系统用户对象
                try {
                    Servicebill servicebill_s = save(servicebill,isExist);
                    servicebill.setId(servicebill_s.getId());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    throw new Exception("保存服务事项明细失败:"+e.getMessage());
                }
            }
        }

        return servicebill;
    }


    /**
     * 保存服务事项分类对象
     * @param value
     * @param isexist
     * @return
     * @throws Exception
     */
    private Servicebill save(Servicebill value,boolean isexist) throws Exception
    {
        Servicebill rt = null;

        try {
            if ((value != null) && (servicebillRepository != null))
            {
                rt = servicebillRepository.save(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("保存服务事项明细对象失败:" + e.getMessage());
        }

        return rt;
    }

}
