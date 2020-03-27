package cn.edu.ujs.lp.intells.common.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页请求基类
 *
 * @author Meredith
 * @date 2019-09-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest extends BaseRequest{

    /**
     * 当前页数，大于0，从1开始
     */
    private int page;

    /**
     * 每页记录数
     */
    private int size;

    /**
     * 排序字符串，排序方式（DESC等）
     */
    private String sort;

    /**
     * 获取索引，对应sql limit
     * @return
     */
    public int getIndex() {
        return (page <= 0 ? 0 : page - 1) * getSize();
    }

    /**
     * 计算每页记录数，默认10
     * @return
     */
    public int getSize() {
        return size <= 0 || size > 100 ? 10 : size;
    }
}
