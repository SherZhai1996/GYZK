package cn.edu.ujs.lp.intells.basicinformation.service;

import cn.edu.ujs.lp.intells.basicinformation.dao.Excompany.ExteamGridRepository;
import cn.edu.ujs.lp.intells.basicinformation.dao.hosp.GridMapper;
import cn.edu.ujs.lp.intells.basicinformation.dao.hosp.GridRepository;
import cn.edu.ujs.lp.intells.basicinformation.dao.hosp.GridTelRepository;
import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.ExteamGrid;
import cn.edu.ujs.lp.intells.basicinformation.entity.Hosp.*;
import cn.edu.ujs.lp.intells.basicinformation.entity.Hosp.GridTel;
import cn.edu.ujs.lp.intells.basicinformation.request.hosp.GridPDFRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.hosp.GridPageRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.hosp.GridSaveRequest;
import cn.edu.ujs.lp.intells.common.entity.Hosp.DepartmentBrief;
import cn.edu.ujs.lp.intells.common.entity.Hosp.GridBrief;
import cn.edu.ujs.lp.intells.common.entity.Hosp.Hosp;
import cn.edu.ujs.lp.intells.common.entity.User.UserRoleBrief;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.service.*;
import cn.edu.ujs.lp.intells.common.utils.*;
import com.alibaba.excel.metadata.BaseRowModel;
import com.github.pagehelper.Page;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 网格服务类，提供与网格相关的服务
 */
@Service
public class GridService {
    @Autowired
    private DBCommonService dbCommonService;

    @Autowired
    private HospService hospService;

    @Autowired
    private UserService userService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private GridRepository gridRepository;

    @Autowired
    private GridMapper gridMapper;

    @Autowired
    private GridTelRepository gridTelRepository;

    @Autowired
    private ExteamGridRepository exteamGridRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private DataDictionaryService dataDictionaryService;


    @Autowired
    private SerialNumberService serialNumberService;

    public boolean setGridLeader(String hospId) throws Exception
    {
        if ((dbCommonService != null) && (staffService != null)) {
            try {
                List<GridBrief> dlst = dbCommonService.getGrids(hospId,null,null,null);

                if (dlst != null) {
                    List<Grid> list = new ArrayList<>();

                    for (GridBrief dptb : dlst) {
                        Staff staff = staffService.findByName(hospId, dptb.getGridLeaderName());
                        if (staff != null) {
                            Grid grid = getbyId(dptb.getId());
                            grid.setGridLeaderTel(staff.getStaffTel());
                            grid.setGridLeaderID(staff.getId());

                            list.add(grid);
                        }
                    }

                    gridRepository.saveAll(list);
                }

                dlst = dbCommonService.getGrids(hospId,null,null,null);
                if (dlst != null) {
                    //对科室负责人设置角色
                    for (GridBrief dptb : dlst) {
                        Staff staff = staffService.findByName(hospId, dptb.getGridLeaderName());

                        if (staff != null) {
                            List<UserRoleBrief> lst = userService.getUserRoles(staff.getId(),"006");

                            if (lst == null) userService.SetUserRole(staff.getId(),"006");
                        }
                    }

                }

                return true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("科室部门负责人信息更新失败:"+e.getMessage());
            }
        }

        return false;
    }

