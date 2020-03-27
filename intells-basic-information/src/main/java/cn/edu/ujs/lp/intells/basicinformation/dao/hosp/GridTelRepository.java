package cn.edu.ujs.lp.intells.basicinformation.dao.hosp;

import cn.edu.ujs.lp.intells.basicinformation.entity.Hosp.GridTel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 网格电话数据库访问类
 */
@Repository
public interface GridTelRepository  extends JpaRepository<GridTel,String> {
}
