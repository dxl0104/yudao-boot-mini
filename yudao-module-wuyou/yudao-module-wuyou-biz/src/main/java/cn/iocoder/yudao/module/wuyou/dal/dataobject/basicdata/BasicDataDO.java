package cn.iocoder.yudao.module.wuyou.dal.dataobject.basicdata;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 无忧基础数据 DO
 *
 * @author admin234
 */
@TableName("wuyou_basic_data")
@KeySequence("wuyou_basic_data_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicDataDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * url商品链接地址
     */
    private String url;
    /**
     * json数据(上传后的URL地址)
     */
    private String dataJson;

    /*
    * offerId
    * */
    private String offerId;

    /*
     * ean
     * */
    private String ean;

    /*
     * 商品标题
     * */
    private String title;

    /*
    * Product ID (EAN/UPC/ISBN/ISSN/Allegro Product ID)
    * */
    private String productId;
    /*
    * Quantity
    * */
    private String quantity;

    /*
     * 价格
     * */
    private String price;

    /*
     * 货币类型
     * */
    private String currency;

    /*
    * 主要商品一级类型
    * */
    private String mainCategory1;

    /*
     * 主要商品二级类型
     * */
    private String mainCategory2;

    /*
     * 主要商品三级类型
     * */
    private String mainCategory3;

    /*
     * imgUrl  商品全部图片
     * */
    private String imgUrl;

    /*
     * Offer Description
     * */
    private String offerDescription;

//    /*
//    * 已售(该产品该卖家最近30天)
//    * */
//    private String buyersQuantity;

//    /*
//     * 已售(该产品所有卖家最近30天)
//     * */
//    private String buyerspurchased;

    /**
     * 快递费
     */
    private Integer delivery;
    /**
     * 分类
     */
    private String category;

    /**
     * 租户编号
     */
    private Long tenantId;
    /**
     * 商品主图
     */
    private String mainUrl;


}