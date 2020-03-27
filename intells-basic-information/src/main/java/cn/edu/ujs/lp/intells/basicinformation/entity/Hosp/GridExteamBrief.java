package cn.edu.ujs.lp.intells.basicinformation.entity.Hosp;

import lombok.Data;

/**
 * 网格所关联的服务班组类，主要用于导出数据用
 */
@Data
public class GridExteamBrief {
    /**
     * 服务班组ID
     */
    private String exteamId;

    /**
     * 服务班组全名
     */
    private String exteamFullname;
}
