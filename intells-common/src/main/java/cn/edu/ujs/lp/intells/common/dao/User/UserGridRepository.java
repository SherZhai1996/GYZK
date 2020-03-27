package cn.edu.ujs.lp.intells.common.dao.User;

import cn.edu.ujs.lp.intells.common.entity.User.UserGrid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGridRepository  extends JpaRepository<UserGrid,String >, JpaSpecificationExecutor<UserGrid> {
}
