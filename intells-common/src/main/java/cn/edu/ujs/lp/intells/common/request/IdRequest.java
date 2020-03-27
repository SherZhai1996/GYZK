package cn.edu.ujs.lp.intells.common.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class IdRequest {

    @NotBlank(message = "编号非空")
    private String id;
}
