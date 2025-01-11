package cn.iocoder.yudao.framework.common.pojo;

import lombok.Data;

@Data
public class CookieResponse {
    private String cookie;
    private Boolean isSuccess;
    private String message;
}
