package cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BasicDataCalVO {
    //商品价格
    private BigDecimal finalProductPrice;
    //物流价格
    private BigDecimal finalShippingFee;
    //级别
    private Integer level;
}
