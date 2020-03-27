package cn.edu.ujs.lp.intells.common.dao.Serviceitem;

import cn.edu.ujs.lp.intells.common.entity.Serviceitem.Serviceitemcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceitemcategoryRepository extends JpaRepository<Serviceitemcategory,String>, JpaSpecificationExecutor<Serviceitemcategory>, CrudRepository<Serviceitemcategory,String> {

}
