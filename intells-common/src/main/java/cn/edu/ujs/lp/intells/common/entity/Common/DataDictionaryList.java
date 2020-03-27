package cn.edu.ujs.lp.intells.common.entity.Common;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

/*
数据字典类，其对象存于Redis内存数据库中
 */
public class DataDictionaryList extends Object implements Serializable {
    private static final long serialVersionUID = 1L;
    private String typename;
    private int count = 0;

    //private Map<String,String> items;
    private List<DataDictionary> itemlist;

    /*
    获取数据字典项数
     */
    public int getCount() {
        if (itemlist != null)
            this.count=itemlist.size();
        else
            this.count=0;
        return this.count;
    }

    /*
    获取数据字典
     */
    public List<DataDictionary> getDictionaryList()
    {
        return itemlist;
    }

    /*
    获取数据字典名
     */
    public String getTypename() {
        return typename;
    }

    /*
    设置数据字典名
     */
    public void setTypename(String typename)
    {
        this.typename=typename;
    }

    /*
    依据名称获取ID
     */
    public String getIDbyName(String name)
    {
        String resultkey = null;

        if ((name != null) && (itemlist != null) && (itemlist.size()>0))
        {
            for (int i=0;i<itemlist.size();i++)
            {
                if (itemlist.get(i).getName().trim().compareTo(name.trim()) == 0) {
                    resultkey = itemlist.get(i).getId();
                    break;
                }
            }
        }

        return resultkey;
    }

    /*
    依据id获取字典项对象
     */
    private DataDictionary getbyID(String id)
    {
        DataDictionary dd=null;

        if ((id != null) && (itemlist != null) && (itemlist.size()>0))
        {
            for (int i=0;i<itemlist.size();i++)
            {
                if (itemlist.get(i).getId().trim().compareTo(id.trim()) == 0) {
                    dd = itemlist.get(i);
                    break;
                }
            }
        }

        return dd;
    }

    /*
    依据ID获取名称
     */
    public String getNamebyID(String id)
    {
        String resultname=null;

        if ((id != null) && (itemlist != null) && (itemlist.size()>0))
        {
            for (int i=0;i<itemlist.size();i++)
            {
                if (itemlist.get(i).getId().trim().compareTo(id.trim()) == 0) {
                    resultname = itemlist.get(i).getName();
                    break;
                }
            }
        }

        return resultname;
    }

    /*
    添加数据字典项
     */
    public void Add(String id,String name)
    {
        if (itemlist == null)
           itemlist=new ArrayList<DataDictionary>();

        if (this.getNamebyID(id) == null)
        itemlist.add(new DataDictionary(id,name));
        count = itemlist.size();
    }

    /*
    从数据字典中删除指定项
     */
    public void RemovebyID(String id)
    {
        if ((itemlist != null) && (itemlist.size()>0)) {
            DataDictionary dd = getbyID(id);
            if (dd != null) {
                itemlist.remove(dd);
                this.count=itemlist.size();
            }
        }
    }

    /*
    初始化数据字典
     */
    public DataDictionaryList()
    {
        itemlist = new ArrayList<DataDictionary>();
        this.count=itemlist.size();
    }

    /*
    初始化数据字典
    */
    public DataDictionaryList(String typename)
    {
        this.typename = typename;

        itemlist = new ArrayList<DataDictionary>();
        this.count=itemlist.size();
    }

    /*
    对象串行化
     */
   @Override
    public String toString() {
        String result = null;

        try
        {
            if ((itemlist != null) && (itemlist.size()>0))
            {
                //result = JSONArray.toJSONString(this);
                result = String.format("typename=%s,count=%d",typename,count);
                if ((itemlist != null) && (itemlist.size()>0))
                {
                    result += ",{";
                    for (int i=0;i<itemlist.size();i++) {
                        if (i>0) result += ",";
                        result += itemlist.get(i).toString();
                    }
                    result += "}";
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = null;
        }

        return result;
    }

    /*
    按JSON格式串行化
     */
    public String toJSONString() {
        String result = null;

        try
        {
            if ((itemlist != null) && (itemlist.size()>0))
            {
                result = JSONArray.toJSONString(this);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = null;
        }

        return result;
    }

}
