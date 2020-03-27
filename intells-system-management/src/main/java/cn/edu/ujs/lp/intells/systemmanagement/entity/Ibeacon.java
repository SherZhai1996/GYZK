package cn.edu.ujs.lp.intells.systemmanagement.entity;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseHospEntity;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
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

/**
 * 蓝牙信标类
 */
@Entity
@Table(name = "tb_ibeacon")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ibeacon extends BaseHospEntity {

    @Column(name = "ibeacon_name",length = 50)
    private String ibeaconName;

    @Column(name = "ibeacon_UUID",length = 32)
    private String ibeaconUUID;

    @Column(name = "ibeacon_RSSI",length = 10)
    private String ibeaconRSSI;

    @Column(name = "grid_id",length = 32)
    private String gridId;

    @Transient
    private String gridName;

    @Column(name = "isusing")
    private String isUsing;

    @Transient
    private String isUsingName;

    @Transient
    private String hospName;

    /**
     *安放网格节点的网格树
     */
    @Transient
    private List<List<String>> gridPathIds;

    /**
     * 获取安放网络的全路径ID列表
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public List<List<String>>  getGridPathbyId(DBCommonService dbCommonService) throws Exception
    {
        List<List<String>> lst = null;

        if ((dbCommonService != null) && (gridId != null) && (gridId.length() > 0)) {
            try {
                lst = new ArrayList<>();
                List<String> its = dbCommonService.getGridPathbyId(gridId);
                lst.add(its);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据网格ID获取安放网格全路径ID列表失败:"+e.getMessage());
            }
        }

        gridPathIds = lst;
        return lst;
    }

    /**
     * 获取网格区域全名
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String getGridName(DBCommonService dbCommonService) throws Exception
    {
        String result = null;

        if ((gridId != null) && (gridId != ""))
            try {
                result = dbCommonService.getGridFullnamebyId(gridId);
            }
            catch (Exception e)
            {
                throw new Exception("依据信标关联的网格ID获取网格区域全名失败："+e.getMessage());
            }

        gridName=result;
        return result;
    }

    /**
     * 依据网格区域全名获取ID
     * @param name
     * @param dbCommonService
     * @throws Exception
     */
    public void setGridIdFromName(String name,DBCommonService dbCommonService) throws Exception
    {
        if ((name != null) && (name != ""))
            try {
                gridId = dbCommonService.getGridIdbyFullname(getHospID(),name);
            }
            catch (Exception e)
            {
                throw new Exception("依据网格区域全名获取ID失败："+e.getMessage());
            }
    }

    /**
     * 获取是否正在使用名称
     * @return
     */
    public String getIsUsingName() {
        String result = null;

        if (isUsing != null)
            if (isUsing.compareTo("0") == 0)
                result = "停用";
            else
                result = "启用";
        else
            result = null;

        isUsingName = result;
        return result;
    }

    /**
     * 设置使用状态
     * @param name
     */
    public void setIsUsingFromName(String name) {
        String result = null;

        if ((name != null) && (name.replace(" ","").compareTo("启用") == 0))
            isUsing = "1";
        else
            isUsing = "0";
    }

    /**
     * 依据医院ID获取医院名称
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String getHospName(DBCommonService dbCommonService) throws Exception
    {
        String result = null;

        if ((getHospID() != null) && (getHospID() != ""))
            try {
                result = dbCommonService.getHospNamebyId(getHospID());
            }
            catch (Exception e)
            {
                throw new Exception("依据医院ID获取医院名称失败："+e.getMessage());
            }

        hospName=result;
        return result;
    }

    /**
     * 依据医院名称设置医院ID
     * @param name
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String setHospIDFromName(String name,DBCommonService dbCommonService) throws Exception
    {
        String result = null;

        if ((name != null) && (name != ""))
            try {
                result = dbCommonService.getHospIdbyName(name);
            }
            catch (Exception e)
            {
                throw new Exception("依据医院名称设置医院ID失败："+e.getMessage());
            }

        setHospID(result);
        return result;
    }
}
