package cn.edu.ujs.lp.intells.basicinformation.service;

import cn.edu.ujs.lp.intells.basicinformation.dao.Device.DevicecategoryMapper;
import cn.edu.ujs.lp.intells.basicinformation.dao.Device.DevicecategoryRepository;
import cn.edu.ujs.lp.intells.common.entity.Common.TreeInterface;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import cn.edu.ujs.lp.intells.common.service.FileService;
import cn.edu.ujs.lp.intells.common.service.SerialNumberService;
import cn.edu.ujs.lp.intells.common.utils.BeanUtils;
import cn.edu.ujs.lp.intells.common.utils.ExcelUtils;
import cn.edu.ujs.lp.intells.common.utils.OSUtils;
import cn.edu.ujs.lp.intells.common.utils.TreeUtil;
import cn.edu.ujs.lp.intells.basicinformation.entity.Device.Devicecategory;
import cn.edu.ujs.lp.intells.common.entity.Device.DevicecategoryBrief;
import cn.edu.ujs.lp.intells.basicinformation.entity.Device.DevicecategoryInfoExcel;
import cn.edu.ujs.lp.intells.basicinformation.request.Device.DevicecategoryPageRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.Device.DevicecategorySaveRequest;
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
public class DevicecategoryService {
    @Autowired
    private DevicecategoryMapper devicecategoryMapper;

    @Autowired
    private DevicecategoryRepository devicecategoryRepository;

    @Autowired
    private DataDictionaryService dataDictionaryService;

    @Autowired
    private DBCommonService dbCommonService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SerialNumberService serialNumberService;


