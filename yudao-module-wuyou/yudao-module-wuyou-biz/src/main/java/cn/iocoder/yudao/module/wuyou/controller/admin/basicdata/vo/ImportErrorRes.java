package cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo;

import cn.iocoder.yudao.framework.common.pojo.CookieDatedRes;
import lombok.Data;

@Data
public class ImportErrorRes {
    private Long id;
    private String url;
    private CookieDatedRes cookieDatedRes;
}
