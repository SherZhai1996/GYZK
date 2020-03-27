package cn.edu.ujs.lp.intells.basicinformation.service;


import cn.edu.ujs.lp.intells.basicinformation.dao.hosp.DeptMapper;
import cn.edu.ujs.lp.intells.basicinformation.dao.hosp.DeptRepository;

import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.Exstaff;
import cn.edu.ujs.lp.intells.basicinformation.entity.Hosp.Department;
import cn.edu.ujs.lp.intells.basicinformation.entity.Hosp.Staff;
import cn.edu.ujs.lp.intells.common.dao.User.UserMapper;
import cn.edu.ujs.lp.intells.common.entity.Hosp.DepartmentBrief;
import cn.edu.ujs.lp.intells.basicinformation.entity.Hosp.DepartmentInfoExcel;

import cn.edu.ujs.lp.intells.basicinformation.request.hosp.DeptPageRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.hosp.DeptSaveRequest;
import cn.edu.ujs.lp.intells.common.entity.Common.TreeInterface;
import cn.edu.ujs.lp.intells.common.entity.Hosp.ExteamBrief;
import cn.edu.ujs.lp.intells.common.entity.Hosp.StaffBrief;
import cn.edu.ujs.lp.intells.common.entity.User.UserRole;
import cn.edu.ujs.lp.intells.common.entity.User.UserRoleBrief;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.service.FileService;
import cn.edu.ujs.lp.intells.common.service.SerialNumberService;
import cn.edu.ujs.lp.intells.common.service.UserService;
import cn.edu.ujs.lp.intells.common.utils.BeanUtils;
import cn.edu.ujs.lp.intells.common.utils.ExcelUtils;
import cn.edu.ujs.lp.intells.common.utils.OSUtils;
import cn.edu.ujs.lp.intells.common.utils.TreeUtil;
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
 * 组织结构Service
 *
 * @author Meredith
 * @data 2019-10-06
 */
@Service
public class DeptService {
    @Autowired
    private DBCommonService dbCommonService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private SerialNumberService serialNumberService;

    @Autowired
    private StaffService staffService;

