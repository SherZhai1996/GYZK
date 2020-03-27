package cn.edu.ujs.lp.intells.common.entity.Common;
import javax.persistence.*;
import java.io.Serializable;

@Entity
public class DataDictionary implements Serializable {
    private String id;
    private String name;

    public DataDictionary(){}
    public DataDictionary(String id,String name)
    {
        this.id=id;
        this.name=name;
    }

    @Id
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString() {
        String result = null;

        result = String.format("{id=%s,name=%s}",id,name);

        return result;
    }


}
