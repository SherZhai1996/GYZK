package cn.edu.ujs.lp.intells.basicinformation.request.hosp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GridsubTreeRequest {
    /**
     * 需需要获取的网格树网格ID列表
     */
    @ApiModelProperty(value = "需要获取的网格树网格ID列表", required = true)
    private List<List<String>> gridIdsList;
}
