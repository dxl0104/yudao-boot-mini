package cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo;

import lombok.Data;

@Data
public class BasicDataImportReqNewVO {

    //商品链接
    private String url;

    //商品详情整体
    private String json;

    private String delivery;
}
