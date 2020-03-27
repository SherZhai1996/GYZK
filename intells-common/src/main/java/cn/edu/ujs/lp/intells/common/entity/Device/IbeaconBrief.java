package cn.edu.ujs.lp.intells.common.entity.Device;

import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class IbeaconBrief {
    /**
     * 网格ID
     */
    private String id;

    private String ibeaconName;

    private String ibeaconUUID;

    private String ibeaconRSSI;

    private String gridId;

    private String gridName;

    private String isUsing;

    private String isUsingName;

    private String hospId;

    private String hospName;

    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    /**
     * 获取网格区域全名
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String getGridName(DBCommonService dbCommonService) throws Exception {
        String result = null;

        if ((gridId != null) && (gridId != ""))
            try {
                result = dbCommonService.getGridFullnamebyId(gridId);
            } catch (Exception e) {
                throw new Exception("获取网格区域全名失败：" + e.getMessage());
            }

        gridName = result;
        if (gridName == null) gridId = null;
        return result;
    }


    /**
     * 获取是否正在使用名称
     * @return
     */
    public String getIsUsingName() {
        String result = null;

        if ((isUsing != null) && (isUsing.compareTo("1") == 0))
            result = "启用";
        else {
            result = "停用";
            isUsing = "0";
        }

        isUsingName = result;
        return result;
    }

    /**
     * 依据医院ID获取医院名称
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String getHospName(DBCommonService dbCommonService) throws Exception {
        String result = null;

        if ((hospId != null) && (hospId != ""))
            try {
                result = dbCommonService.getHospNamebyId(hospId);
            } catch (Exception e) {
                throw new Exception("依据医院ID获取医院名称失败：" + e.getMessage());
            }

        hospName = result;
        if (hospName == null) hospId = null;
        return result;
    }
}
