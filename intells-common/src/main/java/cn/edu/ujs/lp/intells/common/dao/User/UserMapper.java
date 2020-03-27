package cn.edu.ujs.lp.intells.common.dao.User;

import cn.edu.ujs.lp.intells.common.entity.User.User;
import cn.edu.ujs.lp.intells.common.entity.User.UserBrief;
import cn.edu.ujs.lp.intells.common.entity.User.UserRoleBrief;
import cn.edu.ujs.lp.intells.common.request.User.UserPageRequest;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {

    List<Long> Rcount(UserPageRequest request);
    Page<UserBrief> page(UserPageRequest request, RowBounds bounds);
    List<UserBrief> list(UserPageRequest request);
    List<UserBrief> findByRole(@Param("hospId") String hospId, @Param("roleId") String roleId);
    List<User> getbyid(@Param("Id") String Id);
    List<User> findByName(@Param("hospId") String hospId, @Param("userName") String userName, @Param("userMobile") String userMobile, @Param("userLoginname") String userLoginname);
    void delete(@Param("userId") String userId);

    void deleteAtt(@Param("userId") String userId);
    void deleteUserAllRoles(@Param("userId") String userId);
    void deleteUserGrid(@Param("userId") String userId);
    void deleteExstaff_skillcertificate(@Param("userId") String userId);
    void deleteUserHosp(@Param("userId") String userId);

    void SetUserPassword(@Param("userId") String userId,@Param("password") String password);
    void SetUserRole(@Param("userId") String userId,@Param("roleId") String roleId);
    void DeleteUserRole(@Param("userId") String userId,@Param("roleId") String roleId);
    void DeleteUserExstaffRole(@Param("userId") String userId);
    void VerifyUserRoles(@Param("userId") String userId);
    void DeleteUserHospManageRole(@Param("userId") String userId,@Param("hospId") String hospId);
    void SetUserHospManageRole(@Param("userId") String userId,@Param("hospId") String hospId);
    List<UserRoleBrief> getUserRoles(@Param("userId") String userId,@Param("roleId") String roleId);
}
