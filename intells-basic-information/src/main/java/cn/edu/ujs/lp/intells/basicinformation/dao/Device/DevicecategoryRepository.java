package cn.edu.ujs.lp.intells.basicinformation.dao.Device;

import cn.edu.ujs.lp.intells.basicinformation.entity.Device.Devicecategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevicecategoryRepository extends JpaRepository<Devicecategory,String> {
}
