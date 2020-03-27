package cn.edu.ujs.lp.intells.basicinformation.entity.Hosp;

import lombok.Data;

import java.util.List;

/**
 * 网格树节点结构
 */
@Data
public class GridTreeNode {

    private String id;

    private String gridName;

    private String label;

    private String superiorGridId;

    private String gridLevel;

    private List<GridTreeNode> children;
}
