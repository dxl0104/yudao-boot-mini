package cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo;

import cn.iocoder.yudao.framework.common.pojo.CookieDatedRes;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImportSuccessRes {
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6221")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "url", example = "https://www.iocoder.cn")
    @ExcelProperty("url")
    private String url;
}
