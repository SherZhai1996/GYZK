package cn.edu.ujs.lp.intells.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回对象封装
 *
 * @author Meredith
 * @date 2019-09-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResponse<T> {

    private Integer code=0;

    private String msg="";

    private T data;
    public T getData(){return data;}
    public Integer getCode(){return code;}

    public JsonResponse(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }


    @JsonIgnore //使之不在json序列化结果当中
    public boolean isSuccess() {
        return this.code== 0;
    }

    /**
     * 成功时的响应
     * @return
     */
    public static JsonResponse success(String msg){
        return new JsonResponse<String>(
                0,
                msg
        );
    }

    /**
     * 带返回参数的成功响应
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> JsonResponse success(String message,T data){
        return new JsonResponse<T>(
               0,
                message,
                data
        );
    }


    /**
     * 失败时的响应
     * @param code
     * @param message
     * @return
     */
    public static JsonResponse fail(Integer code,String message){
        return new JsonResponse<String>(
                code,
                message
        );
    }
}
