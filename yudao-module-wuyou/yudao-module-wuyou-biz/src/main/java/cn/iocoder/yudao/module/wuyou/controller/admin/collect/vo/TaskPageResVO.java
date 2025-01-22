package cn.iocoder.yudao.module.wuyou.controller.admin.collect.vo;

import lombok.Data;

import java.util.List;

@Data
public class TaskPageResVO {
    private Integer category;
    private String url;
    private int minPageNum;
    private int maxPageNum;
    private List<String> urlList;
}
