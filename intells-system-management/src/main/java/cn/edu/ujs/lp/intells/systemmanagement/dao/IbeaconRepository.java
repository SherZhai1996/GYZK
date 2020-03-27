package cn.edu.ujs.lp.intells.systemmanagement.dao;

import cn.edu.ujs.lp.intells.systemmanagement.entity.Ibeacon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IbeaconRepository extends JpaRepository<Ibeacon,String>, JpaSpecificationExecutor<Ibeacon>, CrudRepository<Ibeacon,String> {
}
