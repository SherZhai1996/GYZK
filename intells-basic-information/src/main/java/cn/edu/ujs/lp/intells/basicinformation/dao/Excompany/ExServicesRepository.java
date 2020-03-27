package cn.edu.ujs.lp.intells.basicinformation.dao.Excompany;

import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.ExServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExServicesRepository extends JpaRepository<ExServices,String>, JpaSpecificationExecutor<ExServices>, CrudRepository<ExServices,String> {

    ExServices findFirstByExserviceName(String exservicesname);
}
