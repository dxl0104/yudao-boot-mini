package cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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
    private Integer delivery;

    @ExcelProperty("Product ID (EAN/UPC/ISBN/ISSN/Allegro Product ID)")
    private String productId;

    @ExcelProperty("Main Category")
    private String mainCategory1;

    @ExcelProperty("类目2")
    private String mainCategory2;

    @ExcelProperty("类目3")
    private String mainCategory3;

    @ExcelProperty("数量")
    private String quantity;

    @ExcelProperty("价格")
    private String price;

    @ExcelProperty("标题")
    private String title;

    @ExcelProperty("图片")
    private String imgUrl;

    @ExcelProperty("Offer Description")
    private String offerDescription;

    @ExcelProperty("货币类型")
    private String currency;


}