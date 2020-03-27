package cn.edu.ujs.lp.intells.basicinformation.entity.Hosp;

import lombok.Data;

import java.util.List;

@Data
public class GridQRBrief {
    /**
     * 网格ID
     */
    private String id;

    private String gridCode;

    private String gridPlaceclassid;

    /**
     * 网格名
     */
    private String gridName;

    /**
     * 网格全名
     */
    private String gridFullname;

    private List<GridExteam> gridExteams;

    public String getBAexteamNeme()
    {
        String result = null;

        if (gridExteams != null)
        {
            for (GridExteam ge:gridExteams)
            {
                if (ge.getServiceId().compareTo("04")==0) {
                    result = ge.getExteamName();
                    break;
                }
            }
        }

        return result;
    }

    public String getBJexteamNeme()
    {
        String result = null;

        if (gridExteams != null)
        {
            for (GridExteam ge:gridExteams)
            {
                if (ge.getServiceId().compareTo("02")==0) {
                    result = ge.getExteamName();
                    break;
                }
            }
        }

        return result;
    }

}
