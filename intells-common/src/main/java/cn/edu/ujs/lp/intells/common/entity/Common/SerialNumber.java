package cn.edu.ujs.lp.intells.common.entity.Common;
import lombok.Data;

import javax.persistence.*;

/*
序列号产生实体类，用于产生各类编码
 */
@Entity
@Data
public class SerialNumber {

    @Id
    private Integer id;
    private String sn;

    /*public SerialNumber(){}


    public Integer getId()
    {
        return id;
    }
    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }
    public void setSn(String sn)
    {
        this.sn = sn;
    }
     */
}
