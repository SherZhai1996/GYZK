package cn.edu.ujs.lp.intells.systemmanagement.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserPosBrief {
    private String id;

    private String userId;
    private String userName;

    private String excompanyName;
    private String exteamName;

    private String gridId;
    private String gridFullname;

    /**
     * 该网格是否存在信标，存在表示该信标的名称，否则为空
     */
    private String ibeaconName;

    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    private String hospID;
    private String hospName;

    /**
     * 该网格是否检测到信标
     */
    private String isexistibeacon;

    /**
     * 该网格是否检测到信标显示内容
     */
    private String isexistibeaconDispName;

    public String getIsexistibeaconDispName() {

        isexistibeaconDispName = "";
        if ((isexistibeacon != null) && (isexistibeacon.compareTo("1")==0)) {
            isexistibeaconDispName = "检测到";
        }

        return isexistibeaconDispName;
    }
}
