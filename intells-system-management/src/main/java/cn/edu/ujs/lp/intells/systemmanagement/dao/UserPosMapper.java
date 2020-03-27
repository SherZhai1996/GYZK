package cn.edu.ujs.lp.intells.systemmanagement.dao;

import cn.edu.ujs.lp.intells.systemmanagement.entity.UserPosBrief;
import cn.edu.ujs.lp.intells.systemmanagement.request.UserPosPageRequest;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserPosMapper {

    List<Long> Rcount(UserPosPageRequest request);
    Page<UserPosBrief> page(UserPosPageRequest request, RowBounds bounds);
}
