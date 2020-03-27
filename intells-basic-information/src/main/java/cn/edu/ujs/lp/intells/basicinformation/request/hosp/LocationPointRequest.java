package cn.edu.ujs.lp.intells.basicinformation.request.hosp;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LocationPointRequest {
    /**
     *X坐标
     *
     */
    double x;

    /**
     * Y坐标
     */
    double y;
}
