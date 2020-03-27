package cn.edu.ujs.lp.intells.basicinformation.service;

import cn.edu.ujs.lp.intells.basicinformation.dao.Device.DevicebillMapper;
import cn.edu.ujs.lp.intells.basicinformation.dao.Device.DevicebillRepository;
import cn.edu.ujs.lp.intells.basicinformation.entity.Device.Devicecategory;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import cn.edu.ujs.lp.intells.common.service.FileService;
import cn.edu.ujs.lp.intells.common.service.SerialNumberService;
import cn.edu.ujs.lp.intells.common.utils.*;
import cn.edu.ujs.lp.intells.basicinformation.entity.Device.Devicebill;
import cn.edu.ujs.lp.intells.common.entity.Device.DevicebillBrief;
import cn.edu.ujs.lp.intells.basicinformation.entity.Device.DevicebillInfoExcel;
import cn.edu.ujs.lp.intells.basicinformation.request.Device.DevicePDFRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.Device.DevicebillPageRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.Device.DevicebillSaveRequest;
import com.alibaba.excel.metadata.BaseRowModel;
import com.github.pagehelper.Page;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class DevicebillService {
    @Autowired
    private DevicecategoryService devicecategoryService;

    @Autowired
    private DevicebillMapper devicebillMapper;

    @Autowired
    private DevicebillRepository devicebillRepository;

    @Autowired
    private DataDictionaryService dataDictionaryService;

    @Autowired
    private DBCommonService dbCommonService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SerialNumberService serialNumberService;

    /**
     * 设备二维码输出
     * @param hospId
     * @param devicePDFRequest
     * @param response
     * @return
     * @throws Exception
     */
    public JsonResponse  ExportDevicePdf(String hospId, DevicePDFRequest devicePDFRequest, HttpServletResponse response) throws Exception {
        JsonResponse rt = JsonResponse.fail(1101,"设备明细二维码输出失败！");
        String templatefile = OSUtils.getDevicePDFtemplatefile();
        String logopath = null;//OSUtils.getPDFlogofile();
        String appqrpath = OSUtils.getPDFapplogofile();

        //初始化文档格式等操作
        try {
            //告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/pdf");
            //下载文件的默认名称
            response.setHeader("Content-Disposition", "attachment;filename=deviceqr.pdf");

            response.setHeader("Access-Control-Allow-Origin","*");

            response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, " +
                    "WG-App-Version, WG-Device-Id, WG-Network-Type, WG-Vendor, WG-OS-Type, WG-OS-Version, WG-Device-Model, WG-CPU, WG-Sid, WG-App-Id, WG-Token");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET");
            response.setHeader("Access-Control-Allow-Credentials", "true");

            List<PdfReader> lst = new ArrayList<PdfReader>();

            for (int i=0;i<devicePDFRequest.getDevicebillIdList().size();i++) {

                String devicebillId = devicePDFRequest.getDevicebillIdList().get(i);

                if ((devicebillId != null)&&(devicebillId != "")) {

                    Devicebill db = getByID(devicebillId);

                    if (db != null) {
                        db.getGridName(dbCommonService);
                        db.getTaskOwnerName(dbCommonService);

                        //创建一个pdf读取对象
                        PdfReader reader = new PdfReader(templatefile);

                        //创建一个输出流
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();

                        //创建pdf模板，参数reader  bos
                        PdfStamper ps = new PdfStamper(reader, bos);

                        String gfn = db.getGridName();

                        //去除医院名称
                        int idx = gfn.indexOf("->");
                        if ((idx >= 0) && (idx < gfn.length() - 2)) gfn = gfn.substring(idx + 2);

                        //封装数据
                        AcroFields s = ps.getAcroFields();

                        if ((gfn != null) && (gfn.length() > 18) && (gfn.length() <= 22))
                            s.setFieldProperty("gridName", "textsize", new Float(9), null);
                        else if ((gfn != null) && (gfn.length() > 22))
                            s.setFieldProperty("gridName", "textsize", new Float(8), null);

                        s.setField("hospName", dbCommonService.getHospNamebyId(hospId));
                        s.setField("gridName", gfn);
                        s.setField("assetName", db.getAssetName());
                        s.setField("exteamName", db.getTaskOwnerName());
                        s.setField("hotTel", dbCommonService.getHospservecentertelbyId(hospId));

                        // 根据路径读取图片
                        Image image = Image.getInstance(appqrpath);

                        //app二维码
                        Rectangle rc = s.getFieldPositions("app_af_image").get(0).position;
                        float x = rc.getLeft();
                        float y = rc.getBottom();

                        // 图片大小自适应
                        image.scaleToFit(rc.getWidth(), rc.getHeight());

                        int pageNo = s.getFieldPositions("app_af_image").get(0).page;
                        // 获取图片页面
                        PdfContentByte under = ps.getOverContent(pageNo);
                        // 添加图片
                        image.setAbsolutePosition(x, y);
                        under.addImage(image);

                        //区域二维码图片
                        rc = s.getFieldPositions("qr_af_image").get(0).position;
                        x = rc.getLeft();
                        y = rc.getBottom();

                        java.awt.Image qrimage = QrCodeUtils.encode(db.getDeviceCode()+";"+db.getGridId(), logopath, true);
                        image = Image.getInstance(ps.getWriter(), qrimage, 1);

                        // 图片大小自适应
                        image.scaleToFit(rc.getWidth(), rc.getHeight());

                        int dx = (int) (rc.getWidth() - image.getScaledWidth()) / 2;
                        pageNo = s.getFieldPositions("qr_af_image").get(0).page;
                        // 获取图片页面
                        under = ps.getOverContent(pageNo);
                        // 添加图片
                        image.setAbsolutePosition(x + dx, y);
                        under.addImage(image);

                        ps.setFormFlattening(true);//如果为false，生成的PDF文件可以编辑，如果为true，生成的PDF文件不可以编辑
                        ps.close();//关闭PdfStamper
                        PdfReader pdfReader = new PdfReader(bos.toByteArray());
                        lst.add(pdfReader);
                        bos.close();
                        reader.close();
                    }
                }
            }

            //融合多页标签
            Document document = new Document();
            PdfCopy copy = new PdfCopy(document, response.getOutputStream());
            document.open();
            for (int k = 0; k < lst.size(); k++) {
                PdfReader pdfReader = lst.get(k);
                document.newPage();
                PdfImportedPage page = copy.getImportedPage(pdfReader, 1);// 从当前Pdf,获取第0页
                copy.addPage(page);
            }
            copy.close();
            document.close();

            rt = JsonResponse.success("设备二维码标签输出成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("设备二维码标签输出失败："+e.getMessage());
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

        /**String templateName = "Devicebilltemplate";

        try {
            ExcelUtils.exportTemplate(DevicebillInfoExcel.class, response, templateName);
            return JsonResponse.success("设备明细导入模板导出成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("设备明细导入模板导出失败:"+e.getMessage());
        }*/
        try
        {
            if (fileService != null) {
                fileService.downloadExcelFile(OSUtils.getDevicebilltemplatefile(),response);
                return JsonResponse.success("设备明细导入模板导出成功");
            }
            else return JsonResponse.success("设备明细导入模板导出失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("设备明细导入模板导出失败:"+e.getMessage());
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
        String fileName = "DevicebillData";

        try {
            List<DevicebillBrief> elist = list(hospId,null);

            List<BaseRowModel> ls = new ArrayList<BaseRowModel>();
            for (DevicebillBrief e:elist) {
                DevicebillInfoExcel ei = new DevicebillInfoExcel();

                //复制
                copyDevicebilltoExcelInfo(hospId,e,ei);

                ls.add(ei);
            }

            ExcelUtils.exportData(DevicebillInfoExcel.class, response, fileName,ls);
            return JsonResponse.success("设备明细数据导出成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("设备明细数据导出失败："+e.getMessage());
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
            List<ArrayList> data = (List<ArrayList>) ExcelUtils.readData(file.getInputStream(), DevicebillInfoExcel.class);

            // arraylist转info.excel对象
            if ((data != null) && (data.size() > 0)) {
                for (ArrayList array : data) {
                    try {
                        //从Excel表提取一行数据
                        DevicebillInfoExcel excel = new DevicebillInfoExcel();
                        if ((array.size() >= 1) && (array.get(0) != null))
                            excel.setAssetCode(array.get(0).toString().replace(" ", ""));
                        if ((array.size() >= 2) && (array.get(1) != null))
                            excel.setAssetName(array.get(1).toString().replace(" ", ""));
                        if ((array.size() >= 3) && (array.get(2) != null))
                            excel.setModelSpec(array.get(2).toString().replace(" ", ""));
                        if ((array.size() >= 4) && (array.get(3) != null))
                            excel.setDeviceCategoryName(array.get(3).toString().replace(" ", ""));
                        if ((array.size() >= 5) && (array.get(4) != null))
                            excel.setIsUsingName(array.get(4).toString().replace(" ", ""));
                        if ((array.size() >= 6) && (array.get(5) != null))
                            excel.setUseStatusName(array.get(5).toString().replace(" ", ""));
                        if ((array.size() >= 7) && (array.get(6) != null))
                            excel.setEnableDate(array.get(6).toString().replace(" ", ""));
                        if ((array.size() >= 8) && (array.get(7) != null))
                            excel.setServiceLife(Integer.parseInt((String) array.get(7)));
                        if ((array.size() >= 9) && (array.get(8) != null))
                            excel.setRepairExpire(array.get(8).toString().replace(" ", ""));
                        if ((array.size() >= 10) && (array.get(9) != null))
                            excel.setAdministratorsName(array.get(9).toString().replace(" ", ""));
                        if ((array.size() >= 11) && (array.get(10) != null))
                            excel.setTaskOwnerName(array.get(10).toString().replace(" ", ""));
                        if ((array.size() >= 12) && (array.get(11) != null))
                            excel.setGridName(array.get(11).toString().replace(" ", ""));
                        if ((array.size() >= 13) && (array.get(12) != null))
                            excel.setDposition(array.get(12).toString().replace(" ", ""));
                        if ((array.size() >= 14) && (array.get(13) != null))
                            excel.setConfiguration(array.get(13).toString().replace(" ", ""));
                        if ((array.size() >= 15) && (array.get(14) != null))
                            excel.setBrand(array.get(14).toString().replace(" ", ""));
                        if ((array.size() >= 16) && (array.get(15) != null))
                            excel.setIsMedicaluseName(array.get(15).toString().replace(" ", ""));
                        if ((array.size() >= 17) && (array.get(16) != null))
                            excel.setMeasurementUnit(array.get(16).toString().replace(" ", ""));
                        if ((array.size() >= 18) && (array.get(17) != null))
                            excel.setEquipmentValue(Double.parseDouble((String) array.get(17)));
                        if ((array.size() >= 19) && (array.get(18) != null))
                            excel.setAssetSourceName(array.get(18).toString().replace(" ", ""));
                        if ((array.size() >= 20) && (array.get(19) != null))
                            excel.setIsImportedName(array.get(19).toString().replace(" ", ""));
                        if ((array.size() >= 21) && (array.get(20) != null))
                            excel.setSupplier(array.get(20).toString().replace(" ", ""));
                        if ((array.size() >= 22) && (array.get(21) != null))
                            excel.setSupplierContract(array.get(21).toString().replace(" ", ""));
                        if ((array.size() >= 23) && (array.get(22) != null))
                            excel.setSupplierTel(array.get(22).toString().replace(" ", ""));
                        if ((array.size() >= 24) && (array.get(23) != null))
                            excel.setManufacturer(array.get(23).toString().replace(" ", ""));
                        if ((array.size() >= 25) && (array.get(24) != null))
                            excel.setProductionPlace(array.get(24).toString().replace(" ", ""));
                        if ((array.size() >= 26) && (array.get(25) != null))
                            excel.setAftersaleTel(array.get(25).toString().replace(" ", ""));
                        if ((array.size() >= 27) && (array.get(26) != null))
                            excel.setAftersaleEngineer(array.get(26).toString().replace(" ", ""));

                        //入库保存
                        if ((excel.getAssetName() != null) && (excel.getAssetName() != "")) {
                            save(hospId, excel);
                            m_count++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_fail++;
                    }
                }

                if (m_count > 0)
                    return JsonResponse.success("设备明细导入成功", String.valueOf(m_count) + "," + String.valueOf(m_fail));
                else
                    return JsonResponse.fail(1009, "设备明细导入失败");
            } else
                return JsonResponse.fail(1009, "读入设备明细为null");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("读入设备明细信息失败:" + e.getMessage());
        }
    }


    /**
     * 查询设备明细，参数可以为null
     * @param hospId
     * @param categoryId
     * @return
     * @throws Exception
     */
    public List<DevicebillBrief> list(String hospId,String categoryId) throws Exception {
        List<DevicebillBrief> result= null;

        if (dbCommonService != null) {
            try {
                result =dbCommonService.getDevicebills(hospId,(categoryId!=null?categoryId.trim():null));

                if ((result != null)&&(result.size()>0))
                {
                    for (DevicebillBrief dbb:result)
                    {
                        dbb.getGridName(dbCommonService);
                        dbb.getDeviceCategoryName(dbCommonService);
                        dbb.getTaskOwnerName(dbCommonService);
                        dbb.getAdministratorsName(dbCommonService);
                        dbb.getUseStatusName(dataDictionaryService);
                        dbb.getAssetSourceName(dataDictionaryService);
                        dbb.getIsUsingName();
                        dbb.getIsMedicaluseName();
                        dbb.getIsImportedName();
                    }
                }
            } catch (Exception e) {
                throw new Exception("查询设备明细失败:"+e.getMessage());
            }
        }

        return result;
    }


    /**
     * 分页查找
     * @param request
     * @return
     */
    public PageResponse<DevicebillBrief> page(String hospId, DevicebillPageRequest request) throws Exception
    {
        try {
            request.setHospId(hospId);
            Page<DevicebillBrief> page = devicebillMapper.page(request, new RowBounds(request.getIndex(), request.getSize()));
            List<Long> lst = devicebillMapper.Rcount(request);
            Long total = (long)(page!=null?page.size():0);
            if ((lst!=null) && (lst.size()>0))
                total = lst.get(0);

            if ((page != null)&&(page.size()>0))
            {
                for (DevicebillBrief dbb:page)
                {
                    dbb.getGridName(dbCommonService);
                    dbb.getDeviceCategoryName(dbCommonService);
                    dbb.getTaskOwnerName(dbCommonService);
                    dbb.getAdministratorsName(dbCommonService);
                    dbb.getUseStatusName(dataDictionaryService);
                    dbb.getAssetSourceName(dataDictionaryService);
                    dbb.getIsUsingName();
                    dbb.getIsMedicaluseName();
                    dbb.getIsImportedName();
                }
            }

            return new PageResponse<DevicebillBrief>(page, request, total);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("设备明细分页查询失败："+e.getMessage());
        }
    }

    /**
     * 依据ID获取一个完整的设备明细对象
     * @param id
     * @return
     * @throws Exception
     */
    public Devicebill getByID(String id) throws  Exception
    {
        Devicebill result=null;

        if (devicebillMapper != null)
        {
            try
            {
                List<Devicebill> lst = devicebillMapper.getbyid(id);
                if ((lst != null) && (lst.size()>0)) {
                    result = lst.get(0);

                    if (result != null) {
                        result.getGridName(dbCommonService);
                        result.getDeviceCategoryName(dbCommonService);
                        result.getTaskOwnerName(dbCommonService);
                        result.getAdministratorsName(dbCommonService);
                        result.getUseStatusName(dataDictionaryService);
                        result.getAssetSourceName(dataDictionaryService);
                        result.getIsUsingName();
                        result.getIsMedicaluseName();
                        result.getIsImportedName();
                        result.getIsAccessoryName();
                        result.getAccessoryClassName(dataDictionaryService);
                        result.getIsSpecialName();
                        result.getSpecialClassName(dataDictionaryService);
                        result.getIsEmergencyName();
                        result.getEmergencyClassName(dataDictionaryService);
                        result.getGridPathbyId(dbCommonService);
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据ID:["+id+"]获取设备明细对象失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 删除指定id的设备明细对象
     * @param id
     * @return
     */
    public boolean delete(String id) throws  Exception
    {
        boolean result = false ;

        if ((devicebillMapper != null) && (dbCommonService != null)&&(id != null))
        {
            try
            {
                devicebillMapper.delete(id);
                result = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("删除设备明细对象["+id+"]异常:"+e.getMessage());
            }
        }

        return result ;
    }

    /**
     * 保存设备明细信息，如果不存在则新建，否则更新
     * @param request
     * @return
     */
    public Devicebill save(String hospId, DevicebillSaveRequest request) throws Exception{

        Devicebill devicebill = null;
        boolean isexist = false ;

        if ((devicebillRepository != null)&&(request != null)) {

            //设备明细名称存在性校验
            try {
                if ((request.getId() != null) && (request.getId().trim() != "")) devicebill = getByID(request.getId());

                if (devicebill == null) {
                    List<Devicebill> lst = devicebillMapper.findByCategoryandName(hospId, request.getDeviceCategory(), request.getAssetName());

                    if ((lst != null) && (lst.size() > 0)) {
                        devicebill = lst.get(0);
                    }
                }

                if (devicebill != null) {
                    devicebill.getGridName(dbCommonService);
                    devicebill.getDeviceCategoryName(dbCommonService);
                    devicebill.getTaskOwnerName(dbCommonService);
                    devicebill.getAdministratorsName(dbCommonService);
                    devicebill.getUseStatusName(dataDictionaryService);
                    devicebill.getAssetSourceName(dataDictionaryService);
                    devicebill.getIsUsingName();
                    devicebill.getIsMedicaluseName();
                    devicebill.getIsImportedName();
                    devicebill.getIsAccessoryName();
                    devicebill.getAccessoryClassName(dataDictionaryService);
                    devicebill.getIsSpecialName();
                    devicebill.getSpecialClassName(dataDictionaryService);
                    devicebill.getIsEmergencyName();
                    devicebill.getEmergencyClassName(dataDictionaryService);
                    devicebill.getGridPathbyId(dbCommonService);
                }
            }catch (Exception e)
            {
                e.printStackTrace();
                throw  new Exception("设备明细名称存在性校验失败："+e.getMessage());
            }

            if (devicebill == null) {
                //构造新的设备明细对象
                devicebill = Devicebill
                        .builder()
                        .build();
            }
            else isexist = true;

            if (devicebill != null) {
                try {
                    //依据保存数据修改设备明细对象
                    BeanUtils.copyBeanIgnoreNull(devicebill, request);
                    //BeanUtils.copyBean(devicebill, request);

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("复制设备明细对象失败:" + e.getMessage());
                }


                //保存设备运维分类对象
                try {
                    devicebill.setHospID(hospId);
                    devicebill = generateCode(hospId, devicebill, isexist);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("保存设备明细对象失败:" + e.getMessage());
                }
            }
        }

        return devicebill;
    }

    /**
     * 依据Excel信息保存设备运维分类信息
     * @param request
     * @return
     * @throws Exception
     */
    private Devicebill save(String hospId,DevicebillInfoExcel request) throws Exception{

        Devicebill devicebill = null;
        boolean isexist = false ;

        if ((devicebillRepository != null)&&(dbCommonService != null)&&(devicebillMapper != null))  {

            //设备明细存在性校验
            try {
                if (request.getDeviceCategoryName() != null) {
                    String categoryId = dbCommonService.getDevicecategoryIdbyFullname(hospId, request.getDeviceCategoryName());
                    List<Devicebill> lst = devicebillMapper.findByCategoryandName(hospId, categoryId, request.getAssetName());

                    if ((lst != null) && (lst.size() > 0)) {
                        devicebill = lst.get(0);

                        if (devicebill != null)
                        {
                            isexist = true;

                            if (devicebill != null) {
                                devicebill.getGridName(dbCommonService);
                                devicebill.getDeviceCategoryName(dbCommonService);
                                devicebill.getTaskOwnerName(dbCommonService);
                                devicebill.getAdministratorsName(dbCommonService);
                                devicebill.getUseStatusName(dataDictionaryService);
                                devicebill.getAssetSourceName(dataDictionaryService);
                                devicebill.getIsUsingName();
                                devicebill.getIsMedicaluseName();
                                devicebill.getIsImportedName();
                                devicebill.getIsAccessoryName();
                                devicebill.getAccessoryClassName(dataDictionaryService);
                                devicebill.getIsSpecialName();
                                devicebill.getSpecialClassName(dataDictionaryService);
                                devicebill.getIsEmergencyName();
                                devicebill.getEmergencyClassName(dataDictionaryService);
                                devicebill.getGridPathbyId(dbCommonService);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("设备明细存在性校验失败："+e.getMessage());
            }

            try {
                if (devicebill == null) {
                    //构造新的设备明细对象
                    devicebill = Devicebill
                            .builder()
                            .build();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("创建设备明细对象失败："+e.getMessage());
            }

            if (devicebill != null) {

                try {
                    BeanUtils.copyBeanIgnoreNull(devicebill,request);
                    devicebill.setHospID(hospId);

                    copyExcelInfotoDevicebill(hospId,request,devicebill);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("复制设备明细对象失败！");
                }

                //保存设备运维分类对象
                try {
                    devicebill = generateCode(hospId,devicebill,isexist);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("保存设备明细对象失败:" + e.getMessage());
                }
            }
        }

        return devicebill;
    }

    /**
     * 设备明细对象编码并保存
     * @param hospId
     * @param value
     * @return
     * @throws Exception
     */
    private Devicebill generateCode(String hospId,Devicebill value,boolean isexist) throws Exception
    {
        Devicebill rt = null;

        try {
            //新对象要产生编码
            if (isexist == false) {
                String superCode = null;
                if ((value.getDeviceCategory() != null) && (value.getDeviceCategory().trim() != "")) {
                    superCode = dbCommonService.getDevicecategoryCodebyId(value.getDeviceCategory());
                }

                value.setDeviceCode(serialNumberService.getSBbillCode(hospId, superCode));
            }

            value.setHospID(hospId);
            if ((value.getIsUsing() == null) || (value.getIsUsing().trim().compareTo("0") != 0)) value.setIsUsing("1");
            if ((value.getIsImported() == null) || (value.getIsImported().trim().compareTo("1") != 0)) value.setIsImported("0");
            if ((value.getIsEmergency() == null) || (value.getIsEmergency().trim().compareTo("1") != 0)) value.setIsEmergency("0");
            if ((value.getIsSpecial() == null) || (value.getIsSpecial().trim().compareTo("1") != 0)) value.setIsSpecial("0");
            if ((value.getIsAccessory() == null) || (value.getIsAccessory().trim().compareTo("1") != 0)) value.setIsAccessory("0");
            if ((value.getIsMedicaluse() == null) || (value.getIsMedicaluse().trim().compareTo("1") != 0)) value.setIsMedicaluse("0");

            rt = devicebillRepository.save(value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("保存设备明细对象失败:" + e.getMessage());
        }

        return rt;
    }

    /**
     * 对象复制，将Excel对象复制到Devicebill对象中
     * @param hospId
     * @param srt
     * @param dst
     * @throws Exception
     */
    private void copyExcelInfotoDevicebill(String hospId,DevicebillInfoExcel srt,Devicebill dst) throws Exception
    {
        try {
            BeanUtils.copyBeanIgnoreNull(dst, srt);

            if ((srt.getDeviceCategoryName() != null) && (srt.getDeviceCategoryName().trim() != "")) {
                dst.setDeviceCategoryFromName(srt.getDeviceCategoryName(),dbCommonService);
            }

            if ((srt.getIsUsingName() != null) && (srt.getIsUsingName() != "")) {
                dst.setIsUsingFromName(srt.getIsUsingName());
            }

            if ((srt.getUseStatusName() != null) && (srt.getUseStatusName() != "")) {
                dst.setUseStatusFromName(srt.getUseStatusName(),dataDictionaryService);
            }

            if ((srt.getEnableDate() != null) && (srt.getEnableDate() != "")) {
                dst.setEnableDate(ExcelUtils.StringToDate(srt.getEnableDate(), "yyyyMMdd"));
            }

            if ((srt.getRepairExpire() != null) && (srt.getRepairExpire() != "")) {
                dst.setRepairExpire(ExcelUtils.StringToDate(srt.getRepairExpire(), "yyyyMMdd"));
            }

            if ((srt.getGridName() != null) && (srt.getGridName().trim() != "")) {
                dst.setGridIdFromName(srt.getGridName(),dbCommonService);
            }

            if ((srt.getIsMedicaluseName() != null) && (srt.getIsMedicaluseName() != "")) {
                dst.setIsMedicaluseFromName(srt.getIsMedicaluseName());
            }

            if ((srt.getAssetSourceName() != null) && (srt.getAssetSourceName() != "")) {
                dst.setAssetSourceFromName(srt.getAssetSourceName(),dataDictionaryService);
            }

            if ((srt.getIsImportedName() != null) && (srt.getIsImportedName() != "")) {
                dst.setIsImportedFromName(srt.getIsImportedName());
            }

            if ((srt.getAdministratorsName() != null) && (srt.getAdministratorsName() != ""))
            {
                dst.setAdministratorsFromName(srt.getAdministratorsName(),dbCommonService);
            }
            else {
                if (dst.getDeviceCategory() != null) {
                    if (devicecategoryService != null) {
                        Devicecategory devicecategory = devicecategoryService.getByID(dst.getDeviceCategory());
                        if ((devicecategory != null) && (devicecategory.getManageAffiliation() != null))
                            dst.setAdministrators(devicecategory.getManageAffiliation());
                    }
                }
            }

            if ((srt.getTaskOwnerName() != null) && (srt.getTaskOwnerName() != "")) {
                String tmp = srt.getTaskOwnerName().replace(" ","").replace("->",".");
                String[] fullnames = tmp.split("\\.", 2);

                if (fullnames.length >= 2) {
                    String excompanyId = dbCommonService.getExcompanyIdbyName(hospId, fullnames[0].trim());
                    dst.setTaskOwner(dbCommonService.getExteamIdbyFullName(hospId,srt.getTaskOwnerName()));
                } else
                    dst.setTaskOwner(null);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("将Excel对象复制到设备明细对象失败："+e.getMessage());
        }
    }

    /**
     * 将设备明细对象复制到Excel对象
     * @param srt
     * @param dst
     * @throws Exception
     */
    private void copyDevicebilltoExcelInfo(String hospId,DevicebillBrief srt,DevicebillInfoExcel dst) throws Exception
    {
        try {
            BeanUtils.copyBeanIgnoreNull(dst,srt);

            dst.setDeviceCategoryName(srt.getDeviceCategoryName(dbCommonService));

            dst.setIsUsingName(srt.getIsUsingName());

            dst.setIsUsingName(srt.getUseStatusName(dataDictionaryService));

            dst.setEnableDate(ExcelUtils.DateToString(srt.getEnableDate(),"yyyyMMdd"));

            dst.setRepairExpire(ExcelUtils.DateToString(srt.getRepairExpire(),"yyyyMMdd"));

            dst.setAdministratorsName(srt.getAdministratorsName(dbCommonService));

            dst.setTaskOwnerName(srt.getTaskOwnerName(dbCommonService));

            dst.setGridName(srt.getGridName(dbCommonService));

            dst.setIsMedicaluseName(srt.getIsMedicaluseName());

            dst.setAssetSourceName(srt.getAssetSourceName(dataDictionaryService));

            dst.setIsImportedName(srt.getIsImportedName());

        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("将设备明细对象复制到Excel对象失败："+e.getMessage());
        }
    }

}
