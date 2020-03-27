package cn.edu.ujs.lp.intells.systemmanagement.dao;

import cn.edu.ujs.lp.intells.systemmanagement.entity.UserPos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPosRepository extends JpaRepository<UserPos,String>, JpaSpecificationExecutor<UserPos>, CrudRepository<UserPos,String> {
}
