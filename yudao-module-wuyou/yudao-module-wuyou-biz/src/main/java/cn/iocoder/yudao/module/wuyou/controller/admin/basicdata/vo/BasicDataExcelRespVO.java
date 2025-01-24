package cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 无忧基础数据 Response VO")
@Data
@ExcelIgnoreUnannotated
public class BasicDataExcelRespVO {



    @Schema(description = "url", example = "https://www.iocoder.cn")
    @ExcelProperty("产品链接")
    private String url;

    @ExcelProperty("Offer ID")
    private String offerId;

    @Schema(description = "快递费")
    @ExcelProperty("快递费")
    private BigDecimal delivery;

    @ExcelProperty("Product ID (EAN/UPC/ISBN/ISSN/Allegro Product ID)")
    private String productId;


    private String title;

    private String mainUrl;

    private BigDecimal price;

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


}