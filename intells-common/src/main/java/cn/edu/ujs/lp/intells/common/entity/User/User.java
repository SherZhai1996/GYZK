package cn.edu.ujs.lp.intells.common.entity.User;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseHospEntity;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import cn.edu.ujs.lp.intells.common.utils.OSUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseHospEntity {

    @Column(name = "openid",length = 120,updatable = false)
    private String openid;

    @Column(name = "user_name",length = 20)
    private String userName;

    @Column(name = "user_sex",length = 32)
    private String userSex;

    /**
     * 用户性别名称
     */
    @Transient
    private String userSexName;

    @Column(name = "user_mobile",length = 15)
    private String userMobile;

    @Column(name = "user_login_name",length = 20)
    private String userLoginname;

    @Column(name = "user_login_nickname",length = 20)
    private String userLoginnickname;

    @Column(name = "user_password",length = 20)
    private String userPassword;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "user_pic_path",length = 20,updatable = false)
    private String userPicpath;

    @Column(name = "is_initial",updatable = false)
    private String isInitial;

    @Column(name = "user_activestate",updatable = false)
    private String userActivestate;

    @Transient
    private String userActivestateName;

    @Transient
    private  String hospName;

    /**
     * 可访问医院列表
     */
    @Transient
    private List<UserHospBrief> accesshosps;

    @Transient
    private List<String> accesshospNames;

    @Transient
    private String userlogopicpath;

    /**
     * 获取显示图片路径
     * @return
     */
    public String getUserlogopicpath()
    {
        if ((userPicpath != null) && (userPicpath.trim().length()>0))
            return userPicpath;
        else
            return OSUtils.getUserlogopicfile();
    }


    public String getUserActivestateName() {
        String result = null;

        if ((userActivestate != null) && (userActivestate.compareTo("1") == 0))
            result = "激活";
        else
        {
            result = "非激活";
            userActivestate = "0";
        }

        userActivestateName = result;
        return result;
    }

    /**
     * 设置激活状态
     * @param name
     */
    public void setuserActivestateFromName(String name) {

        userActivestate = "0";
        if ((name != null) && (name.compareTo("激活") == 0))
            userActivestate = "1";

    }

    /**
     * 获取用户性别名称
     * @param dataDictionaryService
     * @return
     * @throws Exception
     */
    public String getUserSexName(DataDictionaryService dataDictionaryService) throws Exception {
        String result = null;

        if ((userSex != null) && (userSex != ""))
            try {
                result = dataDictionaryService.getDataDictionary("db_sex").getNamebyID(userSex);
            } catch (Exception e) {
                throw new Exception("获取用户性别名称失败：" + e.getMessage());
            }

        userSexName = result;
        if (userSexName == null) userSex = null;
        return result;
    }

    /**
     * 设置用户性别ID
     * @param name
     * @param dataDictionaryService
     * @throws Exception
     */
    public void setUserSexFromName(String name,DataDictionaryService dataDictionaryService) throws Exception
    {
        if ((name != null) && (name != ""))
            try {
                userSex = dataDictionaryService.getDataDictionary("db_sex").getIDbyName(name.replace(" ",""));
            }
            catch (Exception e)
            {
                throw new Exception("获取用户性别ID失败："+e.getMessage());
            }
    }

    /**
     * 获取可访问医院名称列表
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public List<String> getAccesshospnames(DBCommonService dbCommonService) throws Exception{
        List<String> lst = null;

        if ((accesshosps != null) && (accesshosps.size() > 0)) {
            lst = new ArrayList<>();

            for (UserHospBrief gfb : accesshosps) {
                try {
                    lst.add(gfb.getHospName(dbCommonService));
                }
                catch (Exception e)
                {
                    throw new Exception("获取医院名称失败："+e.getMessage());
                }
            }
        }

        accesshospNames=lst;
        return lst;
    }

    /**
     *
     * 设置可访问医院列表
     * @param value
     * @param dbCommonService
     * @throws Exception
     */
    public void setaccesshospsFromNamestrings(List<String> value, DBCommonService dbCommonService) throws Exception
    {
        List<UserHospBrief> lst = null;

        if ((value != null) && (value.size()>0))
        {
            try {
                for (String gt : value) {
                    if ((dbCommonService != null) && (gt != null)) {
                        UserHospBrief hfb = new UserHospBrief();

                        hfb.setHospId(dbCommonService.getHospIdbyName(gt));
                        hfb.setHospName(gt);

                        if (lst == null) lst = new ArrayList<>();
                        lst.add(hfb);
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("设置医院失败:"+e.getMessage());
            }
        }

        accesshosps=lst;
    }

    @Transient
    private List<UserRoleBrief> roles;

    /**
     * 用户角色名称列表
     */
    @Transient
    private List<String> roleNames;

    /**
     * 在系统用户和系统管理员中选择一种
     */
    @Transient
    private String roleName;

    /**
     * 用户角色名称列表
     */
    public List<String> getRoleNames() {
        List<String> lst = null;

        if ((roles != null) && (roles.size() > 0)) {
            for (UserRoleBrief gfb : roles) {
                if (lst == null) lst = new ArrayList<>();

                lst.add(gfb.getRoleName());
            }
        }

        roleNames=lst;
        if ((roleNames != null) && (roleNames.size()>0))
            roleName = roleNames.get(0);
        else
            roleName = null;
        return lst;
    }

    /**
     * 设置用户角色
     */
    public void setrolesFromNamestrings(List<String> value, DBCommonService dbCommonService) throws Exception
    {
        List<UserRoleBrief> lst = null;

        if ((value != null) && (value.size()>0))
        {
            try {
                for (String gt : value) {
                    if ((dbCommonService != null) && (gt != null)) {
                        UserRoleBrief hfb = new UserRoleBrief();

                        hfb.setRoleId(dbCommonService.getRoleidbyName(gt));
                        hfb.setRoleName(gt);

                        if (lst == null) lst = new ArrayList<>();
                        lst.add(hfb);
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("设置角色失败:"+e.getMessage());
            }
        }

        roles=lst;
    }

}
