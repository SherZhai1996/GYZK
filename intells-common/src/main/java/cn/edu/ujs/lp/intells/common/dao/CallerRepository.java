package cn.edu.ujs.lp.intells.common.dao;

import cn.edu.ujs.lp.intells.common.entity.Common.EmptyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CallerRepository extends JpaRepository<EmptyEntity,Integer> {
    @Query(value = "select GetOndutyExStaffTelBygridTel(?1) as tel", nativeQuery = true)
    List getOndutyExStaffTelBygridTel(String gridTel);

    @Query(value = "select Getexteam_leadertel_bygridid(?1) as tel", nativeQuery = true)
    List getexteam_leadertel_bygridid(String gridid);

    @Query(value = "select Getexteam_leadertel_bygridtel(?1) as tel", nativeQuery = true)
    List getexteam_leadertel_bygridtel(String gridtel);

    @Query(value = "select Getexteam_leaderID_bygridtel(?1) as id", nativeQuery = true)
    List getexteam_leaderID_bygridtel(String gridtel);

    @Query(value = "select Getexteam_leaderID_bygridid(?1) as id", nativeQuery = true)
    List getexteam_leaderID_bygridid(String gridid);

    @Query(value = "select Getexcompanyservices_leadertel_bygridtel(?1) as tel", nativeQuery = true)
    List getexcompanyservices_leadertel_bygridtel(String gridtel);

    @Query(value = "select Getexcompanyservices_leadertel_bygridid(?1) as tel", nativeQuery = true)
    List getexcompanyservices_leadertel_bygridid(String gridid);

    @Query(value = "select Getexcompany_leadertel_bygridtel(?1) as tel", nativeQuery = true)
    List getexcompany_leadertel_bygridtel(String gridtel);

    @Query(value = "select Getexcompany_leadertel_bygridid(?1) as tel", nativeQuery = true)
    List getexcompany_leadertel_bygridid(String gridid);
}
