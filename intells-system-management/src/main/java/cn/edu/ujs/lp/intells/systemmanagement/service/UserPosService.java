package cn.edu.ujs.lp.intells.systemmanagement.service;

import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.systemmanagement.dao.UserPosMapper;
import cn.edu.ujs.lp.intells.systemmanagement.dao.UserPosRepository;
import cn.edu.ujs.lp.intells.systemmanagement.entity.UserPos;
import cn.edu.ujs.lp.intells.systemmanagement.entity.UserPosBrief;
import cn.edu.ujs.lp.intells.systemmanagement.request.UserPosPageRequest;
import cn.edu.ujs.lp.intells.systemmanagement.request.UserPosSaveRequest;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.utils.BeanUtils;
import com.github.pagehelper.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPosService {

    @Autowired
    private UserPosMapper userPosMapper;

    @Autowired
    private DBCommonService dbCommonService;

    @Autowired
    private UserPosRepository userPosRepository;

    public PageResponse<UserPosBrief> page(UserPosPageRequest request) throws Exception
    {
        try {
            Page<UserPosBrief> page = userPosMapper.page(request, new RowBounds(request.getIndex(), request.getSize()));
            List<Long> lst = userPosMapper.Rcount(request);
            Long total = 0L;
            if ((lst!=null) && (lst.size()>0))
                total = lst.get(0);

            return new PageResponse<UserPosBrief>(page, request, total);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("员工位置追踪信息分页查询失败："+e.getMessage());
        }
    }


    /**
     * 保存位置
     * @param hospId
     * @param request
     * @return
     * @throws Exception
     */
    public UserPos save(String hospId, UserPosSaveRequest request) throws Exception {

        UserPos userPos = null;

        if (userPosRepository != null) {
            userPos = UserPos
                    .builder()
                    .build();
        }

        if (userPos != null) {
            try {
                userPos.setHospID(hospId);
                BeanUtils.copyBeanIgnoreNull(userPos, request);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("复制位置对象失败:" + e.getMessage());
            }

            //保存在职员工对象
            try {
                if (userPos.getGridId().startsWith("WG"))
                    userPos.setGridId(dbCommonService.getGridIdbyCode(userPos.getGridId()));

                userPosRepository.save(userPos);

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("保存位置对象失败:" + e.getMessage());
            }
        }

        return userPos;
    }


}
