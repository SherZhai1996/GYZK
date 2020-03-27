package cn.edu.ujs.lp.intells.basicinformation.request;

import cn.edu.ujs.lp.intells.common.request.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 封装ID请求参数
 *
 * @author Meredith
 * @date 2019-09-18
 */
@Data
public class IdRequest extends BaseRequest {

    /**
     * 编号
     */
    @NotBlank(message = "id不能为空")
    private String id;
}
