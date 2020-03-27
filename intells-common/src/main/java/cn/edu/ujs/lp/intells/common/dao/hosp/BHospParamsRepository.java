package cn.edu.ujs.lp.intells.common.dao.hosp;

import cn.edu.ujs.lp.intells.common.entity.Hosp.HospParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 医院参数数据库访问类
 */
@Repository
public interface BHospParamsRepository extends JpaRepository<HospParams,String> {
}
