package cn.edu.ujs.lp.intells.common.entity.Common;

import javax.persistence.*;

/*
空实体类，为了配合实现数据库值函数等查询操作，没有什么作用，因此命名为空实体
 */
@Entity
public class EmptyEntity {

    private Integer id;
    private String data;

    public EmptyEntity(){}

    @Id
    public Integer getId()
    {
        return id;
    }
    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getData() {
        return data;
    }
    public void setData(String data)
    {
        this.data = data;
    }
}
