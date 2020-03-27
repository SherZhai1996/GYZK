package cn.edu.ujs.lp.intells.systemmanagement.service;

import cn.edu.ujs.lp.intells.common.entity.Device.IbeaconBrief;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.utils.BeanUtils;
import cn.edu.ujs.lp.intells.common.utils.ExcelUtils;
import cn.edu.ujs.lp.intells.systemmanagement.dao.IbeaconMapper;
import cn.edu.ujs.lp.intells.systemmanagement.dao.IbeaconRepository;
import cn.edu.ujs.lp.intells.systemmanagement.entity.Ibeacon;
import cn.edu.ujs.lp.intells.systemmanagement.entity.IbeaconInfoExcel;
import cn.edu.ujs.lp.intells.systemmanagement.request.IbeaconPageRequest;
import cn.edu.ujs.lp.intells.systemmanagement.request.IbeaconSaveRequest;
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
public class IbeaconService {

    @Autowired
    private IbeaconMapper ibeaconMapper;

    @Autowired
    private IbeaconRepository ibeaconRepository;

    @Autowired
    private DBCommonService dbCommonService;

    /**
     * 导出Excel模板
     * @param response
     * @return
     * @throws Exception
     */
    public JsonResponse exportExcelTemplate(HttpServletResponse response) throws Exception{

        String templateName = "Ibeacontemplate";

        try {
            ExcelUtils.exportTemplate(IbeaconInfoExcel.class, response, templateName);
            return JsonResponse.success("蓝牙信标导入模板导出成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("蓝牙信标导入模板导出失败:"+e.getMessage());
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
        String fileName = "IbeaconData";

        try {
            List<IbeaconBrief> elist = list(hospId,null);

            List<BaseRowModel> ls = new ArrayList<BaseRowModel>();
            for (IbeaconBrief e:elist) {
                IbeaconInfoExcel ei = new IbeaconInfoExcel();

                //复制
                BeanUtils.copyBeanIgnoreNull(ei,e);
                ei.setGridName(e.getGridName(dbCommonService));
                ei.setIsUsingName(e.getIsUsingName());

                ls.add(ei);
            }

            ExcelUtils.exportData(IbeaconInfoExcel.class, response, fileName,ls);
            return JsonResponse.success("蓝牙信标数据导出成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("蓝牙信标数据导出失败："+e.getMessage());
        }
    }

    /**
     * 从Excel导入数据
     * @param file
     * @return
     * @throws Exception
     */
    public JsonResponse ImportExcelData(String hospId, MultipartFile file) throws Exception {
        int m_count = 0, m_fail = 0; //入库记录数量和未入库数量

        try {
            StringBuilder sb = new StringBuilder();

            //读入Excel文件数据
            List<ArrayList> data = (List<ArrayList>) ExcelUtils.readData(file.getInputStream(), IbeaconInfoExcel.class);

            // arraylist转info.excel对象
            if ((data != null) && (data.size() > 0)) {
                for (ArrayList array : data) {
                    try {
                        //从Excel表提取一行数据
                        IbeaconInfoExcel excel = new IbeaconInfoExcel();
                        if ((array.size() >= 1) && (array.get(0) != null))
                            excel.setIbeaconName(array.get(0).toString().replace(" ", ""));
                        if ((array.size() >= 2) && (array.get(1) != null))
                            excel.setIbeaconUUID(array.get(1).toString().replace(" ", ""));
                        if ((array.size() >= 3) && (array.get(2) != null))
                            excel.setIbeaconRSSI(array.get(2).toString().replace(" ", ""));
                        if ((array.size() >= 4) && (array.get(3) != null))
                            excel.setGridName(array.get(3).toString().replace(" ", ""));
                        if ((array.size() >= 5) && (array.get(4) != null))
                            excel.setIsUsingName(array.get(4).toString().replace(" ", ""));

                        //入库保存
                        if ((excel.getIbeaconName() != null) && (excel.getIbeaconName() != "")) {
                            save(hospId, excel);
                            m_count++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_fail++;
                    }
                }

                if (m_count > 0)
                    return JsonResponse.success("蓝牙设备导入成功", String.valueOf(m_count) + "," + String.valueOf(m_fail));
                else
                    return JsonResponse.fail(1009, "蓝牙设备导入失败");
            } else
                return JsonResponse.fail(1009, "读入蓝牙设备为null");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("读入蓝牙设备信息失败:" + e.getMessage());
        }
    }


    /**
     * 查询设备明细，参数可以为null
     * @param hospId
     * @param ibeaconName
     * @return
     * @throws Exception
     */
    public List<IbeaconBrief> list(String hospId, String ibeaconName) throws Exception {
        List<IbeaconBrief> result= null;

        if (dbCommonService != null) {
            try {
                result =dbCommonService.getIbeacons(hospId,ibeaconName);

                if ((result != null)&&(result.size()>0))
                {
                    for (IbeaconBrief dbb:result)
                    {
                        dbb.getGridName(dbCommonService);
                        dbb.getIsUsingName();
                        dbb.getHospName(dbCommonService);
                    }
                }
            } catch (Exception e) {
                throw new Exception("查询蓝牙信标列表失败:"+e.getMessage());
            }
        }

        return result;
    }


    /**
     * 分页查找
     * @param request
     * @return
     */
    public PageResponse<IbeaconBrief> page(String hospId, IbeaconPageRequest request) throws Exception
    {
        try {
            request.setHospId(hospId);
            Page<IbeaconBrief> page = ibeaconMapper.page(request, new RowBounds(request.getIndex(), request.getSize()));
            List<Long> lst = ibeaconMapper.Rcount(request);
            Long total = (long)(page!=null?page.size():0);
            if ((lst!=null) && (lst.size()>0))
                total = lst.get(0);

            if ((page != null)&&(page.size()>0))
            {
                for (IbeaconBrief dbb:page)
                {
                    dbb.getGridName(dbCommonService);
                    dbb.getIsUsingName();
                    dbb.getHospName(dbCommonService);
                }
            }

            return new PageResponse<IbeaconBrief>(page, request, total);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("蓝牙信标分页查询失败："+e.getMessage());
        }
    }

    /**
     * 依据ID获取一个完整的蓝牙信标对象
     * @param id
     * @return
     * @throws Exception
     */
    public Ibeacon getByID(String id) throws  Exception
    {
        Ibeacon result=null;

        if (ibeaconMapper != null)
        {
            try
            {
                List<Ibeacon> lst = ibeaconMapper.getbyid(id);
                if ((lst != null) && (lst.size()>0)) {
                    result = lst.get(0);

                    result.getGridName(dbCommonService);
                    result.getGridPathbyId(dbCommonService);
                    result.getIsUsingName();
                    result.getHospName(dbCommonService);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据ID:["+id+"]获取蓝牙信标对象失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 删除指定id的蓝牙信标对象
     * @param id
     * @return
     */
    public boolean delete(String id) throws  Exception
    {
        boolean result = false ;

        if ((ibeaconMapper != null) && (dbCommonService != null)&&(id != null))
        {
            try
            {
                ibeaconMapper.delete(id);
                result = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("删除蓝牙信标对象["+id+"]异常:"+e.getMessage());
            }
        }

        return result ;
    }

    /**
     * 设置信标状态
     * @param id
     * @param usingflag
     * @throws Exception
     */
    public boolean enableIbeacon(String id,String usingflag) throws Exception
    {
        boolean result = false ;

        if ((ibeaconMapper != null) && (!ExcelUtils.isNullofString(id)) && (!ExcelUtils.isNullofString(usingflag)))
        {
            try {
                ibeaconMapper.enableIbeacon(id.replace(" ", ""), usingflag.replace(" ", ""));
                result = true ;
            }
            catch (Exception e)
            {
                throw new Exception("设置蓝牙信标状态失败："+e.getMessage());
            }
        }

        return result;
    }
    /**
     * 保存设备明细信息，如果不存在则新建，否则更新
     * @param request
     * @return
     */
    public Ibeacon save(String hospId, IbeaconSaveRequest request) throws Exception{

        Ibeacon ibeacon = null;
        boolean isexist = false ;

        if ((ibeaconRepository != null)&&(ibeaconMapper != null)&&(request != null)) {

            //名称存在性校验
            try {
                if (request.getId() != null) ibeacon = getByID(request.getId());

                if (ibeacon == null) {
                    List<Ibeacon> lst = ibeaconMapper.findByName(hospId, request.getIbeaconName());

                    if ((lst != null) && (lst.size() > 0)) {
                        ibeacon = lst.get(0);
                    }
                }
            }catch (Exception e)
            {
                e.printStackTrace();
                throw  new Exception("蓝牙信标名称存在性校验失败："+e.getMessage());
            }

            if (ibeacon == null) {
                //构造新的设备明细对象
                ibeacon = Ibeacon
                        .builder()
                        .build();
            } else
                isexist = true;

            if (ibeacon != null) {
                try {
                    //依据保存数据修改设备明细对象
                    BeanUtils.copyBeanIgnoreNull(ibeacon,request);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    throw  new Exception("复制蓝牙信标对象失败:"+e.getMessage());
                }
            }

            //保存设备运维分类对象
            try {
                ibeacon.setHospID(hospId);
                ibeacon = save(hospId,ibeacon);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("保存蓝牙信标对象失败:" + e.getMessage());
            }
        }

        return ibeacon;
    }

    /**
     * 依据Excel信息保存设备运维分类信息
     * @param request
     * @return
     * @throws Exception
     */
    private Ibeacon save(String hospId,IbeaconInfoExcel request) throws Exception{

        Ibeacon ibeacon = null;
        boolean isexist = false ;

        if ((ibeaconRepository != null)&&(dbCommonService != null)&&(ibeaconMapper != null))  {

            //存在性校验
            try {
                if (request.getIbeaconName() != null) {

                    List<Ibeacon> lst = ibeaconMapper.findByName(hospId, request.getIbeaconName());

                    if ((lst != null) && (lst.size() > 0)) {
                        ibeacon = lst.get(0);

                        if (ibeacon != null) isexist = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("蓝牙信标存在性校验失败："+e.getMessage());
            }

            try {
                if (ibeacon== null) {
                    //构造新的蓝牙信标对象
                    ibeacon = Ibeacon
                            .builder()
                            .build();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("创建蓝牙信标对象失败："+e.getMessage());
            }

            if (ibeacon != null) {

                try {
                    BeanUtils.copyBeanIgnoreNull(ibeacon,request);
                    ibeacon.setHospID(hospId);

                    ibeacon.setGridIdFromName(request.getGridName(),dbCommonService);
                    ibeacon.setIsUsingFromName(request.getIsUsingName());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("复制蓝牙信标对象失败！");
                }

                //保存对象
                try {
                    ibeacon = save(hospId,ibeacon);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("保存蓝牙信标对象失败:" + e.getMessage());
                }
            }
        }

        return ibeacon;
    }

    /**
     * 保存
     * @param hospId
     * @param value
     * @return
     * @throws Exception
     */
    private Ibeacon save(String hospId,Ibeacon value) throws Exception
    {
        Ibeacon rt = null;

        try {
            value.setHospID(hospId);
            Ibeacon ibeacon = ibeaconRepository.save(value);
            value.setId(ibeacon.getId());

            rt = value;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("保存蓝牙信标对象失败:" + e.getMessage());
        }

        return rt;
    }



}
