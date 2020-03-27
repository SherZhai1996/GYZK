package cn.edu.ujs.lp.intells.common.entity.Common;

//import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据库公共字段
 * 不会创建数据表，字段会被其它entity继承
 *
 * @author troy
 * @date 2019-07-05
 */
@Data
@MappedSuperclass
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID,uuid自动生成
     */
    @Id
    @Column(name = "id",length = 32)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    private String id;


    /**
     * 创建时间
     */
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "create_time", columnDefinition = "timestamp default current_timestamp")
    private Date createTime;
    public void setCreateTime(Date date)
    {
        createTime=date;
    }

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "update_time", columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private Date updateTime;
    public void setUpdateTime(Date date)
    {
        updateTime=date;
    }

    /**
     * 备注
     */
    @Column(name = "remark", length = 500)
    private String remark;

    /**
     * 创建者
     */
    @Column(name = "create_by", length = 32)
    private String createBy;

    /**
     * 逻辑删除
     */
    @Column(name = "delete_flag", columnDefinition = "tinyint(1) default 0")
    private Boolean deleteFlag;
    public void setDeleteFlag(boolean flag)
    {
        deleteFlag=flag;
    }

    /**
     * 记录插入前，执行操作
     */
    @PrePersist
    public void prePersist() {
        Date date = new Date();
        this.setDeleteFlag(false);
        this.setCreateTime(date);
        this.setUpdateTime(date);
    }

    /**
     * 记录更新前，执行操作
     */
    @PreUpdate
    public void preUpdate() {
        this.setUpdateTime(new Date());
    }


    public BaseEntity()
    {
        Date date = new Date();
        this.setDeleteFlag(false);
        //this.setCreateTime(date);
        this.setUpdateTime(date);
    }
}
