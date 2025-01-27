package cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 无忧基础数据 Response VO")
@Data
@ExcelIgnoreUnannotated
public class BasicDataExcelRespVO {

    @ExcelProperty("Product ID (EAN/UPC/ISBN/ISSN/Allegro Product ID)")
    @ColumnWidth(value = 20)
    private String productId;

    @ExcelProperty("标题")
    @ColumnWidth(value = 20)
    private String title;

    @ExcelProperty("Main Category")
    @ColumnWidth(value = 20)
    private String mainCategory1;

    @ExcelProperty("Offer Description")
    @ColumnWidth(value = 20)
    private String offerDescription;

    @ExcelProperty("价格")
    @ColumnWidth(value = 20)
    private String price;


    @ExcelProperty("图片")
    @ColumnWidth(value = 20)
    private String imgUrl;

    @Schema(description = "快递费")
    @ColumnWidth(value = 20)
    @ExcelProperty("快递费")
    private Integer delivery;


    @Schema(description = "url", example = "https://www.iocoder.cn")
    @ColumnWidth(value = 20)
    @ExcelProperty("产品链接")
    private String url;

    @ExcelProperty("Offer ID")
    @ColumnWidth(value = 20)
    private String offerId;

    @ExcelProperty("quantity")
    @ColumnWidth(value = 20)
    private String quantity;


//    @ExcelProperty("类目2")
//    private String mainCategory2;
//
//    @ExcelProperty("类目3")
//    private String mainCategory3;









}