    /**
     * 依据部门负责人姓名，关联具体职工
     * @return
     * @throws Exception
     */
    public boolean setDeptLeader(String hospId) throws Exception
    {
        if ((dbCommonService != null) && (staffService != null)&&(userMapper != null)) {
            try {
                List<DepartmentBrief> dlst = dbCommonService.getDepts(hospId, null);

                if (dlst != null) {
                    List<Department> list = new ArrayList<Department>();

                    for (DepartmentBrief dptb : dlst) {
                        Staff staff = staffService.findByName(hospId, dptb.getDeptLeaderName());
                        if (staff != null) {
                            Department department = getByID(dptb.getId());
                            department.setDeptLeaderTel(staff.getStaffTel());
                            department.setDeptLeaderId(staff.getId());

                            list.add(department);
                        }
                    }

                    deptRepository.saveAll(list);
                }

                dlst = dbCommonService.getDepts(hospId, null);
                if (dlst != null) {
                    //对科室负责人设置角色
                    for (DepartmentBrief dptb : dlst) {
                        Staff staff = staffService.findByName(hospId, dptb.getDeptLeaderName());

                        if (staff != null) {
                            List<UserRoleBrief> lst = userService.getUserRoles(staff.getId(),"007");

                            if (lst == null) userService.SetUserRole(staff.getId(),"007");
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
     * 导出Excel模板
     * @param response
     * @return
     * @throws Exception
     */
    public JsonResponse exportExcelTemplate(HttpServletResponse response) throws Exception{

        /**String templateName = "Departmenttemplate";

        try {
            ExcelUtils.exportTemplate(DepartmentInfoExcel.class, response, templateName);
            return JsonResponse.success("科室部门导入模板导出成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("科室部门导入模板导出失败:"+e.getMessage());
        }*/
        try
        {
            if (fileService != null) {
                fileService.downloadExcelFile(OSUtils.getDepttemplatefile(),response);
                return JsonResponse.success("科室(部门)导入模板导出成功");
            }
            else return JsonResponse.success("科室(部门)导入模板导出失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("科室(部门)导入模板导出失败:"+e.getMessage());
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
        String fileName = "DepartmentData";

        try {
            if (dbCommonService != null) {
                List<DepartmentBrief> elist = dbCommonService.getDepts(hospId,null);

                List<BaseRowModel> ls = new ArrayList<BaseRowModel>();

                //获取科室部门数据
                for (DepartmentBrief e : elist) {
                    DepartmentInfoExcel ei = new DepartmentInfoExcel();

                    //复制
                    ei.setDeptName(e.getDeptName());
                    ei.setDeptLeaderName(e.getDeptLeaderName());
                    ei.setDeptLeaderTel(e.getDeptLeaderTel());
                    ei.setDeptCode(e.getDeptCode());

                    //提取上级部门
                    if ((e.getDeptFullname() != null) && (e.getDeptFullname().lastIndexOf("->") >= 0)) {
                        ei.setSuperFullname(e.getDeptFullname().substring(0, e.getDeptFullname().lastIndexOf("->")));
                    }

                    ls.add(ei);
                }

                //输出Excel
                ExcelUtils.exportData(DepartmentInfoExcel.class, response, fileName, ls);
                return JsonResponse.success("科室部门数据导出成功");
            }
            else
                return JsonResponse.fail(1001,"从数据库获取科室信息失败!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("科室部门数据导出失败："+e.getMessage());
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
            List<ArrayList> data = (List<ArrayList>) ExcelUtils.readData(file.getInputStream(), DepartmentInfoExcel.class);

            // arraylist转info.excel对象
            if ((data != null) && (data.size() > 0)) { for (ArrayList array:data) {
                try {
                    //从Excel表提取一行数据
                    DepartmentInfoExcel excel = new DepartmentInfoExcel();
                    if ((array.size()>=1) &&(array.get(0) != null)) excel.setDeptName(array.get(0).toString().replace(" ",""));
                    if ((array.size()>=2) &&(array.get(1) != null)) excel.setDeptLeaderName(array.get(1).toString().replace(" ",""));
                    if ((array.size()>=3) &&(array.get(2) != null)) excel.setDeptLeaderTel(array.get(2).toString().replace(" ",""));
                    if ((array.size()>=4) &&(array.get(3) != null)) excel.setDeptCode( array.get(3).toString().replace(" ",""));
                    if ((array.size()>=5) &&(array.get(4) != null)) excel.setSuperFullname(array.get(4).toString().replace(" ",""));

                    //入库保存
                    if ((excel.getDeptName() != null) && (excel.getDeptName() != "")) {
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
                    return  JsonResponse.success("科室部门数据导入成功",String.valueOf(m_count)+","+String.valueOf(m_fail));
                else
                    return JsonResponse.fail(1009, "科室部门数据导入失败");
            }
            else
                return JsonResponse.fail(1009, "读入科室部门数据为null");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new  Exception("读入科室部门数据信息失败:"+e.getMessage());
        }
    }



    /**
     * 分页查找
     * @param request
     * @return
     */
    public PageResponse<DepartmentBrief> page(String hospId,DeptPageRequest request) throws Exception
    {
        try {
            request.setHospId(hospId);
            Page<DepartmentBrief> page = deptMapper.page(request, new RowBounds(request.getIndex(), request.getSize()));
            List<Long> lst = deptMapper.Rcount(request);
            Long total = (long)0L;
            if ((lst!=null) && (lst.size()>0))
                total = lst.get(0);

            if ((page != null) && (page.size()>0))
            {
                for (DepartmentBrief db:page)
                {
                    if ((db.getSuperiorId() != null) && (db.getSuperiorId() != ""))
                        db.setSuperiorName(dbCommonService.getDeptFullnamebyId(db.getSuperiorId()));
                }
            }

            return new PageResponse<DepartmentBrief>(page, request, total);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("科室部门分页查询失败："+e.getMessage());
        }
    }

    /**
     * 依据ID获取一个完整的科室部门对象
     * @param id
     * @return
     * @throws Exception
     */
    public Department getByID(String id) throws  Exception
    {
        Department result=null;

        if (deptMapper != null)
        {
            try
            {
                if (!ExcelUtils.isNullofString(id)) {
                    List<Department> lst = deptMapper.getbyid(id);
                    if ((lst != null) && (lst.size() > 0)) {
                        result = lst.get(0);
                        if (result != null)
                        {
                            if (result.getSuperiorId() != null)
                            {
                                result.setSuperiorName(dbCommonService.getDeptFullnamebyId(result.getSuperiorId()));
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据ID:["+id+"]获取科室部门对象失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 删除指定科室编码的科室部门对象
     * @param dCode
     * @return
     */
    public boolean delete(String dCode) throws  Exception
    {
        boolean result = false ;

        if (deptMapper != null)
        {
            try
            {
                if (!ExcelUtils.isNullofString(dCode)) {
                    deptMapper.delete(dCode);
                    result = true;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("删除科室部门对象["+dCode+"]异常:"+e.getMessage());
            }
        }

        return result ;
    }

    /**
     * 依据科室全名获取科室对象
     * @param hospId
     * @param fullname
     * @return
     * @throws Exception
     */
    public Department getByFullname(String hospId,String fullname) throws  Exception
    {
        Department result=null;

        if ((deptMapper != null)&&(!ExcelUtils.isNullofString(hospId))&&(!ExcelUtils.isNullofString(fullname)))
        {
            try
            {
                List<Department> lst = deptMapper.findByFullname(hospId,fullname);
                if ((lst != null) && (lst.size()>0)) {
                    result = lst.get(0);

                    if (result.getSuperiorId() != null)
                    {
                        result.setSuperiorName(dbCommonService.getDeptFullnamebyId(result.getSuperiorId()));
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据全名:["+fullname+"]获取科室部门对象失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 科室对象的存在性校验
     * @param hospId
     * @param supperId
     * @param deptName
     * @return
     * @throws Exception
     */
    private Department isExist(String hospId, String supperId,String deptName)  throws  Exception
    {
        Department department = null;

        if (deptMapper != null) {

            //网格存在性校验
            try {

                if ((!ExcelUtils.isNullofString(hospId)) && (!ExcelUtils.isNullofString(deptName))) {
                    List<Department> ls = deptMapper.findByName(hospId.trim(), (supperId!=null?supperId.trim():null), deptName.trim());
                    if ((ls != null) && (ls.size() > 0))
                    {
                        department = ls.get(0);

                        if (department.getSuperiorId() != null)
                        {
                            department.setSuperiorName(dbCommonService.getDeptFullnamebyId(department.getSuperiorId()));
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("科室对象存在性校验失败:" + e.getMessage());
            }
        }

        return department;
    }

    /**
     * 保存科室对象信息，如果不存在则新建，否则更新
     * @param request
     * @return
     */
    public Department save(String hospId,DeptSaveRequest request) throws Exception{

        Department department = null;
        String leaderId = null;

        if ((deptMapper!= null) && (deptRepository != null) && (request != null)) {

            if ((request.getId() != null) && (request.getId().trim() != "")) department = getByID(request.getId());

            if (department == null)  department = isExist(hospId, request.getSuperiorId(), request.getDeptName());

            if (department != null) leaderId = department.getDeptLeaderId();

            if (department == null) {
                //构造新的科室部门对象
                department = Department
                        .builder()
                        .build();
            }

            try {
                department.setHospID(hospId);
                BeanUtils.copyBeanIgnoreNull(department, request);

                if (ExcelUtils.isNullofString(request.getDeptLeaderId()))
                    department.setDeptLeaderId(null);
                if (ExcelUtils.isNullofString(request.getSuperiorId()))
                    department.setSuperiorId(request.getSuperiorId());

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("复制科室部门对象失败:" + e.getMessage());
            }

            //保存科室部门对象
            try {
                //获取父节点自编码
                String superCode = null;
                if ((department.getSuperiorId() != null) && (department.getSuperiorId().trim() != "")) {
                    Department st = getByID(department.getSuperiorId().trim());
                    if (st.getDeptFullname() != null) {
                        department.setDeptFullname(st.getDeptFullname() + "->" + department.getDeptName());
                    }
                    department.setDeptLevel(st.getDeptLevel() + 1);
                    superCode = st.getDeptSelfcode();
                } else {
                    department.setDeptFullname(department.getDeptName());
                    department.setDeptLevel(1);
                }

                department.setDeptSelfcode(serialNumberService.getDTCode(hospId, superCode));
                department.setHospID(hospId);
                Department department_s = deptRepository.save(department);
                department.setId(department_s.getId());

                //设置科室负责人角色
                if (!ExcelUtils.isNullofString(department.getDeptLeaderId())) {
                    if ((!ExcelUtils.isNullofString(leaderId) ) && (department.getDeptLeaderId().compareTo(leaderId) != 0) && (userService != null)) {
                        userService.DeleteUserRole(leaderId, "007");
                        if (userService.getUserRoles(department.getDeptLeaderId(), "007") == null)
                            userService.SetUserRole(department.getDeptLeaderId(), "007");
                    }
                    if (ExcelUtils.isNullofString(leaderId)) {
                        if (userService.getUserRoles(department.getDeptLeaderId(), "007") == null)
                            userService.SetUserRole(department.getDeptLeaderId(), "007");
                    }
                }

                if (ExcelUtils.isNullofString(department.getDeptLeaderId())){
                    if ((!ExcelUtils.isNullofString(leaderId)) && (userService != null))
                        userService.DeleteUserRole(leaderId, "007");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("保存科室部门对象失败:" + e.getMessage());
            }
        }

        return department;
    }

    /**
     * 保存科室部门
     * @param hospId
     * @param request
     * @return
     * @throws Exception
     */
    private Department save(String hospId,DepartmentInfoExcel request) throws Exception{

        Department department = null;
        String leaderId = null;

        if ((deptMapper != null) && (deptRepository != null)) {

            //科室部门存在性校验
            Department supperDept = null;
            if ((request.getSuperFullname() != null) && (request.getSuperFullname().trim().length() >0))
            {
                supperDept = getByFullname(hospId,request.getSuperFullname().trim());
            }

            department = isExist(hospId,(supperDept != null?supperDept.getId():null),request.getDeptName());
            if (department != null) leaderId = department.getDeptLeaderId();

            try {
                if (department == null) {
                    //构造新的服务班组对象
                    department = Department
                            .builder()
                            .build();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("创建科室部门对象失败："+e.getMessage());
            }

            if (department != null) {

                try {
                    BeanUtils.copyBeanIgnoreNull(department,request);
                    if (supperDept != null) department.setSuperiorId(supperDept.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("复制科室部门对象失败:"+e.getMessage());
                }

                //保存科室部门对象
                try {
                    if (supperDept != null) {
                        department.setDeptFullname(supperDept.getDeptFullname() + "->" + department.getDeptName());
                        department.setDeptLevel(supperDept.getDeptLevel()+1);
                    }
                    else {
                        department.setDeptFullname(department.getDeptName());
                        department.setDeptLevel(1);
                    }

                    //产生科室部门编码
                    department.setDeptSelfcode(serialNumberService.getDTCode(hospId,(supperDept!=null?supperDept.getDeptSelfcode():null)));
                    department.setHospID(hospId);
                    Department department_s = deptRepository.save(department);
                    department.setId(department_s.getId());

                    //设置科室负责人角色
                    if (!ExcelUtils.isNullofString(department.getDeptLeaderId())) {
                        if ((!ExcelUtils.isNullofString(leaderId) ) && (department.getDeptLeaderId().compareTo(leaderId) != 0) && (userService != null)) {
                            userService.DeleteUserRole(leaderId, "007");
                            if (userService.getUserRoles(department.getDeptLeaderId(), "007") == null)
                                userService.SetUserRole(department.getDeptLeaderId(), "007");
                        }
                        if (ExcelUtils.isNullofString(leaderId)) {
                            if (userService.getUserRoles(department.getDeptLeaderId(), "007") == null)
                                userService.SetUserRole(department.getDeptLeaderId(), "007");
                        }
                    }

                    if (ExcelUtils.isNullofString(department.getDeptLeaderId())){
                        if ((!ExcelUtils.isNullofString(leaderId)) && (userService != null))
                            userService.DeleteUserRole(leaderId, "007");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("保存科室部门对象失败:" + e.getMessage());
                }
            }
        }

        return department;
    }

}