    /**
     * 导出Excel模板
     * @param response
     * @return
     * @throws Exception
     */
    public JsonResponse exportExcelTemplate(HttpServletResponse response) throws Exception{

        /**String templateName = "Devicecategorytemplate";

        try {
            ExcelUtils.exportTemplate(DevicecategoryInfoExcel.class, response, templateName);
            return JsonResponse.success("设备运维分类导入模板导出成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("设备运维分类导入模板导出失败:"+e.getMessage());
        }*/
        try
        {
            if (fileService != null) {
                fileService.downloadExcelFile(OSUtils.getDevicecategorytemplatefile(),response);
                return JsonResponse.success("设备分类导入模板导出成功");
            }
            else return JsonResponse.success("设备分类导入模板导出失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("设备分类导入模板导出失败:"+e.getMessage());
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
        String fileName = "DevicecategoryData";

        try {
            List<DevicecategoryBrief> elist = list(hospId,null);

            List<BaseRowModel> ls = new ArrayList<BaseRowModel>();
            for (DevicecategoryBrief e:elist) {
                DevicecategoryInfoExcel ei = new DevicecategoryInfoExcel();

                //复制
                ei.setCategoryName(e.getCategoryName());
                ei.setSuperiorName(e.getSuperiorName(dbCommonService));
                ei.setIsUsing(e.getIsUsingName());
                ei.setManageAffiliationname(e.getManageAffiliationName(dbCommonService)); //补充
                ei.setResponAffiliationname(e.getResponAffiliationName(dbCommonService));

                ls.add(ei);
            }

            ExcelUtils.exportData(DevicecategoryInfoExcel.class, response, fileName,ls);
            return JsonResponse.success("设备运维分类数据导出成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("设备运维分类数据导出失败："+e.getMessage());
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
            List<ArrayList> data = (List<ArrayList>) ExcelUtils.readData(file.getInputStream(), DevicecategoryInfoExcel.class);

            // arraylist转info.excel对象
            if ((data != null) && (data.size() > 0)) { for (ArrayList array:data) {
                try {
                    //从Excel表提取一行数据
                    DevicecategoryInfoExcel excel = new DevicecategoryInfoExcel();
                    if ((array.size()>=1) &&(array.get(0) != null)) excel.setCategoryName(array.get(0).toString().replace(" ",""));
                    if ((array.size()>=2) &&(array.get(1) != null))  excel.setSuperiorName(array.get(1).toString().replace(" ",""));
                    if ((array.size()>=3) &&(array.get(2) != null))  excel.setIsUsing(array.get(2).toString().replace(" ",""));
                    if ((array.size()>=4) &&(array.get(3) != null))  excel.setManageAffiliationname(array.get(3).toString().replace(" ",""));
                    if ((array.size()>=5) &&(array.get(4) != null))  excel.setResponAffiliationname(array.get(4).toString().replace(" ",""));

                    //入库保存
                    if ((excel.getCategoryName() != null) && (excel.getCategoryName() != "")) {
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
                    return  JsonResponse.success("设备运维分类导入成功",String.valueOf(m_count)+","+String.valueOf(m_fail));
                else
                    return JsonResponse.fail(1009, "设备运维分类导入失败");
            }
            else
                return JsonResponse.fail(1009, "读入设备运维分类为null");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new  Exception("读入设备运维分类信息失败:"+e.getMessage());
        }
    }

    /**
     * 查询设备运维分类，参数可以为null
     * @param categoryName
     * @return
     * @throws Exception
     */
    public List<DevicecategoryBrief> list(String hospId,String categoryName) throws Exception {
        List<DevicecategoryBrief> result= null;

        if (dbCommonService != null) {
            try {
                result = dbCommonService.getDevicecategories(hospId,(categoryName != null?categoryName.trim():null));

                /**if ((result != null)&&(result.size()>0))
                {
                    for (DevicecategoryBrief dcf:result)
                    {
                        dcf.getIsUsingName();
                        dcf.getSuperiorName(dbCommonService);
                        dcf.getManageAffiliationName(dbCommonService);
                        dcf.getResponAffiliationName(dbCommonService);
                    }
                }*/
            } catch (Exception e) {
                throw new Exception("查询设备运维分类失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 获取设备运维分类的树形结构
     * @param hospId
     * @param categoryName
     * @return
     * @throws Exception
     */
    public List<Object> getDevicecategoryTreeStr(String hospId,String categoryName) throws Exception {
        List<Object> list = null;

        try {
            if (dbCommonService != null) {
                list = dbCommonService.getDevicecategoryTreeStr(hospId, (categoryName != null?categoryName.trim():null));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("获取设备运维分类树形结构失败：" + e.getMessage());
        }

        return list;
    }


    /**
     * 分页查找
     * @param request
     * @return
     */
    public PageResponse<DevicecategoryBrief> page(String hospId, DevicecategoryPageRequest request) throws Exception
    {
        try {
            request.setHospId(hospId);
            Page<DevicecategoryBrief> page = devicecategoryMapper.page(request, new RowBounds(request.getIndex(), request.getSize()));
            List<Long> lst = devicecategoryMapper.Rcount(request);
            Long total = (long)(page!=null?page.size():0);
            if ((lst!=null) && (lst.size()>0))
                total = lst.get(0);

            if ((page != null)&&(page.size()>0))
            {
                for (DevicecategoryBrief dcf:page)
                {
                    dcf.getIsUsingName();
                    dcf.getSuperiorName(dbCommonService);
                    dcf.getManageAffiliationName(dbCommonService);
                    dcf.getResponAffiliationName(dbCommonService);
                }
            }
            return new PageResponse<DevicecategoryBrief>(page, request, total);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("设备运维分类分页查询失败："+e.getMessage());
        }
    }

    /**
     * 依据ID获取一个完整的设备运维分类对象
     * @param id
     * @return
     * @throws Exception
     */
    public Devicecategory getByID(String id) throws  Exception
    {
        Devicecategory result=null;

        if ((devicecategoryMapper != null)&&(id != null))
        {
            try
            {
                List<Devicecategory> lst =devicecategoryMapper.getbyid(id);
                if ((lst != null) && (lst.size()>0)) {
                    result = lst.get(0);

                    if (result != null) {
                        result.getIsUsingName();
                        result.getSuperiorName(dbCommonService);
                        result.getManageAffiliationName(dbCommonService);
                        result.getResponAffiliationName(dbCommonService);
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据ID:["+id+"]获取设备运维分类对象失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 删除指定id的设备运维分类对象
     * @param id
     * @return
     */
    public boolean delete(String id) throws  Exception
    {
        boolean result = false ;

        if ((devicecategoryMapper != null) && (dbCommonService != null)&&(id != null))
        {
            try
            {
                String devacicategoryCode = dbCommonService.getDevicecategoryCodebyId(id);
                devicecategoryMapper.deletebyCode(devacicategoryCode);
                result = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("删除设备运维分类对象["+id+"]异常:"+e.getMessage());
            }
        }

        return result ;
    }

    /**
     * 保存设备运维分类信息，如果不存在则新建，否则更新
     * @param request
     * @return
     */
    public Devicecategory save(String hospId, DevicecategorySaveRequest request) throws Exception{

        Devicecategory devicecategory = null;
        boolean isexist = false ;

        if ((devicecategoryRepository != null) && (request != null)) {

            //设备运维分类名称存在性校验
            try {
                if ((request.getId() != null) && (request.getId().trim() != ""))
                    devicecategory = getByID(request.getId());

                if (devicecategory == null) {
                    List<Devicecategory> lst = devicecategoryMapper.findByName(hospId, request.getSuperiorId(), request.getCategoryName());

                    if ((lst != null) && (lst.size() > 0))
                        devicecategory = lst.get(0);

                    if (devicecategory != null) {
                        devicecategory.getIsUsingName();
                        devicecategory.getSuperiorName(dbCommonService);
                        devicecategory.getManageAffiliationName(dbCommonService);
                        devicecategory.getResponAffiliationName(dbCommonService);
                    }
                }
            }catch (Exception e)
            {
                e.printStackTrace();
                throw  new Exception("设备运维分类名称存在性校验失败："+e.getMessage());
            }

            if (devicecategory == null) {
                //构造新的设备运维分类对象
                devicecategory = Devicecategory
                        .builder()
                        .build();
            }
            else
                isexist=true;

            if (devicecategory != null) {
                try {
                    BeanUtils.copyBeanIgnoreNull(devicecategory,request);
                    devicecategory.setIsUsing(request.getIsUsing());
                    devicecategory.setCategoryName(request.getCategoryName());
                    devicecategory.setManageAffiliation(request.getManageAffiliation());
                    devicecategory.setResponAffiliation(request.getResponAffiliation());
                    devicecategory.setSuperiorId(request.getSuperiorId());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    throw  new Exception("复制设备运维分类对象失败:"+e.getMessage());
                }
            }

            //保存设备运维分类对象
            try {
                devicecategory.setHospID(hospId);
                devicecategory = generateCode(hospId,devicecategory,isexist);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("保存设备运维分类对象失败:" + e.getMessage());
            }
        }

        return devicecategory;
    }

    /**
     * 依据Excel信息保存设备运维分类信息
     * @param request
     * @return
     * @throws Exception
     */
    private Devicecategory save(String hospId,DevicecategoryInfoExcel request) throws Exception{

        Devicecategory devicecategory = null;
        boolean isexist = false ;
        String superID = null;

        if (devicecategoryRepository != null) {

            //设备运维分类存在性校验
            try {
                if ((request.getSuperiorName() != null)&&(request.getSuperiorName() != ""))
                    superID = dbCommonService.getDevicecategoryIdbyFullname(hospId,request.getSuperiorName());

                List<Devicecategory> lst = devicecategoryMapper.findByName(hospId,superID,request.getCategoryName());

                if ((lst != null) && (lst.size()>0))
                    devicecategory = lst.get(0);

                if (devicecategory != null) {
                    devicecategory.getIsUsingName();
                    devicecategory.getSuperiorName(dbCommonService);
                    devicecategory.getManageAffiliationName(dbCommonService);
                    devicecategory.getResponAffiliationName(dbCommonService);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e.getMessage());
            }

            try {
                if (devicecategory == null) {
                    //构造新的设备运维分类对象
                    devicecategory = Devicecategory
                            .builder()
                            .build();
                }
                else
                    isexist=true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("创建设备运维分类对象失败："+e.getMessage());
            }

            if (devicecategory != null) {

                try {
                    BeanUtils.copyBeanIgnoreNull(devicecategory,request);
                    copyExcelInfotoDevicecategory(hospId,request,devicecategory);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("复制设备运维分类对象失败！");
                }

                //保存设备运维分类对象
                try {
                    devicecategory.setHospID(hospId);
                    devicecategory.setSuperiorId(superID);
                    devicecategory = generateCode(hospId,devicecategory,isexist);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("保存设备运维分类对象失败:" + e.getMessage());
                }
            }
        }

        return devicecategory;
    }

    /**
     * 设备运维分类对象编码并保存
     * @param hospId
     * @param value
     * @return
     * @throws Exception
     */
    private Devicecategory generateCode(String hospId,Devicecategory value,boolean isexist) throws Exception
    {
        Devicecategory rt = null;

        try {
            if ((value != null) && (hospId != null))
            {
                value.setHospID(hospId);

                //新建对象，产生编码
                if (isexist == false) {
                    String superCode = null;
                    if ((value.getSuperiorId() != null) && (value.getSuperiorId().trim() != "")) {
                        Devicecategory st = getByID(value.getSuperiorId());

                        if (st != null) {
                            value.setCategoryFullname(st.getCategoryFullname() + "->" + value.getCategoryName());
                            value.setDevicecategoryLevel(st.getDevicecategoryLevel() + 1);
                            superCode = st.getCategoryCode();
                        }
                    } else {
                        value.setDevicecategoryLevel(1);
                        value.setCategoryFullname(value.getCategoryName());
                    }

                    value.setCategoryCode(serialNumberService.getSBcategoryCode(hospId, superCode));
                } else {
                    if ((value.getSuperiorId() != null) && (value.getSuperiorId().trim() != "")) {
                        Devicecategory st = getByID(value.getSuperiorId());

                        if (st != null) {
                            value.setCategoryFullname(st.getCategoryFullname() + "->" + value.getCategoryName());
                        }
                    } else {
                        value.setCategoryFullname(value.getCategoryName());
                    }
                }

                value.setHospID(hospId);

                if (value.getIsUsing() == null) value.setIsUsing("0");

                rt = devicecategoryRepository.save(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("保存设备运维分类对象失败:" + e.getMessage());
        }

        return rt;
    }

    /**
     * 对象复制，将Excel对象复制到Devicecategory对象中
     * @param hospId
     * @param srt
     * @param dst
     * @throws Exception
     */
    private void copyExcelInfotoDevicecategory(String hospId,DevicecategoryInfoExcel srt,Devicecategory dst) throws Exception
    {
        try {
            dst.setHospID(hospId);

            if ((srt.getIsUsing() != null) && (srt.getIsUsing().trim().compareTo("在用") == 0)) {
                dst.setIsUsing("1");
            } else dst.setIsUsing("0");

            if ((srt.getManageAffiliationname() != null) && (srt.getManageAffiliationname() != ""))
                dst.setManageAffiliationFromName(srt.getManageAffiliationname(),dbCommonService);

            if ((srt.getResponAffiliationname() != null) && (srt.getResponAffiliationname().trim() != "")) {
                dst.setResponAffiliationFromName(srt.getResponAffiliationname(),dbCommonService);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("将Excel对象复制到设备运维分类对象失败："+e.getMessage());
        }
    }

}
