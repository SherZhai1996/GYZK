package cn.edu.ujs.lp.intells.common.entity.User;

import cn.edu.ujs.lp.intells.common.entity.Hosp.Hosp;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import lombok.Data;

import java.util.List;

/**
 * 登陆反馈信息
 */
@Data
public class UserLoginInfo {
    private String id;
    private String user_name;
    private String user_login_name;
    private String user_type;  // 用户类型
    private String user_pic_path;  // 用户logo图片相对路径
    private String role_id;
    private String role_name;
    private String hosp_id;
    private String hospName;
    private String hospPic; //医院院徽图片相对路径
    private Double hospCenterX;
    private Double hospCenterY;

    private List<UserHospBrief> accesshosps;  //可访问的医院列表

    public void setUserLoginInfo(User user, DBCommonService dbCommonService) throws Exception
    {
        if (user != null)
        {
            try
            {
                this.id = user.getId();
                this.user_name = user.getUserName();
                this.user_login_name = user.getUserLoginname();
                this.user_type = user.getUserType();
                this.user_pic_path = user.getUserlogopicpath();

                if ((user.getRoles() != null) && (user.getRoles().size()>0))
                {
                    this.role_id = user.getRoles().get(0).getRoleId();
                    this.role_name = user.getRoles().get(0).getRoleName();
                }
                else
                {
                    this.role_id = null;
                    this.role_name = null;
                }

                this.hosp_id = user.getHospID();
                this.hospName = user.getHospName();

                this.hospPic = null;
                if ((this.hospName != null) && (dbCommonService != null))
                {
                    Hosp hosp = dbCommonService.getHospbyID(this.hosp_id,null);

                    if (hosp != null)
                    {
                        this.hospPic = hosp.getHospBadgedisppath();
                        this.hospCenterX = hosp.getHospCenterX();
                        this.hospCenterY = hosp.getHospCenterY();
                    }
                }

                this.accesshosps = user.getAccesshosps();
            }
            catch (Exception e)
            {
                throw new Exception("从用户对象复制到登陆反馈用户对象失败:"+e.getMessage());
            }
        }
    }

}
