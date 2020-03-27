package cn.edu.ujs.lp.intells.common.entity.User;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseHospEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role extends BaseHospEntity {
    @Column(name = "role_name",length = 20)
    private String roleName;

    @Column(name = "role_dec",length = 100)
    private String roleDec;
}