    /**
     * 网格区域二维码输出
     * @param gridPDFRequest
     * @param response
     * @return
     * @throws Exception
     */
    public JsonResponse  ExportGridPdf(String hospId,GridPDFRequest gridPDFRequest, HttpServletResponse response) throws Exception {
        JsonResponse rt = JsonResponse.fail(1101,"网格区域二维码输出失败！");
        String templatefile = OSUtils.getGridPDFtemplatefile();
        String logopath = null;//OSUtils.getPDFlogofile();
        String appqrpath = OSUtils.getPDFapplogofile();

        if (gridMapper != null) {
            //初始化文档格式等操作
            try {
                //告诉浏览器用什么软件可以打开此文件
                response.setHeader("content-Type", "application/pdf");
                //下载文件的默认名称
                response.setHeader("Content-Disposition", "attachment;filename=gridqr.pdf");

                response.setHeader("Access-Control-Allow-Origin","*");

                response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, " +
                        "WG-App-Version, WG-Device-Id, WG-Network-Type, WG-Vendor, WG-OS-Type, WG-OS-Version, WG-Device-Model, WG-CPU, WG-Sid, WG-App-Id, WG-Token");
                response.setHeader("Access-Control-Allow-Methods", "POST, GET");
                response.setHeader("Access-Control-Allow-Credentials", "true");

                List<PdfReader> lst = new ArrayList<PdfReader>();

                for (int i = 0; i < gridPDFRequest.getGridIdList().size(); i++) {

                    String gid = gridPDFRequest.getGridIdList().get(i);
                    String gc = dbCommonService.getGridCodebyId(gid);

                    String hn = dbCommonService.getHospNamebyId(hospId);

                    List<GridQRBrief> sublist = gridMapper.getQRlist(hospId,gc);

                    if (sublist != null)
                    {
                        for (int k=0;k<sublist.size();k++) {
                            GridQRBrief subgbf = sublist.get(k);

                            String gfn = subgbf.getGridFullname();
                            String grid = subgbf.getId();
                            String deviceExteamName = dbCommonService.getDeviceExteamnamebyGridid(grid);

                            String exteamName = (subgbf.getBJexteamNeme()!=null?subgbf.getBJexteamNeme():"");
                            if (exteamName == null)
                                exteamName = (subgbf.getBAexteamNeme()!=null?subgbf.getBAexteamNeme():"");
                            else
                                exteamName += (subgbf.getBAexteamNeme()!=null?"; "+subgbf.getBAexteamNeme():"");

                            if (deviceExteamName != null)
                                exteamName = "设备："+deviceExteamName;

                            //去除医院名称
                            int idx = gfn.indexOf("->");
                            if ((idx >= 0) && (idx < gfn.length() - 2)) gfn = gfn.substring(idx + 2);

                            //创建一个pdf读取对象
                            PdfReader reader = new PdfReader(templatefile);

                            //创建一个输出流
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();

                            //创建pdf模板，参数reader  bos
                            PdfStamper ps = new PdfStamper(reader, bos);

                            //封装数据
                            AcroFields s = ps.getAcroFields();
                            s.setField("hospName", hn);

                            if ((gfn != null) && (gfn.length() > 18) && (gfn.length() <= 22))
                                s.setFieldProperty("gridName", "textsize", new Float(9), null);
                            else if ((gfn != null) && (gfn.length() > 22))
                                s.setFieldProperty("gridName", "textsize", new Float(8), null);

                            s.setField("gridName", gfn);
                            //s.setField("placeClass", placeclassname);
                            s.setField("exteamName",exteamName);
                            //s.setField("BAexteamName", subgbf.getBAexteamNeme());
                            //s.setField("BJexteamName", subgbf.getBJexteamNeme());
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

                            java.awt.Image qrimage = QrCodeUtils.encode(subgbf.getGridCode() + ";" +subgbf.getId()+";"+subgbf.getGridPlaceclassid(), logopath, true);
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

                rt = JsonResponse.success("网格二维码标签输出成功");
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("网格二维码标签输出失败：" + e.getMessage());
            }
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

        /**String templateName = "Gridtemplate"; //网格导出模板名称

        try {
            //输出网格模板
            ExcelUtils.exportTemplate(GridInfoExcel.class, response, templateName);
            return JsonResponse.success("网格区域导入模板导出成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("网格区域导入模板导出失败:"+e.getMessage());
        }*/

        try
        {
            if (fileService != null) {
                fileService.downloadExcelFile(OSUtils.getGridtemplatefile(),response);
                return JsonResponse.success("网格区域导入模板导出成功");
            }
            else return JsonResponse.success("网格区域导入模板导出失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("网格区域导入模板导出失败:"+e.getMessage());
        }
    }

    /**
     * 导出网格区域数据
     * @param response
     * @return
     * @throws Exception
     */
    public JsonResponse exportExcelData(String hospId,HttpServletResponse response) throws Exception
    {
        String fileName = "GridData"; //网格导出名称

        try {
            if ((gridMapper != null) && (dbCommonService != null)) {
                //获取指定医院的网格信息
                List<GridBrief> elist = dbCommonService.getGrids(hospId, null,null,null);
                List<BaseRowModel> ls = new ArrayList<BaseRowModel>();

                for (GridBrief e : elist) {
                    GridInfoExcel ei = new GridInfoExcel();
                    BeanUtils.copyBeanIgnoreNull(ei, e);

                    //获取网格电话列表，并转换为字符串输出
                    List<GridTelBrief> gtbs = gridMapper.getTelsbyGridId(e.getId());
                    if ((gtbs != null) && (gtbs.size() > 0)) {
                        String gtbss = "";
                        for (GridTelBrief gtb : gtbs) {
                            gtbss += (gtbss.length() < 1 ? gtb.getGridTel() : ";" + gtb.getGridTel());
                        }
                        ei.setGridTels(gtbss);
                    }

                    //获取网格所关联的服务班组，并转换为字符串输出
                    if ((e.getGridExteams() != null) && (e.getGridExteams().size() > 0)) {
                        String teamss = "";
                        for (String teamid : e.getGridExteams()) {
                            String exteamName = dbCommonService.getExteamFullpathnamebyId(teamid);
                            teamss += (teamss.length() < 1 ? exteamName : ";" + exteamName);
                        }
                        ei.setGridteams(teamss);
                    }

                    ls.add(ei);
                }

                //输出网格信息
                ExcelUtils.exportData(GridInfoExcel.class, response,fileName,ls);
                return JsonResponse.success("网格区域数据导出成功");
            }
            else
                return JsonResponse.fail(2001,"网格区域数据导出失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("网格区域数据导出失败："+e.getMessage());
        }
    }

    /**
     * 从Excel导入网格区域数据
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
            List<ArrayList> data = (List<ArrayList>) ExcelUtils.readData(file.getInputStream(), GridInfoExcel.class);

            // arraylist转info.excel对象
            if ((data != null) && (data.size() > 0)) {
                for (ArrayList array:data) {
                    try {
                        //从Excel表提取一行数据
                        GridInfoExcel excel = new GridInfoExcel();
                        if ((array.size()>=1) && (array.get(0) != null)) excel.setGridName(array.get(0).toString().replace(" ",""));
                        if ((array.size()>=2) && (array.get(1) != null)) excel.setSuperGridName(array.get(1).toString().replace(" ",""));
                        if ((array.size()>=3) && (array.get(2) != null)) excel.setGridPlaceclassName(array.get(2).toString().replace(" ",""));
                        if ((array.size()>=4) && (array.get(3) != null)) excel.setDeptName(array.get(3).toString().replace(" ",""));
                        if ((array.size()>=5) && (array.get(4) != null))  //对于浮点数特殊处理一下，提高可靠性
                        {
                            try {
                                double tmp = Double.parseDouble(array.get(4).toString().replace(" ",""));
                                excel.setGridArea(tmp);
                            }
                            catch (Exception e)
                            {
                                excel.setGridArea(0.0);
                            }
                        }

                        if ((array.size()>=6) && (array.get(5) != null)) excel.setGridLeaderName(array.get(5).toString().replace(" ",""));
                        if ((array.size()>=7) && (array.get(6) != null)) excel.setGridTels(array.get(6).toString().replace(" ",""));
                        if ((array.size()>=8) && (array.get(7) != null)) excel.setGridteams(array.get(7).toString().replace(" ",""));

                        //入库保存
                        if ((excel.getGridName() != null) && (excel.getGridName() != "")) {
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
                    return  JsonResponse.success("网格区域导入成功",String.valueOf(m_count)+","+String.valueOf(m_fail));
                else
                    return JsonResponse.fail(1009, "网格区域导入失败");
            }
            else
                return JsonResponse.fail(1009, "读入网格区域为null");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new  Exception("读入网格区域信息失败:"+e.getMessage());
        }
    }

    /**
     * 分页查找
     * @param request
     * @return
     */
    public PageResponse<GridBrief> page(String hospId, GridPageRequest request) throws Exception
    {
        try {
            request.setHospId(hospId);
            Page<GridBrief> page = gridMapper.page(request, new RowBounds(request.getIndex(), request.getSize()));
            List<Long> lst = gridMapper.Rcount(request);
            Long total = 0L;
            if ((lst!=null) && (lst.size()>0))
                total = lst.get(0);

            if ((page != null)&&(page.size()>0))
            {
                for (GridBrief gb:page)
                {
                    gb.getSuperiorGridname(dbCommonService);
                    gb.getDeptName(dbCommonService);
                    gb.getGridPlaceclassidName(dataDictionaryService);
                }
            }

            return new PageResponse<GridBrief>(page, request, total);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("医院网格分页查询失败："+e.getMessage());
        }
    }


    /**
     * 依据ID获取指定的网格对象
     * @param id
     * @return
     */
    public Grid getbyId(String id) throws Exception
    {
        Grid result=null;

        if ((gridMapper != null) && (dbCommonService != null) && (hospService != null))
        {
            try
            {
                List<Grid> lst = gridMapper.getbyid(id);
                if ((lst != null) && (lst.size()>0)) {
                    result = lst.get(0);

                    if (result != null)
                    {
                        result.getSuperiorGridname(dbCommonService);
                        result.getDeptName(dbCommonService);
                        result.getGridPlaceclassidName(dataDictionaryService);
                        result.getLeader(userService);
                        result.getGridPathbyId(dbCommonService);
                        result.getGridExteam(dbCommonService);
                        result.getGridTelsString();

                        //对楼栋的中心位置进行合规性判断
                        if ((result.getHospID() != null) && (result.getHospID() != "") && (result.getGridLevel() == 2)) {
                            Hosp hosp = hospService.getHospbyID(result.getHospID(), null);

                            //判断楼栋中心位置是否合规，不合规设定为医院中心位置
                            if (BaiduUtils.getDistance(hosp.getHospCenterX(),hosp.getHospCenterY(),result.getGridcenterX(),result.getGridcenterY())>(2*BaiduUtils.defaulthospRadius))
                            {
                                result.setGridcenterX(hosp.getHospCenterX());
                                result.setGridcenterY(hosp.getHospCenterY());
                            }

                            if (Math.abs(result.getGridradius()-BaiduUtils.defaultbuildingRadius)>(BaiduUtils.defaultbuildingRadius/2))
                                result.setGridradius(BaiduUtils.defaultbuildingRadius);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据ID:["+id+"]获取网格区域对象失败:"+e.getMessage());
            }
        }

        return result;
    }


    /**
     * 依据ID删除网格，且返回删除网格后的所有网格列表
     * @param gridCode
     * @return
     */
    public boolean delete(String hospId,String gridCode) throws Exception
    {
        boolean result = false;

        if (gridMapper != null)
        {
            try {
                gridMapper.deleteGridbycode(gridCode);

                result = true ;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("删除["+gridCode+"]网格失败："+e.getMessage());
            }
        }

        return result;
    }


    /**
     * 保存网格信息，如果不存在则新建，否则更新
     * @param request
     * @return
     */
    public Grid save(String hospId, GridSaveRequest request) throws Exception
    {

        Grid grid = null;
        String leaderId = null;

        if ((gridRepository != null)&&(gridMapper != null) && (request != null)) {

            try {
                if ((request.getId() != null) && (request.getId().trim() != "")) grid = getbyId(request.getId());

                if (grid == null) {
                    //网格存在性校验
                    grid = isExist(hospId, request);
                }

                if (grid != null) leaderId = grid.getGridLeaderID();

                //复制对象
                if (grid == null) {
                    //构造新的网格对象
                    grid = Grid
                            .builder()
                            .build();

                    String superId = request.getSuperiorGridid();
                    String superCode = null;

                    grid.setGridLevel(1);

                    if ((superId != null) && (superId != "")) {
                        List<Grid> ls = gridMapper.getbyid(request.getSuperiorGridid());

                        if ((ls != null) && (ls.size() > 0)) {
                            superCode = ls.get(0).getGridCode();
                            grid.setGridLevel(ls.get(0).getGridLevel() + 1);
                            grid.setSuperiorGridid(ls.get(0).getId());
                        }
                    }

                    grid.setGridCode(serialNumberService.getGridCode(hospId, superCode));
                    grid.setHospID(hospId);
                    //grid.setGridFullname(dbCommonService.getGridFullnamebyId(request.getSuperiorGridid())+"->"+request.getGridName());
                }

                try {
                    //依据Excel数据修改业务部门对象
                    BeanUtils.copyBeanIgnoreNull(grid, request);
                    grid.setGridName(request.getGridName().replace(" ", ""));
                    if (ExcelUtils.isNullofString(request.getSuperiorGridid()))
                        grid.setSuperiorGridid(null);
                    if (ExcelUtils.isNullofString(request.getDeptId()))
                        grid.setDeptId(null);
                    if (ExcelUtils.isNullofString(request.getGridPlaceclassid()))
                        grid.setGridPlaceclassid(null);
                    if (ExcelUtils.isNullofString(request.getGridLeaderID()))
                        grid.setGridLeaderID(null);

                    //grid.setGridTelsFromStrings();
                    if ((request.getGridTels() != null) && (request.getGridTels().size() > 0)) {
                        grid.setGridTels(new ArrayList<>());

                        for (String tel : request.getGridTels()) {
                            GridTelBrief gtb = new GridTelBrief();
                            gtb.setGridTel(tel);

                            grid.getGridTels().add(gtb);
                        }
                    }

                    String superFullname = null;
                    if ((request.getSuperiorGridid() != null) && (request.getSuperiorGridid() != "")) {
                        superFullname = dbCommonService.getGridFullnamebyId(request.getSuperiorGridid());
                    }

                    if (superFullname == null)
                        superFullname = request.getGridName();
                    else
                        superFullname = superFullname + "->" + request.getGridName();

                    grid.setGridFullname(superFullname.replace(" ", ""));

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("复制网格对象失败:" + e.getMessage());
                }

                //保存网格对象
                Grid grid_s = save(grid);
                grid.setId(grid_s.getId());

                //设置科室负责人角色
                if (!ExcelUtils.isNullofString(grid.getGridLeaderID())) {
                    if ((!ExcelUtils.isNullofString(leaderId))  && (grid.getGridLeaderID().compareTo(leaderId) != 0) && (userService != null)) {
                        userService.DeleteUserRole(leaderId, "006");
                        if (userService.getUserRoles(grid.getGridLeaderID(), "006") == null)
                            userService.SetUserRole(grid.getGridLeaderID(), "006");
                    }
                    if ((ExcelUtils.isNullofString(leaderId)) && (userService != null)) {
                        if (userService.getUserRoles(grid.getGridLeaderID(), "006") == null)
                            userService.SetUserRole(grid.getGridLeaderID(), "006");
                    }
                }

                if (ExcelUtils.isNullofString(grid.getGridLeaderID()))
                {
                    if ((!ExcelUtils.isNullofString(leaderId)) && (userService != null)) userService.DeleteUserRole(leaderId, "006");
                }

                //删除网格电话列表
                gridRepository.clear_Grid_Tel(grid.getId());

                //保存新的电话信息
                if (grid.getGridTels() != null) {
                    List<GridTel> lst = new ArrayList<GridTel>();

                    for (GridTelBrief gtb : grid.getGridTels()) {
                        GridTel gt = new GridTel();
                        gt.setGrid_tel(gtb.getGridTel());
                        gt.setGrid_ID(grid.getId());
                        lst.add(gt);
                    }
                    gridTelRepository.saveAll(lst);
                }

                //处理并保存网格关联服务班组信息数据
                if ((request.getGridExteams() != null) && (request.getGridExteams().size() > 0)) {
                    List<ExteamGrid> lts = new ArrayList<>();

                    for (String exteamId : request.getGridExteams()) {
                        if (exteamId != null) {
                            ExteamGrid gbt = new ExteamGrid();
                            gbt.setExteamId(exteamId);
                            gbt.setGridId(grid.getId());
                            gbt.setGridCode(grid.getGridCode());

                            lts.add(gbt);
                        }
                    }

                    if ((lts != null) && (lts.size() > 0)) {
                        gridMapper.clearGridexteam(grid.getId()); //删除关联服务班组
                        exteamGridRepository.saveAll(lts);
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("保存网格对象失败:"+e.getMessage());
            }
        }

        return grid;
    }


    /**
     上传网格图片文件
     */
    public JsonResponse uploadGrid_picture(String hospId,String gridid,MultipartFile grid_picturefile) throws Exception
    {
        JsonResponse rt = JsonResponse.fail(1006, "网格图片保存失败");

        try {
            if ((fileService != null) && (dbCommonService != null) && (gridMapper != null)) {
                String hospCode = dbCommonService.getHospCodebyId(hospId);
                List<Grid> ls = gridMapper.getbyid(gridid);
                if ((ls != null) && (ls.size()>0))
                {
                    Grid grid = ls.get(0);

                    //上传网格图片
                    rt = fileService.fileUpload(hospCode, "basic", grid_picturefile);

                    //上传成功则修改数据库记录内容
                    if (rt.getCode() == 0) {
                        //清空原有网格图片文件，包括数据库记录
                        clearGrid_picture(grid.getId());

                        //保存网格图片文件名到数据库记录
                        gridRepository.updateGrid_picture(grid.getId(), rt.getData().toString());
                        rt = JsonResponse.success("网格图片保存成功");
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("网格图片上传失败："+e.getMessage());
        }

        return rt;
    }

    /**
     * 删除网格图片
     * @return
     */
    public JsonResponse clearGrid_picture(String gridid) throws Exception
    {
        JsonResponse  rt = JsonResponse.fail(2001, "删除网格图片失败");

        try {
            if (gridMapper != null) {
                List<Grid> ls = gridMapper.getbyid(gridid);
                if ((ls != null) && (ls.size() > 0)) {
                    Grid grid = ls.get(0);

                    if (fileService != null) {
                        //删除图像文件
                        if ((grid.getGridPicture() != null) && (grid.getGridPicture().length()>0)) {
                            if (fileService.delFile(grid.getGridPicture()).getCode() == 0) {
                                gridRepository.clearGrid_picture(grid.getId());

                                rt = JsonResponse.success("删除网格图片成功");
                            }
                        } else rt = JsonResponse.success("该网格不存在图片文件");
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("删除网格图片失败:"+e.getMessage());
        }

        return rt;
    }


    /**
     * 依据Excel信息保存网格信息
     * @param request
     * @return
     * @throws Exception
     */
    private Grid save(String hospId,GridInfoExcel request) throws Exception
    {

        Grid grid = null;
        String leaderId = null;

        if (gridRepository != null) {

            //从Excel文件中读取数据
            try {
                //网格对象存在性校验
                grid = isExist(hospId,request);

                if (grid != null) leaderId = grid.getGridLeaderID();

                //构造新对象
                if (grid == null) {
                    //构造新的网格对象
                    grid = Grid
                            .builder()
                            .build();

                    String fullName = null;
                    if ((request.getSuperGridName() != null) && (request.getSuperGridName()!=""))
                        fullName = request.getSuperGridName().replace(" ", "");
                    String superCode = null;

                    grid.setGridLevel(1);

                    //获取父节点的，并获取其编码
                    if (!ExcelUtils.isNullofString(fullName)) {
                        List<Grid> ls = gridMapper.getbyFullname(hospId,fullName.trim());

                        if ((ls != null) && (ls.size() > 0)) {
                            superCode = ls.get(0).getGridCode();
                            grid.setGridLevel(ls.get(0).getGridLevel() + 1);
                            grid.setSuperiorGridid(ls.get(0).getId());
                        }
                    }

                    //产生网格编码
                    grid.setGridCode(serialNumberService.getGridCode(hospId, superCode));
                    grid.setHospID(hospId);
                    //grid.setGridFullname(dbCommonService.getGridFullnamebyId(request.getSuperiorGridid())+"->"+request.getGridName());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("从Excel文件读取数据失败:"+e.getMessage());
            }

            //复制对象
            try {
                //依据Excel数据修改业务部门对象
                BeanUtils.copyBeanIgnoreNull(grid, request);

                //设置网格全名
                String fullName = null;
                if((request.getSuperGridName() != null) && (request.getSuperGridName() != ""))
                    fullName = request.getSuperGridName().replace(" ","");
                if ((fullName == null)|| (fullName.trim() == ""))
                    fullName = request.getGridName();
                else
                    fullName = fullName+"->"+request.getGridName();

                grid.setGridFullname(fullName);

                //提取网格所属科室
                if ((request.getDeptName() != null) && (request.getDeptName() != ""))
                {
                    grid.setDeptId(dbCommonService.getDeptIdbyFullname(hospId,request.getDeptName()));
                }

                //提取网格用途
                if ((request.getGridPlaceclassName() != null) && (request.getGridPlaceclassName() != ""))
                {
                    grid.setGridPlaceclassid(dataDictionaryService != null ? dataDictionaryService.getDataDictionary("db_grid_placeclass_type").getIDbyName(request.getGridPlaceclassName()) : null);
                }

                //提取网格负责人并保存
                if ((request.getGridLeaderName() != null) && (request.getGridLeaderName() != "")){
                    String staffId = dbCommonService.getStaffIdbyName(hospId,null,request.getGridLeaderName().trim());
                    if (staffId != null) {
                        grid.setGridLeaderID(staffId);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("复制网格对象失败:" + e.getMessage());
            }

            //保存网格对象
            try {
                Grid grid_s = save(grid);
                grid.setId(grid_s.getId());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception(e.getMessage());
            }

            //设置科室负责人角色
            if (!ExcelUtils.isNullofString(grid.getGridLeaderID())) {
                if ((!ExcelUtils.isNullofString(leaderId))  && (grid.getGridLeaderID().compareTo(leaderId) != 0) && (userService != null)) {
                    userService.DeleteUserRole(leaderId, "006");
                    if (userService.getUserRoles(grid.getGridLeaderID(), "006") == null)
                        userService.SetUserRole(grid.getGridLeaderID(), "006");
                }
                if ((ExcelUtils.isNullofString(leaderId)) && (userService != null)) {
                    if (userService.getUserRoles(grid.getGridLeaderID(), "006") == null)
                        userService.SetUserRole(grid.getGridLeaderID(), "006");
                }
            }

            if (ExcelUtils.isNullofString(grid.getGridLeaderID()))
            {
                if ((!ExcelUtils.isNullofString(leaderId)) && (userService != null)) userService.DeleteUserRole(leaderId, "006");
            }

            //删除网格电话列表
            try {
                gridRepository.clear_Grid_Tel(grid.getId());

                //处理并保存电话数据
                if ((request.getGridTels() != null) && (request.getGridTels() != "")) {

                    String temp = request.getGridTels();
                    if ((temp!=null)&&(temp.length()>0)) {
                        temp = temp.replace(",", ";");
                        temp = temp.replace("；", ";");
                        temp = temp.replace("，", ";");
                    }

                    if ((temp!=null)&&(temp.length()>0)) {
                        String[] m_tels = temp.split(";|；|,|，");

                        if ((m_tels != null) && (m_tels.length > 0)) {
                            List<GridTel> lts = new ArrayList<GridTel>();

                            for (String tel : m_tels) {
                                GridTel gbt = new GridTel();
                                gbt.setGrid_tel(tel);
                                gbt.setGrid_ID(grid.getId());

                                lts.add(gbt);
                            }
                            gridTelRepository.saveAll(lts);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("保存网格电话信息失败:"+e.getMessage());
            }
        }

        //保存网格关联服务班组信息
        try {
            //处理并保存网格关联服务班组信息数据
            if ((request.getGridteams() != null) && (request.getGridteams() != "")) {

                String temp = request.getGridteams();
                if ((temp!=null)&&(temp.length()>0)) {
                    temp = temp.replace(",", ";");
                    temp = temp.replace("；", ";");
                    temp = temp.replace("，", ";");
                }

                if ((temp!=null)&&(temp.length()>0)) {
                    String[] m_teams = temp.split(";|；|,|，");

                    if ((m_teams != null) && (m_teams.length > 0)) {
                        List<ExteamGrid> lts = new ArrayList<>();

                        for (String team : m_teams) {

                            String exteamId = dbCommonService.getExteamIdbyFullName(hospId, team);

                            if (exteamId != null) {
                                ExteamGrid gbt = new ExteamGrid();
                                gbt.setExteamId(exteamId);
                                gbt.setGridId(grid.getId());
                                gbt.setGridCode(grid.getGridCode());

                                lts.add(gbt);
                            }
                        }

                        if ((lts != null) && (lts.size()>0)) {
                            gridMapper.clearGridexteam(grid.getId()); //删除关联服务班组
                            exteamGridRepository.saveAll(lts);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("保存网格关联服务班组信息失败:" + e.getMessage());
        }

        return grid;
    }

    /**
     * 保存对象的存在性校验
     * @param request
     * @return
     * @throws Exception
     */
    private Grid isExist(String hospId,GridSaveRequest request)  throws  Exception
    {
        Grid grid = null;

        if (gridRepository != null) {

            //网格存在性校验
            try {
                if ((request.getId() != null) && (request.getId() != ""))
                    grid = getbyId(request.getId());

                if (grid == null) {
                    if ((!ExcelUtils.isNullofString(hospId)) && (!ExcelUtils.isNullofString(request.getGridName()))) {
                        String superid = null;
                        if (request.getSuperiorGridid() != null) superid = request.getSuperiorGridid();
                        List<Grid> ls = gridMapper.findGridBysuperIDandGridName(hospId.replace(" ",""), superid != null?superid.replace(" ",""):null, request.getGridName().replace(" ",""));

                        if ((ls != null) && (ls.size() > 0)) grid = ls.get(0);
                    }
                }

                if (grid != null)
                {
                    grid.getSuperiorGridname(dbCommonService);
                    grid.getDeptName(dbCommonService);
                    grid.getGridPlaceclassidName(dataDictionaryService);
                    grid.getLeader(userService);
                    grid.getGridPathbyId(dbCommonService);
                    grid.getGridExteam(dbCommonService);

                    //对楼栋的中心位置进行合规性判断
                    if ((grid.getHospID() != null) && (grid.getHospID() != "") && (grid.getGridLevel() == 2)) {
                        Hosp hosp = hospService.getHospbyID(grid.getHospID(), null);

                        //判断楼栋中心位置是否合规，不合规设定为医院中心位置
                        if (BaiduUtils.getDistance(hosp.getHospCenterX(),hosp.getHospCenterY(),grid.getGridcenterX(),grid.getGridcenterY())>(2*BaiduUtils.defaulthospRadius))
                        {
                            grid.setGridcenterX(hosp.getHospCenterX());
                            grid.setGridcenterY(hosp.getHospCenterY());
                        }

                        if (Math.abs(grid.getGridradius()-BaiduUtils.defaultbuildingRadius)>(BaiduUtils.defaultbuildingRadius/2))
                            grid.setGridradius(BaiduUtils.defaultbuildingRadius);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("网格存在性校验失败:" + e.getMessage());
            }
        }

        return grid;
    }

    /**
     * 保存对象的存在性校验,主要是通过全名来判断
     * @param request
     * @return
     * @throws Exception
     */
    private Grid isExist(String hospId,GridInfoExcel request)  throws  Exception
    {
        Grid grid = null;

        if (gridRepository != null) {

            //依据网格全名判断网格存在性
            try {
                if ((!ExcelUtils.isNullofString(hospId))&&(!ExcelUtils.isNullofString(request.getGridName()))) {
                    String fullName;
                    if ((request.getSuperGridName() == null) || (request.getSuperGridName().trim() == ""))
                        fullName = request.getGridName();
                    else {
                        fullName = request.getSuperGridName().replace(" ", "") + "->" + request.getGridName();
                    }

                    List<Grid> ls = gridMapper.getbyFullname(hospId, fullName.replace(" ",""));

                    if ((ls != null) && (ls.size() > 0)) grid = ls.get(0);

                    if (grid != null)
                    {
                        grid.getSuperiorGridname(dbCommonService);
                        grid.getDeptName(dbCommonService);
                        grid.getGridPlaceclassidName(dataDictionaryService);
                        grid.getLeader(userService);
                        grid.getGridPathbyId(dbCommonService);
                        grid.getGridExteam(dbCommonService);

                        //对楼栋的中心位置进行合规性判断
                        if ((grid.getHospID() != null) && (grid.getHospID() != "") && (grid.getGridLevel() == 2)) {
                            Hosp hosp = hospService.getHospbyID(grid.getHospID(), null);

                            //判断楼栋中心位置是否合规，不合规设定为医院中心位置
                            if (BaiduUtils.getDistance(hosp.getHospCenterX(),hosp.getHospCenterY(),grid.getGridcenterX(),grid.getGridcenterY())>(2*BaiduUtils.defaulthospRadius))
                            {
                                grid.setGridcenterX(hosp.getHospCenterX());
                                grid.setGridcenterY(hosp.getHospCenterY());
                            }

                            if (Math.abs(grid.getGridradius()-BaiduUtils.defaultbuildingRadius)>(BaiduUtils.defaultbuildingRadius/2))
                                grid.setGridradius(BaiduUtils.defaultbuildingRadius);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("网格存在性校验失败:" + e.getMessage());
            }
        }

        return grid;
    }

    /**
     * 保存网格对象
     * @param gd
     * @return
     * @throws Exception
     */
    private Grid save(Grid gd) throws Exception
    {
        Grid grid = null;

        if ((gridRepository != null) && (gd != null))
        {
            //保存网格对象
            try {
                grid = gridRepository.save(gd);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("保存网格对象失败:" + e.getMessage());
            }
        }

        return grid;
    }
}
