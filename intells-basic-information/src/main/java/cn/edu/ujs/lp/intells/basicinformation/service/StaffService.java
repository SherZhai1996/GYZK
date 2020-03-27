package cn.edu.ujs.lp.intells.basicinformation.service;

import cn.edu.ujs.lp.intells.basicinformation.dao.hosp.*;

import cn.edu.ujs.lp.intells.basicinformation.entity.Hosp.*;

import cn.edu.ujs.lp.intells.basicinformation.request.hosp.StaffPageRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.hosp.StaffSaveRequest;
import cn.edu.ujs.lp.intells.common.dao.User.UserGridRepository;
import cn.edu.ujs.lp.intells.common.dao.User.UserMapper;
import cn.edu.ujs.lp.intells.common.dao.User.UserRoleRepository;
import cn.edu.ujs.lp.intells.common.entity.Hosp.StaffBrief;
import cn.edu.ujs.lp.intells.common.entity.User.UserGrid;
import cn.edu.ujs.lp.intells.common.entity.User.UserGridBrief;
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
 *医院职工service
 */
@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private UserRoleRepository staffRoleRepository;

    @Autowired
    private UserGridRepository staffGridRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private DataDictionaryService dataDictionaryService;

    @Autowired
    private DBCommonService dbCommonService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private GridService gridService;


    /**
     * 导出Excel模板
     * @param response
     * @return
     * @throws Exception
     */
    public JsonResponse exportExcelTemplate(HttpServletResponse response) throws Exception{

        /**String templateName = "Stafftemplate";

        try {
            ExcelUtils.exportTemplate(StaffInfoExcel.class, response, templateName);
            return JsonResponse.success("在职员工导入模板导出成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("在职员工导入模板导出失败:"+e.getMessage());
        }*/
        try
        {
            if (fileService != null) {
                fileService.downloadExcelFile(OSUtils.getStafftemplatefile(),response);
                return JsonResponse.success("医院职工导入模板导出成功");
            }
            else return JsonResponse.success("医院职工导入模板导出失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("医院职工导入模板导出失败:"+e.getMessage());
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
        String fileName = "StaffData";

        try {
            if (dbCommonService != null) {
                List<StaffBrief> elist = dbCommonService.getStaffs(hospId, null, null);

                List<BaseRowModel> ls = new ArrayList<BaseRowModel>();

                //获取科室部门数据
                for (StaffBrief e : elist) {
                    StaffInfoExcel ei = new StaffInfoExcel();

                    //复制
                    BeanUtils.copyBeanIgnoreNull(ei, e);

                    //特殊字段的处理，即由ID转换为名称，日期转换为字符串
                    if (e.getStaffSex() != null)
                        ei.setStaffSex(dataDictionaryService != null ? dataDictionaryService.getDataDictionary("db_sex").getNamebyID(e.getStaffSex()) : null);

                    if (e.getStaffBirthday() != null)
                        ei.setStaffBirthday(ExcelUtils.DateToString(e.getStaffBirthday(), "yyyyMMdd"));

                    if (e.getStaffAdminisPosition() != null)
                        ei.setStaffAdminisPosition(dataDictionaryService != null ? dataDictionaryService.getDataDictionary("db_staff_adminis_position").getNamebyID(e.getStaffAdminisPosition().trim()) : null);

                    if (e.getStaffTechnicalPosition() != null)
                        ei.setStaffTechnicalPosition(dataDictionaryService != null ? dataDictionaryService.getDataDictionary("db_staff_technical_position").getNamebyID(e.getStaffTechnicalPosition().trim()) : null);

                    if (e.getStaffDegree() != null)
                        ei.setStaffDegree(dataDictionaryService != null ? dataDictionaryService.getDataDictionary("db_staff_degree").getNamebyID(e.getStaffDegree().trim()) : null);

                    ls.add(ei);
                }

                //输出Excel
                ExcelUtils.exportData(StaffInfoExcel.class, response, fileName, ls);
                return JsonResponse.success("在职员工数据导出成功");
            }
            else
                return JsonResponse.fail(1001,"从数据库中读取在职员工信息失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("在职员工数据导出失败："+e.getMessage());
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
            List<ArrayList> data = (List<ArrayList>) ExcelUtils.readData(file.getInputStream(), StaffInfoExcel.class);

            // arraylist转info.excel对象
            if ((data != null) && (data.size() > 0)) {
                for (ArrayList array : data) {
                    try {
                        //从Excel表提取一行数据
                        StaffInfoExcel excel = new StaffInfoExcel();

                        if ((array.size() >= 1) && (array.get(0) != null)) excel.setStaffName(array.get(0).toString().replace(" ",""));
                        if ((array.size() >= 2) && (array.get(1) != null)) excel.setStaffSex(array.get(1).toString().replace(" ",""));
                        if ((array.size() >= 3) && (array.get(2) != null)) excel.setStaffMobile(array.get(2).toString().replace(" ",""));
                        if ((array.size() >= 4) && (array.get(3) != null))
                            excel.setStaffBirthday( array.get(3).toString().replace(" ",""));
                        if ((array.size() >= 5) && (array.get(4) != null)) excel.setDeptName(array.get(4).toString().replace(" ",""));
                        if ((array.size() >= 6) && (array.get(5) != null))
                            excel.setStaffAdminisPosition( array.get(5).toString().replace(" ",""));
                        if ((array.size() >= 7) && (array.get(6) != null))
                            excel.setStaffTechnicalPosition(array.get(6).toString().replace(" ",""));
                        if ((array.size() >= 8) && (array.get(7) != null))
                            excel.setStaffDegree(array.get(7).toString().replace(" ",""));

                        //入库保存
                        if ((excel.getStaffName() != null) && (excel.getStaffName() != "")) {
                            save(hospId, excel);
                            m_count++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_fail++;
                    }
                }

                if (m_count > 0) {

                    //更新科室部门领导人信息
                    try {
                        gridService.setGridLeader(hospId);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new Exception("更新网格区域负责人信息失败:" + e.getMessage());
                    }

                    //更新科室部门领导人信息
                    try {
                        deptService.setDeptLeader(hospId);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new Exception("更新科室部门负责人信息失败:" + e.getMessage());
                    }

                    return JsonResponse.success("在职员工数据导入成功", String.valueOf(m_count) + "," + String.valueOf(m_fail));
                } else
                    return JsonResponse.fail(1009, "在职员工数据导入失败");
            } else
                return JsonResponse.fail(1009, "读入在职员工数据为null");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new  Exception("读入在职员工数据信息失败:"+e.getMessage());
        }
    }

    /**
     上传职工照片文件
     */
    public JsonResponse uploadStaff_picture(String hospId,String staffid,MultipartFile picturefile) throws Exception
    {
        JsonResponse rt = JsonResponse.fail(1006, "职工照片保存失败");

        try {
            if ((fileService != null) && (dbCommonService != null) && (staffMapper != null)) {
                String hospCode = dbCommonService.getHospCodebyId(hospId);

                List<Staff> ls = staffMapper.getbyid(staffid);
                if ((ls != null) && (ls.size()>0))
                {
                    Staff staff = ls.get(0);

                    //上传网格图片
                    rt = fileService.fileUpload(hospCode, "basic", picturefile);

                    //上传成功则修改数据库记录内容
                    if (rt.getCode() == 0) {
                        //删除原有照片
                        if ((staff.getStaffPicture() != null)&&(staff.getStaffPicture() != ""))
                            fileService.delFile(staff.getStaffPicture());

                        //保存网格图片文件名到数据库记录
                        staffRepository.UpdateStaff_pic(staff.getId(), rt.getData().toString());
                        rt = JsonResponse.success("职工照片保存成功",rt.getData().toString());
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
     * 删除职工照片
     * @return
     */
    public JsonResponse clearStaff_picture(String staffid) throws Exception
    {
        JsonResponse  rt = JsonResponse.fail(2001, "删除职工照片失败");

        try {
            if ((staffRepository != null) && (staffMapper != null)) {
                List<Staff> ls = staffMapper.getbyid(staffid);
                if ((ls != null) && (ls.size() > 0)) {
                    Staff staff = ls.get(0);

                    if (fileService != null) {
                        //删除图像文件
                        if (fileService.delFile(staff.getStaffPicture()).getCode() == 0) {
                            staffRepository.clearStaff_pic(staff.getId());

                            rt = JsonResponse.success("删除职工照片成功");
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("删除职工照片失败:"+e.getMessage());
        }

        return rt;
    }

    /**
     * 分页查找
     * @param request
     * @return
     */
    public PageResponse<StaffBrief> page(String hospId, StaffPageRequest request) throws Exception
    {
        try {
            request.setHospId(hospId);
            Page<StaffBrief> page = staffMapper.page(request, new RowBounds(request.getIndex(), request.getSize()));
            List<Long> lst = staffMapper.Rcount(request);
            Long total = 0L;
            if ((lst!=null) && (lst.size()>0))
                total = lst.get(0);

            if (page != null)
            {
                for (StaffBrief sb:page)
                {

                    sb.getStaffSexName(dataDictionaryService);
                    sb.getStaffAdminisPositionName(dataDictionaryService);
                    sb.getStaffTechnicalPositionName(dataDictionaryService);
                    sb.getStaffEducationPositionName(dataDictionaryService);
                    sb.getStaffTutorTypeName(dataDictionaryService);
                    sb.getStaffDegreeName(dataDictionaryService);
                    sb.getstaffActivestatusName();
                    sb.getIsuseMedicalhelpName();
                    sb.getDeptName(dbCommonService);
                }
            }
            return new PageResponse<StaffBrief>(page, request, total);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("在职员工分页查询失败："+e.getMessage());
        }
    }

    /**
     * 依据ID获取一个完整的科室部门对象
     * @param id
     * @return
     * @throws Exception
     */
    public Staff getByID(String id) throws  Exception
    {
        Staff result=null;

        if (staffMapper != null)
        {
            try
            {
                List<Staff> lst = staffMapper.getbyid(id);
                if ((lst != null) && (lst.size()>0))
                    result = lst.get(0);

                if (result != null)
                {
                    result.getStaffSexName(dataDictionaryService);
                    result.getStaffAdminisPositionName(dataDictionaryService);
                    result.getStaffTechnicalPositionName(dataDictionaryService);
                    result.getStaffEducationPositionName(dataDictionaryService);
                    result.getStaffTutorTypeName(dataDictionaryService);
                    result.getStaffDegreeName(dataDictionaryService);
                    result.getstaffActivestatusName();
                    result.getIsuseMedicalhelpName();
                    result.getFullName();
                    result.getDeptName(dbCommonService);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据ID:["+id+"]获取在职员工对象失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 删除指定科室编码的科室部门对象
     * @param id
     * @return
     */
    public boolean delete(String id) throws  Exception
    {
        boolean result = false ;

        if (staffMapper != null)
        {
            try
            {
                staffMapper.delete(id);
                result = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("删除在职员工对象["+id+"]异常:"+e.getMessage());
            }
        }

        return result ;
    }

    /**
     * 在职员工的存在性校验
     * @param hospId
     * @param staffName
     * @return
     * @throws Exception
     */
    private Staff isExist(String hospId, String staffName)  throws  Exception
    {
        Staff department = null;

        if (staffMapper != null) {

            //在职员工存在性校验
            try {

                List<Staff> ls = staffMapper.findByName(hospId.trim(),null,staffName!=null?staffName.trim():null);
                if ((ls != null) && (ls.size() > 0)) department = ls.get(0);

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("在职员工存在性校验失败:" + e.getMessage());
            }
        }

        return department;
    }

    /**
     * 检索员工
     * @param hospId
     * @param staffName
     * @return
     * @throws Exception
     */
    public Staff findByName(String hospId, String staffName) throws  Exception
    {
        try
        {
            return isExist(hospId,staffName);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("检索在职员工失败:"+e.getMessage());
        }
    }

    /**
     * 保存在职职工的附加信息
     * @param hospId
     * @param staff
     * @return
     * @throws Exception
     */
    private boolean saveattr(String hospId,Staff staff) throws Exception
    {
        boolean result = false ;

        if ((userMapper != null) && (staffRepository != null)) {
            try {

                //删除原有相关信息
                userMapper.deleteUserGrid(staff.getId());

                //保存责任网格区域
                if ((staff.getGridIds() != null) && (staff.getGridIds().size() > 0)) {
                    List<UserGrid> lts = new ArrayList<UserGrid>();

                    for (UserGridBrief gid : staff.getGridIds()) {
                        UserGrid sr = new UserGrid();
                        sr.setUserId(staff.getId());
                        sr.setGridId(gid.getGridId());
                        lts.add(sr);
                    }
                    staffGridRepository.saveAll(lts);
                }

                result=true;
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("保存在职员工附加信息失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 保存在职员工信息，如果不存在则新建，否则更新
     * @param request
     * @return
     */
    public Staff save(String hospId, StaffSaveRequest request) throws Exception{

        boolean isexist = true ;

        Staff staff = null;

        if ((staffMapper!= null) && (staffRepository != null)&&(request != null)) {

            if ((request.getId() != null) && (request.getId().trim() != "")) staff = getByID(request.getId());

            if (staff == null) {
                //在职员工存在性校验
                staff = isExist(hospId, request.getStaffName());
            }

            if (staff == null) {
                //构造新的在职员工对象
                staff = Staff
                        .builder()
                        .build();

                isexist = false ;
            }

            try {
                String loginname = staff.getStaffLoginName();

                staff.setHospID(hospId);
                BeanUtils.copyBeanIgnoreNull(staff,request);

                if (ExcelUtils.isNullofString(request.getDeptId())) staff.setDeptId(null);

                //登陆名修改，复位密码
                if ((loginname != null) && (staff.getStaffLoginName() != null) && (loginname.compareTo(staff.getStaffLoginName()) != 0))
                {
                    staff.setStaffLoginPassword(null);
                }

                if ((request.getStaffBirthday() != null)&&(request.getStaffBirthday().length()>=10))
                    staff.setStaffBirthday(ExcelUtils.StringToDate(request.getStaffBirthday(),"yyyy-MM-dd"));

                if ((request.getGridFullnames() != null) && (request.getGridFullnames().size()>0))
                    staff.setGridIdsFromStrings(request.getGridFullnames(),dbCommonService);

                if((request.getRoleNames() != null) && (request.getRoleNames().size()>0))
                    staff.setRolesFromStrings(request.getRoleNames(),dbCommonService);
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new Exception("复制在职员工对象失败:" + e.getMessage());
            }

            //保存在职员工对象
            try {
                staff.setHospID(hospId);
                Staff staff_s = save(staff);
                staff.setId(staff_s.getId());

                if ((userService != null) && (!isexist)) userService.SetUserRole(staff.getId(),"008");

                //保留附加信息
                saveattr(hospId,staff);

                //校验用户的角色
                userMapper.VerifyUserRoles(staff.getId());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("保存在职员工对象失败:"+e.getMessage());
            }
        }

        return staff;
    }

    /**
     * 保存在职员工
     * @param hospId
     * @param request
     * @return
     * @throws Exception
     */
    private Staff save(String hospId,StaffInfoExcel request) throws Exception{

        boolean isexist = true ;
        Staff staff = null;

        if ((staffMapper != null) && (staffRepository != null)) {

            //在职员工存在性校验
            Department dept = null;
            if ((request.getDeptName() != null) && (request.getDeptName().trim().length() >0))
            {
                dept = deptService.getByFullname(hospId,request.getDeptName());
            }

            staff = isExist(hospId,request.getStaffName());

            try {
                if (staff == null) {
                    //构造新的在职员工对象
                    staff = Staff
                            .builder()
                            .build();

                    isexist = false;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("创建在职员工对象失败："+e.getMessage());
            }

            if (staff != null) {

                try {
                    BeanUtils.copyBeanIgnoreNull(staff,request);

                    //特殊字段的处理
                    staff.setStaffBirthday(ExcelUtils.StringToDate(request.getStaffBirthday(),"yyyyMMdd"));
                    staff.setStaffSexFromName(request.getStaffSex(),dataDictionaryService);
                    staff.setStaffAdminisPositionFromName(request.getStaffAdminisPosition(),dataDictionaryService);
                    staff.setStaffTechnicalPositionFromName(request.getStaffTechnicalPosition(),dataDictionaryService);
                    staff.setStaffDegreeFromName(request.getStaffDegree(),dataDictionaryService);

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("复制在职员工对象失败:"+e.getMessage());
                }

                //保存在职员工对象
                try {
                    staff.setHospID(hospId);

                    if (dept != null)   staff.setDeptId(dept.getId());

                    staff.setStaffLoginPassword(null); //复位密码
                    Staff staff_s = save(staff);
                    staff.setId(staff_s.getId());

                    if ((userService != null) && (!isexist)) userService.SetUserRole(staff.getId(),"008");

                    //保留附加信息
                    saveattr(hospId,staff);

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("保存在职员工对象失败:" + e.getMessage());
                }
            }
        }

        return staff;
    }


    /**
     * 保存在职职工对象
     * @param value
     * @return
     * @throws Exception
     */
    private Staff save(Staff value) throws Exception
    {
        Staff staff = null;

        if ((staffRepository != null) && (value != null))
        {
            //保存网格对象
            try {
                if ((value.getStaffLoginName()==null) || (value.getStaffLoginName().trim() ==""))
                    value.setStaffLoginName(value.getStaffMobile());
                if ((value.getStaffLoginNickname()==null) || (value.getStaffLoginNickname().trim() ==""))
                    value.setStaffLoginNickname(value.getStaffLoginName());
                if ((value.getStaffLoginPassword()==null) || (value.getStaffLoginPassword().trim()==""))
                    value.setStaffLoginPassword(MD5Util.md5(value.getStaffLoginName(),"12345678"));

                if ((value.getDeptName() != null) && (value.getDeptName() != ""))
                    value.setRemark(value.getDeptName()+"->"+value.getStaffName());

                if ((value.getDeptId() != null) && (value.getDeptId() != ""))
                    value.setRemark(dbCommonService.getDeptFullnamebyId(value.getDeptId())+"->"+value.getStaffName());

                value.setUserType("2");
                staff = staffRepository.save(value);

                value.setId(staff.getId());

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("保存在职职工对象失败:" + e.getMessage());
            }
        }

        return value;
    }

}
