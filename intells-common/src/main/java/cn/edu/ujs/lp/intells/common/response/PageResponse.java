package cn.edu.ujs.lp.intells.common.response;

import cn.edu.ujs.lp.intells.common.request.PageRequest;
import com.github.pagehelper.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页响应
 *
 * @author Meredith
 * @date 2019-09-17
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    /**
     * 当前页数
     */
    private int page;

    /**
     * 当前记录数
     */
    private int size;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 当前页数据
     */
    private List<T> list;

    /**
     * 构造函数
     * @param page
     * @param request
     */
    public PageResponse(Page<T> page, PageRequest request, Long total){
        this.size=request.getSize();
        this.page=request.getPage();
        this.list=page.getResult();
        this.total=total;

    }
}
