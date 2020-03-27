package cn.edu.ujs.lp.intells.common.entity.User;

import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import cn.edu.ujs.lp.intells.common.utils.ExcelUtils;
import cn.edu.ujs.lp.intells.common.utils.OSUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserBrief {
    String id;

    private String openid;

    private String userName;

    private String userSex;

    private String userSexName;

    private String userMobile;

    private String userLoginname;

    private String userLoginnickname;

    private String userPassword;

    private String userPicpath;

    private String isInitial;

    private String userActivestate;

    private String userActivestateName;

    private String hospID;

    private  String hospName;

    private List<UserRoleBrief> roles;

    private List<String> rolenames;

    private String roleNamestring;

    /**
     * 显示图片路径
     */
    private String userlogopicpath;

    public String getHospName()
    {
        if (ExcelUtils.isNullofString(hospName))
            hospName = "集团用户";

        return hospName;
    }
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

    /**
     * 获取用户激活状态
     * @return
     */
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
     * 获取角色名称列表
     * @return
     */
    public List<String> getRolenames() {
        List<String> lst = null;
        String result = null;

        if ((roles != null) && (roles.size() > 0)) {
            lst = new ArrayList<>();

            for (UserRoleBrief gfb : roles) {
                String rn = gfb.getRoleName();
                lst.add(rn);
                if (result == null)
                    result = rn;
                else
                    result += (", "+rn);
            }
        }

        rolenames=lst;
        roleNamestring = result;
        return lst;
    }

}
