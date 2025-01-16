package cn.iocoder.yudao.framework.common.pojo;

import lombok.Data;

@Data
public class CookieResponse {
    private String cookies;
    private Boolean isSuccess;
    private String message;
}
