package cn.edu.ujs.lp.intells.common.utils;

import cn.edu.ujs.lp.intells.common.entity.Common.TreeInterface;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形结构工具类 基于医院网格的
 *
 * 将一组list对象转成树形结构
 * 该list得符合设定的字段类型
 */
@Data
public class TreeUtil {

    private List<TreeInterface> treelist;

    /**
     * 获取指定链表的树形链表结构
     * @param lst
     * @return
     */
    public static List<Object> getTreelist(List<TreeInterface> lst)
    {
        List<Object> list = null;

        if (lst != null) {

            List<TreeInterface> lt = new ArrayList<TreeInterface>();
            for (int i = 0; i < lst.size(); i++) lt.add(lst.get(i));

            TreeUtil tu = new TreeUtil();
            tu.setTreelist(lt);
            list = tu.getTree();
        }

        return list;
    }

    /**
     * 得到树形结构节点的全路径名称，根节点的父节点信息为null
     * @param id
     * @return
     */
    public String getTreeNodeFullName(String id)
    {
        String rt = "";

        if ((id == null) || (getNodeSuperiorId(id) == null)||(getNodeSuperiorId(id).equals(""))) //根节点
            return getNodeName(id);
        else
            return getTreeNodeFullName(getNodeSuperiorId(id))+"->"+getNodeName(id); //子节点
    }

    /**
     * 获取指定id的节点名称
     * @param id
     * @return
     */
    private String getNodeName(String id)
    {
        String rt = "";

        if ((treelist != null) && (treelist.size()>0))
        {
            for (TreeInterface treeNode : treelist)
            {
                if (treeNode.getNodeID().trim().equals(id.trim()))
                    rt = treeNode.getNodeName();
            }
        }

        return rt;
    }

    /**
     * 依据ID获取该节点的父节点
     * @param id
     * @return
     */
    private String getNodeSuperiorId(String id)
    {
        String rt = "";

        if ((treelist != null) && (treelist.size()>0))
        {
            for (TreeInterface treeNode : treelist)
            {
                if (treeNode.getNodeID().trim().equals(id.trim()))
                    rt = treeNode.getNodeParentID();
            }
        }

        return rt;
    }

    /**
     * 构建树形结构，返回树形结构列表
     * @param
     * @return
     */
    public List<Object> getTree(){

        List<Object> list = null;

        if ((treelist != null) && (treelist.size()>0)) {
            list = new ArrayList<Object>();

            //遍历表节点
            for (TreeInterface treeNode : treelist) {

                Map<String, Object> mapArr = new LinkedHashMap<String, Object>();
                //寻找根节点
                if ((treeNode.getNodeParentID() == null) || (treeNode.getNodeParentID().equals("")) || !isIntree(treeNode)) {
                    setTreeMap(mapArr, treeNode);
                    list.add(mapArr);
                }
            }
        }

        return list;
    }

    /**
     * 判断节点是否在列表中
     * @param value
     * @return
     */
    private boolean isIntree(TreeInterface value)
    {
        boolean result = false ;
        for (TreeInterface treeNode : treelist)
        {
            if (treeNode.getNodeID().trim().compareTo(value.getNodeParentID().trim())==0)
            {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * 寻找子节点
     * @param id
     * @return
     */
    private List<Object> subChild(String id){
        List<Object> lists=new ArrayList<Object>();

        for (TreeInterface a:treelist){
            Map<String ,Object> chilArray=new LinkedHashMap<String, Object>();

            //寻找ID节点的子节点
            if ((a.getNodeParentID() != null) &&(a.getNodeParentID().equals(id))){
                setTreeMap(chilArray,a);
                lists.add(chilArray);
            }
        }

        return lists;
    }

    /**
     * 提起节点，继续递归寻找子节点
     * @param mapArray
     * @param treeNode
     */
    private void setTreeMap(Map<String ,Object> mapArray,TreeInterface treeNode){
        mapArray.put("id",treeNode.getNodeID());
        mapArray.put("value",treeNode.getNodeID());
        mapArray.put("level",treeNode.getNodeLevel());
        mapArray.put("superiorId",treeNode.getNodeParentID());
        mapArray.put("superiorName",treeNode.getNodeParentName());
        mapArray.put("fullName",ExcelUtils.isNullofString(treeNode.getNodeParentName())? treeNode.getNodeName():treeNode.getNodeParentName()+"->"+treeNode.getNodeName());
        mapArray.put("name",treeNode.getNodeName());
        mapArray.put("label",treeNode.getNodeName());
        mapArray.put("children",subChild(treeNode.getNodeID())); //递归
    }

}
