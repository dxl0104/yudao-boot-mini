package cn.iocoder.yudao.module.wuyou.controller.admin.producturl.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "管理后台 - 商品url列表新增/修改 Request VO")
@Data
public class ProductUrlBatchSaveReqVO {

    private List<String> productList;

}