package cn.iocoder.yudao.module.wuyou.dal.dataobject.deliveryempty;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 快递为零商品 DO
 *
 * @author 芋道源码
 */
@TableName("wuyou_delivery_empty")
@KeySequence("wuyou_delivery_empty_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryEmptyDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * url商品链接
     */
    private String url;
    /**
     * json数据
     */
    private String dataJson;
    /**
     * 快递费
     */
    private BigDecimal delivery;
    /**
     * 分类
     */
    private String category;
    /**
     * offerId
     */
    private String offerId;
    /**
     * ean
     */
    private String ean;
    /**
     * 标题
     */
    private String title;
    /**
     * productId
     */
    private String productId;
    /**
     * quantity
     */
    private String quantity;
    /**
     * price
     */
    private BigDecimal price;
    /**
     * currency
     */
    private String currency;
    /**
     * 一级分类
     */
    private String mainCategory1;
    /**
     * 二级分类
     */
    private String mainCategory2;
    /**
     * 三级分类
     */
    private String mainCategory3;
    /**
     * imgUrl
     */
    private String imgUrl;
    /**
     * 存到文件服务器上
     */
    private String offerDescription;
    /**
     * 商品主图
     */
    private String mainUrl;

}