package cn.iocoder.yudao.module.wuyou.controller.admin.collect.vo;

import cn.iocoder.yudao.module.wuyou.dal.dataobject.producturl.ProductUrlDO;
import lombok.Data;

import java.util.List;

@Data
public class CollectData {
    //0-page  1-detail
    private Integer type;
    //采集url
    private String url;
    //当前页数
    private Integer currentPage;

    private List<String> productList;
}
