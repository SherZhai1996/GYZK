package cn.edu.ujs.lp.intells.common.service;

import cn.edu.ujs.lp.intells.common.dao.User.UserHospRepository;
import cn.edu.ujs.lp.intells.common.dao.User.UserMapper;
import cn.edu.ujs.lp.intells.common.dao.User.UserRepository;
import cn.edu.ujs.lp.intells.common.dao.User.UserRoleRepository;
import cn.edu.ujs.lp.intells.common.entity.User.*;
import cn.edu.ujs.lp.intells.common.request.User.UserPageRequest;
import cn.edu.ujs.lp.intells.common.request.User.UserSaveRequest;
import cn.edu.ujs.lp.intells.common.request.User.UserSetPWRequest;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.utils.BeanUtils;
import cn.edu.ujs.lp.intells.common.utils.ExcelUtils;
import cn.edu.ujs.lp.intells.common.utils.MD5Util;
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
import java.util.Arrays;
import java.util.List;

/**
 *医院职工service
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserHospRepository userHospRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private DataDictionaryService dataDictionaryService;

    @Autowired
    private DBCommonService dbCommonService;


    /**
     * 导出Excel模板
     * @param response
     * @return
     * @throws Exception
     */
    public JsonResponse exportExcelTemplate(HttpServletResponse response) throws Exception{

        /**String templateName = "Usertemplate";

        try {
            ExcelUtils.exportTemplate(UserInfoExcel.class, response, templateName);
            return JsonResponse.success("系统用户导入模板导出成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("系统用户导入模板导出失败:"+e.getMessage());
        }*/

        try
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
        }
    }

    /**
     * 导出Excel数据
     * @param response
     * @return
     * @throws Exception
     */
    public JsonResponse exportExcelData(HttpServletResponse response) throws Exception
    {
        String fileName = "UserData";

        try {
            if (userMapper != null) {
                UserPageRequest userPageRequest = new UserPageRequest();
                userPageRequest.setHospId(null);
                userPageRequest.setUserMobile(null);
                userPageRequest.setUserName(null);
                List<UserBrief> elist = list(null,userPageRequest);

                List<BaseRowModel> ls = new ArrayList<BaseRowModel>();

                for (UserBrief e : elist) {
                    UserInfoExcel ei = new UserInfoExcel();

                    //复制
                    BeanUtils.copyBeanIgnoreNull(ei, e);

                    ls.add(ei);
                }

                //输出Excel
                ExcelUtils.exportData(UserInfoExcel.class, response, fileName, ls);
                return JsonResponse.success("系统用户数据导出成功");
            }
            else
                return JsonResponse.fail(1001,"从数据库中读取系统用户信息失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("系统用户数据导出失败："+e.getMessage());
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
            List<ArrayList> data = (List<ArrayList>) ExcelUtils.readData(file.getInputStream(), UserInfoExcel.class);

            // arraylist转info.excel对象
            if ((data != null) && (data.size() > 0)) {
                for (ArrayList array : data) {
                    try {
                        //从Excel表提取一行数据
                        UserInfoExcel excel = new UserInfoExcel();

                        if ((array.size() >= 1) && (array.get(0) != null)) excel.setUserName(array.get(0).toString().replace(" ",""));
                        if ((array.size() >= 2) && (array.get(1) != null)) excel.setUserSexName(array.get(1).toString().replace(" ",""));
                        if ((array.size() >= 3) && (array.get(2) != null)) excel.setUserMobile(array.get(2).toString().replace(" ",""));

                        //入库保存
                        if (excel.getUserName() != null) {
                            save(hospId, excel);
                            m_count++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        m_fail++;
                    }
                }

                if (m_count > 0) {
                    return JsonResponse.success("系统用户数据导入成功", String.valueOf(m_count) + "," + String.valueOf(m_fail));
                } else
                    return JsonResponse.fail(1009, "系统用户数据导入失败");
            } else
                return JsonResponse.fail(1009, "读入系统用户数据为null");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new  Exception("读入系统用户数据信息失败:"+e.getMessage());
        }
    }

    /**
     上传系统用户logo照片文件
     */
    public JsonResponse upload_logopicture(String userid,MultipartFile picturefile) throws Exception
    {
        JsonResponse rt = JsonResponse.fail(1006, "用户logo图片保存失败");

        try {
            if ((fileService != null) && (dbCommonService != null) && (userMapper != null) && (picturefile != null)) {

                List<User> ls = userMapper.getbyid(userid);
                if ((ls != null) && (ls.size()>0))
                {
                    User user = ls.get(0);

                    //上传网格图片
                    rt = fileService.fileUpload(null, "basic", picturefile);

                    //上传成功则修改数据库记录内容
                    if (rt.getCode() == 0) {
                        //删除原有照片
                        if ((user.getUserPicpath() != null)&&(user.getUserPicpath() != ""))
                            fileService.delFile(user.getUserPicpath());

                        //保存网格图片文件名到数据库记录
                        userRepository.UpdateUserlogopic(user.getId(), rt.getData().toString());
                        rt = JsonResponse.success("系统用户logo图片保存成功",rt.getData().toString());
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("系统用户logo图片上传失败："+e.getMessage());
        }

        return rt;
    }

    /**
     * 删除系统用户logo图片
     * @return
     */
    public JsonResponse clear_logopicture(String userid) throws Exception
    {
        JsonResponse  rt = JsonResponse.fail(2001, "删除系统用户logo图片失败");

        try {
            if ((userRepository != null) && (userMapper != null) && (userid != null)) {
                List<User> ls = userMapper.getbyid(userid);
                if ((ls != null) && (ls.size() > 0)) {
                    User user = ls.get(0);

                    if (fileService != null) {
                        //删除图像文件
                        if (fileService.delFile(user.getUserPicpath()).getCode() == 0) {
                            userRepository.clearUserlogopic(user.getId());

                            rt = JsonResponse.success("删除系统用户logo图片成功");
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("删除系统用户logo图片失败:"+e.getMessage());
        }

        return rt;
    }

    /**
     *
     * 依据医院ID和用户名判断某个用户是否存在
     * @param hospId
     * @param userName
     * @return
     * @throws Exception
     */
    public User isExistofUserName(String hospId, String userName)  throws  Exception
    {
        User user = null;

        if ((userMapper != null)&&(userName != null)&&(userName.trim() != "")) {

            //系统用户存在性校验
            try {

                List<User> ls = userMapper.findByName(hospId,userName.trim(),null,null);
                if ((ls != null) && (ls.size() > 0)) user = ls.get(0);

                if (user != null) {
                    user.getAccesshospnames(dbCommonService);
                    user.getUserSexName(dataDictionaryService);
                    user.getHospName();
                    user.getUserActivestateName();
                    user.getRoleNames();
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("依据医院ID和用户名判断某个用户是否存在失败:" + e.getMessage());
            }
        }

        return user;
    }

    /**
     * 依据用户手机号判断某个用户是否存在
     * @param userMobile
     * @return
     * @throws Exception
     */
    public User isExistofUserMobile(String userMobile)  throws  Exception
    {
        User user = null;

        if ((userMapper != null)&&(userMobile != null)&&(userMobile.trim() != "")) {

            //系统用户存在性校验
            try {

                List<User> ls = userMapper.findByName(null,null,userMobile.trim(),null);
                if ((ls != null) && (ls.size() > 0)) user = ls.get(0);

                if (user != null) {
                    user.getAccesshospnames(dbCommonService);
                    user.getUserSexName(dataDictionaryService);
                    user.getHospName();
                    user.getUserActivestateName();
                    user.getRoleNames();
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("依据用户手机号判断某个用户是否存在失败:" + e.getMessage());
            }
        }

        return user;
    }

    /**
     * 依据用户登录名判断某个用户是否存在
     * @param userLoginname
     * @return
     * @throws Exception
     */
    public User isExistofUserLoginname(String userLoginname)  throws  Exception
    {
        User user = null;

        if ((userMapper != null)&&(userLoginname != null)&&(userLoginname.trim() != "")) {

            //系统用户存在性校验
            try {

                List<User> ls = userMapper.findByName(null,null,null,userLoginname);
                if ((ls != null) && (ls.size() > 0)) user = ls.get(0);

                if (user != null) {
                    user.getAccesshospnames(dbCommonService);
                    user.getUserSexName(dataDictionaryService);
                    user.getHospName();
                    user.getUserActivestateName();
                    user.getRoleNames();
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("依据用户登录名判断某个用户是否存在失败:" + e.getMessage());
            }
        }

        return user;
    }

    /**
     * 依据用户ID设置用户密码
     * @param userSetPWRequest
     * @throws Exception
     */
    public String SetUserPassword(UserSetPWRequest userSetPWRequest) throws Exception
    {
        String result = "密码设置失败";

        if (userSetPWRequest != null) {
            try {
                User user = isExistofUserLoginname(userSetPWRequest.getUsername());

                if (user != null) {
                    if (user.getUserPassword().compareTo(userSetPWRequest.getOldpassword()) == 0) {
                        userMapper.SetUserPassword(user.getId(), userSetPWRequest.getNewpassword());
                        result = null;
                    } else result = "用户口令不对！";
                } else result = "用户不存在！";
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("设置用户密码失败:" + e.getMessage());
            }
        }
        else result  = "参数非法";

        return result;
    }

    /**
     * 分页查找
     * @param request
     * @return
     */
    public PageResponse<UserBrief> page(String hospId, UserPageRequest request) throws Exception
    {
        try {
            //request.setHospId(hospId);
            Page<UserBrief> page = userMapper.page(request, new RowBounds(request.getIndex(), request.getSize()));
            List<Long> lst = userMapper.Rcount(request);
            Long total = 0L;
            if ((lst!=null) && (lst.size()>0))
                total = lst.get(0);

            if (page != null)
            {
                for (UserBrief sb:page)
                {
                    sb.getUserSexName(dataDictionaryService);
                    sb.getHospName();
                    sb.getUserActivestateName();
                    sb.getRolenames();
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
     * @param hospId
     * @param request
     * @return
     * @throws Exception
     */
    public List<UserBrief> list(String hospId, UserPageRequest request) throws Exception
    {
        List<UserBrief> result = null;

        try {
            //request.setHospId(hospId);
            result = userMapper.list(request);

            if (result != null)
            {
                for (UserBrief sb:result)
                {
                    sb.getUserSexName(dataDictionaryService);
                    sb.getHospName();
                    sb.getUserActivestateName();
                    sb.getRolenames();
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
     * 获取指定角色的用户列表
     * @param hospId
     * @param roleId
     * @return
     * @throws Exception
     */
    public List<UserBrief> findByRole(String hospId, String roleId) throws Exception
    {
        List<UserBrief> result = null;

        try {
            if ((hospId != null) && (roleId != null)) {
                result = userMapper.findByRole(hospId, roleId);

                if (result != null) {
                    for (UserBrief sb : result) {
                        sb.getUserSexName(dataDictionaryService);
                        sb.getHospName();
                        sb.getUserActivestateName();
                    }
                }
            }

            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("获取指定角色的用户列表失败："+e.getMessage());
        }
    }

    /**
     * 依据ID获取一个完整的系统用户对象
     * @param id
     * @return
     * @throws Exception
     */
    public User getByID(String id) throws  Exception
    {
        User result=null;

        if (userMapper != null)
        {
            try
            {
                List<User> lst = userMapper.getbyid(id);
                if ((lst != null) && (lst.size()>0))
                    result = lst.get(0);

                if (result != null)
                {
                    result.getAccesshospnames(dbCommonService);
                    result.getUserSexName(dataDictionaryService);
                    result.getHospName();
                    result.getUserActivestateName();

                    //只保留系统管理员和系统用户角色
                    if ((result.getRoles() != null) && (result.getRoles().size()>0)) {
                        for (int i=0;i<result.getRoles().size();i++) {
                            UserRoleBrief urb = result.getRoles().get(i);

                            if ((urb.getRoleId().compareTo("002")!=0) && (urb.getRoleId().compareTo("003")!=0)) {
                                result.getRoles().remove(urb);
                                i--;
                            }
                        }
                    }

                    result.getRoleNames();
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
     * @param id
     * @return
     */
    public boolean delete(String id) throws  Exception
    {
        boolean result = false ;

        if (userMapper != null)
        {
            try
            {
                userMapper.delete(id);
                result = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("删除系统用户对象["+id+"]异常:"+e.getMessage());
            }
        }

        return result ;
    }


    /**
     * 检索系统用户
     * @param hospId
     * @param userName
     * @return
     * @throws Exception
     */
    public User findByName(String hospId, String userName) throws  Exception {
        try {
            if (dbCommonService != null)
                return isExistofUserName(hospId, userName);
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("检索系统用户失败:" + e.getMessage());
        }
    }

    /**
     * 保存系统用户信息，如果不存在则新建，否则更新
     * @param request
     * @return
     */
    public User save(String hospId, UserSaveRequest request) throws Exception{

        User user = null;

        //request.setHospID(hospId);

        if ((dbCommonService != null)&&(userMapper!= null) && (userRepository != null) && (request != null)) {

            if (request.getId() != null) user = getByID(request.getId());

            if (user == null) {
                //系统用户存在性校验
                user = isExistofUserName(null, request.getUserName());
            }

            if (user == null) {
                //构造新的系统用户对象
                user = User
                        .builder()
                        .build();
            }

            try {
                String loginname = user.getUserLoginname();

                BeanUtils.copyBeanIgnoreNull(user,request);
                user.setHospID(hospId);

                //登陆名修改，复位密码
                if ((loginname != null) && (user.getUserLoginname() != null) && (loginname.compareTo(user.getUserLoginname()) != 0))
                {
                    user.setUserPassword(null);
                }

                if ((request.getAccesshospNames() != null) && (request.getAccesshospNames().size()>0))
                    user.setaccesshospsFromNamestrings(request.getAccesshospNames(),dbCommonService);
                else {
                    user.setAccesshosps(null);
                    user.getAccesshospnames(dbCommonService);
                }

                if((request.getRoleNames() != null) && (request.getRoleNames().size()>0))
                    user.setrolesFromNamestrings(request.getRoleNames(),dbCommonService);
                else {
                    user.setRoles(null);
                    user.getRoleNames();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new Exception("复制系统用户对象失败:" + e.getMessage());
            }

            //保存系统用户对象
            try {
                User user_s = save(user);
                user.setId(user_s.getId());

                saveattr(user.getHospID(),user);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("保存系统用户对象失败:"+e.getMessage());
            }
        }

        return user;
    }

    /**
     * 保存系统用户
     * @param hospId
     * @param request
     * @return
     * @throws Exception
     */
    private User save(String hospId,UserInfoExcel request) throws Exception{

        User user = null;

        if ((dbCommonService != null)&&(userMapper != null) && (userRepository != null)) {

            user = isExistofUserName(hospId,request.getUserName());

            try {
                if (user == null) {
                    //构造新的系统用户对象
                    user = User
                            .builder()
                            .build();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("创建系统用户对象失败："+e.getMessage());
            }

            if (user != null) {

                try {
                    BeanUtils.copyBeanIgnoreNull(user,request);
                    user.setHospID(hospId);

                    //特殊字段的处理
                    if ((request.getUserSexName() != null) && (request.getUserSexName().length()>0))
                        user.setUserSexFromName(request.getUserSexName(),dataDictionaryService);

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("复制系统用户对象失败:"+e.getMessage());
                }

                //保存系统用户对象
                try {
                    user.setHospID(null);

                    user.setUserPassword(null);
                    User user_s = save(user);
                    user.setId(user_s.getId());

                    saveattr(user.getHospID(),user);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("保存系统用户对象失败:" + e.getMessage());
                }
            }
        }

        return user;
    }


    /**
     * 保存系统用户对象
     * @param value
     * @return
     * @throws Exception
     */
    public User save(User value) throws Exception
    {
        User user = null;

        if ((userRepository != null) && (value != null))
        {
            //保存网格对象
            try {
                //登录名的处理
                if (ExcelUtils.isNullofString(value.getUserLoginname()))
                    value.setUserLoginname(value.getUserMobile());
                if (ExcelUtils.isNullofString(value.getUserLoginnickname()))
                    value.setUserLoginnickname(value.getUserLoginname());
                if (ExcelUtils.isNullofString(value.getUserPassword()))
                    value.setUserPassword(MD5Util.md5(value.getUserLoginname(),"12345678"));

                //默认值的保存
                value.setUserType("1");
                value.setIsInitial("1");
                value.setUserActivestate("1");

                //保存
                user = userRepository.save(value);

                value.setId(user.getId());

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("保存系统用户对象失败:" + e.getMessage());
            }
        }

        return value;
    }


    /**
     * 保存系统用户附加信息
     * @param hospId
     * @param user
     * @return
     * @throws Exception
     */
    private boolean saveattr(String hospId,User user) throws Exception
    {
        boolean result = false ;

        if ((userMapper != null) && (userRepository != null)) {
            try {

                //获取用户原有角色列表
                List<UserRoleBrief> urbs = getUserRoles(user.getId(),null);
                //只保留系统管理员和系统用户角色
                if ((urbs != null) && (urbs.size()>0)) {
                    for (int i=0;i<urbs.size();i++) {
                        UserRoleBrief urb = urbs.get(i);

                        if ((urb.getRoleId().compareTo("002")!=0) && (urb.getRoleId().compareTo("003")!=0)) {
                            urbs.remove(urb);
                            i--;
                        }
                    }
                }

                //删除原有角色
                if ((urbs != null) && (urbs.size()>0)) {
                    for (int i=0;i<urbs.size();i++) {
                        UserRoleBrief urb = urbs.get(i);

                        userMapper.DeleteUserRole(user.getId(),urb.getRoleId());
                    }
                }

                //保存角色，多个则只保存第一个
                if ((user.getRoles() != null) && (user.getRoles().size() > 0)) {
                    UserRoleBrief rid = user.getRoles().get(0);
                    SetUserRole(user.getId(),rid.getRoleId());
                }
                else //默认为系统用户
                {
                    SetUserRole(user.getId(),"003");
                }

                //保存可访问的医院列表
                //删除原有关联医院
                if (userMapper != null) userMapper.deleteUserHosp(user.getId());
                //保存新关联医院
                if ((user.getAccesshospNames() != null) && (user.getAccesshospNames().size() > 0)) {
                    List<UserHosp> lts = null;

                    for (String hospname : user.getAccesshospNames()) {
                        String hid = dbCommonService.getHospIdbyName(hospname);
                        if (!ExcelUtils.isNullofString(hid)) {
                            UserHosp sr = new UserHosp();

                            sr.setUserId(user.getId());
                            sr.setHospId(hid);

                            if (lts == null) lts = new ArrayList<>();
                            lts.add(sr);
                        }
                    }

                    if ((lts != null) && (lts.size()>0)) userHospRepository.saveAll(lts);
                }

                result=true;
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("保存系统用户附加信息失败:"+e.getMessage());
            }
        }

        return result;
    }


    /**
     * 复位用户口令
     * @param userId
     * @return
     * @throws Exception
     */
    public boolean ResetUserpassword(String userId) throws Exception
    {
        boolean result = false ;

        if ((userMapper != null) && (userId != null)) {
            try {

                User user = getByID(userId);

                if (user != null) {
                    String psw = MD5Util.md5(user.getUserLoginname(), "12345678");

                    //设置口令
                    userMapper.SetUserPassword(userId, psw);

                    result = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("复位用户口令失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 设置指定用户的角色
     * @param userId
     * @param roleId
     * @throws Exception
     */
    public void SetUserRole(String userId, String roleId) throws Exception {
        if ((userRoleRepository != null) && (userId != null) && (userId.length()>0) && (roleId != null) && (roleId.length()>0)) {
            try
            {
                UserRole userRole = UserRole
                        .builder()
                        .build();

                userRole.setRoleId(roleId);
                userRole.setUserId(userId);
                userRoleRepository.save(userRole);
            }
            catch (Exception e)
            {
                throw new Exception("设置用户角色失败: "+e.getMessage());
            }
        }
    }


    /**
     * 设置系统用户医院管理员角色
     * @param userId
     * @param hospId
     * @throws Exception
     */
    public void SetUserHospManageRole(String userId, String hospId) throws Exception {
        if ((userRoleRepository != null) && (userMapper != null) && (!ExcelUtils.isNullofString(userId)) && (!ExcelUtils.isNullofString(hospId))) {
            try
            {
                if (getUserRoles(userId,"004") == null) {
                    UserRole userRole = UserRole
                            .builder()
                            .build();

                    userRole.setRoleId("004");
                    userRole.setUserId(userId);
                    userRoleRepository.save(userRole);
                }

                userMapper.SetUserHospManageRole(userId,hospId);
            }
            catch (Exception e)
            {
                throw new Exception("设置系统用户医院管理员角色失败: "+e.getMessage());
            }
        }
    }

    /**
     * 获取指定用户的角色列表
     * @param userId
     * @return
     * @throws Exception
     */
    public List<UserRoleBrief> getUserRoles(String userId,String roleId) throws Exception {
        List<UserRoleBrief> result = null;

        if ((userMapper != null) && (userId != null) && (userId.length()>0)) {
            try
            {
                result = userMapper.getUserRoles(userId,roleId);
            }
            catch (Exception e)
            {
                throw new Exception("获取用户角色失败: "+e.getMessage());
            }
        }

        if ((result != null) && (result.size()<1)) result = null;
        return result;
    }

    /**
     * 删除用户角色
     * @param userId
     * @param roleId
     * @throws Exception
     */
    public void DeleteUserRole(String userId, String roleId) throws Exception {
        if ((userMapper != null) && (userId != null) && (userId.length()>0) && (roleId != null) && (roleId.length()>0)) {
            try
            {
                userMapper.DeleteUserRole(userId,roleId);
            }
            catch (Exception e)
            {
                throw new Exception("删除用户角色失败: "+e.getMessage());
            }
        }
    }

    /**
     * 删除系统用户医院管理员角色
     * @param userId
     * @param hospId
     * @throws Exception
     */
    public void DeleteUserHospManageRole(String userId, String hospId) throws Exception {
        if ((userMapper != null) && (!ExcelUtils.isNullofString(userId)) && (!ExcelUtils.isNullofString(hospId))) {
            try
            {
                userMapper.DeleteUserHospManageRole(userId,hospId);
            }
            catch (Exception e)
            {
                throw new Exception("删除系统用户医院管理员角色失败: "+e.getMessage());
            }
        }
    }

    /**
     * 删除指定ID的物业职工用户角色
     * @param userId
     * @throws Exception
     */
    public void DeleteUserExstaffRole(String userId) throws Exception {
        if ((userMapper != null) && (userId != null) && (userId.length()>0)) {
            try
            {
                userMapper.DeleteUserExstaffRole(userId);
            }
            catch (Exception e)
            {
                throw new Exception("删除物业职工用户角色失败: "+e.getMessage());
            }
        }

    }


}
