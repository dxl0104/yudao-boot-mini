package cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo;


import lombok.Data;

@Data
public class BasicDataReqVO {
    private String html;
    private String sourceUrl;
    private Integer sourcePlatform;
    private String delivery;
    private String cookie;
}