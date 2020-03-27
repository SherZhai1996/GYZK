package cn.edu.ujs.lp.intells.basicinformation.request.hosp;

import cn.edu.ujs.lp.intells.common.entity.Hosp.GridBrief;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GridPDFRequest {
    /**
     * 需要输出二维码标签的网格对象列表
     */
    @ApiModelProperty(value = "需要输出二维码标签的网格编码列表,最好不超过8个网格ID，多了则分次请求", required = true)
    private List<String> gridIdList;
}
