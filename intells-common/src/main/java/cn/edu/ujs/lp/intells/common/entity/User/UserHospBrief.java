package cn.edu.ujs.lp.intells.common.entity.User;

import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import lombok.Data;

/**
 * 职工网格关联表
 *
 * @author Meredith
 * @data 2019-10-08
 */

@Data
public class UserHospBrief {
    private String hospId;
    private String hospName;

    /**
     * 依据医院ID获取医院名称
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String getHospName(DBCommonService dbCommonService) throws Exception {
        String result = null;

        if ((dbCommonService != null) && (hospId != null))
            try {
                result = dbCommonService.getHospNamebyId(hospId);
            } catch (Exception e) {
                throw new Exception("依据医院ID获取医院名称失败:" + e.getMessage());
            }

        hospName = result;
        if (hospName == null) hospId = null;
        return result;
    }
}
