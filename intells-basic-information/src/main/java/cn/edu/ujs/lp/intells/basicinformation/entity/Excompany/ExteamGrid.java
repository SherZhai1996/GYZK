package cn.edu.ujs.lp.intells.basicinformation.entity.Excompany;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_exteam_grid")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExteamGrid extends BaseEntity {

    /**
     * 职工id
     */
    @Column(name = "team_id",length = 32)
    private String exteamId;

    /**
     * 网格id
     */
    @Column(name = "grid_id",length = 32)
    private String gridId;

    /**
     * 网格编码
     */
    @Column(name = "grid_code",length = 30)
    private String gridCode;
}
